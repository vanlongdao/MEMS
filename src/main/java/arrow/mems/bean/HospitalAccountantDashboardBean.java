package arrow.mems.bean;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.service.MasterDashboardService;
import arrow.mems.util.SelectItemGenerator;

@Named
@ViewScoped
public class HospitalAccountantDashboardBean extends MasterDashboardBean {
  @Inject
  private MasterDashboardService masterDashboardService;
  @Inject
  private UserCredential userCredential;

  // For all task of tab view
  private int totalRepairAllTask;
  private double totalRepairCostAllTask;
  private List<ActionLog> listActionLogOfAllTask;

  // Tab payment and budget status
  private List<ActionLog> listWaitingPaymentList;
  private List<ActionLog> paidList;
  private List<Budget> listBudgetStatus;

  private int valueRadioBtn;


  @Override
  public void filtercheduleItem() {
    switch (this.valueRadioBtn) {
      case 1:
        this.getScheItemFilterByCommingCheck();
        break;
      case 2:
        this.getScheItemFilterByCheckthisMonth();
        break;
      case 3:
        this.getScheItemFilterByNextMonth();
        break;
      case 4:
        this.getScheItemFilterByAllCases();
        break;
      default:
        break;
    }
  }



  // LEFT SCREENS

  @Override
  public String getStatusName(ActionLog actionLog) {
    if (actionLog.getFinishDate() != null)
      return SelectItemGenerator.getListSelectItem(SelectItemGenerator.ListType.SCHEDULE_TASK).get(CommonConstants.TASK_DONE - 1).getLabel();
    else if ((actionLog.getOccurDate() != null) && (actionLog.getContactDate() != null))
      return SelectItemGenerator.getListSelectItem(SelectItemGenerator.ListType.SCHEDULE_TASK).get(CommonConstants.TASK_DOING - 1).getLabel();
    else
      return SelectItemGenerator.getListSelectItem(SelectItemGenerator.ListType.SCHEDULE_TASK).get(CommonConstants.TASK_NOT_START - 1).getLabel();
  }

  private HashMap<Integer, Double> ratePaidAndBudget;

  public String getRatePaidAndBudget(int budgetId, String hospDeptCode, double payOfBudget) {
    if (this.ratePaidAndBudget == null) {
      this.ratePaidAndBudget = new HashMap<Integer, Double>();
    }
    if (this.ratePaidAndBudget.get(budgetId) != null)
      return String.format("%.0f%%", this.ratePaidAndBudget.get(budgetId));
    else {
      final double totalPay = this.masterDashboardService.getSumTotalPayByHospDeptCode(hospDeptCode);
      this.ratePaidAndBudget.put(budgetId, ((totalPay / payOfBudget) * 100));
      return String.format("%.0f%%", this.ratePaidAndBudget.get(budgetId));
    }
  }

  public int getTotalRepairAllTask() {
    if (this.totalRepairAllTask == 0) {
      this.totalRepairAllTask = this.masterDashboardService.getTotalRepairCurrentMonthOfAllTask(this.userCredential.getUserInfo().getOfficeCode());
    }
    return this.totalRepairAllTask;
  }

  public void setTotalRepairAllTask(int pTotalRepairAllTask) {
    this.totalRepairAllTask = pTotalRepairAllTask;
  }


  public double getTotalRepairCostAllTask() {
    if (this.totalRepairCostAllTask == 0) {
      this.totalRepairCostAllTask =
          this.masterDashboardService.getTotalRepairCostCurrentMonthOfAllTask(this.userCredential.getUserInfo().getOfficeCode());
    }
    return this.totalRepairCostAllTask;
  }

  public void setTotalRepairCostAllTask(double pTotalRepairCostAllTask) {
    this.totalRepairCostAllTask = pTotalRepairCostAllTask;
  }

  public List<ActionLog> getListActionLogOfAllTask() {
    if (this.listActionLogOfAllTask == null) {
      this.listActionLogOfAllTask = this.masterDashboardService.getListContactOfAllTask(this.userCredential.getUserInfo().getOfficeCode());
    }
    return this.listActionLogOfAllTask;
  }

  public void setListActionLogOfAllTask(List<ActionLog> pListActionLogOfAllTask) {
    this.listActionLogOfAllTask = pListActionLogOfAllTask;
  }


  public List<ActionLog> getListWaitingPaymentList() {
    if (this.listWaitingPaymentList == null) {
      this.listWaitingPaymentList = this.masterDashboardService.getActionLogByTotalPayAndPayDate(this.userCredential.getOfficeCode());
    }
    return this.listWaitingPaymentList;
  }

  public void setListWaitingPaymentList(List<ActionLog> pListWaitingPaymentList) {
    this.listWaitingPaymentList = pListWaitingPaymentList;
  }

  public List<ActionLog> getPaidList() {
    if (this.paidList == null) {
      this.paidList = this.masterDashboardService.getActionLogForPaidList(this.userCredential.getOfficeCode());
    }
    return this.paidList;
  }

  public void setPaidList(List<ActionLog> pPaidList) {
    this.paidList = pPaidList;
  }

  public List<Budget> getListBudgetStatus() {
    if (this.listBudgetStatus == null) {
      this.listBudgetStatus = this.masterDashboardService.getBudgetForBudgetStatus(this.userCredential.getOfficeCode());
    }
    return this.listBudgetStatus;
  }

  public void setListBudgetStatus(List<Budget> pListBudgetStatus) {
    this.listBudgetStatus = pListBudgetStatus;
  }

}
