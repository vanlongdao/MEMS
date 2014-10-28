package arrow.mems.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.data.api.QueryResult;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.persistence.util.Condition.Junction;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.RememberSearchCriteria;
import arrow.mems.bean.data.SearchCriteria;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.generator.DeviceIdGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.DeviceDto;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.persistence.repository.ActionLogRepository;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.ReplPartRepository;
import arrow.mems.persistence.repository.ScheduleItemRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.util.QueryByCompareOperatorUtils;
import arrow.mems.util.string.ArrowStrUtils;

@Named
public class DeviceManagementService extends AbstractService {
  @Inject
  private DeviceRepository deviceRepo;
  @Inject
  private UsersRepository usersRepository;
  @Inject
  private OperationLogService logService;
  @Inject
  private UserCredential userCredential;
  @Inject
  private ScheduleItemRepository scheduleItemRepo;
  @Inject
  private ScheduleService scheduleService;
  @Inject
  private ActionLogRepository actionLogRepo;
  @Inject
  private ReplPartRepository replPartRepo;

  public Device loadDeviceByDeviceCode(String devCode) {
    return this.deviceRepo.findActiveDeviceByDeviceCode(devCode).getAnyResult();
  }

  public List<Device> searchListDevice(String manufactoryValue, String genaralValue, String nameValue, List<SearchCriteria> conditionSearch,
      String officeCode, List<Integer> deviceType, RememberSearchCriteria rememberSearchCriteria) {
    final List<Integer> listCreatedByUserId = this.usersRepository.findUserInOneOffice(officeCode);
    final ArrowQuery<Device> query = new ArrowQuery<Device>(super.em);
    query.select("m").from("Device m ");
    query.where(" m.isDeleted = 0");
    query.where(" m.createdBy in (?)", listCreatedByUserId);
    if (ArrayUtils.isNotEmpty(deviceType.toArray())) {
      query.where(" m.mDevice.mdevType in (?)", deviceType);
    }

    // add filter and sort
    if (ArrowStrUtils.isNotEmpty(manufactoryValue)) {
      query.where("upper(m.mDevice.manufacturerOffice.name) LIKE ?", ArrowStrUtils.likePattern(manufactoryValue.toUpperCase()));
    }
    if (ArrowStrUtils.isNotEmpty(genaralValue)) {
      query.where("upper(m.mDevice.catName) LIKE ?", ArrowStrUtils.likePattern(genaralValue.toUpperCase()));
    }
    if (ArrowStrUtils.isNotEmpty(nameValue)) {
      query.where("upper(m.mDevice.name) LIKE ?", ArrowStrUtils.likePattern(nameValue.toUpperCase()));
    }

    if (CollectionUtils.isNotEmpty(conditionSearch)) {
      final Junction whereCond = QueryByCompareOperatorUtils.createJunction(rememberSearchCriteria);
      for (final SearchCriteria cond : conditionSearch) {
        if (StringUtils.isEmpty(cond.getParam())) {
          continue;
        }
        switch (cond.getType()) {
          case CommonConstants.PULLDOWN_MANUFACTORY_CONTACT_PERSON:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("m.mDevice.manufacturerPerson.name", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          case CommonConstants.PULLDOWN_MANUFACTORY_COUNTRY:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("m.mDevice.mtCountry.name", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          case CommonConstants.PULLDOWN_MANUFACTORY_CATALOG:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("m.mDevice.catalog", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          case CommonConstants.PULLDOWN_MANUFACTORY_SPECIFICATION:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("m.mDevice.specification", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          case CommonConstants.PULLDOWN_MANUFACTORY_NOTICE:
            whereCond.add(QueryByCompareOperatorUtils.queryCondition("m.mDevice.notice", cond.getOperator()),
                QueryByCompareOperatorUtils.queryParam(cond.getParam(), cond.getOperator()));
            break;
          default:
            break;
        }
      }
      query.where(whereCond);
    }

    query.addFilterAndSorterForString("clientMgmtCode", "m.clientMgmtCode");
    query.addFilterAndSorterForString("modelNo", "m.mDevice.modelNo");
    query.addFilterAndSorterForString("name", "m.mDevice.name");
    query.addFilterAndSorterForString("catName", "m.mDevice.catName");
    query.addFilterAndSorterForString("manufacturerName", "m.mDevice.supplierOffice.name");
    query.addFilterAndSorterForString("country", "m.mDevice.mtCountry.name");
    query.orderBy("m.createdAt");
    return query.getResultList();
  }

  public ServiceResult<DeviceDto> prepareEditDevice(Device device) {
    ServiceResult<DeviceDto> result = null;
    Device getDevice = new Device();
    if (device.isSelected()) {
      getDevice = this.deviceRepo.findBy(device.getDevId());
      final DeviceDto deviceDTO = new DeviceDto();
      BeanCopier.copy(getDevice, deviceDTO);
      result = new ServiceResult<DeviceDto>(true, deviceDTO);
    }
    return result;
  }

  public ServiceResult<Device> deleteDevice(Device selectedDevice) {
    ServiceResult<Device> result = null;
    final Device deletePerson = this.deviceRepo.findBy(selectedDevice.getDevId());
    deletePerson.setIsDeleted(CommonConstants.DELETE);
    this.deviceRepo.saveAndFlush(deletePerson);
    result = new ServiceResult<>(true, null, null);
    // check if remove Device success then remove schedule of deleted device
    if (result.isSuccess()) {
      this.scheduleService.deleteScheduleOfDevice(selectedDevice.getDevCode());
    }
    return result;

  }

  public ServiceResult<Device> saveDevice(DeviceDto currentDevice) {
    ServiceResult<Device> result = null;
    if (currentDevice.getDevId() == 0) {
      final DeviceIdGenerator generator = new DeviceIdGenerator(this.userCredential.getUserInfo().getOfficeCode(), LocalDate.now().getYear());
      final String nextDeviceCode = generator.getNext();

      currentDevice.setDevCode(nextDeviceCode);

      result = this.createDevice(currentDevice, null, null);
      if (result.isSuccess()) {
        if ((currentDevice.getAcceptanceDate() != null) && (currentDevice.getExpireDate() != null)) {
          this.scheduleService.saveScheduleItemForDevice(currentDevice, currentDevice.getMdevCode(), currentDevice.getAcceptanceDate(),
              currentDevice.getExpireDate(), false);
        }
      }
    } else {
      final Device editDevice = this.deviceRepo.findBy(currentDevice.getDevId());
      if (editDevice.isPassedApprovalProcess()) {
        editDevice.setIsDeleted(CommonConstants.DELETE);
        this.deviceRepo.saveAndFlush(editDevice);
        result = this.createDevice(currentDevice, this.userCredential.getUserId(), LocalDateTime.now());
      } else {
        editDevice.setIsDeleted(CommonConstants.DELETE);
        this.deviceRepo.saveAndFlush(editDevice);
        result = this.createDevice(currentDevice, null, null);

      }
      // Check if Expire Date and Accept Date change then recalculation schedule
      if (result.isSuccess()) {
        final Device deviceOld = this.deviceRepo.findDeviceAfterUpdate(result.getData().getDevCode(), currentDevice.getDevId()).getAnyResult();
        if ((currentDevice.getAcceptanceDate() != null) && (currentDevice.getExpireDate() != null)) {
          if (deviceOld.getAcceptanceDate() == null) {
            this.scheduleService.saveScheduleItemForDevice(currentDevice, currentDevice.getMdevCode(), currentDevice.getAcceptanceDate(),
                currentDevice.getExpireDate(), false);
          }
          // Case when acceptanceDate created after edit
          else if ((!deviceOld.getAcceptanceDate().isEqual(currentDevice.getAcceptanceDate())) || (!deviceOld.getExpireDate().isEqual(
              currentDevice.getExpireDate()))) {
            this.scheduleService.saveScheduleItemForDevice(currentDevice, currentDevice.getMdevCode(), currentDevice.getAcceptanceDate(),
                currentDevice.getExpireDate(), true);
          }
        }
        // add operation log after edit Device
        this.logService.addOperationLog(Device.class.getName(), currentDevice.getDevId(), result.getData().getDevId(), "update approve");
      }
    }
    return result;
  }

  public ServiceResult<Device> createDevice(DeviceDto currentDevice, Integer checkedBy, LocalDateTime checkedDate) {
    final List<Message> message = new ArrayList<Message>();
    ServiceResult<Device> result = null;
    final Device newDevice = new Device();
    BeanCopier.copy(currentDevice, newDevice);

    // copy images
    if (currentDevice.getLocationImage() != null) {
      newDevice.setLocationImage(currentDevice.getLocationImage());
    }
    if (currentDevice.getImageFile() != null) {
      newDevice.setImageFile(currentDevice.getImageFile());
    }
    if (currentDevice.getSupplierOffice() != null) {
      newDevice.setSupllierOffice(currentDevice.getSupplierOffice().getOfficeCode());
    }
    if (currentDevice.getRepairedByOffice() != null) {
      newDevice.setLastServiceOffice(currentDevice.getRepairedByOffice().getOfficeCode());
    }
    if (currentDevice.getSupplierPerson() != null) {
      newDevice.setSupplierPsn(currentDevice.getSupplierPerson().getPsnCode());
    }
    if (currentDevice.getRepairedPerson() != null) {
      newDevice.setLastServicePsn(currentDevice.getRepairedPerson().getPsnCode());
    }
    if (currentDevice.getHospitalDept() != null) {
      newDevice.setHospDeptCode(currentDevice.getHospitalDept().getDeptCode());
    }
    newDevice.setSoftwareRev(currentDevice.getSoftwareRevison().getSoftwareRev());
    if (currentDevice.getAcquiMaster() != null) {
      newDevice.setAcqCode(currentDevice.getAcquiMaster().getAcqCode());
    }
    if (currentDevice.getTargetDevice() != null) {
      newDevice.setTargetDevCode(currentDevice.getTargetDevice().getDevCode());
    }
    newDevice.setIsDeleted(CommonConstants.ACTIVE);
    newDevice.setCheckedAt(checkedDate);
    newDevice.setCheckedBy(checkedBy);
    newDevice.setCreatedAt(LocalDateTime.now());
    newDevice.setCreatedBy(this.userCredential.getUserId());

    this.deviceRepo.saveAndFlush(newDevice);
    message.add(MMIMessages.MMI00006());
    result = new ServiceResult<>(true, newDevice, message);
    return result;
  }

  public ActionLog findActionByDeviceCode(String deviceCode) {
    final List<ActionLog> listActionLog = this.actionLogRepo.findActionByDeviceCode(deviceCode);
    if (listActionLog.size() > 0)
      return listActionLog.get(0);
    return null;
  }

  public List<ReplPart> findReplPartByDeviceCode(String deviceCode, String actionCode) {
    final QueryResult<ReplPart> listRelpPart = this.replPartRepo.findReplPartByDeviceCode(deviceCode, actionCode);
    if (listRelpPart.count() > 0)
      return listRelpPart.getResultList();
    return null;
  }

  public ScheduleItem findScheduleItemByDeviceCode(String deviceCode) {
    final List<ScheduleItem> listScheduleItem = this.scheduleItemRepo.findScheduleItemByDevCode(deviceCode);
    if (listScheduleItem.size() > 0)
      return listScheduleItem.get(0);
    return null;
  }
}
