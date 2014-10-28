package arrow.mems.bean;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;

import arrow.framework.exception.ServiceException;
import arrow.framework.faces.messages.Message;
import arrow.framework.faces.util.LabelKeySelectItem;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.framework.util.CollectionUtils;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.RememberSearchCriteria;
import arrow.mems.bean.data.SearchCriteria;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.MdeviceIdGenerator;
import arrow.mems.messages.MDMMessages;
import arrow.mems.persistence.dto.MDeviceDto;
import arrow.mems.persistence.dto.MdevChecklistDto;
import arrow.mems.persistence.dto.MdevChecklistItemDto;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.PartsList;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.SearchCondition;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.mapped.DocumentMapped_;
import arrow.mems.service.AdvanceSearchService;
import arrow.mems.service.DevicesManagementService;
import arrow.mems.service.ManagerUserService;
import arrow.mems.util.QueryByCompareOperatorUtils;
import arrow.mems.util.file.FileUtils;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class DevicesManagementBean extends AbstractApprovalBean {

  @PostConstruct
  public void init() {
    this.imageStreamBean.reset();
    // reset search condition
    this.listSearchConditions = new ArrayList<SearchCriteria>();
    this.listSearchConditions.add(new SearchCriteria());
    this.rememberSearchCriteria = new RememberSearchCriteria();
    this.listResultConditions = new ArrayList<SearchCondition>();
  }

  @Inject
  private UserCredential userCredential;
  @Inject
  private DevicesManagementService devicesManagerService;
  @Inject
  private AdvanceSearchService advanceSearchService;
  @Inject
  private ManagerUserService userService;
  @Inject
  private PartListDeviceMasterBean partlistDevMasterBean;
  @Inject
  private MasterDeviceChecklistManageBean checklistMasterBean;
  @Inject
  private ManagementDocumentOfMasterDeviceBean documentOfMasterDeviceBean;
  @Inject
  private ScheduleBean scheduleBean;
  @Inject
  private MasterDeviceChecklistManageBean masterDeviceChecklistBean;
  @Inject
  private ImageStreamBean imageStreamBean;

  // Fields : add new [+] condition to search
  private int pullDownConditionSearch;
  private String defaultConditionSearch;
  private int[] pullDownStoreKeyCondition;
  private String[] conditionSearch;

  private String manufactoryValue;
  private String genaralValue;
  private String nameValue;

  private List<MDevice> listSearchDevices;
  private MDevice selectedDevice;

  // For MDM02_01
  private MDeviceDto currentDevice;
  private List<Office> listCheckListOffcice;
  private List<MDevice> listPartsList;
  private List<Document> listDocument;
  private List<MtCountry> listCountries;
  private List<MdevChecklistDto> listChecklist;
  private UploadedFile file;


  // For MDM02_01 : Variable to set int autocomplete input
  private Office selectedManufacturer;
  private Person selectedManufacturerContact;
  private Office selectedSoldSupplier;
  private Person selectedSoldSupplierContact;

  // For MDM02_01 : Variable to set draft when click edit part list, document, check list
  private PartsList currentPartsList;
  private Document currentDocument;
  private MdevChecklist currentMdevChecklist;

  private int tabIndex;

  private List<SelectItem> searchDeviceConditionTypes;
  private List<SearchCriteria> listSearchConditions;
  private RememberSearchCriteria rememberSearchCriteria;
  private List<SearchCondition> listResultConditions;

  public void addNewCondition() {
    this.getListSearchConditions().add(new SearchCriteria());
  }

  public void removeCondition(SearchCriteria cond) {
    this.getListSearchConditions().remove(cond);
  }

  public void searchDevices() {

    this.listSearchDevices =
        this.devicesManagerService.searchDevicesSync(this.manufactoryValue, this.genaralValue, this.nameValue, this.getListSearchConditions(),
            this.userCredential.getUserInfo().getOfficeCode(), this.rememberSearchCriteria);
    if (this.listSearchDevices.size() == 0) {
      MDMMessages.MDM00001().show();
    }
  }

  public void saveMdevice() {
    try {
      this.saveInfoDevice();
    } catch (ServiceException | IOException e) {
      super.log.debug(e.getMessage(), e);
    }
  }

  public boolean saveInfoDevice() throws IOException, ServiceException {
    if (this.setValueOfDeviceToUpdate() == null) {
      MDMMessages.MDM00008().show();
      return false;
    }
    MDevice savedDevice = new MDevice();
    if (this.currentDevice.getMdevId() > 0) {
      // update
      savedDevice = this.devicesManagerService.getDeviceFromPK(this.currentDevice);
      BeanCopier.copy(this.currentDevice, savedDevice);
    } else {
      // add new
      BeanCopier.copy(this.currentDevice, savedDevice);
      final MdeviceIdGenerator generatorCode = new MdeviceIdGenerator(LocalDateTime.now().getYear());
      savedDevice.setMdevCode(generatorCode.getNext());
    }
    // this.listPartsList

    // add list document
    final List<Document> listDeletingDocuments = this.documentOfMasterDeviceBean.getDeletingDocuments();
    final List<Document> listNewDocuments = this.documentOfMasterDeviceBean.getListNewDocuments();
    final List<Document> listModifiedDocuments = this.documentOfMasterDeviceBean.getListModifiedDocuments();

    // Validate require software revision must be not empty
    if (this.validateSoftwareRevision(listNewDocuments, listModifiedDocuments, listDeletingDocuments, this.currentDevice)) {
      MDMMessages.MDM00010().show();
      return false;
    }

    final ServiceResult<MDevice> result =
        this.devicesManagerService.saveMasterDevice(savedDevice, this.partlistDevMasterBean.getListDeletedPartlist(),
            this.partlistDevMasterBean.getListNewPartlist(), this.checklistMasterBean.getChecklists(),
            this.checklistMasterBean.getDeletedChecklistTemp(), listNewDocuments, listDeletingDocuments, listModifiedDocuments,
            this.scheduleBean.getListMasterSchedule(), this.scheduleBean.getListDeletedSchedule(), this.scheduleBean.getListMasterScheduleAlert(),
            this.scheduleBean.getListDeletedScheduleAlert(), this.userCredential.getUserId());

    if (result.isSuccess()) {
      result.showMessages();
      this.currentDevice = new MDeviceDto();
      BeanCopier.copy(result.getData(), this.currentDevice);
      // reset data with saved device
      this.loadData(this.currentDevice);
      return true;
    } else
      throw new ServiceException("Failed When Call Save Master Device", result.getErrors());
  }

  public boolean validateSoftwareRevision(List<Document> listNewDocuments, List<Document> listModifiedDocuments,
      List<Document> listDeletingDocuments, MDeviceDto currentDevice) {
    if (currentDevice.isPersisted()) {
      // Edit case
      if (CollectionUtils.isEmpty(listNewDocuments) && CollectionUtils.isEmpty(listModifiedDocuments) && CollectionUtils
          .isEmpty(listDeletingDocuments))
        return false;
      if (CollectionUtils.isNotEmpty(listModifiedDocuments) || CollectionUtils.isNotEmpty(listNewDocuments))
        return false;
      // Find old document
      final List<Document> oldListDocs =
          this.devicesManagerService.findAllEntity(Document.class, DocumentMapped_.mdevCode, currentDevice.getMdevCode());
      if (CollectionUtils.isNotEmpty(oldListDocs) && CollectionUtils.isNotEmpty(listDeletingDocuments)) {
        if (oldListDocs.size() == listDeletingDocuments.size())
          return true;
        return false;
      }
    }
    if (CollectionUtils.isEmpty(listNewDocuments))
      return true;
    int flag = 0;
    if (CollectionUtils.isNotEmpty(listNewDocuments)) {
      for (final Document document : listNewDocuments) {
        if (ArrowStrUtils.isNotEmpty(document.getSoftwareRev())) {
          flag++;
          break;
        }
      }
    }
    if (flag != 0)
      return false;
    return true;
  }

  public void deleteMasterDevice() {
    if (this.currentDevice.getMdevCode() != null) {
      final ServiceResult<Message> result = this.devicesManagerService.deleteMasterDevice(this.currentDevice.getMdevCode(), CommonConstants.DELETE);
      if (result.isSuccess()) {
        this.searchDevices();
        this.resetData();
      }
      result.showMessages();
    } else {
      MDMMessages.MDM00007().show();
    }
    this.resetData();
  }

  public void openToEditDevice() {
    this.currentDevice = new MDeviceDto();
    if (this.selectedDevice != null) {
      BeanCopier.copy(this.selectedDevice, this.currentDevice);
      this.currentDevice.setCheckedAt(this.selectedDevice.getCheckedAt());
      this.currentDevice.setCheckedBy(this.selectedDevice.getCheckedBy());

      // init data
      this.loadData(this.currentDevice);
    } else {
      MDMMessages.MDM00007().show();
    }
  }

  public void resetData() {
    this.currentDevice = null;
    this.partlistDevMasterBean.resetAll();
    this.checklistMasterBean.setChecklistItems(null);
    this.checklistMasterBean.setChecklists(null);
    this.scheduleBean.resetAll();
    this.scheduleBean.resetAllScheAlert();
    this.documentOfMasterDeviceBean.resetAll();
    this.tabIndex = 0;
  }

  public void clearAndMarkNew() {
    this.addNewDeviceAction();
  }

  public MDeviceDto setValueOfDeviceToUpdate() {
    if (this.file != null) {
      if (this.file.getSize() != 0) {
        try {
          final byte[] content = FileUtils.getContentFile(this.file.getInputstream());
          this.file.getContents();
          this.currentDevice.setPicture(content);
          System.out.println(this.file.getFileName());
        } catch (final IOException e) {
          super.log.debug(e.getMessage(), e);
          return null;
        }
      }
    }
    return this.currentDevice;
  }

  public void handFileUpload(FileUploadEvent event) {
    this.file = event.getFile();
    try {
      final byte[] content = FileUtils.getContentFile(this.file.getInputstream());
      this.currentDevice.setPicture(content);
      this.imageStreamBean.setContentFile(content);
      this.imageStreamBean.setEditting(true);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  public List<MtCountry> filterCountry(final String query) {
    if (StringUtils.isEmpty(query))
      return this.getListCountries();

    return CollectionUtils.filter(this.getListCountries(), object -> {
      final MtCountry item = (MtCountry) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || String.valueOf(item.getCountryId()).contains(query);
    });
  }

  public String getNameOfCreatedBy(MDevice device) {
    final Users userResult = this.userService.getUserById(device.getCreatedBy());
    if (userResult == null)
      return null;
    else
      return userResult.getName();
  }

  public void toggleSelection(final MDevice device) {
    if (device.isSelected()) {
      if (this.selectedDevice != null) {
        this.selectedDevice.setSelected(false);
      }
      this.selectedDevice = device;
    } else {
      this.selectedDevice = null;
    }
  }

  public List<Office> filterSupplierAndManufacturer(final String query) {
    final List<Office> listOffices = this.getAvailableOffices();
    if (StringUtils.isEmpty(query))
      return listOffices;
    return CollectionUtils.filter(listOffices, object -> {
      final Office item = (Office) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || StringUtils.containsIgnoreCase(item.getOfficeCode(), query);
    });
  }

  public List<Person> filterSupplierContact(final String query) {
    final String supplierOfficeCode = this.currentDevice.getSupplierCode();
    return this.filterSupplierAndManufacturerContact(query, supplierOfficeCode);
  }

  public List<Person> filterManufacturerContact(final String query) {
    final String manufacturerCode = this.currentDevice.getManufacturerCode();
    return this.filterSupplierAndManufacturerContact(query, manufacturerCode);
  }

  public void addNewDeviceAction() {
    this.resetData();
    this.selectedManufacturer = null;
    this.selectedManufacturerContact = null;
    this.selectedSoldSupplier = null;
    this.selectedSoldSupplierContact = null;
    this.listChecklist = null;
    this.listPartsList = null;
    this.file = null;
    this.currentDevice = new MDeviceDto();

    // set default mdevType
    this.currentDevice.setMdevType(CommonConstants.MDevTypeConstants.MEDICAL_EQUIPMENT);
    if (this.userCredential.getPerson() != null)
      if (this.userCredential.getPerson().getAddress() != null) {
        this.currentDevice.setMtCountry(this.userCredential.getPerson().getAddress().getMtCountry());
      }
  }

  public int getPullDownConditionSearch() {
    return this.pullDownConditionSearch;
  }

  public void setPullDownConditionSearch(int pPullDownConditionSearch) {
    this.pullDownConditionSearch = pPullDownConditionSearch;
  }

  public String[] getConditionSearch() {
    return this.conditionSearch;
  }

  public void setConditionSearch(String[] pConditionSearch) {
    this.conditionSearch = pConditionSearch;
  }

  public int[] getPullDownStoreKeyCondition() {
    if (this.pullDownStoreKeyCondition == null) {
      this.pullDownStoreKeyCondition = new int[6];
    }
    return this.pullDownStoreKeyCondition;
  }

  public void setPullDownStoreKeyCondition(int[] pPullDownStoreKeyCondition) {
    this.pullDownStoreKeyCondition = pPullDownStoreKeyCondition;
  }

  public String getDefaultConditionSearch() {
    return this.defaultConditionSearch;
  }

  public void setDefaultConditionSearch(String pDefaultConditionSearch) {
    this.defaultConditionSearch = pDefaultConditionSearch;
  }

  public String getManufactoryValue() {
    return this.manufactoryValue;
  }

  public void setManufactoryValue(String pManufactoryValue) {
    this.manufactoryValue = pManufactoryValue;
  }


  public String getGenaralValue() {
    return this.genaralValue;
  }

  public void setGenaralValue(String pGenaralValue) {
    this.genaralValue = pGenaralValue;
  }

  public String getNameValue() {
    return this.nameValue;
  }

  public void setNameValue(String pNameValue) {
    this.nameValue = pNameValue;
  }

  public List<MDevice> getListSearchDevices() {
    return this.listSearchDevices;
  }

  public void setListSearchDevices(List<MDevice> pListSearchDevices) {
    this.listSearchDevices = pListSearchDevices;
  }

  public MDevice getSelectedDevice() {
    return this.selectedDevice;
  }

  public void setSelectedDevice(MDevice pSelectedDevice) {
    this.selectedDevice = pSelectedDevice;
  }

  public List<Office> getAvailableOffices() {
    if (this.listCheckListOffcice == null) {
      this.listCheckListOffcice =
          this.devicesManagerService.getAllOfficeByOwnedOfficeAndIsDeleted(this.userCredential.getUserInfo().getOfficeCode(), CommonConstants.ACTIVE);
    }
    return this.listCheckListOffcice;
  }

  public MDeviceDto getCurrentDevice() {
    return this.currentDevice;
  }

  public void setCurrentDevice(MDeviceDto pCurrentDevice) {
    this.currentDevice = pCurrentDevice;
  }


  public List<MDevice> getListPartsList() {
    if (this.listPartsList == null) {
      if (this.currentDevice.getMdevCode() != null) {
        this.listPartsList = this.devicesManagerService.getPartlistInfo(this.currentDevice.getMdevCode());
      }
    }
    return this.listPartsList;
  }

  public void setListPartsList(List<MDevice> pListPartsList) {
    this.listPartsList = pListPartsList;
  }

  public List<Document> getListDocument() {
    return this.listDocument;
  }

  public void setListDocument(List<Document> pListDocument) {
    this.listDocument = pListDocument;
  }


  public Office getSelectedSoldSupplier() {
    return this.selectedSoldSupplier;
  }

  public void setSelectedSoldSupplier(Office pSelectedSoldSupplier) {
    this.selectedSoldSupplier = pSelectedSoldSupplier;
  }

  public Person getSelectedSoldSupplierContact() {
    return this.selectedSoldSupplierContact;
  }

  public void setSelectedSoldSupplierContact(Person pSelectedSoldSupplierContact) {
    this.selectedSoldSupplierContact = pSelectedSoldSupplierContact;
  }

  public PartsList getCurrentPartsList() {
    return this.currentPartsList;
  }

  public void setCurrentPartsList(PartsList pCurrentPartsList) {
    this.currentPartsList = pCurrentPartsList;
  }

  public Document getCurrentDocument() {
    return this.currentDocument;
  }

  public void setCurrentDocument(Document pCurrentDocument) {
    this.currentDocument = pCurrentDocument;
  }

  public MdevChecklist getCurrentMdevChecklist() {
    return this.currentMdevChecklist;
  }

  public void setCurrentMdevChecklist(MdevChecklist pCurrentMdevChecklist) {
    this.currentMdevChecklist = pCurrentMdevChecklist;
  }

  public List<MtCountry> getListCountries() {
    if (this.listCountries == null) {
      this.listCountries = this.devicesManagerService.getCountry();
    }
    return this.listCountries;
  }

  public List<SearchCriteria> getListSearchConditions() {
    return this.listSearchConditions;
  }

  public void setListSearchConditions(List<SearchCriteria> pListSearchConditions) {
    this.listSearchConditions = pListSearchConditions;
  }

  public List<SearchCondition> getListResultConditions() {
    return this.listResultConditions;
  }

  public void setListResultConditions(List<SearchCondition> pListResultConditions) {
    this.listResultConditions = pListResultConditions;
  }

  public RememberSearchCriteria getRememberSearchCriteria() {
    return this.rememberSearchCriteria;
  }

  public void setRememberSearchCriteria(RememberSearchCriteria pRememberSearchCriteria) {
    this.rememberSearchCriteria = pRememberSearchCriteria;
  }


  public void setListCountries(List<MtCountry> pListCountries) {
    this.listCountries = pListCountries;
  }

  public UploadedFile getFile() {
    return this.file;
  }

  public void setFile(UploadedFile pFile) {
    this.file = pFile;
  }

  public Office getSelectedManufacturer() {
    if ((this.selectedManufacturer == null) && (this.currentDevice.getManufacturerOffice() != null)) {
      this.selectedManufacturer = this.currentDevice.getManufacturerOffice();
    }
    return this.selectedManufacturer;
  }

  public void setSelectedManufacturer(Office pSelectedManufacturer) {
    this.selectedManufacturer = pSelectedManufacturer;
  }

  public Person getSelectedManufacturerContact() {
    if ((this.selectedManufacturerContact == null) && (this.currentDevice.getManufacturerPerson() != null)) {
      this.selectedManufacturerContact = this.currentDevice.getManufacturerPerson();
    }
    return this.selectedManufacturerContact;
  }

  public void setSelectedManufacturerContact(Person pSelectedManufacturerContact) {
    this.selectedManufacturerContact = pSelectedManufacturerContact;
  }

  public List<MdevChecklistDto> getListChecklist() {
    if ((this.listChecklist == null) && (this.currentDevice.getMdevCode() != null)) {
      final List<MdevChecklist> initChecklist =
          this.devicesManagerService.getMdevChecklistByMdevcode(this.currentDevice.getMdevCode(), CommonConstants.ACTIVE);
      this.listChecklist = this.convertChecklistEntitytoDTO(initChecklist);
    }
    return this.listChecklist;
  }

  public List<MdevChecklistDto> convertChecklistEntitytoDTO(List<MdevChecklist> checklist) {
    final List<MdevChecklistDto> newChecklist = new ArrayList<MdevChecklistDto>();
    for (final MdevChecklist mdevChecklist : checklist) {
      final MdevChecklistDto tempChecklist = new MdevChecklistDto();
      BeanCopier.copy(mdevChecklist, tempChecklist);
      tempChecklist.setChecklistItems(this.setDefaultChecklistItemDto(mdevChecklist));
      newChecklist.add(tempChecklist);
    }
    return newChecklist;
  }

  public List<MdevChecklistItemDto> setDefaultChecklistItemDto(MdevChecklist checklist) {
    final List<MdevChecklistItemDto> newChecklistItemDto = new ArrayList<MdevChecklistItemDto>();
    final List<MdevChecklistItem> currentChecklistItem =
        this.devicesManagerService.getMdevChecklistItemByCklistCode(checklist.getCklistCode(), CommonConstants.ACTIVE);
    if (currentChecklistItem != null) {
      for (final MdevChecklistItem mdevChecklistItem : currentChecklistItem) {
        final MdevChecklistItemDto tempChecklist = new MdevChecklistItemDto();
        BeanCopier.copy(mdevChecklistItem, tempChecklist);
        newChecklistItemDto.add(tempChecklist);
      }
    }
    return newChecklistItemDto;
  }

  public void setListChecklist(List<MdevChecklistDto> pListChecklist) {
    this.listChecklist = pListChecklist;
  }

  public int getTabIndex() {
    return this.tabIndex;
  }

  public void setTabIndex(int pTabIndex) {
    this.tabIndex = pTabIndex;
  }

  public List<SelectItem> getSearchDeviceConditions() {
    if (this.searchDeviceConditionTypes == null) {
      this.searchDeviceConditionTypes = new ArrayList<>();
      this.searchDeviceConditionTypes.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_CONTACT_PERSON, "manufactureContactPerson"));
      this.searchDeviceConditionTypes.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_COUNTRY, "country"));
      this.searchDeviceConditionTypes.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_CATALOG, "catalog"));
      this.searchDeviceConditionTypes.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_SPECIFICATION, "specification"));
      this.searchDeviceConditionTypes.add(new LabelKeySelectItem(CommonConstants.PULLDOWN_MANUFACTORY_NOTICE, "notice"));
    }
    return this.searchDeviceConditionTypes;
  }

  public void handleSearchResultToggle(ToggleEvent event) {
    if (event.getVisibility() == Visibility.VISIBLE) {
      this.listResultConditions = this.advanceSearchService.searchConditionSync(this.userCredential);
    }
  }

  public void saveConditions() {

    final ServiceResult<SearchCondition> result =
        this.advanceSearchService.saveAdvanceConditions(QueryByCompareOperatorUtils.createNewConditions(this.rememberSearchCriteria,
            this.userCredential, this.listSearchConditions));
    this.listResultConditions.add(result.getData());
    result.showMessages();
  }

  public void filterByRecentConditions(SearchCondition condition) {

    this.listSearchDevices =
        this.devicesManagerService.searchDevicesSync(this.manufactoryValue, this.genaralValue, this.nameValue,
            QueryByCompareOperatorUtils.getListSearchCondition(condition), this.userCredential.getUserInfo().getOfficeCode(),
            QueryByCompareOperatorUtils.getRememberSearchCondition(condition));
    if (this.listSearchDevices.size() == 0) {
      MDMMessages.MDM00001().show();
    }
  }

  public void removeRecentConditions(SearchCondition condition) {
    this.advanceSearchService.removeAdvanceConditions(condition);
    this.listResultConditions.remove(condition);
  }


  private void loadData(MDeviceDto pCurrentDevice) {
    this.currentDevice = pCurrentDevice;
    // reset data with saved device
    this.documentOfMasterDeviceBean.resetAll();
    this.documentOfMasterDeviceBean.setMasterDeviceCode(this.currentDevice.getMdevCode());
    this.masterDeviceChecklistBean.resetAll();
    this.scheduleBean.resetAll();
    this.scheduleBean.resetAllScheAlert();
  }

  private List<Person> filterSupplierAndManufacturerContact(final String query, final String officeCode) {

    final List<Person> listPersons =
        this.devicesManagerService.getAllPersonByOwnedOfficeAndOfficeCodeAndIsDeleted(this.userCredential.getUserInfo().getOfficeCode(), officeCode,
            CommonConstants.ACTIVE);
    if (StringUtils.isEmpty(query))
      return listPersons;
    return CollectionUtils.filter(listPersons, object -> {
      final Person item = (Person) object;
      return StringUtils.containsIgnoreCase(item.getAddress().getAddress1(), query) || StringUtils.containsIgnoreCase(item.getPsnCode(), query);
    });
  }

  @Override
  public Object getEntityId() {
    return this.currentDevice.getMdevCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.MDEVICE;
  }

  @Override
  public String getItemLabel() {
    return this.currentDevice.getName();
  }

}
