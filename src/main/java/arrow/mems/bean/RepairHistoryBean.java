package arrow.mems.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.dto.ActionBillDto;
import arrow.mems.persistence.dto.ActionLogDto;
import arrow.mems.persistence.dto.FaultDto;
import arrow.mems.persistence.entity.ActionBill;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Fault;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.service.RepairHistoryService;

@Named
@ViewScoped
public class RepairHistoryBean extends AbstractBean {

  private String deviceName;
  private boolean isMaintenance = false;
  private boolean isPerformanceTesting = false;
  private boolean isCalibration = false;
  private boolean isRepair = false;
  private boolean isQualifiedVerification = false;
  private boolean isTesting = false;
  private boolean isConsulting = false;
  private boolean isManagement = false;
  private boolean isSafetyTesting = false;

  private boolean isEnableFilter;
  private ActionLog selectedActionLog;
  @Inject
  RepairHistoryService repairHistoryService;
  @Inject
  UserCredential userCredential;
  @Inject
  DeviceRepository deviceRepo;

  private List<ReplPart> listReplPart;
  private ActionLogDto currentActionLog;
  private List<ActionLog> listActionLogs;
  private Device device;
  private FaultDto faultDto;
  private ActionBillDto actionBillDto;
  private List<Checklist> listChecklist;
  private List<ChecklistItem> listChecklistItem;
  private List<PartEstimate> listPartEstimate;
  private List<PartEstimateItem> listPartEstimateItem;
  private List<PartOrder> listPartOrder;
  private List<PartOrderItem> listPartOrderItem;

  private Checklist selectedChecklist;
  private PartEstimate selectedEstimate;
  private PartOrder selectedOrder;

  // Set&Get DeviceCode form QRCode
  private String deviceCode;

  public String getDeviceCode() {
    return this.deviceCode;
  }

  public void setDeviceCode(String pDeviceCode) {
    this.deviceCode = pDeviceCode;
    if (StringUtils.isNotEmpty(this.deviceCode)) {
      this.device = this.deviceRepo.findActiveDeviceByDeviceCode(this.deviceCode).getAnyResult();
      this.searchRepairHistory();
    }

  }

  public void toggleSelectionViewDetailRepairHistory(final ActionLog actionLog) {
    if (actionLog.isSelected()) {
      if (this.selectedActionLog != null) {
        this.selectedActionLog.setSelected(false);
      }
      this.selectedActionLog = actionLog;
      // display device, action, repl part
      this.currentActionLog = new ActionLogDto();
      BeanCopier.copy(this.selectedActionLog, this.currentActionLog);
      final String actionCode = this.currentActionLog.getActionCode();
      // fault
      this.faultDto = new FaultDto();
      final Fault fault = this.repairHistoryService.findFaultByActionLogCode(actionCode);
      if (fault != null) {
        BeanCopier.copy(fault, this.faultDto);
      }
      // list repl part
      final String deviceCode = this.currentActionLog.getDevCode();
      this.listReplPart = this.repairHistoryService.findReplPartByDeviceCode(deviceCode, actionCode);
      // list check list. Check if empty reset selected checklist, and checklistItem
      this.listChecklist = this.repairHistoryService.getListChecklist(actionCode);
      if (this.listChecklist.isEmpty()) {
        this.selectedChecklist = null;
        this.listChecklistItem = null;
      }
      // list part estimate. Check if empty reset selected part estimate, and part estimate item
      this.listPartEstimate = this.repairHistoryService.getListEstimate(actionCode);
      if (this.listPartEstimate.isEmpty()) {
        this.selectedEstimate = null;
        this.listPartEstimateItem = null;
      }
      // list part order. Check if empty reset selected part order, and part order item
      this.listPartOrder = this.repairHistoryService.getListPartOrder(actionCode);
      if (this.listPartOrder.isEmpty()) {
        this.selectedOrder = null;
        this.listPartOrderItem = null;
      }

      // action bill
      final ActionBill actionBill = this.repairHistoryService.findActionBillByActionLogCode(actionCode);
      if (actionBill != null) {
        this.actionBillDto = new ActionBillDto();
        BeanCopier.copy(actionBill, this.actionBillDto);
      } else {
        this.actionBillDto = null;
      }
    } else {
      this.selectedOrder = null;
      this.selectedChecklist = null;
      this.selectedEstimate = null;
      this.selectedActionLog = null;
      this.currentActionLog = null;
      this.listReplPart = null;
      this.actionBillDto = null;
      this.listChecklist = null;
      this.listPartEstimate = null;
      this.listPartOrder = null;
      this.listChecklistItem = null;
      this.listPartEstimateItem = null;
      this.listPartOrderItem = null;
    }
  }

  public void searchRepairHistory() {
    final List<String> listActionTypeFilter = new ArrayList<String>();
    // add filter after search. Check if condition is checked and not checked.
    final String actionTypMaintaine = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Maintenance"));
    if (this.isMaintenance) {
      listActionTypeFilter.add(actionTypMaintaine);
    } else {
      if (listActionTypeFilter.contains(actionTypMaintaine)) {
        listActionTypeFilter.remove(actionTypMaintaine);
      }
    }
    final String actionTypeConsult = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Consulting"));
    if (this.isConsulting) {
      listActionTypeFilter.add(actionTypeConsult);
    } else {
      if (listActionTypeFilter.contains(actionTypeConsult)) {
        listActionTypeFilter.remove(actionTypeConsult);
      }
    }
    final String actionSafety = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Safety Testing"));
    if (this.isSafetyTesting) {
      listActionTypeFilter.add(actionSafety);
    } else {
      if (listActionTypeFilter.contains(actionSafety)) {
        listActionTypeFilter.remove(actionSafety);
      }
    }
    final String actionManagement = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Management"));
    if (this.isManagement) {
      listActionTypeFilter.add(actionManagement);
    } else {
      if (listActionTypeFilter.contains(actionManagement)) {
        listActionTypeFilter.remove(actionManagement);
      }
    }
    final String actionTypePerforman = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Performance Testing"));
    if (this.isPerformanceTesting) {
      listActionTypeFilter.add(actionTypePerforman);
    } else {
      if (listActionTypeFilter.contains(actionTypePerforman)) {
        listActionTypeFilter.remove(actionTypePerforman);
      }
    }
    final String actionTypeCalibration = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Calibration"));
    if (this.isCalibration) {
      listActionTypeFilter.add(actionTypeCalibration);
    } else {
      if (listActionTypeFilter.contains(actionTypeCalibration)) {
        listActionTypeFilter.remove(actionTypeCalibration);
      }
    }
    final String actionTypeRepair = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Repair"));
    if (this.isRepair) {
      listActionTypeFilter.add(actionTypeRepair);
    } else {
      if (listActionTypeFilter.contains(actionTypeRepair)) {
        listActionTypeFilter.remove(actionTypeRepair);
      }
    }
    final String actionTypeQualified = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Verification"));
    if (this.isQualifiedVerification) {
      listActionTypeFilter.add(actionTypeQualified);
    } else {
      if (listActionTypeFilter.contains(actionTypeQualified)) {
        listActionTypeFilter.remove(actionTypeQualified);
      }
    }
    final String actionTypeTesting = this.repairHistoryService.getActionTypeByLabel(StringUtils.upperCase("Testing"));
    if (this.isTesting) {
      listActionTypeFilter.add(actionTypeTesting);
    } else {
      if (listActionTypeFilter.contains(actionTypeTesting)) {
        listActionTypeFilter.remove(actionTypeTesting);
      }
    }

    if (this.device == null) {
      this.listActionLogs =
          this.repairHistoryService.getListSearchRepairHistory(null, null, this.userCredential.getOfficeCode(), listActionTypeFilter);
    } else {
      this.listActionLogs =
          this.repairHistoryService.getListSearchRepairHistory(this.device.getDevCode(), this.device.getMDevice().getName(),
              this.userCredential.getOfficeCode(), listActionTypeFilter);
    }
    if (this.listActionLogs != null) {
      this.enableFilterCheckboxs();
    }
    this.currentActionLog = null;
    // return this.listActionLogs;
  }

  public Checklist getSelectedChecklist() {
    return this.selectedChecklist;
  }

  public void setSelectedChecklist(Checklist pSelectedChecklist) {
    this.selectedChecklist = pSelectedChecklist;
    if (this.selectedChecklist != null) {
      this.listChecklistItem = this.repairHistoryService.findChecklistItemByChkListCode(this.selectedChecklist.getCklistLogCode());
    }
  }

  public List<Checklist> getListChecklist() {
    return this.listChecklist;
  }

  public void setListChecklist(List<Checklist> pListChecklist) {
    this.listChecklist = pListChecklist;
  }

  public List<ChecklistItem> getListChecklistItem() {
    return this.listChecklistItem;
  }

  public void setListChecklistItem(List<ChecklistItem> pListChecklistItem) {
    this.listChecklistItem = pListChecklistItem;
  }

  public List<PartEstimate> getListPartEstimate() {
    return this.listPartEstimate;
  }

  public void setListPartEstimate(List<PartEstimate> pListPartEstimate) {
    this.listPartEstimate = pListPartEstimate;
  }

  public List<PartEstimateItem> getListPartEstimateItem() {
    return this.listPartEstimateItem;
  }

  public void setListPartEstimateItem(List<PartEstimateItem> pListPartEstimateItem) {
    this.listPartEstimateItem = pListPartEstimateItem;
  }

  public void enableFilterCheckboxs() {
    this.isEnableFilter = true;
    this.isMaintenance = true;
    this.isPerformanceTesting = true;
    this.isCalibration = true;
    this.isRepair = true;
    this.isQualifiedVerification = true;
    this.isTesting = true;
    this.isConsulting = true;
    this.isSafetyTesting = true;
    this.isManagement = true;
  }

  public void disableFilterCheckboxs() {
    this.isEnableFilter = false;
    this.isMaintenance = false;
    this.isPerformanceTesting = false;
    this.isCalibration = false;
    this.isRepair = false;
    this.isQualifiedVerification = false;
    this.isTesting = false;
    this.isConsulting = false;
    this.isSafetyTesting = false;
    this.isManagement = false;
  }

  public void resetSearchCondition() {
    this.device = null;
    this.deviceName = null;
    this.listActionLogs = null;
    this.currentActionLog = null;
    this.disableFilterCheckboxs();
  }

  public ActionLog getSelectedActionLog() {
    return this.selectedActionLog;
  }

  public void setSelectedActionLog(ActionLog pSelectedActionLog) {
    this.selectedActionLog = pSelectedActionLog;
  }

  public PartEstimate getSelectedEstimate() {
    return this.selectedEstimate;
  }

  public void setSelectedEstimate(PartEstimate pSelectedEstimate) {
    this.selectedEstimate = pSelectedEstimate;
    if (this.selectedEstimate != null) {
      this.listPartEstimateItem = this.repairHistoryService.getListPartEstimate(this.selectedEstimate.getPeCode());
    }
  }

  public List<PartOrder> getListPartOrder() {
    return this.listPartOrder;
  }

  public void setListPartOrder(List<PartOrder> pListPartOrder) {
    this.listPartOrder = pListPartOrder;
  }

  public List<PartOrderItem> getListPartOrderItem() {
    return this.listPartOrderItem;
  }

  public void setListPartOrderItem(List<PartOrderItem> pListPartOrderItem) {
    this.listPartOrderItem = pListPartOrderItem;
  }

  public PartOrder getSelectedOrder() {
    return this.selectedOrder;
  }

  public void setSelectedOrder(PartOrder pSelectedOrder) {
    this.selectedOrder = pSelectedOrder;
    if (this.selectedOrder != null) {
      this.listPartOrderItem = this.repairHistoryService.getListPartOrderItem(this.selectedOrder.getPoCode());
    }
  }


  public ActionBillDto getActionBillDto() {
    return this.actionBillDto;
  }

  public void setActionBillDto(ActionBillDto pActionBillDto) {
    this.actionBillDto = pActionBillDto;
  }

  public FaultDto getFaultDto() {
    return this.faultDto;
  }

  public void setFaultDto(FaultDto pFaultDto) {
    this.faultDto = pFaultDto;
  }

  public boolean isEnableFilter() {
    return this.isEnableFilter;
  }

  public void setEnableFilter(boolean pIsEnableFilter) {
    this.isEnableFilter = pIsEnableFilter;
  }

  public List<ActionLog> getListActionLogs() {
    return this.listActionLogs;
  }

  public void setListActionLogs(List<ActionLog> pListActionLogs) {
    this.listActionLogs = pListActionLogs;
  }

  public ActionLogDto getCurrentActionLog() {
    return this.currentActionLog;
  }

  public void setCurrentActionLog(ActionLogDto pCurrentActionLog) {
    this.currentActionLog = pCurrentActionLog;
  }

  public Device getDevice() {
    return this.device;
  }

  public void setDevice(Device pDevice) {
    this.device = pDevice;
  }

  public String getDeviceName() {
    return this.deviceName;
  }

  public void setDeviceName(String pDeviceName) {
    this.deviceName = pDeviceName;
  }

  public boolean isMaintenance() {
    return this.isMaintenance;
  }

  public void setMaintenance(boolean pIsMaintenance) {
    this.isMaintenance = pIsMaintenance;
  }


  public boolean isConsulting() {
    return this.isConsulting;
  }

  public void setConsulting(boolean pIsConsulting) {
    this.isConsulting = pIsConsulting;
  }

  public boolean isManagement() {
    return this.isManagement;
  }

  public void setManagement(boolean pIsManagement) {
    this.isManagement = pIsManagement;
  }

  public boolean isSafetyTesting() {
    return this.isSafetyTesting;
  }

  public void setSafetyTesting(boolean pIsSafetyTesting) {
    this.isSafetyTesting = pIsSafetyTesting;
  }

  public boolean isPerformanceTesting() {
    return this.isPerformanceTesting;
  }

  public void setPerformanceTesting(boolean pIsPerformanceTesting) {
    this.isPerformanceTesting = pIsPerformanceTesting;
  }

  public boolean isCalibration() {
    return this.isCalibration;
  }

  public void setCalibration(boolean pIsCalibration) {
    this.isCalibration = pIsCalibration;
  }

  public boolean isRepair() {
    return this.isRepair;
  }

  public void setRepair(boolean pIsRepair) {
    this.isRepair = pIsRepair;
  }

  public boolean isQualifiedVerification() {
    return this.isQualifiedVerification;
  }

  public void setQualifiedVerification(boolean pIsQualifiedVerification) {
    this.isQualifiedVerification = pIsQualifiedVerification;
  }

  public boolean isTesting() {
    return this.isTesting;
  }

  public void setTesting(boolean pIsTesting) {
    this.isTesting = pIsTesting;
  }

  public List<ReplPart> getListReplPart() {
    return this.listReplPart;
  }

  public void setListReplPart(List<ReplPart> pListReplPart) {
    this.listReplPart = pListReplPart;
  }

}
