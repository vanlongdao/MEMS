package arrow.mems.bean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.BeanCopier;
import arrow.mems.messages.MPMMessages;
import arrow.mems.persistence.dto.ActionLogDto;
import arrow.mems.persistence.dto.DeviceDto;
import arrow.mems.persistence.dto.ReplPartDto;
import arrow.mems.persistence.dto.ScheduleItemDto;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.service.DeviceManagementService;

@Named
@ViewScoped
public class ViewDetailQRCodeBean extends AbstractBean {

  @Inject
  DeviceManagementService deviceService;

  private DeviceDto currentDevice;
  private ActionLogDto actionLogDto;
  private ReplPartDto replPartDto;
  private String deviceCode;
  private ScheduleItemDto scheduleItemDto;
  List<ReplPart> listReplPart = new ArrayList<ReplPart>();
  boolean isDisplay = false;

  public void loadData() {
    // Load Device info
    final Device device = this.deviceService.loadDeviceByDeviceCode(this.deviceCode);
    if (device == null) {
      MPMMessages.MPM00018().show();
      this.initData();
      this.isDisplay = false;
      return;
    } else {
      this.initData();
      this.isDisplay = true;
      BeanCopier.copy(device, this.currentDevice);
      // Load action log of device
      final ActionLog actionlog = this.deviceService.findActionByDeviceCode(this.deviceCode);
      if (actionlog != null) {
        BeanCopier.copy(actionlog, this.actionLogDto);
        this.listReplPart = this.deviceService.findReplPartByDeviceCode(this.deviceCode, actionlog.getActionCode());
      }
      // Load Repl part of device
      final ScheduleItem scheduleItem = this.deviceService.findScheduleItemByDeviceCode(this.deviceCode);
      if (scheduleItem != null) {
        BeanCopier.copy(scheduleItem, this.scheduleItemDto);
      }
    }
  }

  public void initData() {
    this.currentDevice = new DeviceDto();
    this.scheduleItemDto = new ScheduleItemDto();
    this.listReplPart = new ArrayList<ReplPart>();
    this.actionLogDto = new ActionLogDto();
    this.scheduleItemDto = new ScheduleItemDto();
  }

  public void viewHistoryCheckorRepair() {

  }

  public void registerNewCheckorRepair() {

  }

  public boolean isDisplay() {
    return this.isDisplay;
  }

  public void setDisplay(boolean pIsDisplay) {
    this.isDisplay = pIsDisplay;
  }

  public ScheduleItemDto getScheduleItemDto() {
    return this.scheduleItemDto;
  }

  public void setScheduleItemDto(ScheduleItemDto pScheduleItemDto) {
    this.scheduleItemDto = pScheduleItemDto;
  }

  public List<ReplPart> getListReplPart() {
    return this.listReplPart;
  }

  public void setListReplPart(List<ReplPart> pListReplPart) {
    this.listReplPart = pListReplPart;
  }

  public ReplPartDto getReplPartDto() {
    return this.replPartDto;
  }

  public void setReplPartDto(ReplPartDto pReplPartDto) {
    this.replPartDto = pReplPartDto;
  }

  public String getDeviceCode() {
    return this.deviceCode;
  }

  public void setDeviceCode(String pDeviceCode) {
    this.deviceCode = pDeviceCode;
  }

  public ActionLogDto getActionLogDto() {
    return this.actionLogDto;
  }

  public void setActionLogDto(ActionLogDto pActionLogDto) {
    this.actionLogDto = pActionLogDto;
  }

  public DeviceDto getCurrentDevice() {
    return this.currentDevice;
  }

  public void setCurrentDevice(DeviceDto pCurrentDevice) {
    this.currentDevice = pCurrentDevice;
  }

}
