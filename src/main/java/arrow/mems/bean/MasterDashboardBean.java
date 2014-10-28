package arrow.mems.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.DateUtils;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.AuthenticationConstants;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.CountAndScheduleItemEntity;
import arrow.mems.persistence.entity.CountScheduleItem;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.NoticeLog;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.service.MasterDashboardService;
import arrow.mems.util.SelectItemGenerator;
import arrow.mems.util.dialog.DialogUtil;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class MasterDashboardBean extends AbstractBean {
  @Inject
  private UserCredential userCredential;
  @Inject
  private MasterDashboardService masterDashboardService;
  @Inject
  private UsersRepository usersRepository;
  @Inject
  private InputRepairRequestBean inputRepairRequestBean;

  // Screen input right
  private List<ScheduleItem> listScheduleItem1;
  private List<CountScheduleItem> listCountScheduleItem1;
  private List<ScheduleItem> listScheduleItem2;
  private List<CountScheduleItem> listCountScheduleItem2;

  private List<ScheduleItem> listScheduleItemAllCases;
  private List<CountScheduleItem> listCountScheduleItemAllCase;
  // List device expired
  private List<Device> listDeviceExpired;

  private int valueRadioBtn;
  private List<NoticeLog> listNoticeLog;
  private List<PartOrderItem> listDeliverySchedule;
  private CountAndScheduleItemEntity selectedScheItem;
  private NoticeLog selectedNoticeLog;

  // List store scheduleItem And CountScheduleItem
  private List<CountAndScheduleItemEntity> generalListOfScheduleItem;

  // Left screens
  private int totalRepairAllTask;
  private int totalRepairItemOfYear;
  private double totalRepairCostAllTask;
  private double totalRepairItemCostOfYear;

  private List<ActionLog> listActionLogOfAllTask;

  @PostConstruct
  public void init() {
    this.valueRadioBtn = 1;
    this.getScheItemFilterByCommingCheck();
  }

  public void showDialogConfirmLogin(CountAndScheduleItemEntity selectedScheItem, NoticeLog selectedNoticeLog) {
    this.selectedScheItem = selectedScheItem;
    this.selectedNoticeLog = selectedNoticeLog;
    // Check if you are login
    if (this.isSupervisor()) {
      if (this.selectedScheItem != null) {
        this.renderDismissDialog = true;
      }
      if (this.selectedNoticeLog != null) {
        this.renderDismissNotificationDialog = true;
      }
      return;
    }
    if (!this.userCredential.isSupervisor()) {
      DialogUtil.OpenDialog("supervisor_login");
    }
  }

  private String userLogin;
  private boolean renderDismissDialog;
  private boolean renderDismissNotificationDialog;

  public boolean isSupervisor() {
    if (ArrowStrUtils.isNotEmpty(this.userLogin))
      return true;
    final FacesContext context = FacesContext.getCurrentInstance();
    final HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    final Object userId = session.getAttribute(AuthenticationConstants.SUPERVISOR_SESSION_KEY);
    if (userId == null)
      return false;
    if (ArrowStrUtils.isEmpty(userId.toString()))
      return false;
    else {
      final Users user = this.usersRepository.findBy(Integer.parseInt(userId.toString()));
      if (user.isSuppervisor()) {
        this.userLogin = userId.toString();
        return true;
      }
      return false;
    }
  }

  public void showDialogConfirmDismiss() {
    if (this.isSupervisor()) {
      this.renderDismissDialog = true;
    } else {
      this.renderDismissDialog = false;
    }
  }

  public void showDialogConfirmNotificationDismiss() {
    if (this.isSupervisor()) {
      this.renderDismissNotificationDialog = true;
    } else {
      this.renderDismissNotificationDialog = false;
    }
  }

  public void resetScheduleItem() {
    this.listCountScheduleItem1 = null;
    this.listCountScheduleItem2 = null;
    this.listScheduleItem1 = null;
    this.listScheduleItem2 = null;
    this.listCountScheduleItemAllCase = null;
    this.listScheduleItemAllCases = null;
    this.filtercheduleItem();
  }

  public void resetNoticeLog() {
    this.listNoticeLog = null;
    this.getListNoticeLog();
  }

  public void dismissOk(CountAndScheduleItemEntity selectedItem) {
    this.dismissScheduleItem(selectedItem);
    this.renderDismissDialog = false;
    this.resetScheduleItem();
  }

  public void dismissNotificationOk(NoticeLog selectedNoticeLog) {
    if (selectedNoticeLog != null) {
      this.masterDashboardService.deleteNoticeLogById(selectedNoticeLog.getNoticeId());
    }
    if (this.selectedNoticeLog != null) {
      this.masterDashboardService.deleteNoticeLogById(this.selectedNoticeLog.getNoticeId());
    }
    this.renderDismissNotificationDialog = false;
    this.resetNoticeLog();
  }

  public void dismissCancel() {
    this.renderDismissDialog = false;
    this.renderDismissNotificationDialog = false;
  }

  public void dismissScheduleItem(CountAndScheduleItemEntity selectedItem) {
    if (selectedItem != null) {
      if (selectedItem.getItemType() == CountAndScheduleItemEntity.SCHEDULE_ITEM_TYPE) {
        this.masterDashboardService.deleteScheduleItemByCode(selectedItem.getCode());
      } else {
        this.masterDashboardService.deleteCountScheItemByCode(selectedItem.getCode());
      }
    }
    if (this.selectedScheItem != null) {
      if (this.selectedScheItem.getItemType() == CountAndScheduleItemEntity.SCHEDULE_ITEM_TYPE) {
        this.masterDashboardService.deleteScheduleItemByCode(this.selectedScheItem.getCode());
      } else {
        this.masterDashboardService.deleteCountScheItemByCode(this.selectedScheItem.getCode());
      }
    }
  }

  public void setInputRepairRequestVar(CountAndScheduleItemEntity selectedSched) {
    final Device selectedDevice = this.masterDashboardService.getDeviceByDevCode(selectedSched.getDevCode());
    this.inputRepairRequestBean.setSelectedDevice(selectedDevice);
    this.inputRepairRequestBean.setRepairMode(CommonConstants.REPAIR_MODE_USE_SCHEDULE);
    if (selectedSched.getItemType() == CountAndScheduleItemEntity.COUNT_SCHEDULE_ITEM_TYPE) {
      this.inputRepairRequestBean.setScheduleType(CommonConstants.USE_COUNT_SCHEDULE);
    } else {
      this.inputRepairRequestBean.setScheduleType(CommonConstants.USE_SCHEDULE_ITEM);
    }
    this.inputRepairRequestBean.setScheduleCode(selectedSched.getCode());
    this.inputRepairRequestBean.addNewActionLog();
  }

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

  public void getScheItemFilterByCommingCheck() {
    if (this.listCountScheduleItem1 == null) {
      this.listCountScheduleItem1 =
          this.masterDashboardService.getCountScheItemFilterByStartToEndDate(this.userCredential.getUserInfo().getOfficeCode(), 30);
    }
    if (this.listScheduleItem1 == null) {
      this.listScheduleItem1 = this.masterDashboardService.getScheItemFilterByStartToEndDate(this.userCredential.getUserInfo().getOfficeCode(), 30);
    }
    this.generalListOfScheduleItem = new ArrayList<CountAndScheduleItemEntity>();
    if (this.listCountScheduleItem1 != null) {
      for (final CountScheduleItem cScheItem : this.listCountScheduleItem1) {
        this.setValueIntoGeneralListOfScheduleItem(null, cScheItem);
      }
    }
    if (this.listScheduleItem1 != null) {
      for (final ScheduleItem scheItem : this.listScheduleItem1) {
        this.setValueIntoGeneralListOfScheduleItem(scheItem, null);
      }
    }
  }

  public void getScheItemFilterByCheckthisMonth() {
    if (this.listScheduleItem2 == null) {
      this.listScheduleItem2 =
          this.masterDashboardService.getScheItemFilterByFirstDateOfCurrentMonthToEndDate(this.userCredential.getUserInfo().getOfficeCode(),
              DateUtils.getNextDateFromFirstDayOfMonth(45));
    }
    this.generalListOfScheduleItem = new ArrayList<CountAndScheduleItemEntity>();
    if (this.listScheduleItem2 != null) {
      for (final ScheduleItem scheItem : this.listScheduleItem2) {
        this.setValueIntoGeneralListOfScheduleItem(scheItem, null);
      }
    }
  }

  public void getScheItemFilterByNextMonth() {
    if (this.listCountScheduleItem2 == null) {
      this.listCountScheduleItem2 =
          this.masterDashboardService.getCountScheItemFilterByFirstDateOfCurrentMonthToEndDate(this.userCredential.getUserInfo().getOfficeCode(),
              DateUtils.getNextDateFromFirstDayOfMonth(45));
    }
    this.generalListOfScheduleItem = new ArrayList<CountAndScheduleItemEntity>();
    if (this.listCountScheduleItem2 != null) {
      for (final CountScheduleItem countScheItem : this.listCountScheduleItem2) {
        this.setValueIntoGeneralListOfScheduleItem(null, countScheItem);
      }
    }
  }

  public void getScheItemFilterByAllCases() {
    LocalDate toDate;
    if (DateUtils.getNextDateFromCurrentDay(30).isBefore(DateUtils.getNextDateFromFirstDayOfMonth(45))) {
      toDate = DateUtils.getNextDateFromFirstDayOfMonth(45);
    } else {
      toDate = DateUtils.getNextDateFromCurrentDay(30);
    }
    if (this.listScheduleItemAllCases == null) {
      this.listScheduleItemAllCases =
          this.masterDashboardService.getScheItemFilterByFirstDateOfCurrentMonthToEndDate(this.userCredential.getUserInfo().getOfficeCode(), toDate);
    }
    if (this.listCountScheduleItemAllCase == null) {
      this.listCountScheduleItemAllCase =
          this.masterDashboardService.getCountScheItemFilterByFirstDateOfCurrentMonthToEndDate(this.userCredential.getUserInfo().getOfficeCode(),
              toDate);
    }

    this.generalListOfScheduleItem = new ArrayList<CountAndScheduleItemEntity>();
    if (this.listCountScheduleItemAllCase != null) {
      for (final CountScheduleItem cScheItem : this.listCountScheduleItemAllCase) {
        this.setValueIntoGeneralListOfScheduleItem(null, cScheItem);
      }
    }
    if (this.listScheduleItemAllCases != null) {
      for (final ScheduleItem scheItem : this.listScheduleItemAllCases) {
        this.setValueIntoGeneralListOfScheduleItem(scheItem, null);
      }
    }
  }

  private void setValueIntoGeneralListOfScheduleItem(ScheduleItem scheItem, CountScheduleItem countScheItem) {
    if (scheItem != null) {
      final CountAndScheduleItemEntity newScheItem = new CountAndScheduleItemEntity();
      newScheItem.setActionCode(scheItem.getActionCode());
      newScheItem.setCode(scheItem.getSchedCode());
      newScheItem.setDescription(scheItem.getSchedTitle());
      newScheItem.setDevCode(scheItem.getDevCode());
      newScheItem.setItemType(CountAndScheduleItemEntity.SCHEDULE_ITEM_TYPE);
      this.generalListOfScheduleItem.add(newScheItem);
    }
    if (countScheItem != null) {
      final CountAndScheduleItemEntity newCountScheItem = new CountAndScheduleItemEntity();
      newCountScheItem.setActionCode(countScheItem.getActionCode());
      newCountScheItem.setCode(countScheItem.getCountSchedCode());
      newCountScheItem.setDescription(countScheItem.getSchedTitle());
      newCountScheItem.setDevCode(countScheItem.getDevCode());
      newCountScheItem.setItemType(CountAndScheduleItemEntity.COUNT_SCHEDULE_ITEM_TYPE);
      this.generalListOfScheduleItem.add(newCountScheItem);
    }
  }

  public int getValueRadioBtn() {
    return this.valueRadioBtn;
  }

  public void setValueRadioBtn(int pValueRadioBtn) {
    this.valueRadioBtn = pValueRadioBtn;
  }

  public List<NoticeLog> getListNoticeLog() {
    if (this.listNoticeLog == null) {
      this.listNoticeLog = this.masterDashboardService.getNoticeLogByOfficeCode(this.userCredential.getUserInfo().getOfficeCode());
    }
    return this.listNoticeLog;
  }

  public void setListNoticeLog(List<NoticeLog> pListNoticeLog) {
    this.listNoticeLog = pListNoticeLog;
  }

  public List<PartOrderItem> getListDeliverySchedule() {
    if (this.listDeliverySchedule == null) {
      this.listDeliverySchedule = this.masterDashboardService.getDeliverySchedule(this.userCredential.getUserInfo().getOfficeCode());
    }
    return this.listDeliverySchedule;
  }

  public void setListDeliverySchedule(List<PartOrderItem> pListDeliverySchedule) {
    this.listDeliverySchedule = pListDeliverySchedule;
  }

  public List<CountAndScheduleItemEntity> getGeneralListOfScheduleItem() {
    return this.generalListOfScheduleItem;
  }

  public void setGeneralListOfScheduleItem(List<CountAndScheduleItemEntity> pGeneralListOfScheduleItem) {
    this.generalListOfScheduleItem = pGeneralListOfScheduleItem;
  }

  public boolean isRenderDismissDialog() {
    return this.renderDismissDialog;
  }

  public void setRenderDismissDialog(boolean pRenderDismissDialog) {
    this.renderDismissDialog = pRenderDismissDialog;
  }

  public boolean isRenderDismissNotificationDialog() {
    return this.renderDismissNotificationDialog;
  }

  public void setRenderDismissNotificationDialog(boolean pRenderDismissNotificationDialog) {
    this.renderDismissNotificationDialog = pRenderDismissNotificationDialog;
  }

  public int totalRepairOfCurrentMonthByCorpCode(boolean isTabAllTask) {
    if (isTabAllTask) {
      if (this.totalRepairAllTask == 0) {
        this.totalRepairAllTask = this.masterDashboardService.getTotalRepairCurrentMonthOfAllTaskByCorpCode(this.userCredential.getOfficeCode());
      }
      return this.totalRepairAllTask;
    } else
      return 0;
  }

  public int totalRepairItemOfYearByCorpCode(boolean isTabAllTask) {
    if (isTabAllTask) {
      if (this.totalRepairItemOfYear == 0) {
        this.totalRepairItemOfYear =
            this.masterDashboardService.getTotalRepairItemOfYearAllTaskByCorpCode(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairItemOfYear;
    } else
      return 0;
  }

  public double totalRepairCostOfCurrentMonthByCorpCode(boolean isTabAllTask) {
    if (isTabAllTask) {
      if (this.totalRepairCostAllTask == 0) {
        this.totalRepairCostAllTask =
            this.masterDashboardService.getTotalRepairCostCurrentMonthOfAllTaskByCorpCode(this.userCredential.getOfficeCode());
      }
      return this.totalRepairCostAllTask;
    } else
      return 0;
  }

  public double totalRepairCostItemOfYearByCorpCode(boolean isTabAllTask) {
    if (isTabAllTask) {
      if (this.totalRepairItemCostOfYear == 0) {
        this.totalRepairItemCostOfYear =
            this.masterDashboardService.getTotalRepairCostCurrentYearOfAllTaskByCorpCode(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.totalRepairItemCostOfYear;
    } else
      return 0;
  }

  public List<ActionLog> listActionLogOfAllTaskByCorpCode(boolean isTabAllTask) {
    if (isTabAllTask) {
      if (this.listActionLogOfAllTask == null) {
        this.listActionLogOfAllTask =
            this.masterDashboardService.getListContactOfAllTaskByCorpCode(this.userCredential.getUserInfo().getOfficeCode());
      }
      return this.listActionLogOfAllTask;
    } else
      return null;
  }

  public String getStatusName(ActionLog actionLog) {
    if (actionLog.getFinishDate() != null)
      return SelectItemGenerator.getListSelectItem(SelectItemGenerator.ListType.SCHEDULE_TASK).get(CommonConstants.TASK_DONE - 1).getLabel();
    else if ((actionLog.getOccurDate() != null) && (actionLog.getContactDate() != null))
      return SelectItemGenerator.getListSelectItem(SelectItemGenerator.ListType.SCHEDULE_TASK).get(CommonConstants.TASK_DOING - 1).getLabel();
    else
      return SelectItemGenerator.getListSelectItem(SelectItemGenerator.ListType.SCHEDULE_TASK).get(CommonConstants.TASK_NOT_START - 1).getLabel();
  }

  public List<Device> getListDeviceExpired() {
    return this.listDeviceExpired;
  }

  public void setListDeviceExpired(List<Device> pListDeviceExpired) {
    this.listDeviceExpired = pListDeviceExpired;
  }


}
