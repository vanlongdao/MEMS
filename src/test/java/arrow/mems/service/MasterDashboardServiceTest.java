package arrow.mems.service;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.annotations.Test;

import arrow.framework.util.DateUtils;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.persistence.entity.CountScheduleItem;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.NoticeLog;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.persistence.repository.ActionLogRepository;
import arrow.mems.persistence.repository.BudgetRepository;
import arrow.mems.persistence.repository.CountScheduleItemRepository;
import arrow.mems.persistence.repository.ScheduleItemRepository;

@Test
@UsingDataSet("datasets/long_dataset.xls")
public class MasterDashboardServiceTest extends Arquillian {

  @Inject
  private MasterDashboardService testInstance;
  @Inject
  private ActionLogRepository actionLogRepository;
  @Inject
  private ScheduleItemRepository scheduleItemRepository;
  @Inject
  private CountScheduleItemRepository countScheItemRepository;
  @Inject
  private BudgetRepository budgetRepository;

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairCurrentMonthOfMyTask() throws Exception {
    final long result =
        this.actionLogRepository.getTotalRepairCurrentMonthOfMyTask(1, DateUtils.getDateByInputNumber(2014, 10, 1),
            DateUtils.getDateByInputNumber(2014, 10, 30), CommonConstants.ACTIVE);
    Assertions.assertThat(result).isEqualTo(3);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairCostCurrentMonthOfMyTask() throws Exception {
    final double result =
        this.actionLogRepository.getTotalRepairCostCurrentMonthOfMyTask(1, DateUtils.getDateByInputNumber(2014, 10, 1),
            DateUtils.getDateByInputNumber(2014, 10, 30), CommonConstants.ACTIVE);
    Assertions.assertThat(result).isEqualTo(21200);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairDoingMyTask() throws Exception {
    final long result = this.testInstance.getTotalRepairDoingMyTask(1);
    Assertions.assertThat(result).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairNotStartMyTask() throws Exception {
    final long result = this.testInstance.getTotalRepairNotStartMyTask(1);
    Assertions.assertThat(result).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairCurrentMonthOfAllTask() throws Exception {
    final long result =
        this.actionLogRepository.getTotalRepairCurrentMonthOfAllTask(DateUtils.getDateByInputNumber(2014, 10, 1),
            DateUtils.getDateByInputNumber(2014, 10, 31), CommonConstants.ACTIVE, "11400001");
    Assertions.assertThat(result).isEqualTo(4);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairCostCurrentMonthOfAllTask() throws Exception {
    final double result =
        this.actionLogRepository.getTotalRepairCostCurrentMonthOfAllTask(DateUtils.getDateByInputNumber(2014, 10, 1),
            DateUtils.getDateByInputNumber(2014, 10, 31), CommonConstants.ACTIVE, "11400001");
    Assertions.assertThat(result).isEqualTo(31200);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairDoingAllTask() throws Exception {
    final int result = this.testInstance.getTotalRepairDoingAllTask("11400001");
    Assertions.assertThat(result).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairNotStartAllTask() throws Exception {
    final long result = this.actionLogRepository.getTotalRepairNotStartOfAllTask(CommonConstants.ACTIVE, "11400001");
    Assertions.assertThat(result).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetListContactOfMyTask() throws Exception {
    final List<ActionLog> result = this.testInstance.getListContactOfMyTask(1);
    Assertions.assertThat(result.size()).isEqualTo(3);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetListContactOfAllTask() throws Exception {
    final List<ActionLog> result = this.testInstance.getListContactOfAllTask("11400001");
    Assertions.assertThat(result.size()).isEqualTo(4);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetScheItemFilterByStartToEndDate() throws Exception {
    final List<ScheduleItem> result =
        this.scheduleItemRepository.findScheItemsFilterByStartToEndDate(CommonConstants.ACTIVE, LocalDate.now(),
            DateUtils.getNextDateFromCurrentDay(30), "11400001").getResultList();
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetCountScheItemFilterByStartToEndDate() throws Exception {
    final List<CountScheduleItem> result =
        this.countScheItemRepository.findCountScheItemsFilterByStartToEndDate(CommonConstants.ACTIVE, LocalDate.now(),
            DateUtils.getNextDateFromFirstDayOfMonth(30), "11400001").getResultList();
    Assertions.assertThat(result.size()).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetNoticeLogByOfficeCode() throws Exception {
    final List<NoticeLog> result = this.testInstance.getNoticeLogByOfficeCode("11400001");
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetDeliverySchedule() throws Exception {
    final List<PartOrderItem> result = this.testInstance.getDeliverySchedule("11400001");
    Assertions.assertThat(result.size()).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetHospitalDeptByOfficeCode() throws Exception {
    final List<HospitalDept> result = this.testInstance.getHospitalDeptByOfficeCode("11400001");
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairOfCurrentMonthDepartment() throws Exception {
    final long result =
        this.actionLogRepository.getTotalRepairCurrentMonthDepartment("123456", DateUtils.getDateByInputNumber(2014, 10, 1),
            DateUtils.getDateByInputNumber(2014, 10, 31), CommonConstants.ACTIVE);
    Assertions.assertThat(result).isEqualTo(4);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairCostCurrentMonthDepartment() throws Exception {
    final double result =
        this.actionLogRepository.getTotalRepairCostCurrentMonthOfDepartment(DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, "123456");
    Assertions.assertThat(result).isEqualTo(31200);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairDoingDepartment() throws Exception {
    final int result = this.testInstance.getTotalRepairDoingDepartment("123456");
    Assertions.assertThat(result).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairNotStartDepartment() throws Exception {
    final int result = this.testInstance.getTotalRepairNotStartDepartment("123456");
    Assertions.assertThat(result).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairItemOfYearAllTasks() throws Exception {
    final long result =
        this.actionLogRepository.getTotalRepairCurrentMonthOfAllTask(DateUtils.getDateByInputNumber(2014, 1, 1),
            DateUtils.getDateByInputNumber(2014, 12, 31), CommonConstants.ACTIVE, "11400001");
    Assertions.assertThat(result).isEqualTo(4);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairItemOfYearDepartment() throws Exception {
    final long result =
        this.actionLogRepository.getTotalRepairCurrentMonthDepartment("123456", DateUtils.getDateByInputNumber(2014, 1, 1),
            DateUtils.getDateByInputNumber(2014, 12, 31), CommonConstants.ACTIVE);
    Assertions.assertThat(result).isEqualTo(4);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairItemCostAllTasks() throws Exception {
    final double result =
        this.actionLogRepository.getTotalRepairCostCurrentMonthOfAllTask(DateUtils.getDateByInputNumber(2014, 1, 1),
            DateUtils.getDateByInputNumber(2014, 12, 31), CommonConstants.ACTIVE, "11400001");
    Assertions.assertThat(result).isEqualTo(31200);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairItemCostDepartment() throws Exception {
    final double result =
        this.actionLogRepository.getTotalRepairCostCurrentMonthOfDepartment(DateUtils.getFirstDateOfCurrentMonth(),
            DateUtils.getLastDateOfCurrentMonth(), CommonConstants.ACTIVE, "123456");
    Assertions.assertThat(result).isEqualTo(31200);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetListContactDepartment() throws Exception {
    final List<ActionLog> result = this.testInstance.getListContactDepartment("123456");
    Assertions.assertThat(result.size()).isEqualTo(4);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairCurrentMonthOfMyTaskByHospPsnCode() throws Exception {
    final long result =
        this.actionLogRepository.getTotalRepairCurrentMonthOfMyTaskByHospContactPsn(DateUtils.getDateByInputNumber(2014, 10, 1),
            DateUtils.getDateByInputNumber(2014, 10, 31), CommonConstants.ACTIVE, 1);
    Assertions.assertThat(result).isEqualTo(3);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairDoingMyTaskByHospPnsCode() throws Exception {
    final int result = this.testInstance.getTotalRepairDoingMyTaskByHospPnsCode(1);
    Assertions.assertThat(result).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairCostCurrentMonthOfMyTaskByHospPnsCode() throws Exception {
    final double result = this.testInstance.getTotalRepairCostCurrentMonthOfMyTaskByHospPnsCode(1);
    Assertions.assertThat(result).isEqualTo(21200);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetTotalRepairNotStartMyTaskByHospPnsCode() throws Exception {
    final long result = this.testInstance.getTotalRepairNotStartMyTaskByHospPnsCode(1);
    Assertions.assertThat(result).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetListContactOfMyTaskByHospPnsCode() throws Exception {
    final List<ActionLog> result = this.testInstance.getListContactOfMyTaskByHospPnsCode(1);
    Assertions.assertThat(result.size()).isEqualTo(3);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetActionLogByTotalPayAndPayDate() throws Exception {
    final List<ActionLog> result =
        this.actionLogRepository.findActionLogByTotalPayAndPayDate(CommonConstants.ACTIVE, DateUtils.getDateByInputNumber(2014, 10, 7), "11400001")
            .getResultList();
    Assertions.assertThat(result.size()).isEqualTo(3);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetActionLogForPaidList() throws Exception {
    final List<ActionLog> result =
        this.actionLogRepository.findActionLogWhenSettedTotalPayAndPayDate(CommonConstants.ACTIVE, DateUtils.getDateByInputNumber(2014, 10, 7),
            "11400001").getResultList();
    Assertions.assertThat(result.size()).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetBudgetForBudgetStatus() throws Exception {
    final List<Budget> result =
        this.budgetRepository.findBudgetByDateAndHospDeptCode(CommonConstants.ACTIVE, DateUtils.getDateByInputNumber(2014, 10, 7), "11400001")
        .getResultList();
    Assertions.assertThat(result.size()).isEqualTo(3);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetSumTotalPayByHospDeptCode() throws Exception {
    final double result =
        this.actionLogRepository.sumTotalPayByHospDeptCode(CommonConstants.ACTIVE, DateUtils.getDateByInputNumber(2014, 10, 7), "123456");
    Assertions.assertThat(result).isEqualTo(10000);
  }
}
