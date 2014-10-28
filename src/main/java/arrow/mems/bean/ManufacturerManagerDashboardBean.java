package arrow.mems.bean;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.Office;
import arrow.mems.service.MasterDashboardService;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class ManufacturerManagerDashboardBean extends AbstractBean {
  @Inject
  private UserCredential userCredential;
  @Inject
  private MasterDashboardService masterDashboardService;

  private String modelNo;
  private String supplierOffice;
  private String hospitalCode;

  private List<MDevice> listMdevicesForModelNo;
  private List<Device> listDevicesForSupplier;
  private List<Office> listOfficesForSupplier;

  // For MANUFACTURER ENGINEER 09
  private List<Hospital> listHospital;

  public int totalRepairOfCurrentMonth(boolean isTabModel, String dashboardCode) {
    if (isTabModel) {
      if (ArrowStrUtils.isNotEmpty(this.modelNo))
        return this.totalRepairOfCurrentMonthByModel(this.modelNo);
      return 0;
    } else if (ArrowStrUtils.isNotEmpty(this.supplierOffice) && ArrowStrUtils.isEmpty(dashboardCode))
      return this.totalRepairOfCurrentMonthBySupplierOffice(this.supplierOffice);
    else if (ArrowStrUtils.isNotEmpty(this.hospitalCode) && ArrowStrUtils.isNotEmpty(dashboardCode)) {
      if (CommonConstants.MASTER_DASHBORAD_MANUFACTURER_ENGINNER.equalsIgnoreCase(dashboardCode))
        return this.totalRepairOfCurrentMonthByHospitalCode(this.hospitalCode);
    }
    return 0;
  }

  public double totalRepairCostOfCurrentMonth(boolean isTabModel, String dashboardCode) {
    if (isTabModel) {
      if (ArrowStrUtils.isNotEmpty(this.modelNo))
        return this.totalRepairCostCurrentMonthByModel(this.modelNo);
      return 0;
    } else if (ArrowStrUtils.isNotEmpty(this.supplierOffice) && ArrowStrUtils.isEmpty(dashboardCode))
      return this.totalRepairCostCurrentMonthBySupplierOffice(this.supplierOffice);
    else if (ArrowStrUtils.isNotEmpty(this.hospitalCode) && ArrowStrUtils.isNotEmpty(dashboardCode)) {
      if (CommonConstants.MASTER_DASHBORAD_MANUFACTURER_ENGINNER.equalsIgnoreCase(dashboardCode))
        return this.totalRepairCostCurrentMonthByHospital(this.hospitalCode);
    }
    return 0;
  }

  public int totalRepairItemOfYear(boolean isTabModel, String dashboardCode) {
    if (isTabModel) {
      if (ArrowStrUtils.isNotEmpty(this.modelNo))
        return this.totalRepairItemOfYearModel(this.modelNo);
      return 0;
    } else if (ArrowStrUtils.isNotEmpty(this.supplierOffice) && ArrowStrUtils.isEmpty(dashboardCode))
      return this.totalRepairItemOfYearSupplierOffice(this.supplierOffice);
    else if (ArrowStrUtils.isNotEmpty(this.hospitalCode) && ArrowStrUtils.isNotEmpty(dashboardCode)) {
      if (CommonConstants.MASTER_DASHBORAD_MANUFACTURER_ENGINNER.equalsIgnoreCase(dashboardCode))
        return this.totalRepairItemOfYearHospital(this.hospitalCode);
    }
    return 0;
  }

  public double totalRepairCostItemOfYear(boolean isTabModel, String dashboardCode) {
    if (isTabModel) {
      if (ArrowStrUtils.isNotEmpty(this.modelNo))
        return this.totalRepairItemCostOfYearModelNo(this.modelNo);
      return 0;
    } else if (ArrowStrUtils.isNotEmpty(this.supplierOffice) && ArrowStrUtils.isEmpty(dashboardCode))
      return this.totalRepairItemCostOfYearSupplierOffice(this.supplierOffice);
    else if (ArrowStrUtils.isNotEmpty(this.hospitalCode) && ArrowStrUtils.isNotEmpty(dashboardCode)) {
      if (CommonConstants.MASTER_DASHBORAD_MANUFACTURER_ENGINNER.equalsIgnoreCase(dashboardCode))
        return this.totalRepairItemCostOfYearHospital(this.hospitalCode);
    }
    return 0;
  }

  private HashMap<String, Double> totalRepairItemCostOfYearModelNo;

  private double totalRepairItemCostOfYearModelNo(String modelNo) {
    if (this.totalRepairItemCostOfYearModelNo == null) {
      this.totalRepairItemCostOfYearModelNo = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostOfYearModelNo.get(modelNo) != null)
      return this.totalRepairItemCostOfYearModelNo.get(modelNo);
    else {
      this.totalRepairItemCostOfYearModelNo.put(modelNo, this.masterDashboardService.getTotalRepairCostOfYearByModelNo(modelNo));
    }
    return this.totalRepairItemCostOfYearModelNo.get(modelNo);
  }

  private HashMap<String, Double> totalRepairItemCostOfYearBySupplier;

  private double totalRepairItemCostOfYearSupplierOffice(String supplierOffice) {
    if (this.totalRepairItemCostOfYearBySupplier == null) {
      this.totalRepairItemCostOfYearBySupplier = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostOfYearBySupplier.get(supplierOffice) != null)
      return this.totalRepairItemCostOfYearBySupplier.get(supplierOffice);
    else {
      this.totalRepairItemCostOfYearBySupplier.put(supplierOffice,
          this.masterDashboardService.getTotalRepairCostOfYearBySupplierOffice(supplierOffice));
    }
    return this.totalRepairItemCostOfYearBySupplier.get(supplierOffice);
  }

  private HashMap<String, Double> totalRepairItemCostOfYearByHospital;

  private double totalRepairItemCostOfYearHospital(String hospitalCode) {
    if (this.totalRepairItemCostOfYearByHospital == null) {
      this.totalRepairItemCostOfYearByHospital = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostOfYearByHospital.get(hospitalCode) != null)
      return this.totalRepairItemCostOfYearByHospital.get(hospitalCode);
    else {
      this.totalRepairItemCostOfYearByHospital.put(hospitalCode, this.masterDashboardService.getTotalRepairCostOfYearByHospital(hospitalCode));
    }
    return this.totalRepairItemCostOfYearByHospital.get(hospitalCode);
  }

  private HashMap<String, Integer> totalRepairOfCurrentMonthBySupplier;

  private int totalRepairOfCurrentMonthBySupplierOffice(String supplierOffice) {
    if (this.totalRepairOfCurrentMonthBySupplier == null) {
      this.totalRepairOfCurrentMonthBySupplier = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfCurrentMonthBySupplier.get(supplierOffice) != null)
      return this.totalRepairOfCurrentMonthBySupplier.get(supplierOffice);
    else {
      this.totalRepairOfCurrentMonthBySupplier
          .put(supplierOffice, this.masterDashboardService.getTotalRepairOfCurrentMonthBySupplier(supplierOffice));
    }
    return this.totalRepairOfCurrentMonthBySupplier.get(supplierOffice);
  }

  private HashMap<String, Integer> totalRepairOfCurrentMonthByEngineer;

  private int totalRepairOfCurrentMonthByHospitalCode(String hospitalCode) {
    if (this.totalRepairOfCurrentMonthByEngineer == null) {
      this.totalRepairOfCurrentMonthByEngineer = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfCurrentMonthByEngineer.get(hospitalCode) != null)
      return this.totalRepairOfCurrentMonthByEngineer.get(hospitalCode);
    else {
      this.totalRepairOfCurrentMonthByEngineer.put(hospitalCode, this.masterDashboardService.getTotalRepairOfCurrentMonthByHospital(hospitalCode));
    }
    return this.totalRepairOfCurrentMonthByEngineer.get(hospitalCode);
  }

  private HashMap<String, Integer> totalRepairOfCurrentMonthByModelNo;

  private int totalRepairOfCurrentMonthByModel(String modelNo) {
    if (this.totalRepairOfCurrentMonthByModelNo == null) {
      this.totalRepairOfCurrentMonthByModelNo = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfCurrentMonthByModelNo.get(modelNo) != null)
      return this.totalRepairOfCurrentMonthByModelNo.get(modelNo);
    else {
      this.totalRepairOfCurrentMonthByModelNo.put(modelNo, this.masterDashboardService.getTotalRepairOfCurrentMonthByModelNo(modelNo));
    }
    return this.totalRepairOfCurrentMonthByModelNo.get(modelNo);
  }


  private HashMap<String, Double> totalRepairItemCostOfCurrentMonthBySupplier;

  private double totalRepairCostCurrentMonthBySupplierOffice(String supplierOffice) {
    if (this.totalRepairItemCostOfCurrentMonthBySupplier == null) {
      this.totalRepairItemCostOfCurrentMonthBySupplier = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostOfCurrentMonthBySupplier.get(supplierOffice) != null)
      return this.totalRepairItemCostOfCurrentMonthBySupplier.get(supplierOffice);
    else {
      this.totalRepairItemCostOfCurrentMonthBySupplier.put(supplierOffice,
          this.masterDashboardService.getTotalRepairCostCurrentMonthBySupplierOffice(supplierOffice));
    }
    return this.totalRepairItemCostOfCurrentMonthBySupplier.get(supplierOffice);
  }

  private HashMap<String, Double> totalRepairItemCostOfCurrentMonthByModelNo;

  private double totalRepairCostCurrentMonthByModel(String modelNo) {
    if (this.totalRepairItemCostOfCurrentMonthByModelNo == null) {
      this.totalRepairItemCostOfCurrentMonthByModelNo = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostOfCurrentMonthByModelNo.get(modelNo) != null)
      return this.totalRepairItemCostOfCurrentMonthByModelNo.get(modelNo);
    else {
      this.totalRepairItemCostOfCurrentMonthByModelNo.put(modelNo, this.masterDashboardService.getTotalRepairCostCurrentMonthByModelNo(modelNo));
    }
    return this.totalRepairItemCostOfCurrentMonthByModelNo.get(modelNo);
  }

  private HashMap<String, Double> totalRepairItemCostOfCurrentMonthByHospital;

  private double totalRepairCostCurrentMonthByHospital(String hospitalCode) {
    if (this.totalRepairItemCostOfCurrentMonthByHospital == null) {
      this.totalRepairItemCostOfCurrentMonthByHospital = new HashMap<String, Double>();
    }
    if (this.totalRepairItemCostOfCurrentMonthByHospital.get(hospitalCode) != null)
      return this.totalRepairItemCostOfCurrentMonthByHospital.get(hospitalCode);
    else {
      this.totalRepairItemCostOfCurrentMonthByHospital.put(hospitalCode,
          this.masterDashboardService.getTotalRepairCostCurrentMonthByHospital(hospitalCode));
    }
    return this.totalRepairItemCostOfCurrentMonthByHospital.get(hospitalCode);
  }

  private HashMap<String, Integer> totalRepairOfYearBySupplier;

  private int totalRepairItemOfYearSupplierOffice(String supplierOffice) {
    if (this.totalRepairOfYearBySupplier == null) {
      this.totalRepairOfYearBySupplier = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfYearBySupplier.get(supplierOffice) != null)
      return this.totalRepairOfYearBySupplier.get(supplierOffice);
    else {
      this.totalRepairOfYearBySupplier.put(supplierOffice, this.masterDashboardService.getTotalRepairOfYearBySupplier(supplierOffice));
    }
    return this.totalRepairOfYearBySupplier.get(supplierOffice);
  }

  private HashMap<String, Integer> totalRepairOfYearByHospital;

  private int totalRepairItemOfYearHospital(String hospitalCode) {
    if (this.totalRepairOfYearByHospital == null) {
      this.totalRepairOfYearByHospital = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfYearByHospital.get(hospitalCode) != null)
      return this.totalRepairOfYearByHospital.get(hospitalCode);
    else {
      this.totalRepairOfYearByHospital.put(hospitalCode, this.masterDashboardService.getTotalRepairOfYearByHospital(hospitalCode));
    }
    return this.totalRepairOfYearByHospital.get(hospitalCode);
  }

  private HashMap<String, Integer> totalRepairOfYearByModelNo;

  private int totalRepairItemOfYearModel(String modelNo) {
    if (this.totalRepairOfYearByModelNo == null) {
      this.totalRepairOfYearByModelNo = new HashMap<String, Integer>();
    }
    if (this.totalRepairOfYearByModelNo.get(modelNo) != null)
      return this.totalRepairOfYearByModelNo.get(modelNo);
    else {
      this.totalRepairOfYearByModelNo.put(modelNo, this.masterDashboardService.getTotalRepairOfYearByModelNo(modelNo));
    }
    return this.totalRepairOfYearByModelNo.get(modelNo);
  }

  public List<MDevice> getListMdevicesForModelNo() {
    if (this.listMdevicesForModelNo == null) {
      this.listMdevicesForModelNo = this.masterDashboardService.getListMdeviceByCorpCodeAndOfficeCode(this.userCredential.getOfficeCode());
    }
    return this.listMdevicesForModelNo;
  }

  public void setListMdevicesForModelNo(List<MDevice> pListMdevicesForModelNo) {
    this.listMdevicesForModelNo = pListMdevicesForModelNo;
  }

  public List<Device> getListDevicesForSupplier() {
    if (this.listDevicesForSupplier == null) {
      this.listDevicesForSupplier = this.masterDashboardService.getListDeviceByCorpCodeAndOfficeCode(this.userCredential.getOfficeCode());
    }
    return this.listDevicesForSupplier;
  }

  public List<Office> getListOfficesForSupplier() {
    if (this.listOfficesForSupplier == null) {
      this.listOfficesForSupplier = this.masterDashboardService.getListOfficeByCorpCodeAndOfficeCode(this.userCredential.getOfficeCode());
    }
    return this.listOfficesForSupplier;
  }

  public List<Hospital> getListHospital() {
    if (this.listHospital == null) {
      this.listHospital = this.masterDashboardService.getListHospitalByOfficeCode(this.userCredential.getOfficeCode());
    }
    return this.listHospital;
  }

  public void setListDevicesForSupplier(List<Device> pListDevicesForSupplier) {
    this.listDevicesForSupplier = pListDevicesForSupplier;
  }

  public String getModelNo() {
    return this.modelNo;
  }

  public void setModelNo(String pModelNo) {
    this.modelNo = pModelNo;
  }

  public String getSupplierOffice() {
    return this.supplierOffice;
  }

  public void setSupplierOffice(String pSupplierOffice) {
    this.supplierOffice = pSupplierOffice;
  }

  public String getHospitalCode() {
    return this.hospitalCode;
  }

  public void setHospitalCode(String pHospitalCode) {
    this.hospitalCode = pHospitalCode;
  }

  public List<ActionLog> listActionLog(boolean isTabModel, String dashboardCode) {
    if (isTabModel) {
      if (ArrowStrUtils.isNotEmpty(this.modelNo))
        return this.getListActionLogByModelNo(this.modelNo);
      return null;
    } else if (ArrowStrUtils.isNotEmpty(this.supplierOffice))
      return this.getListActionLogBySupplier(this.supplierOffice);
    else if (ArrowStrUtils.isNotEmpty(this.hospitalCode) && ArrowStrUtils.isNotEmpty(dashboardCode)) {
      if (CommonConstants.MASTER_DASHBORAD_MANUFACTURER_ENGINNER.equalsIgnoreCase(dashboardCode))
        return this.getListActionLogByHospital(this.hospitalCode);
    }
    return null;
  }

  private HashMap<String, List<ActionLog>> listActionLogBySupplier;

  public List<ActionLog> getListActionLogBySupplier(String supplierCode) {
    if (this.listActionLogBySupplier == null) {
      this.listActionLogBySupplier = new HashMap<String, List<ActionLog>>();
    }
    if (this.listActionLogBySupplier.get(supplierCode) != null)
      return this.listActionLogBySupplier.get(supplierCode);
    else {
      this.listActionLogBySupplier.put(supplierCode, this.masterDashboardService.getListContactBySupplier(supplierCode));
    }
    return this.listActionLogBySupplier.get(supplierCode);
  }

  private HashMap<String, List<ActionLog>> listActionLogByModelNo;

  public List<ActionLog> getListActionLogByModelNo(String modelNo) {
    if (this.listActionLogByModelNo == null) {
      this.listActionLogByModelNo = new HashMap<String, List<ActionLog>>();
    }
    if (this.listActionLogByModelNo.get(modelNo) != null)
      return this.listActionLogByModelNo.get(modelNo);
    else {
      this.listActionLogByModelNo.put(modelNo, this.masterDashboardService.getListContactByModelNo(modelNo));
    }
    return this.listActionLogByModelNo.get(modelNo);
  }

  private HashMap<String, List<ActionLog>> listActionLogByHospital;

  public List<ActionLog> getListActionLogByHospital(String hospitalCode) {
    if (this.listActionLogByHospital == null) {
      this.listActionLogByHospital = new HashMap<String, List<ActionLog>>();
    }
    if (this.listActionLogByHospital.get(hospitalCode) != null)
      return this.listActionLogByHospital.get(hospitalCode);
    else {
      this.listActionLogByHospital.put(hospitalCode, this.masterDashboardService.getListContactByHospial(hospitalCode));
    }
    return this.listActionLogByModelNo.get(this.modelNo);
  }
}
