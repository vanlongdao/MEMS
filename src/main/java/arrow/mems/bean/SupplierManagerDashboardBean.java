package arrow.mems.bean;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.service.MasterDashboardService;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class SupplierManagerDashboardBean extends AbstractBean {
  @Inject
  private UserCredential userCredential;
  @Inject
  private MasterDashboardService masterDashboardService;

  private String hospitalCode;
  private String contactPerson;
  private List<HumanResource> listHumanResource;

  public int totalRepairOfCurrentMonth(boolean isTabClient) {
    if (isTabClient) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalCode))
        return this.totalRepairOfCurrentMonthByClient(this.hospitalCode);
      return 0;
    } else {
      if (ArrowStrUtils.isNotEmpty(this.contactPerson))
        return this.totalRepairOfCurrentMonthByEngineer(this.contactPerson);
      return 0;
    }
  }

  public double totalRepairCostOfCurrentMonth(boolean isTabClient) {
    if (isTabClient) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalCode))
        return this.totalRepairCostCurrentMonthByClient(this.hospitalCode);
      return 0;
    } else {
      if (ArrowStrUtils.isNotEmpty(this.contactPerson))
        return this.totalRepairCostCurrentMonthByEngineer(this.contactPerson);
      return 0;
    }
  }

  public int totalRepairDoing(boolean isTabClient) {
    if (isTabClient) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalCode))
        return this.totalRepairDoingClient(this.hospitalCode);
      return 0;
    } else {
      if (ArrowStrUtils.isNotEmpty(this.contactPerson))
        return this.totalRepairDoingEngineer(this.contactPerson);
      return 0;
    }
  }

  public int totalRepairNotStart(boolean isTabClient) {
    if (isTabClient) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalCode))
        return this.totalRepairNotStartClient(this.hospitalCode);
      return 0;
    } else {
      if (ArrowStrUtils.isNotEmpty(this.contactPerson))
        return this.totalRepairNotStartEngineer(this.contactPerson);
      return 0;
    }
  }

  public int totalRepairItemOfYear(boolean isTabClient) {
    if (isTabClient) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalCode))
        return this.totalRepairItemOfYearClient(this.hospitalCode);
      return 0;
    } else {
      if (ArrowStrUtils.isNotEmpty(this.contactPerson))
        return this.totalRepairItemOfYearEngineer(this.contactPerson);
      return 0;
    }
  }

  public double totalRepairCostItemOfYear(boolean isTabClient) {
    if (isTabClient) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalCode))
        return this.totalRepairItemCostClient(this.hospitalCode);
      return 0;
    } else {
      if (ArrowStrUtils.isNotEmpty(this.contactPerson))
        return this.totalRepairItemCostEngineer(this.contactPerson);
      return 0;
    }
  }

  private HashMap<String, Double> totalRepairItemCostClient;

  public double totalRepairItemCostClient(String hospCode) {
    if (this.totalRepairItemCostClient == null) {
      this.totalRepairItemCostClient = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostClient.get(hospCode) != null)
      return this.totalRepairItemCostClient.get(hospCode);
    else {
      this.totalRepairItemCostClient.put(hospCode, this.masterDashboardService.getTotalRepairItemCostClient(hospCode));
    }
    return this.totalRepairItemCostClient(hospCode);
  }

  private HashMap<String, Double> totalRepairItemCostEngineer;

  public double totalRepairItemCostEngineer(String personCode) {
    if (this.totalRepairItemCostEngineer == null) {
      this.totalRepairItemCostEngineer = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostEngineer.get(personCode) != null)
      return this.totalRepairItemCostEngineer.get(personCode);
    else {
      this.totalRepairItemCostEngineer.put(personCode, this.masterDashboardService.getTotalRepairItemCostEngineer(personCode));
    }
    return this.totalRepairItemCostEngineer.get(personCode);
  }

  private HashMap<String, Integer> totalRepairItemOfYearClient;

  public int totalRepairItemOfYearClient(String hospCode) {
    if (this.totalRepairItemOfYearClient == null) {
      this.totalRepairItemOfYearClient = new HashMap<String, Integer>();
    }
    if (this.totalRepairItemOfYearClient.get(hospCode) != null)
      return this.totalRepairItemOfYearClient.get(hospCode);
    else {
      this.totalRepairItemOfYearClient.put(hospCode, this.masterDashboardService.getTotalRepairItemOfYearClient(hospCode));
    }
    return this.totalRepairItemOfYearClient.get(hospCode);
  }

  private HashMap<String, Integer> totalRepairItemOfYearEngineer;

  public int totalRepairItemOfYearEngineer(String personCode) {
    if (this.totalRepairItemOfYearEngineer == null) {
      this.totalRepairItemOfYearEngineer = new HashMap<String, Integer>();
    }
    if (this.totalRepairItemOfYearEngineer.get(personCode) != null)
      return this.totalRepairItemOfYearEngineer.get(personCode);
    else {
      this.totalRepairItemOfYearEngineer.put(personCode, this.masterDashboardService.getTotalRepairItemOfYearEngineer(personCode));
    }
    return this.totalRepairItemOfYearEngineer.get(personCode);
  }


  private HashMap<String, Integer> totalRepairOfCurrentMonthByClient;

  public int totalRepairOfCurrentMonthByClient(String hospCode) {
    if (this.totalRepairOfCurrentMonthByClient == null) {
      this.totalRepairOfCurrentMonthByClient = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfCurrentMonthByClient.get(hospCode) != null) {
      this.totalRepairOfCurrentMonthByClient.get(hospCode);
    } else {
      this.totalRepairOfCurrentMonthByClient.put(hospCode, this.masterDashboardService.getTotalRepairOfCurrentMonthClient(hospCode));
    }
    return this.totalRepairOfCurrentMonthByClient.get(hospCode);
  }

  private HashMap<String, Integer> totalRepairOfCurrentMonthByEngineer;

  public int totalRepairOfCurrentMonthByEngineer(String hospDeptCode) {
    if (this.totalRepairOfCurrentMonthByEngineer == null) {
      this.totalRepairOfCurrentMonthByEngineer = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfCurrentMonthByEngineer.get(hospDeptCode) != null) {
      this.totalRepairOfCurrentMonthByEngineer.get(hospDeptCode);
    } else {
      this.totalRepairOfCurrentMonthByEngineer.put(hospDeptCode, this.masterDashboardService.getTotalRepairOfCurrentMonthEngineer(hospDeptCode));
    }
    return this.totalRepairOfCurrentMonthByEngineer.get(hospDeptCode);
  }

  private HashMap<String, Double> totalRepairCostCurrentMonthByClient;

  public double totalRepairCostCurrentMonthByClient(String hospCode) {
    if (this.totalRepairCostCurrentMonthByClient == null) {
      this.totalRepairCostCurrentMonthByClient = new HashMap<String, Double>();
    }
    if (this.totalRepairCostCurrentMonthByClient.get(hospCode) != null)
      return this.totalRepairCostCurrentMonthByClient.get(hospCode);
    else {
      this.totalRepairCostCurrentMonthByClient.put(hospCode, this.masterDashboardService.getTotalRepairCostCurrentMonthClient(hospCode));
    }
    return this.totalRepairCostCurrentMonthByClient.get(hospCode);
  }

  private HashMap<String, Double> totalRepairCostCurrentMonthByEngineer;

  public double totalRepairCostCurrentMonthByEngineer(String personCode) {
    if (this.totalRepairCostCurrentMonthByEngineer == null) {
      this.totalRepairCostCurrentMonthByEngineer = new HashMap<String, Double>();
    }
    if (this.totalRepairCostCurrentMonthByEngineer.get(personCode) != null)
      return this.totalRepairCostCurrentMonthByEngineer.get(personCode);
    else {
      this.totalRepairCostCurrentMonthByEngineer.put(personCode, this.masterDashboardService.getTotalRepairCostCurrentMonthEngineer(personCode));
    }
    return this.totalRepairCostCurrentMonthByEngineer.get(personCode);
  }

  private HashMap<String, Integer> totalRepairDoingClient;

  public int totalRepairDoingClient(String hospCode) {
    if (this.totalRepairDoingClient == null) {
      this.totalRepairDoingClient = new HashMap<String, Integer>();
    }
    if (this.totalRepairDoingClient.get(hospCode) != null)
      return this.totalRepairDoingClient.get(hospCode);
    else {
      this.totalRepairDoingClient.put(hospCode, this.masterDashboardService.getTotalRepairDoingClient(hospCode));
    }
    return this.totalRepairDoingClient.get(hospCode);
  }

  private HashMap<String, Integer> totalRepairDoingEngineer;

  public int totalRepairDoingEngineer(String personCode) {
    if (this.totalRepairDoingEngineer == null) {
      this.totalRepairDoingEngineer = new HashMap<String, Integer>();
    }
    if (this.totalRepairDoingEngineer.get(personCode) != null)
      return this.totalRepairDoingEngineer.get(personCode);
    else {
      this.totalRepairDoingEngineer.put(personCode, this.masterDashboardService.getTotalRepairDoingEngineer(personCode));
    }
    return this.totalRepairDoingEngineer.get(personCode);
  }

  private HashMap<String, Integer> totalRepairNotStartClient;

  public int totalRepairNotStartClient(String hospCode) {
    if (this.totalRepairNotStartClient == null) {
      this.totalRepairNotStartClient = new HashMap<String, Integer>();
    }
    if (this.totalRepairNotStartClient.get(hospCode) != null)
      return this.totalRepairNotStartClient.get(hospCode);
    else {
      this.totalRepairNotStartClient.put(hospCode, this.masterDashboardService.getTotalRepairNotStartClient(hospCode));
    }
    return this.totalRepairNotStartClient.get(hospCode);
  }

  private HashMap<String, Integer> totalRepairNotStartEngineer;

  public int totalRepairNotStartEngineer(String personCode) {
    if (this.totalRepairNotStartEngineer == null) {
      this.totalRepairNotStartEngineer = new HashMap<String, Integer>();
    }
    if (this.totalRepairNotStartEngineer.get(personCode) != null)
      return this.totalRepairNotStartEngineer.get(personCode);
    else {
      this.totalRepairNotStartEngineer.put(personCode, this.masterDashboardService.getTotalRepairNotStartEngineer(personCode));
    }
    return this.totalRepairNotStartEngineer.get(personCode);
  }

  public List<ActionLog> listActionLogInHospitalManager(boolean isTabClient) {
    if (isTabClient) {
      if (ArrowStrUtils.isNotEmpty(this.hospitalCode))
        return this.getListContactHospitalClient(this.hospitalCode);
      return null;
    } else {
      if (ArrowStrUtils.isNotEmpty(this.contactPerson))
        return this.getListContactHospitalEngineer(this.contactPerson);
      return null;
    }
  }

  private HashMap<String, List<ActionLog>> listContactHospitalClient;

  public List<ActionLog> getListContactHospitalClient(String hospCode) {
    if (this.listContactHospitalClient == null) {
      this.listContactHospitalClient = new HashMap<>();
    }
    if (this.listContactHospitalClient.get(hospCode) != null)
      return this.listContactHospitalClient.get(hospCode);
    else {
      this.listContactHospitalClient.put(hospCode, this.masterDashboardService.getListContactClient(hospCode));
    }
    return this.listContactHospitalClient.get(hospCode);
  }

  private HashMap<String, List<ActionLog>> listContactHospitalEngineer;

  public List<ActionLog> getListContactHospitalEngineer(String personCode) {
    if (this.listContactHospitalEngineer == null) {
      this.listContactHospitalEngineer = new HashMap<>();
    }
    if (this.listContactHospitalEngineer.get(personCode) != null)
      return this.listContactHospitalEngineer.get(personCode);
    else {
      this.listContactHospitalEngineer.put(personCode, this.masterDashboardService.getListContactEngineer(personCode));
    }
    return this.listContactHospitalEngineer.get(personCode);
  }

  public List<HumanResource> getListHumanResource() {
    if (this.listHumanResource == null) {
      this.listHumanResource = this.masterDashboardService.getListHumanResourceByOfficeCode(this.userCredential.getOfficeCode());
    }
    return this.listHumanResource;
  }

  public void setListHumanResource(List<HumanResource> pListHumanResource) {
    this.listHumanResource = pListHumanResource;
  }

  public String getHospitalCode() {
    return this.hospitalCode;
  }

  public void setHospitalCode(String pHospitalCode) {
    this.hospitalCode = pHospitalCode;
  }

  public String getContactPerson() {
    return this.contactPerson;
  }

  public void setContactPerson(String pContactPerson) {
    this.contactPerson = pContactPerson;
  }



}
