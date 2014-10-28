package arrow.mems.bean;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.Visibility;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.framework.util.CollectionUtils;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.RememberSearchCriteria;
import arrow.mems.bean.data.SearchCriteria;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.constant.MemsDataType;
import arrow.mems.messages.MDMMessages;
import arrow.mems.messages.MPMMessages;
import arrow.mems.persistence.dto.DeviceDto;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.entity.MtCurrency;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.SearchCondition;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.DocumentRepository;
import arrow.mems.persistence.repository.MtCurrencyRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.service.AdvanceSearchService;
import arrow.mems.service.DeviceManagementService;
import arrow.mems.util.QueryByCompareOperatorUtils;
import arrow.mems.util.file.FileUtils;

@Named
@ViewScoped
public class DeviceManagementBean extends AbstractApprovalBean {

  @PostConstruct
  public void init() {
    this.listSearchConditions = new ArrayList<SearchCriteria>();
    this.listSearchConditions.add(new SearchCriteria());
    this.rememberSearchCriteria = new RememberSearchCriteria();
    this.listResultConditions = new ArrayList<SearchCondition>();
  }

  @Inject
  private UserCredential userCredential;
  @Inject
  private DeviceManagementService deviceManagerService;
  @Inject
  private AdvanceSearchService advanceSearchService;
  @Inject
  private PartListDeviceMasterBean partlistDevMasterBean;
  @Inject
  private MasterDeviceChecklistManageBean checklistMasterBean;
  @Inject
  private PersonRepository personRepo;
  @Inject
  private DocumentRepository documentRepo;
  @Inject
  private MtCurrencyRepository currencyRepo;
  @Inject
  private UsersRepository userRepo;
  @Inject
  private DeviceRepository deviceRepo;
  @Inject
  private ImageStreamBean imageBean;

  private static final int IS_HAVE_NO_SERIAL = 1;
  private static final int IS_HAVE_SERIAL = 0;

  /** General search field */
  private String manufactoryValue;
  private String genaralValue;
  private String nameValue;

  /** List info of devices */
  private List<Device> listSearchDevice;
  private Device selectedDevice;
  private DeviceDto currentDevice;
  private List<Document> listDocument;
  private List<Device> listDevice;
  private List<Person> listPerson;

  /** Data for auto complete */
  private Office selectedManufacturer;
  private Person selectedManufacturerContact;
  private Office selectedSoldSupplier;
  private Person selectedSoldSupplierContact;

  /** Field device type */
  private boolean isMedicalEquipment = true;
  private boolean isPart = true;
  private boolean isMeasureDevice = true;

  /** Flag collapse */
  private boolean isCollapseResultCondititonPanel = true;

  /** Advance condition variables */
  private RememberSearchCriteria rememberSearchCriteria;
  private List<SearchCondition> listResultConditions;
  private List<SelectItem> searchDeviceConditionTypes;
  private List<SearchCriteria> listSearchConditions;

  /** Upload file variables */
  private UploadedFile locationImage;
  private UploadedFile image;
  //
  private MtCurrency currency;
  private Device partOfDevice;
  private String currentDeviceKey;
  private boolean isHaveNoSerial;

  // Set&Get deviceCode from QRcode screen
  private String deviceCode;

  public String getDeviceCode() {
    return this.deviceCode;
  }

  public void setDeviceCode(String pDeviceCode) {
    this.deviceCode = pDeviceCode;
    if (StringUtils.isNotEmpty(this.deviceCode)) {
      this.selectedDevice = this.deviceRepo.findActiveDeviceByDeviceCode(this.deviceCode).getAnyResult();
      this.currentDevice = new DeviceDto();
      BeanCopier.copy(this.selectedDevice, this.currentDevice);
    }
  }

  public void eventChangeMDevice() {
    if (this.getCurrentDevice().getSoftwareRevison() != null) {
      this.getCurrentDevice().setSoftwareRevison(null);
    }
    if (this.getCurrentDevice().getTargetDevice() != null) {
      this.getCurrentDevice().setTargetDevice(null);
    }
  }

  public void eventChangeSupplierOffice() {
    if (this.getCurrentDevice().getSupplierOffice() != null) {
      this.getCurrentDevice().setSupplierPerson(null);
    }
  }

  public void eventChangeRepairOffice() {
    if (this.getCurrentDevice().getRepairedByOffice() != null) {
      this.getCurrentDevice().setRepairedPerson(null);
    }
  }

  public void toggleSelection(final Device device) {
    if (device.isSelected()) {
      if (this.selectedDevice != null) {
        this.selectedDevice.setSelected(false);
      }
      this.selectedDevice = device;
    } else {
      this.selectedDevice = null;
    }
  }

  public List<Document> filterDocument(String query) {
    List<Document> listDocument = new ArrayList<Document>();
    String mDevCode = null;
    if (this.getCurrentDevice() != null) {
      mDevCode = this.getCurrentDevice().getMDevice().getMdevCode();
    }
    if (mDevCode != null)
      if (StringUtils.isEmpty(query)) {
        if (StringUtils.isEmpty(query) && StringUtils.isEmpty(mDevCode)) {
          listDocument = this.getListDocument(mDevCode);
        } else {
          listDocument = CollectionUtils.filter(this.getListDocument(mDevCode), arg0 -> {
            final Document item = (Document) arg0;
            return StringUtils.containsIgnoreCase(item.getSoftwareRev(), query);
          });
        }
      }
    return listDocument;
  }

  public List<Device> filterDeviceInDevice(String query) {
    List<Device> listDevice = new ArrayList<Device>();
    if (StringUtils.isEmpty(query)) {
      listDevice = this.getListDevice();
    } else {
      listDevice = CollectionUtils.filter(this.getListDevice(), arg0 -> {
        final Device item = (Device) arg0;
        return StringUtils.containsIgnoreCase(item.getMDevice().getName(), query);
      });
    }
    return listDevice;
  }

  public List<Person> filterSupplierPerson(String query) {
    String officeCode = null;
    if (this.getCurrentDevice() != null) {
      officeCode = this.getCurrentDevice().getSupplierOffice().getOfficeCode();
    }
    if (officeCode != null) {
      final List<Person> listPerson = this.getListPerson(officeCode);
      return this.filterListPersonByQuery(listPerson, query);
    }
    return Collections.emptyList();
  }

  public List<Person> filterRepairedPerson(String query) {
    String officeCode = null;
    if (this.getCurrentDevice() != null) {
      officeCode = this.getCurrentDevice().getRepairedByOffice().getOfficeCode();
    }
    if (officeCode != null) {
      final List<Person> listPerson = this.getListPerson(officeCode);
      return this.filterListPersonByQuery(listPerson, query);
    }
    return Collections.emptyList();
  }

  public List<Person> getListPerson(String officeCode) {
    final List<Integer> listUsersInOneOffice = this.userRepo.findUserInOneOffice(this.userCredential.getUserInfo().getOfficeCode());
    this.listPerson = this.personRepo.findAllActivePersonInOneOffice(officeCode, listUsersInOneOffice);
    return this.listPerson;

  }

  public List<Device> getListDevice() {
    final List<Integer> listUsersInOneOffice = this.userRepo.findUserInOneOffice(this.userCredential.getUserInfo().getOfficeCode());
    this.listDevice = this.deviceRepo.findAllAvaiableDevice(listUsersInOneOffice).getResultList();
    return this.listDevice;
  }

  public List<Document> getListDocument(String mDeviceCode) {
    final List<Integer> listUsersInOneOffice = this.userRepo.findUserInOneOffice(this.userCredential.getUserInfo().getOfficeCode());
    this.listDocument = this.documentRepo.findAllDocumentByMDevice(mDeviceCode, listUsersInOneOffice).getResultList();
    return this.listDocument;
  }

  public void deleteDevice() {
    if (this.selectedDevice.isSelected()) {
      this.deviceManagerService.deleteDevice(this.selectedDevice);
    }
    this.cleanStage();
  }

  public void searchDevice() {
    this.listSearchDevice =
        this.deviceManagerService.searchListDevice(this.manufactoryValue, this.genaralValue, this.nameValue, this.getListSearchConditions(),
            this.userCredential.getUserInfo().getOfficeCode(), this.getDeviceTypeCondition(), this.rememberSearchCriteria);
    if (this.listSearchDevice.size() == 0) {
      MDMMessages.MDM00001().show();
    }
  }

  public void resetSearch() {

    this.listSearchConditions = new ArrayList<SearchCriteria>();
    this.listSearchConditions.add(new SearchCriteria());
    this.rememberSearchCriteria = new RememberSearchCriteria();
    this.listResultConditions = new ArrayList<>();
    this.listSearchDevice = null;
    this.selectedManufacturer = null;
    this.selectedManufacturerContact = null;
    this.selectedSoldSupplier = null;
    this.selectedSoldSupplierContact = null;
    this.manufactoryValue = null;
    this.genaralValue = null;
    this.nameValue = null;
    this.isMedicalEquipment = true;
    this.isPart = true;
    this.isMeasureDevice = true;
    this.isCollapseResultCondititonPanel = true;

    if (this.selectedDevice != null) {
      this.selectedDevice = null;
    }
    this.backToSearchScreen();
  }

  public void cleanStage() {

    this.setHaveNoSerial(false);
    this.partOfDevice = null;
    this.isMedicalEquipment = true;
    this.isPart = true;
    this.isMeasureDevice = true;
    this.locationImage = null;
    this.image = null;
    this.currency = null;
    this.currentDevice = null;
    this.listSearchDevice = null;

    if (this.selectedDevice != null) {
      this.selectedDevice = null;
    }
  }

  public void backToSearchScreen() {
    this.currentDevice = null;
    this.partlistDevMasterBean.setListInfoPartlistTemp(null);
    this.partlistDevMasterBean.setListDeletedPartlist(null);
    this.partlistDevMasterBean.setListSearchInfoPartlist(null);
    this.checklistMasterBean.setChecklistItems(null);
    this.checklistMasterBean.setChecklists(null);
  }

  public void editDevice() {
    final ServiceResult<DeviceDto> result = this.deviceManagerService.prepareEditDevice(this.getSelectedDevice());
    if (result.isSuccess()) {
      this.currentDevice = result.getData();

      this.currentDeviceKey = this.currentDevice.getDevCode();

      if (this.currentDevice.getPCcy() != null) {
        this.setCurrency(this.currencyRepo.findAllCurrencyInOneOffice(this.currentDevice.getPCcy()).getAnyResult());
      }

      if (this.currentDevice.getNoSerialConfirm() == DeviceManagementBean.IS_HAVE_NO_SERIAL) {
        this.setHaveNoSerial(true);
      } else if (this.currentDevice.getNoSerialConfirm() == DeviceManagementBean.IS_HAVE_SERIAL) {
        this.setHaveNoSerial(false);
      }
    } else {
      this.currentDevice = null;
    }
  }

  public void saveDevice() {
    if ((!this.isHaveNoSerial) && StringUtils.isEmpty(this.currentDevice.getSerialNo())) {
      MPMMessages.MPM00015().show();
      return;
    }
    if ((this.currentDevice.getAcceptanceDate() != null) && (this.currentDevice.getExpireDate() != null)) {
      if (!this.isValidDateRange(this.currentDevice.getAcceptanceDate(), this.currentDevice.getExpireDate())) {
        MPMMessages.MPM00009().show();
        return;
      }
    }
    if (this.isHaveNoSerial) {
      this.currentDevice.setNoSerialConfirm(DeviceManagementBean.IS_HAVE_NO_SERIAL);
      this.currentDevice.setSerialNo(null);
    } else {
      this.currentDevice.setNoSerialConfirm(DeviceManagementBean.IS_HAVE_SERIAL);
    }
    if (this.locationImage != null) {
      this.currentDevice.setLocationImage(this.imageBean.getDeviceLocationImage(this.currentDeviceKey));
    }
    if (this.image != null) {
      this.currentDevice.setImageFile(this.imageBean.getDeviceImage(this.currentDeviceKey));
    }
    if (this.currency != null) {
      this.currentDevice.setPCcy(this.currency.getCcyId());
    }

    final ServiceResult<Device> result = this.deviceManagerService.saveDevice(this.currentDevice);
    if (result.isSuccess()) {
      result.showMessages();
    }
    this.cleanStage();
  }

  public boolean isValidDateRange(LocalDate startDate, LocalDate endDate) {
    boolean isValid = true;
    if (startDate.isAfter(endDate)) {
      isValid = false;
    }
    return isValid;
  }

  public void closeUpdateDevice(ActionEvent ae) {
    this.currentDevice = null;
    if (this.selectedDevice != null) {
      this.selectedDevice = null;
    }
    this.resetStage(ae);
  }

  public boolean isEnableUploadImage() {
    return this.currentDevice.getMDevice() != null;
  }

  public boolean isEnableChooseSofwareRevision() {
    return this.currentDevice.getMDevice() != null;
  }

  public boolean isEnableChooseSupplierContact() {
    return this.currentDevice.getSupllierOffice() != null;
  }

  public boolean isEnableChooseRepairedContact() {
    return this.currentDevice.getRepairedByOffice() != null;
  }

  public boolean isHaveNoSerial() {
    return this.isHaveNoSerial;
  }

  public void setHaveNoSerial(boolean pIsHaveNoSerial) {
    this.isHaveNoSerial = pIsHaveNoSerial;
  }

  public boolean isMedicalEquipment() {
    return this.isMedicalEquipment;
  }

  public void setMedicalEquipment(boolean pIsMedicalEquipment) {
    this.isMedicalEquipment = pIsMedicalEquipment;
  }

  public boolean isPart() {
    return this.isPart;
  }

  public void setPart(boolean pIsPart) {
    this.isPart = pIsPart;
  }

  public boolean isMeasureDevice() {
    return this.isMeasureDevice;
  }

  public void setMeasureDevice(boolean pIsMeasureDevice) {
    this.isMeasureDevice = pIsMeasureDevice;
  }

  public List<Device> getListSearchDevice() {
    if (this.userCredential.isLoggedIn() && (this.listSearchDevice == null)) {
      this.listSearchDevice =
          this.deviceManagerService.searchListDevice(this.manufactoryValue, this.genaralValue, this.nameValue, this.getListSearchConditions(),
              this.userCredential.getUserInfo().getOfficeCode(), this.getDeviceTypeCondition(), this.rememberSearchCriteria);
    }
    return this.listSearchDevice;
  }

  public void setListSearchDevice(List<Device> pListSearchDevice) {
    this.listSearchDevice = pListSearchDevice;
  }

  public DeviceDto getCurrentDevice() {
    return this.currentDevice;
  }

  public void setCurrentDevice(DeviceDto pCurrentDevice) {
    this.currentDevice = pCurrentDevice;
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

  public Office getSelectedManufacturer() {
    return this.selectedManufacturer;
  }

  public void setSelectedManufacturer(Office pSelectedManufacturer) {
    this.selectedManufacturer = pSelectedManufacturer;
  }

  public Person getSelectedManufacturerContact() {
    return this.selectedManufacturerContact;
  }

  public void setSelectedManufacturerContact(Person pSelectedManufacturerContact) {
    this.selectedManufacturerContact = pSelectedManufacturerContact;
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

  public UploadedFile getLocationImage() {
    return this.locationImage;
  }

  public void setLocationImage(UploadedFile pLocationImage) {
    this.locationImage = pLocationImage;
  }

  public UploadedFile getImage() {
    return this.image;
  }

  public void setImage(UploadedFile pImage) {
    this.image = pImage;
  }

  public void fileLocationImageUploadListener(FileUploadEvent e) throws IOException {
    this.locationImage = e.getFile();
    final byte[] content = FileUtils.getContentFile(this.locationImage.getInputstream());
    this.imageBean.uploadDeviceLocationImageTemporary(this.currentDeviceKey, content);

  }

  public String getCurrentDeviceKey() {
    return this.currentDeviceKey;
  }

  public void setCurrentDeviceKey(String pCurrentDeviceKey) {
    this.currentDeviceKey = pCurrentDeviceKey;
  }

  public void fileImageUploadListener(FileUploadEvent e) throws IOException {
    this.image = e.getFile();
    final byte[] content = FileUtils.getContentFile(this.image.getInputstream());
    this.imageBean.uploadDeviceImageTemporary(this.currentDeviceKey, content);
  }



  public Device getPartOfDevice() {
    return this.partOfDevice;
  }

  public void setPartOfDevice(Device pPartOfDevice) {
    this.partOfDevice = pPartOfDevice;
  }

  public MtCurrency getCurrency() {
    return this.currency;
  }

  public void setCurrency(MtCurrency pCurrency) {
    this.currency = pCurrency;
  }

  public Device getSelectedDevice() {
    return this.selectedDevice;
  }

  public void setSelectedDevice(Device pSelectedDevice) {
    this.selectedDevice = pSelectedDevice;
  }

  public boolean isEnableEditOrDelete() {
    return this.selectedDevice != null;
  }

  public void setCollapseResultCondititonPanel(boolean pIsCollapseResultCondititonPanel) {
    this.isCollapseResultCondititonPanel = pIsCollapseResultCondititonPanel;
  }

  public List<SelectItem> getSearchDeviceConditionTypes() {
    return this.searchDeviceConditionTypes;
  }

  public void setSearchDeviceConditionTypes(List<SelectItem> pSearchDeviceConditionTypes) {
    this.searchDeviceConditionTypes = pSearchDeviceConditionTypes;
  }

  public List<SearchCriteria> getListSearchConditions() {
    return this.listSearchConditions;
  }

  public void setListSearchConditions(List<SearchCriteria> pListSearchConditions) {
    this.listSearchConditions = pListSearchConditions;
  }

  public boolean isCollapseResultCondititonPanel() {
    return this.isCollapseResultCondititonPanel;
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

  public void saveConditions() {

    final ServiceResult<SearchCondition> result =
        this.advanceSearchService.saveAdvanceConditions(QueryByCompareOperatorUtils.createNewConditions(this.rememberSearchCriteria,
            this.userCredential, this.listSearchConditions));
    this.listResultConditions.add(result.getData());
    result.showMessages();
  }


  public void handleSearchResultToggle(ToggleEvent event) {
    if (event.getVisibility() == Visibility.VISIBLE) {
      this.listResultConditions = this.advanceSearchService.searchConditionSync(this.userCredential);
    }
  }

  public void filterByRecentConditions(SearchCondition condition) {

    this.listSearchDevice =
        this.deviceManagerService.searchListDevice(this.manufactoryValue, this.genaralValue, this.nameValue,
            QueryByCompareOperatorUtils.getListSearchCondition(condition), this.userCredential.getUserInfo().getOfficeCode(),
            this.getDeviceTypeCondition(), QueryByCompareOperatorUtils.getRememberSearchCondition(condition));
    if (this.listSearchDevice.size() == 0) {
      MDMMessages.MDM00001().show();
    }
  }

  public void removeRecentConditions(SearchCondition condition) {
    this.advanceSearchService.removeAdvanceConditions(condition);
    this.listResultConditions.remove(condition);
  }

  public void addNewDevice() {
    this.currentDevice = new DeviceDto();
    this.currentDeviceKey = UUID.randomUUID().toString();
    this.isHaveNoSerial = false;
  }

  public void addNewCondition() {
    this.getListSearchConditions().add(new SearchCriteria());
  }

  public void removeCondition(SearchCriteria cond) {
    this.getListSearchConditions().remove(cond);
  }

  private List<Person> filterListPersonByQuery(List<Person> listPerson, String query) {
    if (StringUtils.isEmpty(query))
      return listPerson;
    else
      return CollectionUtils.filter(listPerson, arg0 -> {
        final Person item = (Person) arg0;
        return StringUtils.containsIgnoreCase(item.getName(), query);
      });
  }

  private List<Integer> getDeviceTypeCondition() {
    final List<Integer> deviceTypeCond = new ArrayList<Integer>();
    if (this.isMedicalEquipment) {
      deviceTypeCond.add(CommonConstants.MDevTypeConstants.MEDICAL_EQUIPMENT);
    }
    if (this.isMeasureDevice) {
      deviceTypeCond.add(CommonConstants.MDevTypeConstants.MEASUREMENT_DEVICE);
    }
    if (this.isPart) {
      deviceTypeCond.add(CommonConstants.MDevTypeConstants.PARTSLIST);
    }
    return deviceTypeCond;
  }

  @Override
  public Object getEntityId() {
    return this.currentDevice.getDevCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.DEVICE;
  }

  @Override
  public String getItemLabel() {
    return this.currentDevice.getSerialNo();
  }

}
