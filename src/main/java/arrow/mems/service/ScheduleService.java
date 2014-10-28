package arrow.mems.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.generator.ScheduleItemIdGenerator;
import arrow.mems.persistence.dto.DeviceDto;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Schedule;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.ScheduleItemRepository;
import arrow.mems.persistence.repository.ScheduleRepository;

@Named
public class ScheduleService extends AbstractService {
  @Inject
  OperationLogService logService;
  @Inject
  UserCredential userCredential;
  @Inject
  ScheduleRepository scheduleRepo;
  @Inject
  ScheduleItemRepository scheduleItemRepo;
  @Inject
  DeviceRepository deviceRepo;

  public ServiceResult<ScheduleItem> saveScheduleItemForDeviceWhenChangeIntervalMonth(String mDeviceCode) {
    ServiceResult<ScheduleItem> result = null;
    final Schedule schedule = this.scheduleRepo.findAllScheduleByMDeviceCode(mDeviceCode).getAnyResult();
    final Device deviceInMDevice = this.deviceRepo.findActiveDeviceByMDeviceCode(mDeviceCode);
    final DeviceDto currentDevice = new DeviceDto();
    BeanCopier.copy(deviceInMDevice, currentDevice);
    final List<ScheduleItem> listScheduleItemByDevice = this.scheduleItemRepo.findScheduleItemByMDeviceCode(mDeviceCode);
    // Check when schedule not created for master device
    if (schedule != null) {
      // for delete
      for (final ScheduleItem scheduleItemByDevice : listScheduleItemByDevice) {
        scheduleItemByDevice.setIsDeleted(CommonConstants.DELETE);
        this.scheduleItemRepo.saveAndFlush(scheduleItemByDevice);
      }
      result =
          this.createScheduleItemForDevice(currentDevice, schedule, currentDevice.getAcceptanceDate(), currentDevice.getExpireDate(), null, null);
    }
    return result;
  }

  public ServiceResult<ScheduleItem> saveScheduleItemForDevice(DeviceDto currentDevice, String mDeviceCode, LocalDate acceptDate,
      LocalDate expireDate, boolean isUpdate) {
    ServiceResult<ScheduleItem> result = null;
    final Schedule schedule = this.scheduleRepo.findAllScheduleByMDeviceCode(mDeviceCode).getAnyResult();
    final List<ScheduleItem> listScheduleItemByDevice = this.scheduleItemRepo.findScheduleItemByDeviceCode(currentDevice.getDevCode());
    // Check when shedule not created for master device
    if (schedule != null) {
      if (!isUpdate) {
        this.createScheduleItemForDevice(currentDevice, schedule, acceptDate, expireDate, null, null);
      } else {
        // for delete
        for (final ScheduleItem scheduleItemByDevice : listScheduleItemByDevice) {
          scheduleItemByDevice.setIsDeleted(CommonConstants.DELETE);
          this.scheduleItemRepo.saveAndFlush(scheduleItemByDevice);
        }
        // for insert
        // final ScheduleItem scheduleItemByDevice = listScheduleItemByDevice.get(0);
        // if (scheduleItemByDevice.isPassedApprovalProcess()) {
        // this.scheduleItemRepo.saveAndFlush(scheduleItemByDevice);
        // result =
        // this.createScheduleItemForDevice(currentDevice, schedule, acceptDate, expireDate,
        // this.userCredential.getUserId(), LocalDateTime.now());
        // } else {
        // this.scheduleItemRepo.saveAndFlush(scheduleItemByDevice);
        result = this.createScheduleItemForDevice(currentDevice, schedule, acceptDate, expireDate, null, null);

      }
    }
    return result;
  }

  public ServiceResult<ScheduleItem> createScheduleItemForDevice(DeviceDto currentDevice, Schedule schedule, LocalDate acceptDate,
      LocalDate expireDate, Integer checkedBy, LocalDateTime checkedAt) {
    ServiceResult<ScheduleItem> result = null;
    final int intervalMonth = schedule.getIntervalMonth();
    // Case when intervalMonth is number
    final long durationMonth = ChronoUnit.MONTHS.between(acceptDate, expireDate);
    final long everageMonth = (long) Math.ceil(durationMonth / intervalMonth);
    // check if duration month greater than interval month
    if (durationMonth > intervalMonth) {
      // init new schedule item
      final ScheduleItem scheduleItem = new ScheduleItem();
      // set next schedule item code
      final ScheduleItemIdGenerator generator =
          new ScheduleItemIdGenerator(this.userCredential.getUserInfo().getOfficeCode(), LocalDate.now().getYear());
      final String nextScheduleItemCode = generator.getNext();
      scheduleItem.setSchedBaseCode(nextScheduleItemCode);

      // init TargetDate, StartDate, EndDate
      LocalDate targetDate;
      LocalDate startDate;
      LocalDate endDate;
      // foreach duration time
      for (long durationStart = 1; durationStart <= everageMonth; durationStart++) {
        targetDate = acceptDate.plusDays(durationStart * 30 * intervalMonth);
        startDate = targetDate.minusDays(currentDevice.getHospitalDept().getCheckDaysMargin());
        endDate = targetDate.plusDays(currentDevice.getHospitalDept().getCheckDaysMargin());

        scheduleItem.setTargetDate(targetDate);
        scheduleItem.setStartDate(startDate);
        scheduleItem.setEndDate(endDate);

        scheduleItem.setSchedTitle("Device periodical check " + targetDate.toString());
        scheduleItem.setHospDeptCode(currentDevice.getHospDeptCode());
        scheduleItem.setSchedCode(schedule.getSchedBaseCode());
        scheduleItem.setCklistCode(schedule.getCklistCode());
        scheduleItem.setDevCode(currentDevice.getDevCode());
        scheduleItem.setDescription(schedule.getDescription());

        scheduleItem.setActionCode(StringUtils.EMPTY);
        scheduleItem.setAssignedPsn(currentDevice.getLastServicePsn());
        scheduleItem.setIsDispOff(CommonConstants.ACTIVE);
        scheduleItem.setCheckedAt(checkedAt);
        scheduleItem.setCheckedBy(checkedBy);
        scheduleItem.setIsDeleted(CommonConstants.ACTIVE);
        scheduleItem.setCreatedAt(LocalDateTime.now());
        scheduleItem.setCreatedBy(this.userCredential.getUserId());
        // Save to DB
        this.scheduleItemRepo.saveAndFlush(scheduleItem);
        result = new ServiceResult<>(true, scheduleItem, null);
      }
    }
    return result;
  }

  public void deleteScheduleOfDevice(String mDeviceCode) {
    final List<ScheduleItem> listScheduleItemByDevice = this.scheduleItemRepo.findScheduleItemByDeviceCode(mDeviceCode);
    for (final ScheduleItem scheduleItemByDevice : listScheduleItemByDevice) {
      scheduleItemByDevice.setIsDeleted(CommonConstants.DELETE);
      this.scheduleItemRepo.saveAndFlush(scheduleItemByDevice);
    }
  }

}
