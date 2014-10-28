package arrow.mems.bean;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.service.MasterDashboardService;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class HospitalManagerDashboardBean extends MasterDashboardBean {
  @Inject
  private UserCredential userCredential;
  @Inject
  private MasterDashboardService masterDashboardService;

  // Left screens
  private int totalRepairAllTask;
  private double totalRepairCostAllTask;
  private int totalRepairDoingAllTask;
  private int totalRepairNotStartAllTask;
  private int totalRepairItemOfYear;
  private double totalRepairItemCostOfYear;

  private String hospitalDept;
  private List<HospitalDept> listHospitalDept;
  private List<ActionLog> listActionLogOfAllTask;

  /*
   * For left screen
   */
  public int totalRepairOfCurrentMonth(boolean isTabDepartment) {
    if (isTabDepartment) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalDept))
        return this.totalRepairOfCurrentMonthByDepartment(this.hospitalDept);
      return 0;
    } else {
      if (this.totalRepairAllTask == 0) {
        this.totalRepairAllTask = this.masterDashboardService.getTotalRepairCurrentMonthOfAllTask(this.userCredential.getOfficeCode());
      }
      return this.totalRepairAllTask;
    }
  }

  public double totalRepairCostOfCurrentMonth(boolean isTabDepartment) {
    if (isTabDepartment) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalDept))
        return this.totalRepairCostCurrentMonthByDepartment(this.hospitalDept);
      return 0;
    } else {
      if (this.totalRepairCostAllTask == 0) {
        this.totalRepairCostAllTask = this.masterDashboardService.getTotalRepairCostCurrentMonthOfAllTask(this.userCredential.getOfficeCode());
      }
      return this.totalRepairCostAllTask;
    }
  }

  public int totalRepairDoing(boolean isTabDepartment) {
    if (isTabDepartment) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalDept))
        return this.totalRepairDoingDepartment(this.hospitalDept);
      return 0;
    } else {
      if (this.totalRepairDoingAllTask == 0) {
        this.totalRepairDoingAllTask = this.masterDashboardService.getTotalRepairDoingAllTask(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairDoingAllTask;
    }
  }

  public int totalRepairNotStart(boolean isTabDepartment) {
    if (isTabDepartment) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalDept))
        return this.totalRepairNotStartDepartment(this.hospitalDept);
      return 0;
    } else {
      if (this.totalRepairNotStartAllTask == 0) {
        this.totalRepairNotStartAllTask =
            this.masterDashboardService.getTotalRepairNotStartAllTask(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairNotStartAllTask;
    }
  }

  public int totalRepairItemOfYear(boolean isTabDepartment) {
    if (isTabDepartment) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalDept))
        return this.totalRepairItemOfYearDepartment(this.hospitalDept);
      return 0;
    } else {
      if (this.totalRepairItemOfYear == 0) {
        this.totalRepairItemOfYear = this.masterDashboardService.getTotalRepairItemOfYearAllTasks(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairItemOfYear;
    }
  }

  public double totalRepairCostItemOfYear(boolean isTabDepartment) {
    if (isTabDepartment) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalDept))
        return this.totalRepairItemCostDepartment(this.hospitalDept);
      return 0;
    } else {
      if (this.totalRepairItemCostOfYear == 0) {
        this.totalRepairItemCostOfYear =
            this.masterDashboardService.getTotalRepairItemCostAllTasks(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairItemCostOfYear;
    }
  }

  private HashMap<String, Double> totalRepairItemCostDepartment;

  public double totalRepairItemCostDepartment(String hospDeptCode) {
    if (this.totalRepairItemCostDepartment == null) {
      this.totalRepairItemCostDepartment = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostDepartment.get(hospDeptCode) != null)
      return this.totalRepairItemCostDepartment.get(hospDeptCode);
    else {
      this.totalRepairItemCostDepartment.put(hospDeptCode, this.masterDashboardService.getTotalRepairItemCostDepartment(hospDeptCode));
    }
    return this.totalRepairItemCostDepartment(hospDeptCode);
  }

  private HashMap<String, Integer> totalRepairItemOfYearDepartment;

  public int totalRepairItemOfYearDepartment(String hospDeptCode) {
    if (this.totalRepairItemOfYearDepartment == null) {
      this.totalRepairItemOfYearDepartment = new HashMap<String, Integer>();
    }
    if (this.totalRepairItemOfYearDepartment.get(hospDeptCode) != null)
      return this.totalRepairItemOfYearDepartment.get(hospDeptCode);
    else {
      this.totalRepairItemOfYearDepartment.put(hospDeptCode, this.masterDashboardService.getTotalRepairItemOfYearDepartment(hospDeptCode));
    }
    return this.totalRepairItemOfYearDepartment.get(hospDeptCode);
  }

  private HashMap<String, Integer> totalRepairOfCurrentMonthByDepartment;

  public int totalRepairOfCurrentMonthByDepartment(String hospDeptCode) {
    if (this.totalRepairOfCurrentMonthByDepartment == null) {
      this.totalRepairOfCurrentMonthByDepartment = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfCurrentMonthByDepartment.get(hospDeptCode) != null) {
      this.totalRepairOfCurrentMonthByDepartment.get(hospDeptCode);
    } else {
      this.totalRepairOfCurrentMonthByDepartment.put(hospDeptCode, this.masterDashboardService.getTotalRepairOfCurrentMonthDepartment(hospDeptCode));
    }
    return this.totalRepairOfCurrentMonthByDepartment.get(hospDeptCode);
  }

  private HashMap<String, Double> totalRepairCostCurrentMonthByDepartment;

  public double totalRepairCostCurrentMonthByDepartment(String hospDeptCode) {
    if (this.totalRepairCostCurrentMonthByDepartment == null) {
      this.totalRepairCostCurrentMonthByDepartment = new HashMap<String, Double>();
    }
    if (this.totalRepairCostCurrentMonthByDepartment.get(hospDeptCode) != null)
      return this.totalRepairCostCurrentMonthByDepartment.get(hospDeptCode);
    else {
      this.totalRepairCostCurrentMonthByDepartment.put(hospDeptCode,
          this.masterDashboardService.getTotalRepairCostCurrentMonthDepartment(hospDeptCode));
    }
    return this.totalRepairCostCurrentMonthByDepartment.get(hospDeptCode);
  }

  private HashMap<String, Integer> totalRepairDoingDepartment;

  public int totalRepairDoingDepartment(String hospDeptCode) {
    if (this.totalRepairDoingDepartment == null) {
      this.totalRepairDoingDepartment = new HashMap<String, Integer>();
    }
    if (this.totalRepairDoingDepartment.get(hospDeptCode) != null)
      return this.totalRepairDoingDepartment.get(hospDeptCode);
    else {
      this.totalRepairDoingDepartment.put(hospDeptCode, this.masterDashboardService.getTotalRepairDoingDepartment(hospDeptCode));
    }
    return this.totalRepairDoingDepartment.get(hospDeptCode);
  }

  private HashMap<String, Integer> totalRepairNotStartDepartment;

  public int totalRepairNotStartDepartment(String hospDeptCode) {
    if (this.totalRepairNotStartDepartment == null) {
      this.totalRepairNotStartDepartment = new HashMap<String, Integer>();
    }
    if (this.totalRepairNotStartDepartment.get(hospDeptCode) != null)
      return this.totalRepairNotStartDepartment.get(hospDeptCode);
    else {
      this.totalRepairNotStartDepartment.put(hospDeptCode, this.masterDashboardService.getTotalRepairNotStartDepartment(hospDeptCode));
    }
    return this.totalRepairNotStartDepartment.get(hospDeptCode);
  }

  public List<ActionLog> listActionLogInHospitalManager(boolean isTabDepartment) {
    if (isTabDepartment) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalDept))
        return this.getListContactHospitalDepartment(this.hospitalDept);
      return null;
    } else {
      if (this.listActionLogOfAllTask == null) {
        this.listActionLogOfAllTask = this.masterDashboardService.getListContactOfAllTask(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.listActionLogOfAllTask;
    }
  }

  private HashMap<String, List<ActionLog>> listContactHospitalDepartment;

  public List<ActionLog> getListContactHospitalDepartment(String hospDeptCode) {
    if (this.listContactHospitalDepartment == null) {
      this.listContactHospitalDepartment = new HashMap<>();
    }
    if (this.listContactHospitalDepartment.get(hospDeptCode) != null)
      return this.listContactHospitalDepartment.get(hospDeptCode);
    else {
      this.listContactHospitalDepartment.put(hospDeptCode, this.masterDashboardService.getListContactDepartment(hospDeptCode));
    }
    return this.listContactHospitalDepartment.get(hospDeptCode);
  }

  public List<HospitalDept> getListHospitalDept() {
    if (this.listHospitalDept == null) {
      this.listHospitalDept = this.masterDashboardService.getHospitalDeptByOfficeCode(this.userCredential.getOfficeCode());
    }
    return this.listHospitalDept;
  }

  public void setListHospitalDept(List<HospitalDept> pListHospitalDept) {
    this.listHospitalDept = pListHospitalDept;
  }

  public String getHospitalDept() {
    return this.hospitalDept;
  }

  public void setHospitalDept(String pHospitalDept) {
    this.hospitalDept = pHospitalDept;
  }

}
