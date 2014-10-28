package arrow.mems.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.persistence.util.Condition.Junction;
import arrow.framework.util.BeanCopier;
import arrow.framework.validator.ArrowValidator;
import arrow.mems.bean.data.RememberSearchCriteria;
import arrow.mems.bean.data.SearchCriteria;
import arrow.mems.constant.CommonConstants;
import arrow.mems.constant.DatabaseConstants;
import arrow.mems.generator.AlertByCountIdGenerator;
import arrow.mems.generator.MdevChecklistIdGenerator;
import arrow.mems.generator.MdevChecklistItemIdGenerator;
import arrow.mems.generator.ScheduleIdGenerator;
import arrow.mems.helper.UploadHelper;
import arrow.mems.messages.MAUMessages;
import arrow.mems.messages.MDMMessages;
import arrow.mems.persistence.dto.AlertByCountDto;
import arrow.mems.persistence.dto.DocumentDto;
import arrow.mems.persistence.dto.MDeviceDto;
import arrow.mems.persistence.dto.MdevChecklistDto;
import arrow.mems.persistence.dto.MdevChecklistItemDto;
import arrow.mems.persistence.dto.ScheduleDto;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.AlertByCount;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.PartsList;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.Schedule;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.mapped.AbstractDeletableMapped_;
import arrow.mems.persistence.repository.AlertByCountRepository;
import arrow.mems.persistence.repository.DocumentRepository;
import arrow.mems.persistence.repository.MDeviceRepository;
import arrow.mems.persistence.repository.MdevChecklistItemRepository;
import arrow.mems.persistence.repository.MdevChecklistRepository;
import arrow.mems.persistence.repository.MtCountryRepository;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.persistence.repository.PartsListRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.ScheduleRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.util.QueryByCompareOperatorUtils;
import arrow.mems.util.file.FileUtils;
import arrow.mems.util.string.ArrowStrUtils;

@Named
public class DevicesManagementService extends AbstractService {

  @Inject
  private MDeviceRepository mdeviceReporitory;
  @Inject
  private OfficeRepository officeRepository;
  @Inject
  private MtCountryRepository countryRepository;
  @Inject
  private DocumentRepository documentRepository;
  @Inject
  private PersonRepository personRepository;
  @Inject
  private MdevChecklistRepository mdevChecklistRepository;
  @Inject
  private MdevChecklistItemRepository mdevChecklistItemRepository;
  @Inject
  private UsersRepository usersRepository;
  @Inject
  private PartsListRepository partlistRepository;
  @Inject
  private ManageDocumentOfDeviceService documentService;
  @Inject
  private ScheduleRepository scheduleRepository;
  @Inject
  private AlertByCountRepository alertRepository;

  public List<MDevice> searchDevicesSync(String manufactoryValue, String genaralValue, String nameValue,
      List<arrow.mems.bean.data.SearchCriteria> conditionSearch, String officeCode, RememberSearchCriteria rememberSearchCriteria) {
    final List<Integer> listCreatedByUserId = this.usersRepository.findUserInOneOffice(officeCode);
    final ArrowQuery<MDevice> query = new ArrowQuery<MDevice>(super.em);
    query.select("m").from("MDevice m ");
    query.leftJoin("m.manufacturerPerson p");
    query.leftJoin("m.mtCountry c");
    query.leftJoin("m.manufacturerOffice o");
    query.where(" m.isDeleted = 0");
    query.where(" m.createdBy in (?)", listCreatedByUserId);

    // add filter and sort
    if (ArrowStrUtils.isNotEmpty(manufactoryValue)) {
      query.where("upper(o.name) LIKE ?", ArrowStrUtils.likePattern(ArrowStrUtils.nullTrim(manufactoryValue).toUpperCase()));
    }
    if (ArrowStrUtils.isNotEmpty(genaralValue)) {
      query.where("upper(m.catName) LIKE ?", ArrowStrUtils.likePattern(ArrowStrUtils.nullTrim(genaralValue).toUpperCase()));
    }
    if (ArrowStrUtils.isNotEmpty(nameValue)) {
      query.where("upper(m.name) LIKE ?", ArrowStrUtils.likePattern(ArrowStrUtils.nullTrim(nameValue).toUpperCase()));
    }

    if (CollectionUtils.isNotEmpty(conditionSearch)) {
      final Junction whereCond = QueryByCompareOperatorUtils.createJunction(rememberSearchCriteria);
      for (final SearchCriteria cond : conditionSearch) {
        if (StringUtils.isEmpty(cond.getParam())) {
          continue;
        }
        switch (cond.getType()) {
          case CommonConstants.PULLDOWN_MANUFACTORY_CONTACT_PERSON:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("p.name", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          case CommonConstants.PULLDOWN_MANUFACTORY_COUNTRY:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("c.name", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          case CommonConstants.PULLDOWN_MANUFACTORY_CATALOG:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("m.catalog", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          case CommonConstants.PULLDOWN_MANUFACTORY_SPECIFICATION:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("m.specification", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          case CommonConstants.PULLDOWN_MANUFACTORY_NOTICE:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("m.notice", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          default:
            break;
        }
      }
      query.where(whereCond);
    }

    query.addFilterAndSorterForString("name", "m.name");
    query.addFilterAndSorterForString("catName", "m.catName");
    query.addFilterAndSorterForString("manufacturerName", "m.manufacturerOffice.name");
    query.addFilterAndSorterForString("country", "m.mtCountry.name");
    query.orderBy("m.createdAt");
    return query.getResultList();
  }

  @Inject
  @RequestScoped
  private ArrowValidator validator;

  public ServiceResult<MDevice> saveMasterDevice(MDevice device, List<MDevice> deletedPartList, List<MDevice> newPartsList,
      List<MdevChecklistDto> listChecklist, List<MdevChecklistDto> deletedChecklist, List<Document> listDocumentAdd,
      List<Document> listDocumentDelete, List<Document> listModifiedDocuments, List<ScheduleDto> listSchedule, List<Schedule> deletedListSchedule,
      List<AlertByCountDto> listScheduleAlert, List<AlertByCount> deletedListScheduleAlert, int createdBy) throws IOException {
    final List<Message> messages = new ArrayList<Message>();

    messages.addAll(this.validator.validate(device));

    if (CollectionUtils.isNotEmpty(messages))
      return new ServiceResult<MDevice>(false, null, messages);
    try {
      device = this.mdeviceReporitory.saveAndFlushAndRefresh(device);
      this.addNewPartlist(newPartsList, device.getMdevCode(), createdBy); // Save data in
      this.deletePartlist(deletedPartList, device.getMdevCode());
      final Map<Integer, String> storeCklistCode = this.saveChecklist(listChecklist, listSchedule, device, createdBy);
      this.deleteChecklist(deletedChecklist, device.getMdevCode(), createdBy);

      if (CollectionUtils.isNotEmpty(listDocumentDelete)) {
        this.deleteDocumentOfMasterDevice(listDocumentDelete);
      }
      if (CollectionUtils.isNotEmpty(listDocumentAdd)) {
        this.addDocumentToMasterDevice(listDocumentAdd, device, createdBy);
      }
      if (CollectionUtils.isNotEmpty(listModifiedDocuments)) {
        this.updateDocument(listModifiedDocuments, device, createdBy);
      }


      this.deleteSchedule(deletedListSchedule);
      if (CollectionUtils.isNotEmpty(listSchedule)) {
        this.saveSchedule(listSchedule, device, createdBy, storeCklistCode);
      }

      this.deleteScheduleAlert(deletedListScheduleAlert);
      if (CollectionUtils.isNotEmpty(listScheduleAlert)) {
        this.saveScheduleAlert(listScheduleAlert, device, createdBy, storeCklistCode);
      }

      messages.add(MDMMessages.MDM00003());
      return new ServiceResult<MDevice>(true, device, messages);
    } catch (final PersistenceException e) {
      super.logger.debug(e.getMessage(), e);
      messages.add(MDMMessages.MDM00002());
    }
    return new ServiceResult<MDevice>(false, null, messages);
  }

  private void updateDocument(List<Document> pListModifiedDocuments, MDevice device, int userId) throws IOException {
    for (final Document modifiedDoc : pListModifiedDocuments) {

      final Document oldDoc = this.documentRepository.findBy(modifiedDoc.getDocId());
      if (!StringUtils.equals(oldDoc.getSoftwareRev(), modifiedDoc.getSoftwareRev())) {
        FileUtils.renameDir(UploadHelper.getUploadDir(this.appConfig.getUploadDir(), device.getMdevCode(), oldDoc.getSoftwareRev()),
            UploadHelper.getUploadDir(this.appConfig.getUploadDir(), device.getMdevCode(), modifiedDoc.getSoftwareRev()));
      }

      // copy
      FileUtils.copyDir(UploadHelper.getUploadTempPath(this.appConfig.getUploadTempDir(), modifiedDoc.getUploadFolderName()),
          UploadHelper.getUploadDir(this.appConfig.getUploadDir(), device.getMdevCode(), modifiedDoc.getSoftwareRev()));

      this.documentRepository.deleteItem(oldDoc);
      final Document newDoc = new Document();
      BeanCopier.copy(modifiedDoc, newDoc);
      newDoc.setMDevice(device);
      newDoc.setCheckedAt(device.getCheckedAt());
      newDoc.setCheckedBy(device.getCheckedBy());
      this.documentRepository.saveAndFlush(newDoc);
    }
  }

  public void saveSchedule(List<ScheduleDto> listSchedule, MDevice device, int createdBy, Map<Integer, String> storeCklistCode) {
    final ScheduleIdGenerator generatorCode = new ScheduleIdGenerator(device.getMdevCode());
    if (storeCklistCode == null) {
      storeCklistCode = new HashMap<Integer, String>();
    }
    for (final ScheduleDto scheduleDto : listSchedule) {
      String cklistCode = null;
      if (scheduleDto.getMdevChecklist() != null) {
        cklistCode =
            ArrowStrUtils.isNotEmpty(storeCklistCode.get(scheduleDto.getMdevChecklist().getCklistId())) ? storeCklistCode.get(scheduleDto
                .getMdevChecklist().getCklistId()) : scheduleDto.getMdevChecklist().getCklistCode();
      }
      if (ArrowStrUtils.isEmpty(scheduleDto.getSchedBaseCode())) {
        // Case add new schedule
        final Schedule newSchedule = new Schedule();
        BeanCopier.copy(scheduleDto, newSchedule);
        newSchedule.setSchedBaseCode(generatorCode.getNext());
        newSchedule.setCreatedBy(createdBy);
        final MdevChecklist mdevChecklist =
            this.mdevChecklistRepository.findMevChecklistByChecklistCode(cklistCode, CommonConstants.ACTIVE).getAnyResult();
        newSchedule.setMdevChecklist(mdevChecklist);
        newSchedule.setMdevCode(device.getMdevCode());
        newSchedule.setCheckedAt(device.getCheckedAt());
        newSchedule.setCheckedBy(device.getCheckedBy());
        this.scheduleRepository.saveAndFlush(newSchedule);
      } else {
        final MdevChecklist checklist =
            this.mdevChecklistRepository.findMevChecklistByChecklistCode(cklistCode, CommonConstants.ACTIVE).getAnyResult();

        // Edit
        final Schedule updateSchedule =
            this.scheduleRepository.findSchedulByScheduleCodeAndIsDeleted(scheduleDto.getSchedBaseCode(), CommonConstants.ACTIVE).getAnyResult();
        BeanCopier.copy(scheduleDto, updateSchedule);
        updateSchedule.setMdevChecklist(checklist);
        updateSchedule.setCheckedAt(device.getCheckedAt());
        updateSchedule.setCheckedBy(device.getCheckedBy());
        this.scheduleRepository.saveAndFlush(updateSchedule);
      }
    }
  }

  public void saveScheduleAlert(List<AlertByCountDto> listScheduleAlert, MDevice device, int createdBy, Map<Integer, String> storeCklistCode) {
    final AlertByCountIdGenerator generatorCode = new AlertByCountIdGenerator(device.getMdevCode());
    for (final AlertByCountDto alertDto : listScheduleAlert) {
      String cklistCode = null;
      if (alertDto.getMdevChecklist() != null) {
        cklistCode =
            ArrowStrUtils.isNotEmpty(storeCklistCode.get(alertDto.getMdevChecklist().getCklistId())) ? storeCklistCode.get(alertDto
                .getMdevChecklist().getCklistId()) : alertDto.getMdevChecklist().getCklistCode();
      }
      if (ArrowStrUtils.isEmpty(alertDto.getCounterBaseCode())) {
        // Case add new schedule alert by count
        final AlertByCount newScheduleAlert = new AlertByCount();
        BeanCopier.copy(alertDto, newScheduleAlert);
        newScheduleAlert.setCounterBaseCode(generatorCode.getNext());
        newScheduleAlert.setCreatedBy(createdBy);
        final MdevChecklist checklist =
            this.mdevChecklistRepository.findMevChecklistByChecklistCode(cklistCode, CommonConstants.ACTIVE).getAnyResult();
        newScheduleAlert.setMdevChecklist(checklist);
        newScheduleAlert.setMdevCode(device.getMdevCode());
        newScheduleAlert.setCheckedAt(device.getCheckedAt());
        newScheduleAlert.setCheckedBy(device.getCheckedBy());
        this.alertRepository.saveAndFlush(newScheduleAlert);
      } else {
        final MdevChecklist mdevChecklist =
            this.mdevChecklistRepository.findMevChecklistByChecklistCode(cklistCode, CommonConstants.ACTIVE).getAnyResult();
        // Edit
        final AlertByCount updateScheduleAlert =
            this.alertRepository.findSchedulAlertByBaseCodeAndIsDeleted(alertDto.getCounterBaseCode(), CommonConstants.ACTIVE).getAnyResult();
        BeanCopier.copy(alertDto, updateScheduleAlert);
        updateScheduleAlert.setMdevChecklist(mdevChecklist);
        updateScheduleAlert.setCheckedAt(device.getCheckedAt());
        updateScheduleAlert.setCheckedBy(device.getCheckedBy());
        this.alertRepository.saveAndFlush(updateScheduleAlert);
      }
    }
  }

  public int deleteSchedule(List<Schedule> deletedListSchedule) {
    int result = 0;
    if (deletedListSchedule == null)
      return result;
    for (final Schedule schedule : deletedListSchedule) {
      result += this.scheduleRepository.updateIsDeletedByScheduleId(CommonConstants.DELETE, schedule.getSchedBaseId());
    }
    return result;
  }

  public int deleteScheduleAlert(List<AlertByCount> deletedListScheduleAlert) {
    int result = 0;
    if (deletedListScheduleAlert == null)
      return result;
    for (final AlertByCount alert : deletedListScheduleAlert) {
      result += this.alertRepository.updateIsDeletedByCounterBaseId(CommonConstants.DELETE, alert.getCounterBaseId());
    }
    return result;
  }

  public void deleteDocumentOfMasterDevice(List<Document> listDocumentDelete) throws IOException {
    for (final Document documentDelete : listDocumentDelete) {
      this.documentService.deleteDocument(documentDelete);

      // delete files
      FileUtils.rmdir(UploadHelper.getUploadDir(this.appConfig.getUploadDir(), documentDelete.getMdevCode(), documentDelete.getSoftwareRev()));
    }
  }

  public void addDocumentToMasterDevice(List<Document> listDocumentAdd, MDevice device, int userId) throws IOException {
    final DocumentDto documentDto = new DocumentDto();
    try {
      for (final Document documentAdd : listDocumentAdd) {
        BeanCopier.copy(documentAdd, documentDto);
        documentDto.setMDevice(device);
        documentDto.setCheckedAt(device.getCheckedAt());
        documentDto.setCheckedBy(device.getCheckedBy());
        this.documentService.saveDocument(documentDto);
        final File tempDir = new File(UploadHelper.getUploadTempPath(this.appConfig.getUploadTempDir(), documentAdd.getUploadFolderName()));
        if (tempDir.exists()) {
          FileUtils.copyDir(tempDir.getAbsolutePath(),
              UploadHelper.getUploadDir(this.appConfig.getUploadDir(), device.getMdevCode(), documentAdd.getSoftwareRev()));
        }
      }
    } catch (final PersistenceException e) {
      super.logger.debug(e.getMessage(), e);
    }
  }

  public int deletePartlist(List<MDevice> deletedPartList, String mdevCode) {
    int result = 0;
    if (deletedPartList != null) {
      for (final MDevice mDevice : deletedPartList) {
        result = this.partlistRepository.updateIsDeletedByMdevcodeAndPartcode(CommonConstants.DELETE, mdevCode, mDevice.getMdevCode());
      }
    }
    return result;
  }

  public void addNewPartlist(List<MDevice> partsList, String mdevCode, int createdBy) {
    if (partsList != null) {
      for (final MDevice parts : partsList) {
        try {
          final PartsList newPartlist = new PartsList();
          newPartlist.setPartCode(parts.getMdevCode());
          newPartlist.setCreatedBy(createdBy);
          newPartlist.setMdevCode(mdevCode);
          this.partlistRepository.saveAndFlush(newPartlist);
        } catch (final PersistenceException e) {
          super.logger.debug(e.getMessage(), e);
        }
      }
    }
  }

  public int deleteChecklist(List<MdevChecklistDto> deletedChecklist, String mdevCode, int createdBy) {
    int result = 0;
    if (deletedChecklist != null) {
      for (final MdevChecklistDto checklist : deletedChecklist) {
        result +=
            this.mdevChecklistRepository.updateIsDeletedByCklistCodeAndId(CommonConstants.DELETE, checklist.getCklistCode(), checklist.getCklistId());
        result += this.mdevChecklistItemRepository.updateIsDeletedByCklistCode(CommonConstants.DELETE, checklist.getCklistCode());
      }
    }
    return result;
  }

  public Map<Integer, String> saveChecklist(List<MdevChecklistDto> listChecklist, List<ScheduleDto> listSchedule, MDevice savedDevice, int createdBy) {
    final MdevChecklistIdGenerator generatorCkCode = new MdevChecklistIdGenerator(LocalDateTime.now().getYear());
    final MdevChecklistItemIdGenerator generatorCkiCode = new MdevChecklistItemIdGenerator(LocalDateTime.now().getYear());
    final Map<Integer, String> storeCklistCode = new HashMap<>();
    if (listChecklist == null)
      return storeCklistCode;
    for (final MdevChecklistDto checklistDto : listChecklist) {
      if (ArrowStrUtils.isEmpty(checklistDto.getCklistCode())) {
        final String cklistCode = generatorCkCode.getNext();
        final MdevChecklist newChecklist = new MdevChecklist();
        BeanCopier.copy(checklistDto, newChecklist);
        newChecklist.setCklistCode(cklistCode);
        newChecklist.setMdevCode(savedDevice.getMdevCode());
        newChecklist.setCreatedBy(createdBy);
        newChecklist.setCheckedAt(savedDevice.getCheckedAt());
        newChecklist.setCheckedBy(savedDevice.getCheckedBy());
        this.mdevChecklistRepository.saveAndFlush(newChecklist);
        storeCklistCode.put(checklistDto.getCklistId(), cklistCode);
        // Add new ChecklistItem
        if (checklistDto.getChecklistItems() != null) {
          for (final MdevChecklistItemDto checklistItemDto : checklistDto.getChecklistItems()) {
            final MdevChecklistItem newChecklistItem = new MdevChecklistItem();
            BeanCopier.copy(checklistItemDto, newChecklistItem);
            newChecklistItem.setCkiCode(generatorCkiCode.getNext());
            newChecklistItem.setCklistCode(cklistCode);
            newChecklistItem.setCreatedBy(createdBy);
            newChecklistItem.setCountry(savedDevice.getCountry());
            newChecklistItem.setCheckedAt(savedDevice.getCheckedAt());
            newChecklistItem.setCheckedBy(savedDevice.getCheckedBy());
            this.mdevChecklistItemRepository.saveAndFlush(newChecklistItem);
          }
        }
      } else {
        // Edit case : Update checklist
        final MdevChecklist updateChecklist = this.mdevChecklistRepository.findMevChecklistByChecklistId(checklistDto.getCklistId()).getAnyResult();
        final List<MdevChecklistItem> oldMdevChecklistItem =
            this.getMdevChecklistItemByCklistCode(checklistDto.getCklistCode(), CommonConstants.ACTIVE);
        final Map<String, String> mapMdevChecklistItem = new HashMap<String, String>();
        BeanCopier.copy(checklistDto, updateChecklist);
        updateChecklist.setCheckedAt(savedDevice.getCheckedAt());
        updateChecklist.setCheckedBy(savedDevice.getCheckedBy());
        this.mdevChecklistRepository.saveAndFlush(updateChecklist);
        // Delete checklistItem case
        if (CollectionUtils.isNotEmpty(checklistDto.getChecklistItems())) {
          for (final MdevChecklistItemDto checklistItemDto : checklistDto.getChecklistItems()) {
            if (!ArrowStrUtils.isEmpty(checklistItemDto.getCkiCode())) {
              mapMdevChecklistItem.put(checklistItemDto.getCkiCode(), checklistItemDto.getCkiCode());
            }
          }
        }
        if (!CollectionUtils.isEmpty(oldMdevChecklistItem)) {
          for (final MdevChecklistItem cklistItem : oldMdevChecklistItem) {
            if (!mapMdevChecklistItem.containsKey(cklistItem.getCkiCode())) {
              this.mdevChecklistItemRepository.updateIsDeletedByCkiCode(CommonConstants.DELETE, cklistItem.getCkiCode());
            }
          }
        }
        // For checklistItem
        if (CollectionUtils.isEmpty(checklistDto.getChecklistItems())) {
          continue;
        }
        for (final MdevChecklistItemDto checklistItemDto : checklistDto.getChecklistItems()) {
          if (ArrowStrUtils.isEmpty(checklistItemDto.getCkiCode())) {
            // Add new checklistItem
            final MdevChecklistItem newChecklistItem = new MdevChecklistItem();
            BeanCopier.copy(checklistItemDto, newChecklistItem);
            newChecklistItem.setCkiCode(generatorCkiCode.getNext());
            newChecklistItem.setCklistCode(checklistDto.getCklistCode());
            newChecklistItem.setCreatedBy(createdBy);
            newChecklistItem.setCountry(savedDevice.getCountry());
            newChecklistItem.setCheckedAt(savedDevice.getCheckedAt());
            newChecklistItem.setCheckedBy(savedDevice.getCheckedBy());
            this.mdevChecklistItemRepository.saveAndFlush(newChecklistItem);
          } else {
            // Update exist checklistItem
            final MdevChecklistItem updateChecklistItem =
                this.mdevChecklistItemRepository.findMdevChecklistItemByChecklistId(checklistItemDto.getCkiId()).getAnyResult();
            BeanCopier.copy(checklistItemDto, updateChecklistItem);
            updateChecklistItem.setCheckedAt(savedDevice.getCheckedAt());
            updateChecklistItem.setCheckedBy(savedDevice.getCheckedBy());
            this.mdevChecklistItemRepository.saveAndFlush(updateChecklistItem);
          }
        }
      }
    }

    return storeCklistCode;
  }

  public MDevice getDeviceFromPK(MDeviceDto deviceDto) {
    return this.mdeviceReporitory.findBy(deviceDto.getMdevId());
  }

  public MDevice getDeviceFromMdevCode(String mdevCode, int isDeleted) {
    return this.mdeviceReporitory.findDevicesByMdevcodeAndIsDeleted(mdevCode, isDeleted).getAnyResult();
  }


  public List<MtCountry> getCountry() {
    return this.countryRepository.findAll();
  }

  public Office getSupplierOffice(String supplierCode, int isDeleted) {
    return this.officeRepository.findOfficeByOfficeCodeAndIsDeleted(supplierCode, isDeleted).getAnyResult();
  }

  public Person getSupplierPerson(String supplierPsn, int isDeleted) {
    return this.personRepository.findPersonByPsnCodeAndIsDeleted(supplierPsn, isDeleted).getAnyResult();
  }

  public List<Document> getDocument(String mdevCode, int isDeleted) {
    return this.documentRepository.findDocumentByDeviceCodeAndIsDeleted(mdevCode, isDeleted).getResultList();
  }

  public List<Office> getAllOfficeByOwnedOfficeAndIsDeleted(String ownedOfficeCode, int isDeleted) {
    return this.officeRepository.findOfficeByOwnedOfficeCodeAndIsDeleted(ownedOfficeCode, isDeleted).getResultList();
  }

  public List<Person> getAllPersonByOwnedOfficeAndIsDeleted(String ownedOfficeCode, int isDeleted) {
    return this.personRepository.findPersonByOwnedOfficeCodeAndIsDeleted(ownedOfficeCode, isDeleted).getResultList();
  }


  /* Code area for MDM02_02 */

  public List<MdevChecklist> getMdevChecklistByMdevcode(String mdevCode, int isDeleted) {
    return this.mdevChecklistRepository.findMevChecklistByMdevcodeAndisDeleted(mdevCode, isDeleted).getResultList();
  }

  public List<MdevChecklistItem> getMdevChecklistItemByCklistCode(String cklistCode, int isDeleted) {
    return this.mdevChecklistItemRepository.findMdevChecklistItemByCklistCodeAndIsDeleted(cklistCode, isDeleted).getResultList();
  }

  public Users getNameUserByCreatedBy(int createdBy) {
    return this.usersRepository.findUserById(createdBy).getAnyResult();
  }

  /* End code area of MDM02_02 */

  /* Code area for MDM02_04 */

  public List<MDevice> getInfoPartlistByMdevcode(String mdevCode, int isDeletedDevice, int isDeletedPartlist) {
    return this.mdeviceReporitory.findInfoDevicesByCodeAndIsDeleted(mdevCode, isDeletedDevice, isDeletedPartlist).getResultList();
  }

  public List<MDevice> searchAvailableParts(MDevice device, String keyword) {
    final StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM MDevice m LEFT JOIN m.manufacturerOffice mo LEFT JOIN m.supplierOffice so WHERE ");
    sb.append(" m.isDeleted = ").append(device.getIsDeleted());
    sb.append(" AND m.mdevType = ").append(CommonConstants.MDevTypeConstants.PARTSLIST);
    if (StringUtils.isNotEmpty(device.getMdevCode())) {
      sb.append(" AND m.mdevCode NOT IN (");
      sb.append("SELECT md.mdevCode FROM PartsList p , MDevice md WHERE p.partCode = md.mdevCode AND p.isDeleted=md.isDeleted AND (p.mdevCode = '")
      .append(device.getMdevCode()).append("') ");
      sb.append(" AND p.isDeleted = ").append(device.getIsDeleted());
      sb.append(") ");
    }

    if (ArrowStrUtils.isNotEmpty(keyword)) {
      final String likePattern = ArrowStrUtils.likePattern(keyword);
      sb.append(" AND (UPPER(m.modelNo) LIKE '").append(likePattern).append("'");
      sb.append(" OR UPPER(m.catName) LIKE '").append(likePattern).append("'");
      sb.append(" OR UPPER(m.name) LIKE '").append(likePattern).append("'");
      sb.append(" OR UPPER(mo.name) LIKE '").append(likePattern).append("'");
      sb.append(" OR UPPER(so.name) LIKE '").append(likePattern).append("')");
    }
    return super.em.createQuery(sb.toString(), MDevice.class).getResultList();
  }

  /* End code area of MDM02_04 */

  /* Code area for MDM02_05 */
  public List<Office> autoCompleteOfficeManufacturer(int isDeleted) {
    return this.officeRepository.findOfficeManufacturerByIsDeleted(isDeleted).getResultList();
  }

  public List<Person> autoCompletePersonManufacturer(int isDeleted) {
    return this.personRepository.findPersonByIsDeleted(isDeleted).getResultList();
  }

  public List<MDevice> findMDeviceByOwnedOfficeCodeAndIsDeleted(String ownedOfficeCode, int isDeleted) {
    return this.mdeviceReporitory.findMDeviceByOwnedOfficeCodeAndIsDeleted(ownedOfficeCode, isDeleted).getResultList();
  }

  public List<Office> searchOfficeManufacturer(String officeName, String mdeviceName, String personName, int isDeleted) {
    final StringBuilder sb = new StringBuilder();
    sb.append("SELECT DISTINCT o FROM Office o");
    sb.append(" WHERE ");
    sb.append(" o.isDeleted = ").append(isDeleted);
    if (ArrowStrUtils.isNotEmpty(officeName)) {
      sb.append(" AND UPPER(o.name) LIKE '").append(ArrowStrUtils.likePattern(officeName)).append("' ");
    }
    if (ArrowStrUtils.isNotEmpty(mdeviceName)) {
      sb.append(" AND (o.officeCode IN (");
      sb.append(" SELECT DISTINCT m.manufacturerCode FROM MDevice m WHERE m.isDeleted = ").append(isDeleted);
      sb.append(" AND UPPER(m.name) LIKE '").append(ArrowStrUtils.likePattern(mdeviceName)).append("' ");
      sb.append(")");
      sb.append(")");
    }
    if (ArrowStrUtils.isNotEmpty(personName)) {
      sb.append(" AND o.officeCode IN ( ");
      sb.append(" SELECT DISTINCT p.officeCode FROM Person p WHERE p.isDeleted = ").append(isDeleted);
      sb.append(" AND UPPER(p.name) LIKE '").append(ArrowStrUtils.likePattern(personName)).append("' ");
      sb.append(")");
    }
    return super.em.createQuery(sb.toString(), Office.class).getResultList();
  }

  public List<Person> getPersonByOfficeCode(Office office) {
    return this.personRepository.findPersonByOfficeCodeAndIsDeleted(office.getOfficeCode(), office.getIsDeleted()).getResultList();
  }

  /* End code area of MDM02_05 */


  /**
   * Delete master device.
   *
   * @param mdevCode the mdevice code
   * @param isDeleted the is deleted ; In this case : isDeleted = 1
   * @return the service result
   */
  public ServiceResult<Message> deleteMasterDevice(String mdevCode, int isDeleted) {
    final List<Message> message = new ArrayList<Message>();
    final PartsList currentPartlist = this.partlistRepository.findPartlistByMdevcodeAndIsDeleted(mdevCode, CommonConstants.ACTIVE).getAnyResult();
    final ArrayList<List<MdevChecklistItem>> currentChecklistItem = new ArrayList<List<MdevChecklistItem>>();
    final List<MdevChecklist> currentMdevChecklist =
        this.mdevChecklistRepository.findMevChecklistByMdevcodeAndisDeleted(mdevCode, CommonConstants.ACTIVE).getResultList();
    for (final MdevChecklist mdevChecklist : currentMdevChecklist) {
      final List<MdevChecklistItem> checklistItem =
          this.mdevChecklistItemRepository.findMdevChecklistItemByCklistCodeAndIsDeleted(mdevChecklist.getCklistCode(), CommonConstants.ACTIVE)
          .getResultList();
      if (checklistItem != null) {
        currentChecklistItem.add(checklistItem);
      }
    }
    int resultUpdateMdevChecklist = DatabaseConstants.UPDATE_UNSUCCESS;
    final int resultUpdateMdevice = this.mdeviceReporitory.updateIsDeleted(isDeleted, mdevCode);
    if ((currentPartlist != null) && (resultUpdateMdevice != DatabaseConstants.UPDATE_UNSUCCESS)) {
      this.partlistRepository.updateIsDeleted(isDeleted, mdevCode);
    }
    if (currentMdevChecklist != null) {
      for (final MdevChecklist mdevChecklist : currentMdevChecklist) {
        resultUpdateMdevChecklist = this.mdevChecklistRepository.updateIsDeleted(isDeleted, mdevChecklist.getCklistCode());
      }
    }
    if ((currentChecklistItem != null) && (resultUpdateMdevChecklist != DatabaseConstants.UPDATE_UNSUCCESS)) {
      for (int i = 0; i < currentChecklistItem.size(); i++) {
        for (final MdevChecklistItem cklistItem : currentChecklistItem.get(i)) {
          this.mdevChecklistItemRepository.updateIsDeletedByCklistCode(isDeleted, cklistItem.getCklistCode());
        }
      }
    }
    if (resultUpdateMdevice == DatabaseConstants.UPDATE_UNSUCCESS) {
      message.add(MAUMessages.MAU00020());
    }
    if (message.size() == 0) {
      message.add(MAUMessages.MAU00019());
      return new ServiceResult<Message>(true, null, message);
    } else
      return new ServiceResult<Message>(true, null, message);
  }

  public List<MDevice> getPartlistInfo(String mdevCode) {
    return this.getInfoPartlistByMdevcode(mdevCode, CommonConstants.ACTIVE, CommonConstants.ACTIVE);
  }

  public List<Schedule> getMasterScheduleByCurrentDevice(String mdevCode) {
    return this.scheduleRepository.findSchedulByMdevCodeAndIsDeleted(mdevCode, CommonConstants.ACTIVE).getResultList();
  }

  public List<Person> getAllPersonByOwnedOfficeAndOfficeCodeAndIsDeleted(String ownedOfficeCode, String officeCode, int pActive) {
    return this.personRepository.findByOwnedOfficeAndOfficeCodeAndIsDeleted(ownedOfficeCode, officeCode, pActive);
  }

  public ServiceResult<List<PartsList>> getAllPartsListOfMasterDevice(String mdevCode) {
    final List<PartsList> list = this.partlistRepository.findPartlistByMdevcodeAndIsDeleted(mdevCode, CommonConstants.FLAG_FALSE).getResultList();
    return new ServiceResult<List<PartsList>>(true, list);
  }

  // 2014-10-03
  public List<AlertByCount> getAlertByCountByMdevcode(String mdevCode) {
    return this.alertRepository.findAlertByMdevCodeAndIsDeleted(mdevCode, CommonConstants.ACTIVE).getResultList();
  }

  public ServiceResult<List<MDevice>> getAllPartsOfMasterDevice(String mdevCode) {
    final List<MDevice> list = this.mdeviceReporitory.findPartsOfMasterDevice(mdevCode, CommonConstants.FLAG_FALSE).getResultList();
    return new ServiceResult<List<MDevice>>(true, list);
  }

  public <T extends AbstractApprovalEntity> List<T> findAllEntity(Class<T> clazz, SingularAttribute codeField, String codeValue) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();
    final CriteriaQuery<T> query = cb.createQuery(clazz);

    final Root<T> root = query.from(clazz);
    query.where(cb.and(cb.equal(root.get(codeField), codeValue), cb.equal(root.get(AbstractDeletableMapped_.isDeleted), 0)));
    final javax.persistence.Query run = this.em.createQuery(query);
    return run.getResultList();
  }
}
