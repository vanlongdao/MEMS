package arrow.mems.service;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.util.DateUtils;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.persistence.entity.CountScheduleItem;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.NoticeLog;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.persistence.repository.ActionLogRepository;
import arrow.mems.persistence.repository.BudgetRepository;
import arrow.mems.persistence.repository.CountScheduleItemRepository;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.HospitalDeptRepository;
import arrow.mems.persistence.repository.HospitalRepository;
import arrow.mems.persistence.repository.HumanResourceRepository;
import arrow.mems.persistence.repository.MDeviceRepository;
import arrow.mems.persistence.repository.NoticeLogRepository;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.persistence.repository.PartOrderItemRepository;
import arrow.mems.persistence.repository.ScheduleItemRepository;

@Named
public class MasterDashboardService extends AbstractService {
  @Inject
  private ActionLogRepository actionLogRepository;
  @Inject
  private ScheduleItemRepository scheduleItemRepository;
  @Inject
  private CountScheduleItemRepository countScheItemRepository;
  @Inject
  private NoticeLogRepository noticelogRepository;
  @Inject
  private PartOrderItemRepository partOrderItemRepository;
  @Inject
  private HospitalDeptRepository hospitalDeptRepository;
  @Inject
  private HospitalRepository hospitalRepository;
  @Inject
  private BudgetRepository budgetRepository;
  @Inject
  private DeviceRepository deviceRepository;
  @Inject
  private MDeviceRepository mDeviceRepository;
  @Inject
  private HumanResourceRepository humanResourceRepository;
  @Inject
  private OfficeRepository officeRepository;

  public int getTotalRepairCurrentMonthOfMyTask(int userId) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairCurrentMonthOfMyTask(userId, DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public double getTotalRepairCostCurrentMonthOfMyTask(int userId) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfMyTask(userId, DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE);
  }

  public int getTotalRepairDoingMyTask(int userId) {
    final long totalRepairDoing = this.actionLogRepository.getTotalRepairDoingOfMyTask(userId, CommonConstants.ACTIVE);
    return (int) totalRepairDoing;
  }

  public int getTotalRepairNotStartMyTask(int userId) {
    final long totalRepairNotStart = this.actionLogRepository.getTotalRepairNotStartOfMyTask(userId, CommonConstants.ACTIVE);
    return (int) totalRepairNotStart;
  }

  public int getTotalRepairCurrentMonthOfAllTask(String ownedOfficeCode) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairCurrentMonthOfAllTask(DateUtils.getFirstDateOfCurrentMonth(), DateUtils.getLastDateOfCurrentMonth(),
            CommonConstants.ACTIVE, ownedOfficeCode);
    return (int) totalRepair;
  }

  public double getTotalRepairCostCurrentMonthOfAllTask(String ownedOfficeCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfAllTask(DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, ownedOfficeCode);
  }

  public int getTotalRepairDoingAllTask(String ownedOfficeCode) {
    final long totalRepairDoing = this.actionLogRepository.getTotalRepairDoingOfAllTask(CommonConstants.ACTIVE, ownedOfficeCode);
    return (int) totalRepairDoing;
  }

  public int getTotalRepairNotStartAllTask(String ownedOfficeCode) {
    final long totalRepairNotStart = this.actionLogRepository.getTotalRepairNotStartOfAllTask(CommonConstants.ACTIVE, ownedOfficeCode);
    return (int) totalRepairNotStart;
  }

  public List<ActionLog> getListContactOfMyTask(int userId) {
    return this.actionLogRepository.findListContactOfMyTask(userId, CommonConstants.ACTIVE).getResultList();
  }

  public List<ActionLog> getListContactOfAllTask(String ownedOfficeCode) {
    return this.actionLogRepository.findListContactOfAllTask(CommonConstants.ACTIVE, ownedOfficeCode).getResultList();
  }

  public List<ScheduleItem> getScheItemFilterByStartToEndDate(String ownedOfficeCode, int numberDays) {
    return this.scheduleItemRepository.findScheItemsFilterByStartToEndDate(CommonConstants.ACTIVE, LocalDate.now(),
        DateUtils.getNextDateFromCurrentDay(numberDays), ownedOfficeCode).getResultList();
  }

  public List<ScheduleItem> getScheItemFilterByFirstDateOfCurrentMonthToEndDate(String ownedOfficeCode, LocalDate toDate) {
    return this.scheduleItemRepository.findScheItemsFilterByStartToEndDate(CommonConstants.ACTIVE, DateUtils.getFirstDateOfCurrentMonth(), toDate,
        ownedOfficeCode).getResultList();
  }

  public List<CountScheduleItem> getCountScheItemFilterByStartToEndDate(String ownedOfficeCode, int numberDays) {
    return this.countScheItemRepository.findCountScheItemsFilterByStartToEndDate(CommonConstants.ACTIVE, LocalDate.now(),
        DateUtils.getNextDateFromFirstDayOfMonth(numberDays), ownedOfficeCode).getResultList();
  }

  public List<CountScheduleItem> getCountScheItemFilterByFirstDateOfCurrentMonthToEndDate(String ownedOfficeCode, LocalDate toDate) {
    return this.countScheItemRepository.findCountScheItemsFilterByStartToEndDate(CommonConstants.ACTIVE, DateUtils.getFirstDateOfCurrentMonth(),
        toDate, ownedOfficeCode).getResultList();
  }

  public List<NoticeLog> getNoticeLogByOfficeCode(String ownedOfficeCode) {
    return this.noticelogRepository.findNoticeLogByOfficeCode(CommonConstants.ACTIVE, ownedOfficeCode).getResultList();
  }

  public List<PartOrderItem> getDeliverySchedule(String officeCode) {
    return this.partOrderItemRepository.findPartOrderItemByOfficeCodeAndETA(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public List<HospitalDept> getHospitalDeptByOfficeCode(String officeCode) {
    return this.hospitalDeptRepository.findHospitalDeptByUserId(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public int getTotalRepairOfCurrentMonthDepartment(String hospDeptCode) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairCurrentMonthDepartment(hospDeptCode, DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public double getTotalRepairCostCurrentMonthDepartment(String hospDeptCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfDepartment(DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, hospDeptCode);
  }

  public int getTotalRepairDoingDepartment(String hospDeptCode) {
    final long totalRepair = this.actionLogRepository.getTotalRepairDoingDepartment(CommonConstants.ACTIVE, hospDeptCode);
    return (int) totalRepair;
  }

  public int getTotalRepairNotStartDepartment(String hospDeptCode) {
    final long totalRepairNotStart = this.actionLogRepository.getTotalRepairNotStartDepartment(CommonConstants.ACTIVE, hospDeptCode);
    return (int) totalRepairNotStart;
  }

  public int getTotalRepairItemOfYearAllTasks(String ownedOfficeCode) {
    final long totalRepairNotStart =
        this.actionLogRepository.getTotalRepairCurrentMonthOfAllTask(DateUtils.getFirstDayOfCurrentYear(), DateUtils.getLastDayOfCurrentYear(),
            CommonConstants.ACTIVE, ownedOfficeCode);
    return (int) totalRepairNotStart;
  }

  public int getTotalRepairItemOfYearDepartment(String hospDeptCode) {
    final long totalRepairNotStart =
        this.actionLogRepository.getTotalRepairCurrentMonthDepartment(hospDeptCode, DateUtils.getFirstDayOfCurrentYear(),
            DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE);
    return (int) totalRepairNotStart;
  }

  public double getTotalRepairItemCostAllTasks(String officeCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfAllTask(DateUtils.getFirstDayOfCurrentYear(),
        DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE, officeCode);
  }

  public double getTotalRepairItemCostDepartment(String hospDeptCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfDepartment(DateUtils.getFirstDayOfCurrentYear(),
        DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE, hospDeptCode);
  }

  public List<ActionLog> getListContactDepartment(String hospDeptCode) {
    return this.actionLogRepository.findListActionLogByHospDeptCode(CommonConstants.ACTIVE, hospDeptCode).getResultList();
  }

  public int getTotalRepairCurrentMonthOfMyTaskByHospPsnCode(int userId) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairCurrentMonthOfMyTaskByHospContactPsn(DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, userId);
    return (int) totalRepair;
  }

  public int getTotalRepairDoingMyTaskByHospPnsCode(int userId) {
    final long totalRepairDoing = this.actionLogRepository.getTotalRepairDoingOfMyTaskByHospPsnCode(CommonConstants.ACTIVE, userId);
    return (int) totalRepairDoing;
  }

  public double getTotalRepairCostCurrentMonthOfMyTaskByHospPnsCode(int userId) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfMyTaskByHospPnsCode(DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, userId);
  }


  public int getTotalRepairNotStartMyTaskByHospPnsCode(int userId) {
    final long totalRepairNotStart = this.actionLogRepository.getTotalRepairNotStartOfMyTask(userId, CommonConstants.ACTIVE);
    return (int) totalRepairNotStart;
  }

  public List<ActionLog> getListContactOfMyTaskByHospPnsCode(int userId) {
    return this.actionLogRepository.findListContactOfMyTaskByHospPnsCode(CommonConstants.ACTIVE, userId).getResultList();
  }

  public List<ActionLog> getActionLogByTotalPayAndPayDate(String officeCode) {
    return this.actionLogRepository.findActionLogByTotalPayAndPayDate(CommonConstants.ACTIVE, LocalDate.now(), officeCode).getResultList();
  }

  public List<ActionLog> getActionLogForPaidList(String officeCode) {
    return this.actionLogRepository.findActionLogWhenSettedTotalPayAndPayDate(CommonConstants.ACTIVE, LocalDate.now(), officeCode).getResultList();
  }

  public List<Budget> getBudgetForBudgetStatus(String officeCode) {
    return this.budgetRepository.findBudgetByDateAndHospDeptCode(CommonConstants.ACTIVE, LocalDate.now(), officeCode).getResultList();
  }

  public double getSumTotalPayByHospDeptCode(String hospDeptCode) {
    return this.actionLogRepository.sumTotalPayByHospDeptCode(CommonConstants.ACTIVE, LocalDate.now(), hospDeptCode);
  }

  // / Not yet write unit test 2014-10-09
  public List<Device> getDeviceByOfficeCode(String officeCode) {
    return this.deviceRepository.findDevicesByOfficeCode(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public List<ActionLog> getActionLogByOfficeCode(String officeCode) {
    return this.actionLogRepository.findActionLogByOfficeCode(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public ActionLog getActionLogByOfficeCodeBeforOccurDate(String officeCode, LocalDate occurDate, int currentActionLogId) {
    return this.actionLogRepository.findActionLogByOfficeCodeBeforOccurDate(CommonConstants.ACTIVE, officeCode, occurDate, currentActionLogId)
        .getAnyResult();
  }

  public ActionLog getActionLogByOfficeCodeAfterOccurDate(String officeCode, LocalDate occurDate, int currentActionLogId) {
    return this.actionLogRepository.findActionLogByOfficeCodeAfterOccurDate(CommonConstants.ACTIVE, officeCode, occurDate, currentActionLogId)
        .getAnyResult();
  }

  public int deleteScheduleItemByCode(String scheduleCode) {
    return this.scheduleItemRepository.updateScheduleItemByCode(CommonConstants.DELETE, scheduleCode);
  }

  public int deleteCountScheItemByCode(String countScheCode) {
    return this.countScheItemRepository.updateCountScheduleItemByCode(CommonConstants.DELETE, countScheCode);
  }

  public int deleteNoticeLogById(int noticeLogId) {
    return this.noticelogRepository.updateNoticeLogById(CommonConstants.DELETE, noticeLogId);
  }

  public List<HumanResource> getListHumanResourceByOfficeCode(String officeCode) {
    return this.humanResourceRepository.findHumanResourcesByOfficeCode(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public Device getDeviceByDevCode(String devCode) {
    return this.deviceRepository.findActiveDeviceByDeviceCode(devCode).getAnyResult();
  }

  public double getTotalRepairItemCostClient(String hospCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfClient(DateUtils.getFirstDayOfCurrentYear(), DateUtils.getLastDayOfCurrentYear(),
        CommonConstants.ACTIVE, hospCode);
  }

  public double getTotalRepairItemCostEngineer(String personCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfEngineer(DateUtils.getFirstDayOfCurrentYear(),
        DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE, personCode);
  }

  public int getTotalRepairItemOfYearClient(String hospCode) {
    final long totalRepairNotStart =
        this.actionLogRepository.getTotalRepairCurrentMonthClient(hospCode, DateUtils.getFirstDayOfCurrentYear(),
            DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE);
    return (int) totalRepairNotStart;
  }

  public int getTotalRepairItemOfYearEngineer(String personCode) {
    final long totalRepairNotStart =
        this.actionLogRepository.getTotalRepairCurrentMonthEngineer(personCode, DateUtils.getFirstDayOfCurrentYear(),
            DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE);
    return (int) totalRepairNotStart;
  }

  public int getTotalRepairOfCurrentMonthClient(String hospCode) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairCurrentMonthClient(hospCode, DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public int getTotalRepairOfCurrentMonthEngineer(String hospDeptCode) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairCurrentMonthEngineer(hospDeptCode, DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public double getTotalRepairCostCurrentMonthClient(String hospCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfClient(DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, hospCode);
  }

  public double getTotalRepairCostCurrentMonthEngineer(String personCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOfEngineer(DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, personCode);
  }

  public int getTotalRepairDoingClient(String hospCode) {
    final long totalRepair = this.actionLogRepository.getTotalRepairDoingClient(CommonConstants.ACTIVE, hospCode);
    return (int) totalRepair;
  }

  public int getTotalRepairDoingEngineer(String personCode) {
    final long totalRepair = this.actionLogRepository.getTotalRepairDoingEngineer(CommonConstants.ACTIVE, personCode);
    return (int) totalRepair;
  }

  public int getTotalRepairNotStartClient(String hospCode) {
    final long totalRepairNotStart = this.actionLogRepository.getTotalRepairNotStartClient(CommonConstants.ACTIVE, hospCode);
    return (int) totalRepairNotStart;
  }

  public int getTotalRepairNotStartEngineer(String personCode) {
    final long totalRepairNotStart = this.actionLogRepository.getTotalRepairNotStartEngineer(CommonConstants.ACTIVE, personCode);
    return (int) totalRepairNotStart;
  }

  public List<ActionLog> getListContactClient(String hospCode) {
    return this.actionLogRepository.findListActionLogByHospCode(CommonConstants.ACTIVE, hospCode).getResultList();
  }

  public List<ActionLog> getListContactEngineer(String personCode) {
    return this.actionLogRepository.findListActionLogByContactPsn(CommonConstants.ACTIVE, personCode).getResultList();
  }

  public List<ActionLog> getListContactOfAllTaskByCorpCode(String ownedOfficeCode) {
    return this.actionLogRepository.findListContactOfAllTaskByCorpCode(CommonConstants.ACTIVE, ownedOfficeCode).getResultList();
  }

  public int getTotalRepairCurrentMonthOfAllTaskByCorpCode(String ownedOfficeCode) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairCurrentMonthOrYearOfAllTaskCorpCode(DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, ownedOfficeCode);
    return (int) totalRepair;
  }

  public int getTotalRepairItemOfYearAllTaskByCorpCode(String ownedOfficeCode) {
    final long totalRepairNotStart =
        this.actionLogRepository.getTotalRepairCurrentMonthOrYearOfAllTaskCorpCode(DateUtils.getFirstDayOfCurrentYear(),
            DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE, ownedOfficeCode);
    return (int) totalRepairNotStart;
  }

  public double getTotalRepairCostCurrentMonthOfAllTaskByCorpCode(String ownedOfficeCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOrYearOfAllTask(DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, ownedOfficeCode);
  }

  public double getTotalRepairCostCurrentYearOfAllTaskByCorpCode(String ownedOfficeCode) {
    return this.actionLogRepository.getTotalRepairCostCurrentMonthOrYearOfAllTask(DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, ownedOfficeCode);
  }

  public List<Device> getListDeviceByCorpCodeAndOfficeCode(String officeCode) {
    return this.deviceRepository.findDevicesByCorpCodeAndOfficeCode(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public List<Office> getListOfficeByCorpCodeAndOfficeCode(String officeCode) {
    return this.officeRepository.findOfficeByCorpCodeAndOfficeCode(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public List<MDevice> getListMdeviceByCorpCodeAndOfficeCode(String officeCode) {
    return this.mDeviceRepository.findMdevicesByCorpCodeAndOfficeCode(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public double getTotalRepairCostCurrentMonthBySupplierOffice(String supplierOffice) {
    return this.actionLogRepository.getTotalRepairItemCostBySupplierOffice(DateUtils.getFirstDateOfCurrentMonth(),
        DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, supplierOffice);
  }

  public double getTotalRepairCostOfYearBySupplierOffice(String supplierOffice) {
    return this.actionLogRepository.getTotalRepairItemCostBySupplierOffice(DateUtils.getFirstDayOfCurrentYear(), DateUtils.getLastDayOfCurrentYear(),
        CommonConstants.ACTIVE, supplierOffice);
  }

  public double getTotalRepairCostCurrentMonthByModelNo(String modelNo) {
    return this.actionLogRepository.getTotalRepairItemCostByModelNo(DateUtils.getFirstDateOfCurrentMonth(), DateUtils.getLastDateOfCurrentMonth(),
        CommonConstants.ACTIVE, modelNo);
  }

  public double getTotalRepairCostOfYearByModelNo(String modelNo) {
    return this.actionLogRepository.getTotalRepairItemCostByModelNo(DateUtils.getFirstDayOfCurrentYear(), DateUtils.getLastDayOfCurrentYear(),
        CommonConstants.ACTIVE, modelNo);
  }

  public int getTotalRepairOfCurrentMonthBySupplier(String supplierOffice) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairBySupplierOffice(supplierOffice, DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public int getTotalRepairOfCurrentMonthByModelNo(String modelNo) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairByModelNo(modelNo, DateUtils.getFirstDateOfCurrentMonth(), DateUtils.getLastDateOfCurrentMonth(),
            CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public int getTotalRepairOfYearBySupplier(String supplierOffice) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairBySupplierOffice(supplierOffice, DateUtils.getFirstDayOfCurrentYear(),
            DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public int getTotalRepairOfYearByModelNo(String modelNo) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairByModelNo(modelNo, DateUtils.getFirstDayOfCurrentYear(), DateUtils.getLastDayOfCurrentYear(),
            CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public List<ActionLog> getListContactBySupplier(String supplierCode) {
    return this.actionLogRepository.findListActionLogBySupplier(CommonConstants.ACTIVE, supplierCode).getResultList();
  }

  public List<ActionLog> getListContactByModelNo(String modelNo) {
    return this.actionLogRepository.findListActionLogByModelNo(CommonConstants.ACTIVE, modelNo).getResultList();
  }

  public List<ActionLog> getListContactByHospial(String hospitalCode) {
    return this.actionLogRepository.findListActionLogByHospital(CommonConstants.ACTIVE, hospitalCode).getResultList();
  }

  public List<Hospital> getListHospitalByOfficeCode(String officeCode) {
    return this.hospitalRepository.findHospitalByOfficeCode(CommonConstants.ACTIVE, officeCode).getResultList();
  }

  public int getTotalRepairOfCurrentMonthByHospital(String hospitalCode) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairByHospitalCode(hospitalCode, DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public int getTotalRepairOfYearByHospital(String hospitalCode) {
    final long totalRepair =
        this.actionLogRepository.getTotalRepairByHospitalCode(hospitalCode, DateUtils.getFirstDayOfCurrentYear(),
            DateUtils.getLastDayOfCurrentYear(), CommonConstants.ACTIVE);
    return (int) totalRepair;
  }

  public double getTotalRepairCostCurrentMonthByHospital(String hospitalCode) {
    return this.actionLogRepository.getTotalRepairItemCostByHospital(DateUtils.getFirstDateOfCurrentMonth(), DateUtils.getLastDateOfCurrentMonth(),
        CommonConstants.ACTIVE, hospitalCode);
  }

  public double getTotalRepairCostOfYearByHospital(String hospitalCode) {
    return this.actionLogRepository.getTotalRepairItemCostByHospital(DateUtils.getFirstDayOfCurrentYear(), DateUtils.getLastDayOfCurrentYear(),
        CommonConstants.ACTIVE, hospitalCode);
  }

  // public List<Device> listDeviceExpiredDate() {
  // return deviceRepository.
  // }
}
