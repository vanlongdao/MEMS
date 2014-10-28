package arrow.mems.bean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.service.MasterDashboardService;

@Named
@ViewScoped
public class EngineerSupplierDashboardBean extends MasterDashboardBean {
  @Inject
  private MasterDashboardService masterDashboardService;
  @Inject
  private UserCredential userCredential;

  // For my task of tab view
  private int totalRepairMyTask;
  private double totalRepairCostMyTask;
  private int totalRepairDoingMyTask;
  private int totalRepairNotStartMyTask;
  private List<ActionLog> listActionLogOfMyTask;

  // For all task of tab view
  private int totalRepairAllTask;
  private double totalRepairCostAllTask;
  private int totalRepairDoingAllTask;
  private int totalRepairNotStartAllTask;
  private List<ActionLog> listActionLogOfAllTask;



  // LEFT SCREENS
  public int totalRepairTasksByUser(int userId) {
    if (userId == 0) {
      if (this.totalRepairAllTask == 0) {
        this.totalRepairAllTask = this.masterDashboardService.getTotalRepairCurrentMonthOfAllTask(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairAllTask;
    } else {
      if (this.totalRepairMyTask == 0) {
        this.totalRepairMyTask = this.masterDashboardService.getTotalRepairCurrentMonthOfMyTask(userId);
      }
      return this.totalRepairMyTask;
    }
  }

  public int totalRepairDoingTasksByUser(int userId) {
    if (userId == 0) {
      if (this.totalRepairDoingAllTask == 0) {
        this.totalRepairDoingAllTask = this.masterDashboardService.getTotalRepairDoingAllTask(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairDoingAllTask;
    } else {
      if (this.totalRepairDoingMyTask == 0) {
        this.totalRepairDoingMyTask = this.masterDashboardService.getTotalRepairDoingMyTask(userId);
      }
      return this.totalRepairDoingMyTask;
    }
  }

  public double totalRepairCostTasksByUser(int userId) {
    if (userId == 0) {
      if (this.totalRepairCostAllTask == 0) {
        this.totalRepairCostAllTask =
            this.masterDashboardService.getTotalRepairCostCurrentMonthOfAllTask(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairCostAllTask;
    } else {
      if (this.totalRepairCostMyTask == 0) {
        this.totalRepairCostMyTask = this.masterDashboardService.getTotalRepairCostCurrentMonthOfMyTask(userId);
      }
      return this.totalRepairCostMyTask;
    }
  }

  public int totalRepairNotStartTasksByUser(int userId) {
    if (userId == 0) {
      if (this.totalRepairNotStartAllTask == 0) {
        this.totalRepairNotStartAllTask =
            this.masterDashboardService.getTotalRepairNotStartAllTask(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairNotStartAllTask;
    } else {
      if (this.totalRepairNotStartMyTask == 0) {
        this.totalRepairNotStartMyTask = this.masterDashboardService.getTotalRepairNotStartMyTask(userId);
      }
      return this.totalRepairNotStartMyTask;
    }
  }

  public List<ActionLog> listActionLogOfTasksByUser(int userId) {
    if (userId == 0) {
      if (this.listActionLogOfAllTask == null) {
        this.listActionLogOfAllTask = this.masterDashboardService.getListContactOfAllTask(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.listActionLogOfAllTask;
    } else {
      if (this.listActionLogOfMyTask == null) {
        this.listActionLogOfMyTask = this.masterDashboardService.getListContactOfMyTask(this.userCredential.getUserId());
      }
      return this.listActionLogOfMyTask;
    }
  }

}
