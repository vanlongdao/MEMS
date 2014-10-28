package arrow.mems.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.messages.MDMMessages;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.dto.AlertByCountDto;
import arrow.mems.persistence.dto.MdevChecklistDto;
import arrow.mems.persistence.dto.ScheduleDto;
import arrow.mems.persistence.entity.AlertByCount;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.Schedule;
import arrow.mems.service.DevicesManagementService;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class ScheduleBean extends AbstractBean {
  @Inject
  private DevicesManagementService devicesManagerService;
  @Inject
  private DevicesManagementBean devicesManagerBean;
  @Inject
  private UserCredential userCredential;
  @Inject
  private MasterDeviceChecklistManageBean masterDeviceCklistBean;

  private List<ScheduleDto> listMasterSchedule;
  private List<ScheduleDto> oldStateScheduleTemp;

  private List<Schedule> listDeletedSchedule;
  private ScheduleDto currentSchedule;

  private ScheduleDto selectedSchedule;
  private MdevChecklistDto selectedChecklist;
  @NotNull(message = "{" + MessageCode.MDM00005 + "}")
  private String indentifiedChecklist;

  // For schedule of alert by count
  private List<AlertByCountDto> listMasterScheduleAlert;
  private List<AlertByCountDto> oldStateScheduleAlertTemp;

  private List<AlertByCount> listDeletedScheduleAlert;
  private AlertByCountDto currentScheduleAlert;

  private AlertByCountDto selectedScheduleAlert;
  private MdevChecklistDto selectedChecklistAlert;
  @NotNull(message = "{" + MessageCode.MDM00005 + "}")
  private String indentifiedChecklistAlert;


  public void editSchedule() {
    if (this.selectedSchedule != null) {
      this.currentSchedule = this.selectedSchedule;
      if (this.currentSchedule.getMdevChecklist() != null) {
        this.setSelectedChecklistWhenEdit();
      } else {
        this.selectedChecklist = null;
        this.indentifiedChecklist = null;
      }
    } else {
      MDMMessages.MDM00007().show();
    }
  }

  public void editScheduleAlert() {
    if (this.selectedScheduleAlert != null) {
      this.currentScheduleAlert = this.selectedScheduleAlert;
      if (this.currentScheduleAlert.getMdevChecklist() != null) {
        this.setSelectedChecklistAlertWhenEdit();
      } else {
        this.selectedChecklistAlert = null;
        this.indentifiedChecklistAlert = null;
      }
    } else {
      MDMMessages.MDM00007().show();
    }
  }

  public void setSelectedChecklistWhenEdit() {
    final List<MdevChecklistDto> currentChecklist = this.masterDeviceCklistBean.getChecklists();
    for (final MdevChecklistDto mdevChecklistDto : currentChecklist) {
      if (this.currentSchedule.getMdevChecklist().getCklistId() == mdevChecklistDto.getCklistId()) {
        this.selectedChecklist = mdevChecklistDto;
        this.indentifiedChecklist = String.valueOf(mdevChecklistDto.getCklistId());
        break;
      }
    }
  }

  public void setSelectedChecklistAlertWhenEdit() {
    final List<MdevChecklistDto> currentChecklist = this.masterDeviceCklistBean.getChecklists();
    for (final MdevChecklistDto mdevChecklistDto : currentChecklist) {
      if (this.currentScheduleAlert.getMdevChecklist().getCklistId() == mdevChecklistDto.getCklistId()) {
        this.selectedChecklistAlert = mdevChecklistDto;
        this.indentifiedChecklistAlert = String.valueOf(mdevChecklistDto.getCklistId());
        break;
      }
    }
  }

  public void deleteSchedule() {
    if (this.selectedSchedule != null) {
      this.listMasterSchedule.remove(this.selectedSchedule);
      if (this.oldStateScheduleTemp != null) {
        if (this.oldStateScheduleTemp.contains(this.selectedSchedule)) {
          if (this.listDeletedSchedule == null) {
            this.listDeletedSchedule = new ArrayList<Schedule>();
          }
          this.listDeletedSchedule.add(this.selectedSchedule);
        }
      }
      this.currentSchedule = null;
      this.selectedSchedule = null;
    }
  }

  public void deleteScheduleAlert() {
    if (this.selectedScheduleAlert != null) {
      this.listMasterScheduleAlert.remove(this.selectedScheduleAlert);
      if (this.oldStateScheduleAlertTemp != null) {
        if (this.oldStateScheduleAlertTemp.contains(this.selectedScheduleAlert)) {
          if (this.listDeletedScheduleAlert == null) {
            this.listDeletedScheduleAlert = new ArrayList<AlertByCount>();
          }
          this.listDeletedScheduleAlert.add(this.selectedScheduleAlert);
        }
      }
      this.currentScheduleAlert = null;
      this.selectedScheduleAlert = null;
    }
  }

  public void saveSchedule() {
    if (this.currentSchedule == null)
      return;
    if (this.listMasterSchedule == null) {
      this.listMasterSchedule = new ArrayList<ScheduleDto>();
    }
    // Check the selected checklist is used in alert by count
    final MdevChecklist newChecklist = this.setMdevChecklistIntoSchedulder(this.indentifiedChecklist);
    this.getListMasterScheduleAlert();
    if (this.validateUsedSchedule(newChecklist))
      return;

    if (!this.listMasterSchedule.contains(this.currentSchedule)) {
      // Case create new Schedule
      this.currentSchedule.setCreatedBy(this.userCredential.getUserId());
      this.currentSchedule.setMdevChecklist(newChecklist);
      if (ArrowStrUtils.isNotEmpty(this.devicesManagerBean.getCurrentDevice().getMdevCode())) {
        this.currentSchedule.setMdevCode(this.devicesManagerBean.getCurrentDevice().getMdevCode());
      }
      this.listMasterSchedule.add(this.currentSchedule);
    } else {
      // Case edit Schedule
      this.currentSchedule.setMdevChecklist(this.setMdevChecklistIntoSchedulder(this.indentifiedChecklist));
    }
    this.resetSchedule();
  }

  public boolean validateUsedSchedule(MdevChecklist newChecklist) {
    if (CollectionUtils.isNotEmpty(this.listMasterScheduleAlert)) {
      for (final AlertByCount alert : this.listMasterScheduleAlert) {
        if (newChecklist.getCklistId() == alert.getMdevChecklist().getCklistId()) {
          MDMMessages.MDM00017().show();
          return true;
        }
      }
    }

    if (CollectionUtils.isNotEmpty(this.listMasterSchedule)) {
      for (final Schedule schedule : this.listMasterSchedule) {
        if (schedule.getMdevChecklist() != null) {
          if (this.currentSchedule.getMdevChecklist() != null) {
            if (this.indentifiedChecklist.equalsIgnoreCase(String.valueOf(this.currentSchedule.getMdevChecklist().getCklistId()))) {
              break;
            }
          }

          if (newChecklist.getCklistId() == schedule.getMdevChecklist().getCklistId()) {
            MDMMessages.MDM00017().show();
            return true;
          }
        }
      }
    }
    return false;
  }

  public void saveScheduleAlert() {
    if (this.currentScheduleAlert == null)
      return;
    if (this.listMasterScheduleAlert == null) {
      this.listMasterScheduleAlert = new ArrayList<AlertByCountDto>();
    }
    final MdevChecklist newChecklist = this.setMdevChecklistIntoSchedulder(this.indentifiedChecklistAlert);
    this.getListMasterSchedule();
    if (this.validateUsedScheduleAlert(newChecklist))
      return;

    if (!this.listMasterScheduleAlert.contains(this.currentScheduleAlert)) {
      // Case create new Schedule
      this.currentScheduleAlert.setCreatedBy(this.userCredential.getUserId());
      this.currentScheduleAlert.setMdevChecklist(newChecklist);
      if (ArrowStrUtils.isNotEmpty(this.devicesManagerBean.getCurrentDevice().getMdevCode())) {
        this.currentScheduleAlert.setMdevCode(this.devicesManagerBean.getCurrentDevice().getMdevCode());
      }
      this.listMasterScheduleAlert.add(this.currentScheduleAlert);
    } else {
      // Case edit Schedule
      this.currentScheduleAlert.setMdevChecklist(this.setMdevChecklistIntoSchedulder(this.indentifiedChecklistAlert));
    }
    this.resetScheAlert();
  }

  public boolean validateUsedScheduleAlert(MdevChecklist newChecklist) {
    for (final AlertByCount alert : this.listMasterScheduleAlert) {
      if (alert.getMdevChecklist() != null) {
        if (this.currentScheduleAlert.getMdevChecklist() != null) {
          if (this.indentifiedChecklistAlert.equalsIgnoreCase(String.valueOf(this.currentScheduleAlert.getMdevChecklist().getCklistId()))) {
            break;
          }
        }
        if (newChecklist.getCklistId() == alert.getMdevChecklist().getCklistId()) {
          MDMMessages.MDM00017().show();
          return true;
        }
      }
    }
    if (this.listMasterSchedule == null)
      return false;
    for (final Schedule schedule : this.listMasterSchedule) {
      if (schedule.getMdevChecklist() != null) {
        if (newChecklist.getCklistId() == schedule.getMdevChecklist().getCklistId()) {
          MDMMessages.MDM00017().show();
          return true;
        }
      }
    }
    return false;
  }

  public void setChecktypeTime() {}

  public MdevChecklist setMdevChecklistIntoSchedulder(String indentifiedCklist) {
    final List<MdevChecklistDto> listChecklistDto = this.masterDeviceCklistBean.getChecklists();
    for (final MdevChecklistDto mdevChecklistDto : listChecklistDto) {
      if (String.valueOf(mdevChecklistDto.getCklistId()).equalsIgnoreCase(indentifiedCklist))
        return mdevChecklistDto;
    }
    return new MdevChecklist();
  }

  public void createNewSchedule() {
    this.currentSchedule = new ScheduleDto();
    this.selectedChecklist = null;
    this.indentifiedChecklist = null;
    if (this.selectedSchedule != null) {
      this.selectedSchedule.setSelected(false);
      this.selectedSchedule = null;
    }
  }

  public void createNewScheduleAlert() {
    this.currentScheduleAlert = new AlertByCountDto();
    this.selectedChecklistAlert = null;
    this.indentifiedChecklistAlert = null;
    if (this.selectedScheduleAlert != null) {
      this.selectedScheduleAlert.setSelected(false);
      this.selectedScheduleAlert = null;
    }
  }

  public void toggleSelectSchedule(ScheduleDto selectedSchedule) {
    if (selectedSchedule.isSelected()) {
      if (this.selectedSchedule != null) {
        this.selectedSchedule.setSelected(false);
      }
      this.selectedSchedule = selectedSchedule;
    } else {
      this.selectedSchedule = null;
    }
  }

  public void toggleSelectScheduleAlert(AlertByCountDto selectedScheduleAlert) {
    if (selectedScheduleAlert.isSelected()) {
      if (this.selectedScheduleAlert != null) {
        this.selectedScheduleAlert.setSelected(false);
      }
      this.selectedScheduleAlert = selectedScheduleAlert;
    } else {
      this.selectedScheduleAlert = null;
    }
  }

  public List<ScheduleDto> getListMasterSchedule() {
    if (this.listMasterSchedule == null) {
      if (ArrowStrUtils.isNotEmpty(this.devicesManagerBean.getCurrentDevice().getMdevCode())) {
        final List<Schedule> listSchedule =
            this.devicesManagerService.getMasterScheduleByCurrentDevice(this.devicesManagerBean.getCurrentDevice().getMdevCode());
        this.listMasterSchedule = this.initScheduleList(listSchedule);
        if (this.listMasterSchedule != null) {
          this.oldStateScheduleTemp = new ArrayList<ScheduleDto>();
          this.oldStateScheduleTemp.addAll(this.listMasterSchedule);
        }
      }
    }
    return this.listMasterSchedule;
  }

  public List<ScheduleDto> initScheduleList(List<Schedule> listSchedule) {
    final List<ScheduleDto> listScheduleDto = new ArrayList<ScheduleDto>();
    for (final Schedule schedule : listSchedule) {
      final ScheduleDto tempSchedule = new ScheduleDto();
      BeanCopier.copy(schedule, tempSchedule);
      listScheduleDto.add(tempSchedule);
    }
    return listScheduleDto;
  }

  public List<AlertByCountDto> initScheduleAlertList(List<AlertByCount> listScheduleAlert) {
    final List<AlertByCountDto> listScheduleAlertDto = new ArrayList<AlertByCountDto>();
    for (final AlertByCount scheduleAlert : listScheduleAlert) {
      final AlertByCountDto tempScheduleAlert = new AlertByCountDto();
      BeanCopier.copy(scheduleAlert, tempScheduleAlert);
      listScheduleAlertDto.add(tempScheduleAlert);
    }
    return listScheduleAlertDto;
  }

  public void discardChange() {
    this.listMasterSchedule = null;

    this.getListMasterSchedule();
    this.currentSchedule = null;
    this.indentifiedChecklist = null;
    if (this.selectedSchedule != null) {
      this.selectedSchedule.setSelected(false);
      this.selectedSchedule = null;
    }
    this.closeConfirmScheDlg();
  }

  public void discardChangeScheAlert() {
    this.listMasterScheduleAlert = null;
    this.getListMasterScheduleAlert();
    this.currentScheduleAlert = null;
    this.indentifiedChecklistAlert = null;
    if (this.selectedScheduleAlert != null) {
      this.selectedScheduleAlert.setSelected(false);
      this.selectedScheduleAlert = null;
    }
    this.closeConfirmAlertDlg();
  }

  public void resetAll() {
    this.listMasterSchedule = null;
    this.currentSchedule = null;
    this.indentifiedChecklist = null;
    this.oldStateScheduleTemp = null;
    this.currentScheduleAlert = null;
  }

  public void resetSchedule() {
    this.currentSchedule = null;
    this.indentifiedChecklist = null;
    this.oldStateScheduleTemp = null;
    if (this.selectedSchedule != null) {
      this.selectedSchedule.setSelected(false);
      this.selectedSchedule = null;
    }
  }

  public void resetScheAlert() {
    this.currentScheduleAlert = null;
    this.indentifiedChecklistAlert = null;
    this.oldStateScheduleAlertTemp = null;
    if (this.selectedScheduleAlert != null) {
      this.selectedScheduleAlert.setSelected(false);
      this.selectedScheduleAlert = null;
    }
  }

  public void resetAllScheAlert() {
    this.listMasterScheduleAlert = null;
    this.currentScheduleAlert = null;
    this.indentifiedChecklistAlert = null;
    this.oldStateScheduleAlertTemp = null;
  }

  private boolean renderConfirmScheDlg;
  private boolean renderConfirmAlertDlg;

  public void closeConfirmScheDlg() {
    this.renderConfirmScheDlg = false;
  }

  public void closeConfirmAlertDlg() {
    this.renderConfirmAlertDlg = false;
  }

  private List<ScheduleDto> originSchedule;
  private List<AlertByCountDto> originAlert;

  public boolean showConfirmDiscardScheChange() {
    if (this.oldStateScheduleTemp == null) {
      if ((this.listMasterSchedule != null) && (this.listMasterSchedule.size() > 0)) {
        this.renderConfirmScheDlg = true;
        return true;
      } else {
        this.renderConfirmScheDlg = false;
        return false;
      }
    }
    if (this.listMasterSchedule.size() != this.oldStateScheduleTemp.size()) {
      this.renderConfirmScheDlg = true;
      return true;
    }
    if (this.originSchedule == null) {
      this.originSchedule = new ArrayList<>();
      final List<Schedule> listSchedule =
          this.devicesManagerService.getMasterScheduleByCurrentDevice(this.devicesManagerBean.getCurrentDevice().getMdevCode());
      this.originSchedule = this.initScheduleList(listSchedule);
    }

    final Map<String, Integer> mapOldStateSche = new HashMap<String, Integer>();
    final Map<String, ScheduleDto> mapOldStateScheData = new HashMap<String, ScheduleDto>();
    for (final ScheduleDto scheDto : this.originSchedule) {
      mapOldStateSche.put(scheDto.getCklistCode(), scheDto.getSchedBaseId());
      mapOldStateScheData.put(scheDto.getCklistCode(), scheDto);
    }
    for (final ScheduleDto scheDto : this.listMasterSchedule) {
      if (ArrowStrUtils.isEmpty(scheDto.getCklistCode())) {
        this.renderConfirmScheDlg = true;
        return true;
      } else {
        if (!mapOldStateSche.containsKey(scheDto.getCklistCode())) {
          this.renderConfirmScheDlg = true;
          return true;
        }
        // Check if change item
        final ScheduleDto schedule = mapOldStateScheData.get(scheDto.getCklistCode());
        if (!this.compareBeanSchedule(schedule, scheDto)) {
          this.renderConfirmScheDlg = true;
          return true;
        }
      }
    }
    this.renderConfirmScheDlg = false;
    return false;
  }

  /**
   * Compare bean daily report dto.
   *
   * @param bean1 the bean1
   * @param bean2 the bean2
   * @return true, if them equal , and false if not equal
   */
  public boolean compareBeanSchedule(final ScheduleDto bean1, final ScheduleDto bean2) {
    final ComparatorChain comparatorChain = new ComparatorChain();
    comparatorChain.addComparator(new BeanComparator<Object>("cklistCode"));

    // comparatorChain.addComparator(new BeanComparator("dai_work_date"));
    if (ArrowStrUtils.isNotEmpty(bean1.getDescription()) && ArrowStrUtils.isNotEmpty(bean2.getDescription())) {
      comparatorChain.addComparator(new BeanComparator<Object>("description"));
    } else
      return false;
    if ((bean1.getIntervalMonth() != 0) && (bean2.getIntervalMonth() != 0)) {
      comparatorChain.addComparator(new BeanComparator<Object>("intervalMonth"));
    } else
      return false;
    if (ArrowStrUtils.isNotEmpty(bean1.getName()) && ArrowStrUtils.isNotEmpty(bean2.getName())) {
      comparatorChain.addComparator(new BeanComparator<Object>("name"));
    } else
      return false;

    final int result = comparatorChain.compare(bean1, bean2);
    if (result == 0)
      return true;
    return false;
  }

  public boolean showConfirmDiscardAlertChange() {
    if (this.oldStateScheduleAlertTemp == null) {
      if ((this.listMasterScheduleAlert != null) && (this.listMasterScheduleAlert.size() > 0)) {
        this.renderConfirmAlertDlg = true;
        return true;
      } else {
        this.renderConfirmAlertDlg = false;
        return false;
      }
    }
    if (this.listMasterScheduleAlert.size() != this.oldStateScheduleAlertTemp.size()) {
      this.renderConfirmAlertDlg = true;
      return true;
    }
    if (this.originAlert == null) {
      this.originAlert = new ArrayList<>();
      final List<AlertByCount> listScheduleAlert =
          this.devicesManagerService.getAlertByCountByMdevcode(this.devicesManagerBean.getCurrentDevice().getMdevCode());
      this.originAlert = this.initScheduleAlertList(listScheduleAlert);
    }

    final Map<String, Integer> mapOldStateSche = new HashMap<String, Integer>();
    final Map<String, AlertByCountDto> mapOldStateScheData = new HashMap<String, AlertByCountDto>();
    for (final AlertByCountDto scheDto : this.originAlert) {
      mapOldStateSche.put(scheDto.getCounterBaseCode(), scheDto.getCounterBaseId());
      mapOldStateScheData.put(scheDto.getCounterBaseCode(), scheDto);
    }
    for (final AlertByCountDto scheDto : this.listMasterScheduleAlert) {
      if (ArrowStrUtils.isEmpty(scheDto.getCounterBaseCode())) {
        this.renderConfirmAlertDlg = true;
        return true;
      } else {
        if (!mapOldStateSche.containsKey(scheDto.getCounterBaseCode())) {
          this.renderConfirmAlertDlg = true;
          return true;
        }
        // Check if change item
        final AlertByCountDto schedule = mapOldStateScheData.get(scheDto.getCounterBaseCode());
        if (!this.compareBeanScheduleAlert(schedule, scheDto)) {
          this.renderConfirmScheDlg = true;
          return true;
        }
      }
    }
    this.renderConfirmAlertDlg = false;
    return false;
  }

  public boolean compareBeanScheduleAlert(final AlertByCountDto bean1, final AlertByCountDto bean2) {
    final ComparatorChain comparatorChain = new ComparatorChain();
    comparatorChain.addComparator(new BeanComparator<Object>("cklistCode"));

    // comparatorChain.addComparator(new BeanComparator("dai_work_date"));
    if (ArrowStrUtils.isNotEmpty(bean1.getDescription()) && ArrowStrUtils.isNotEmpty(bean2.getDescription())) {
      comparatorChain.addComparator(new BeanComparator<Object>("description"));
    } else
      return false;
    if ((bean1.getIntervalCount() != 0) && (bean2.getIntervalCount() != 0)) {
      comparatorChain.addComparator(new BeanComparator<Object>("intervalCount"));
    } else
      return false;
    if (ArrowStrUtils.isNotEmpty(bean1.getName()) && ArrowStrUtils.isNotEmpty(bean2.getName())) {
      comparatorChain.addComparator(new BeanComparator<Object>("name"));
    } else
      return false;
    if (ArrowStrUtils.isNotEmpty(bean1.getUnit()) && ArrowStrUtils.isNotEmpty(bean2.getUnit())) {
      comparatorChain.addComparator(new BeanComparator<Object>("unit"));
    } else
      return false;

    final int result = comparatorChain.compare(bean1, bean2);
    if (result == 0)
      return true;
    return false;
  }

  public boolean isRenderConfirmAlertDlg() {
    return this.renderConfirmAlertDlg;
  }

  public void setRenderConfirmAlertDlg(boolean pRenderConfirmAlertDlg) {
    this.renderConfirmAlertDlg = pRenderConfirmAlertDlg;
  }

  public boolean isRenderConfirmScheDlg() {
    return this.renderConfirmScheDlg;
  }

  public void setRenderConfirmScheDlg(boolean pRenderConfirmScheDlg) {
    this.renderConfirmScheDlg = pRenderConfirmScheDlg;
  }

  public MdevChecklistDto getSelectedChecklist() {
    return this.selectedChecklist;
  }

  public void setSelectedChecklist(MdevChecklistDto pSelectedChecklist) {
    this.selectedChecklist = pSelectedChecklist;
  }

  public List<Schedule> getListDeletedSchedule() {
    return this.listDeletedSchedule;
  }

  public void setListDeletedSchedule(List<Schedule> pListDeletedSchedule) {
    this.listDeletedSchedule = pListDeletedSchedule;
  }

  public ScheduleDto getCurrentSchedule() {
    return this.currentSchedule;
  }

  public void setCurrentSchedule(ScheduleDto pCurrentSchedule) {
    this.currentSchedule = pCurrentSchedule;
  }

  public Schedule getSelectedSchedule() {
    return this.selectedSchedule;
  }

  public void setSelectedSchedule(ScheduleDto pSelectedSchedule) {
    this.selectedSchedule = pSelectedSchedule;
  }

  public List<ScheduleDto> getOldStateScheduleTemp() {
    return this.oldStateScheduleTemp;
  }

  public void setOldStateScheduleTemp(List<ScheduleDto> pOldStateScheduleTemp) {
    this.oldStateScheduleTemp = pOldStateScheduleTemp;
  }

  public void setListMasterSchedule(List<ScheduleDto> pListMasterSchedule) {
    this.listMasterSchedule = pListMasterSchedule;
  }

  public String getIndentifiedChecklist() {
    return this.indentifiedChecklist;
  }

  public void setIndentifiedChecklist(String pIndentifiedChecklist) {
    this.indentifiedChecklist = pIndentifiedChecklist;
  }

  // //////////////////////////////////////////////////////

  public List<AlertByCountDto> getListMasterScheduleAlert() {
    if (this.listMasterScheduleAlert == null) {
      if (ArrowStrUtils.isNotEmpty(this.devicesManagerBean.getCurrentDevice().getMdevCode())) {
        final List<AlertByCount> listScheduleAlert =
            this.devicesManagerService.getAlertByCountByMdevcode(this.devicesManagerBean.getCurrentDevice().getMdevCode());
        this.listMasterScheduleAlert = this.initScheduleAlertList(listScheduleAlert);
        if (this.listMasterScheduleAlert != null) {
          this.oldStateScheduleAlertTemp = new ArrayList<AlertByCountDto>();
          this.oldStateScheduleAlertTemp.addAll(this.listMasterScheduleAlert);
        }
      }
    }
    return this.listMasterScheduleAlert;
  }

  public void setListMasterScheduleAlert(List<AlertByCountDto> pListMasterScheduleAlert) {
    this.listMasterScheduleAlert = pListMasterScheduleAlert;
  }

  public List<AlertByCountDto> getOldStateScheduleAlertTemp() {
    return this.oldStateScheduleAlertTemp;
  }

  public void setOldStateScheduleAlertTemp(List<AlertByCountDto> pOldStateScheduleAlertTemp) {
    this.oldStateScheduleAlertTemp = pOldStateScheduleAlertTemp;
  }

  public List<AlertByCount> getListDeletedScheduleAlert() {
    return this.listDeletedScheduleAlert;
  }

  public void setListDeletedScheduleAlert(List<AlertByCount> pListDeletedScheduleAlert) {
    this.listDeletedScheduleAlert = pListDeletedScheduleAlert;
  }

  public AlertByCountDto getCurrentScheduleAlert() {
    return this.currentScheduleAlert;
  }

  public void setCurrentScheduleAlert(AlertByCountDto pCurrentScheduleAlert) {
    this.currentScheduleAlert = pCurrentScheduleAlert;
  }

  public AlertByCountDto getSelectedScheduleAlert() {
    return this.selectedScheduleAlert;
  }

  public void setSelectedScheduleAlert(AlertByCountDto pSelectedScheduleAlert) {
    this.selectedScheduleAlert = pSelectedScheduleAlert;
  }

  public MdevChecklistDto getSelectedChecklistAlert() {
    return this.selectedChecklistAlert;
  }

  public void setSelectedChecklistAlert(MdevChecklistDto pSelectedChecklistAlert) {
    this.selectedChecklistAlert = pSelectedChecklistAlert;
  }

  public String getIndentifiedChecklistAlert() {
    return this.indentifiedChecklistAlert;
  }

  public void setIndentifiedChecklistAlert(String pIndentifiedChecklistAlert) {
    this.indentifiedChecklistAlert = pIndentifiedChecklistAlert;
  }

}
