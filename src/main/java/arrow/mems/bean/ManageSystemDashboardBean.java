package arrow.mems.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.framework.util.CollectionUtils;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Device;
import arrow.mems.service.MasterDashboardService;

@Named
@ViewScoped
public class ManageSystemDashboardBean extends MasterDashboardBean {

  @Inject
  private MasterDashboardService masterDashboardService;
  @Inject
  private UserCredential userCredential;
  @Inject
  private InputRepairRequestBean inputRepairRequestBean;

  private String deviceId;
  private String actionCode;
  private ActionLog currentActionLog;
  private Device currentDevice;


  // Init first value
  private List<String> listCandiatesDeviceId;
  private List<Device> listCandidateDevice;
  private List<ActionLog> listCandiatesActionLog;

  public void editCheckRepair() {
    this.inputRepairRequestBean.setRepairMode(CommonConstants.REPAIR_MODE_NOT_USE_SCHEDULE);
    this.inputRepairRequestBean.setSelectedActionLog(this.currentActionLog);
    this.inputRepairRequestBean.editActionLog();
  }

  public void addCheckRepair() {
    this.inputRepairRequestBean.setRepairMode(CommonConstants.REPAIR_MODE_NOT_USE_SCHEDULE);
    this.inputRepairRequestBean.setSelectedDevice(this.currentDevice);
    this.inputRepairRequestBean.addNewActionLog();

  }

  public void getNewerRequestNo() {
    if (this.currentActionLog != null) {
      final ActionLog newerActionLog =
          this.masterDashboardService.getActionLogByOfficeCodeAfterOccurDate(this.userCredential.getOfficeCode(),
              this.currentActionLog.getOccurDate(), this.currentActionLog.getActionId());
      if (newerActionLog != null) {
        this.currentActionLog = newerActionLog;
      }
    }
  }

  public void getOlderRequestNo() {
    if (this.currentActionLog != null) {
      final ActionLog olderActionLog =
          this.masterDashboardService.getActionLogByOfficeCodeBeforOccurDate(this.userCredential.getOfficeCode(),
              this.currentActionLog.getOccurDate(), this.currentActionLog.getActionId());
      if (olderActionLog != null) {
        this.currentActionLog = olderActionLog;
      }
    }
  }

  public List<String> autoCompleteDeviceId(String query) {
    if (this.listCandidateDevice == null) {
      this.listCandidateDevice = this.masterDashboardService.getDeviceByOfficeCode(this.userCredential.getOfficeCode());
    }
    final List<String> candidateResults = new ArrayList<String>();
    if (CollectionUtils.isNotEmpty(this.listCandidateDevice)) {
      for (final Device device : this.listCandidateDevice) {
        if (candidateResults.contains(device.getDevCode())) {
          continue;
        }
        if (StringUtils.isNotEmpty(device.getDevCode())) {
          if (StringUtils.startsWithIgnoreCase(device.getDevCode(), query)) {
            candidateResults.add(device.getDevCode());
          }
        }
      }
    }
    return candidateResults;
  }

  public List<ActionLog> autoCompleteActionCode(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListCandiatesActionLog();
    return CollectionUtils.filter(this.getListCandiatesActionLog(), object -> {
      final ActionLog item = (ActionLog) object;
      return StringUtils.containsIgnoreCase(item.getActionCode(), query) || StringUtils.containsIgnoreCase(item.getActionType(), query);
    });
  }

  public List<Device> autoCompleteDevice(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListCandiatesDevice();
    return CollectionUtils.filter(
        this.getListCandiatesDevice(),
        object -> {
          final Device item = (Device) object;
          return StringUtils.containsIgnoreCase(item.getMDevice().getModelNo(), query) || StringUtils.containsIgnoreCase(item.getMDevice().getName(),
              query) || StringUtils.containsIgnoreCase(item.getSerialNo(), query) || StringUtils.containsIgnoreCase(item.getDevCode(), query);
        });
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String pDeviceId) {
    this.deviceId = pDeviceId;
  }

  public List<String> getListCandiatesDeviceId() {
    return this.listCandiatesDeviceId;
  }

  public void setListCandiatesDeviceId(List<String> pListCandiatesDeviceId) {
    this.listCandiatesDeviceId = pListCandiatesDeviceId;
  }

  public String getActionCode() {
    return this.actionCode;
  }

  public void setActionCode(String pActionCode) {
    this.actionCode = pActionCode;
  }

  public List<ActionLog> getListCandiatesActionLog() {
    if (this.listCandiatesActionLog == null) {
      this.listCandiatesActionLog = this.masterDashboardService.getActionLogByOfficeCode(this.userCredential.getOfficeCode());
    }
    return this.listCandiatesActionLog;
  }

  public void setListCandiatesActionLog(List<ActionLog> pListCandiatesActionLog) {
    this.listCandiatesActionLog = pListCandiatesActionLog;
  }

  public ActionLog getCurrentActionLog() {
    return this.currentActionLog;
  }

  public void setCurrentActionLog(ActionLog pCurrentActionLog) {
    this.currentActionLog = pCurrentActionLog;
  }

  public Device getCurrentDevice() {
    return this.currentDevice;
  }

  public void setCurrentDevice(Device pCurrentDevice) {
    this.currentDevice = pCurrentDevice;
  }

  private List<Device> listCandiatesDevice;

  public List<Device> getListCandiatesDevice() {
    if (this.listCandiatesDevice == null) {
      this.listCandiatesDevice = this.masterDashboardService.getDeviceByOfficeCode(this.userCredential.getOfficeCode());
    }
    return this.listCandiatesDevice;
  }

}
