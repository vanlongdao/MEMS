/**
 *
 */
package arrow.mems.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.CollectionUtils;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.bean.mrr.PartEstimationBean;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.ActionTypeMaster;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.Fault;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.service.InputRepairRequestService;
import arrow.mems.service.ReplacedPartService;
import arrow.mems.util.dialog.DialogUtil;
import arrow.mems.util.file.FileUtils;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class InputRepairRequestBean extends AbstractApprovalBean {

  @Inject
  DeviceRepository deviceRepository;
  @Inject
  InputRepairRequestService requestService;
  @Inject
  ReplacedPartBean replacePartBean;
  @Inject
  ReplacedPartService replacePartService;
  @Inject
  ChecklistResultsBean checklistResultsBean;
  @Inject
  PartEstimationBean partEstimationBean;
  @Inject
  PartsOrderBean partsOrderBean;
  @Inject
  FeeBean feeBean;
  @Inject
  ImageStreamBean imageStreamBean;
  @Inject
  UserCredential userCredential;

  private ActionLog currentActionLog;
  private ActionLog selectedActionLog;

  private HumanResource hospContPerson;

  private Users checker;

  private Device selectedDevice;

  private boolean useModeSchedule;

  private int repairMode;
  private int scheduleType;

  private String scheduleCode;

  private UploadedFile image;
  private String currentKey;

  private List<Users> users;

  private Device currentDevice;
  private Fault currentFault;
  private List<HumanResource> listHumanResources;
  private List<Person> listContactPersons;

  // Set&Get DeviceCode from QRCode Screen
  private String deviceCode;

  public String getDeviceCode() {
    return this.deviceCode;
  }

  public void setDeviceCode(String pDeviceCode) {
    this.deviceCode = pDeviceCode;
    if (StringUtils.isNotEmpty(this.deviceCode)) {
      this.selectedDevice = this.deviceRepository.findActiveDeviceByDeviceCode(this.deviceCode).getAnyResult();
      this.addNewActionLog();
    }
  }

  public Device getSelectedDevice() {
    return this.selectedDevice;
  }

  public void setSelectedDevice(Device pSelectedDevice) {
    this.selectedDevice = pSelectedDevice;
  }

  public ActionLog getSelectedActionLog() {
    return this.selectedActionLog;
  }

  public void setSelectedActionLog(ActionLog pSelectedActionLog) {
    this.selectedActionLog = pSelectedActionLog;
  }

  private List<ActionLog> listActionLogs;

  public List<ActionLog> getListActionLogs() {
    if (this.listActionLogs == null) {
      this.listActionLogs = new ArrayList<>();
      final String officeCode = this.userCredential.getOfficeCode();
      final ServiceResult<List<ActionLog>> result = this.requestService.findAllActiveActionLog(officeCode);
      if (result.isSuccess()) {
        this.listActionLogs = result.getData();
      }
    }
    return this.listActionLogs;
  }

  public void setListActionLogs(List<ActionLog> pListActionLogs) {
    this.listActionLogs = pListActionLogs;
  }


  public void selectActionLog(SelectEvent event) {
    this.selectedActionLog = (ActionLog) event.getObject();
  }

  public void editActionLog() {
    if (this.selectedActionLog == null)
      return;
    this.setCurrentDevice(this.selectedActionLog.getDevice());
    this.selectedActionLog.setSelected(true);
    final ServiceResult<ActionLog> result = this.requestService.editInputRepairRequest(this.selectedActionLog);

    if (result != null) {
      this.currentActionLog = result.getData();
      if (this.selectedActionLog.getCheckedBy() != null) {
        this.currentActionLog.setCheckedBy(this.selectedActionLog.getCheckedBy());
        this.checker = this.currentActionLog.getCheckedByUser();
      }
      this.transferActionLogToBeans(this.currentActionLog);

      this.currentKey = this.currentActionLog.getActionCode();
      // get data of table Fault
      final ServiceResult<Fault> faultResult = this.requestService.findFaultByActionCode(this.currentActionLog.getActionCode());
      this.currentFault = faultResult.getData();
    }
  }

  public void addNewActionLog() {
    if (this.selectedDevice == null)
      return;
    this.currentActionLog = new ActionLog();
    this.currentActionLog.setDevice(this.selectedDevice);
    this.setCurrentDevice(this.selectedDevice);
    this.currentFault = new Fault();
    this.transferActionLogToBeans(this.currentActionLog);
    this.currentKey = this.currentActionLog.getFakeId();
  }

  public void transferActionLogToBeans(ActionLog actionLog) {
    this.partEstimationBean.setCurrentActionLog(actionLog);
    this.partsOrderBean.setCurrentActionLog(actionLog);
    this.checklistResultsBean.setCurrentActionLog(actionLog);
    this.replacePartBean.setCurrentActionLog(actionLog);
    this.feeBean.setCurrentActionLog(actionLog);
  }

  public void saveFault() {
    if (!this.currentFault.isPersisted()) {
      final String actionCode = this.currentActionLog.getActionCode();
      this.requestService.saveNewFault(this.currentFault, actionCode);
    } else {
      this.requestService.updateFault(this.currentFault);
    }
  }

  public void saveRepairRequest() {
    if (this.image != null) {
      this.currentActionLog.setInstallConfirmImg(this.imageStreamBean.getRequestImage(this.currentKey));
    }

    if (!this.replacePartBean.validateData())
      return;

    final String officeCode = this.userCredential.getOfficeCode();
    final ServiceResult<ActionLog> result = this.requestService.saveActionLog(this.currentActionLog, officeCode);
    result.showMessages();
    if (!result.isSuccess())
      return;
    this.currentActionLog = result.getData();
    this.saveSchedule();
    this.saveFault();
    this.feeBean.saveActionBill();
    this.transferActionLogToBeans(this.currentActionLog);
    this.replacePartBean.saveReplacedParts();
    this.checklistResultsBean.saveChecklistResult();
    this.partEstimationBean.savePartEstimate();
    this.partsOrderBean.savePartOrder();

    // change current mode to mode edit
    this.changeToModeEdit();
  }

  public void changeToModeEdit() {
    this.selectedActionLog = this.currentActionLog;
    this.discardChanges();
    this.editActionLog();
  }

  public void discardChanges() {
    this.currentActionLog = null;
    this.currentDevice = null;
    this.currentFault = null;
    this.currentKey = null;
    this.checker = null;
    this.listHumanResources = null;
    this.listContactPersons = null;
    this.image = null;
    this.hospContPerson = null;
    this.selectedDevice = null;
    this.users = null;
  }

  public void saveSchedule() {
    if (this.repairMode == CommonConstants.REPAIR_MODE_NOT_USE_SCHEDULE)
      return;
    if (this.scheduleType == CommonConstants.USE_COUNT_SCHEDULE) {
      this.requestService.saveCountSchedule(this.scheduleCode, this.currentActionLog);
    }
    if (this.scheduleType == CommonConstants.USE_SCHEDULE_ITEM) {
      this.requestService.saveScheduleItem(this.scheduleCode, this.currentActionLog);
    }
  }

  public ActionLog getCurrentActionLog() {
    return this.currentActionLog;
  }

  public void setCurrentActionLog(ActionLog pCurrentActionLog) {
    this.currentActionLog = pCurrentActionLog;
    if ((this.currentActionLog != null) && this.useModeSchedule) {
      final ActionTypeMaster type = this.requestService.getActionTypeMaster(CommonConstants.TYPE_MAINTENANCE);
      this.currentActionLog.setActionTypeMaster(type);
    }
  }

  public List<HumanResource> filterHospDeptContactPerson(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListHumanResources();
    return CollectionUtils.filter(this.getListHumanResources(), object -> {
      final HumanResource item = (HumanResource) object;
      return StringUtils.containsIgnoreCase(item.getPerson().getName(), query) || item.getPerson().getPsnCode().contains(query);
    });
  }

  public List<Person> filterContactPerson(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListContactPersons();
    return CollectionUtils.filter(this.getListContactPersons(), object -> {
      final Person item = (Person) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || item.getPsnCode().contains(query);
    });
  }

  public List<HumanResource> getListHumanResources() {
    if ((this.listHumanResources == null) || this.listHumanResources.isEmpty()) {
      this.listHumanResources = new ArrayList<>();

      if ((this.currentActionLog.getDevice() == null) || (this.currentActionLog.getDevice().getHospitalDept().getHospital() == null))
        return this.listHumanResources;

      final String hopsCode = this.currentActionLog.getDevice().getHospitalDept().getHospital().getHospCode();
      final String officeCode = this.userCredential.getOfficeCode();
      this.listHumanResources = this.requestService.getAllHumanInOneHospital(hopsCode, officeCode);
    }
    return this.listHumanResources;
  }

  public List<Users> filterCheckedBy(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListUsers();
    return CollectionUtils.filter(this.getListUsers(), object -> {
      final Users item = (Users) object;
      return StringUtils.containsIgnoreCase(item.getName(), query);
    });
  }

  public List<Users> getListUsers() {
    if ((this.users == null) || this.users.isEmpty()) {
      this.users = new ArrayList<Users>();

      if ((this.listHumanResources == null) || this.listHumanResources.isEmpty()) {
        this.getListHumanResources();
      }

      final List<String> listPsnCode = new ArrayList<>();
      for (final HumanResource human : this.listHumanResources) {
        listPsnCode.add(human.getPsnCode());
      }
      this.users = this.requestService.findActiveUserByPsnCodes(listPsnCode);
    }
    return this.users;
  }

  public void setListHumanResources(List<HumanResource> pListHumanResources) {
    this.listHumanResources = pListHumanResources;
  }

  public Fault getCurrentFault() {
    return this.currentFault;
  }

  public void setCurrentFault(Fault pCurrentFault) {
    this.currentFault = pCurrentFault;
  }

  public Device getCurrentDevice() {
    return this.currentDevice;
  }

  public void setCurrentDevice(Device pCurrentDevice) {
    this.currentDevice = pCurrentDevice;
  }

  public String getCurrentKey() {
    return this.currentKey;
  }

  public void setCurrentKey(String pCurrentKey) {
    this.currentKey = pCurrentKey;
  }

  public void fileImageUploadListener(FileUploadEvent e) throws IOException {
    this.setImage(e.getFile());
    final byte[] content = FileUtils.getContentFile(this.getImage().getInputstream());
    this.imageStreamBean.uploadEstimateImageTemporary(this.currentKey, content);
  }

  public UploadedFile getImage() {
    return this.image;
  }

  public void setImage(UploadedFile pImage) {
    this.image = pImage;
  }

  @Override
  public Object getEntityId() {
    return null;
  }

  @Override
  public String getDataType() {
    return null;
  }

  @Override
  public String getItemLabel() {
    return null;
  }

  public List<Users> getUsers() {
    return this.users;
  }

  public void setUsers(List<Users> pUsers) {
    this.users = pUsers;
  }

  public Users getChecker() {
    return this.checker;
  }

  public void setChecker(Users pChecker) {
    this.checker = pChecker;
    if (this.checker != null) {
      this.currentActionLog.setCheckedBy(this.checker.getUserId());
    }
  }

  public String getScheduleCode() {
    return this.scheduleCode;
  }

  public void setScheduleCode(String pScheduleCode) {
    this.scheduleCode = pScheduleCode;
  }

  public int getRepairMode() {
    return this.repairMode;
  }

  public void setRepairMode(int pRepairMode) {
    this.repairMode = pRepairMode;
  }

  public int getScheduleType() {
    return this.scheduleType;
  }

  public void setScheduleType(int pScheduleType) {
    this.scheduleType = pScheduleType;
  }

  public boolean isUseModeSchedule() {
    return this.useModeSchedule;
  }

  public void setUseModeSchedule(boolean pUseModeSchedule) {
    this.useModeSchedule = pUseModeSchedule;
    if (this.useModeSchedule) {
      this.repairMode = CommonConstants.REPAIR_MODE_USE_SCHEDULE;
    } else {
      this.repairMode = CommonConstants.REPAIR_MODE_NOT_USE_SCHEDULE;
    }
  }

  public HumanResource getHospContPerson() {
    if (this.currentActionLog.getHospitalContactPerson() != null) {
      final String psnCode = this.currentActionLog.getHospitalContactPerson().getPsnCode();
      final String hospCode = this.currentActionLog.getHospCode();
      final String hospDeptCode = this.currentActionLog.getHospDeptCode();
      this.hospContPerson = this.requestService.getHumanOfThisAction(psnCode, hospCode, hospDeptCode);
    }
    return this.hospContPerson;
  }

  public void setHospContPerson(HumanResource pHospContPerson) {
    this.hospContPerson = pHospContPerson;
    if (this.hospContPerson != null) {
      this.currentActionLog.setHospitalContactPerson(this.hospContPerson.getPerson());
      this.currentActionLog.setHospCode(this.hospContPerson.getHospCode());
      this.currentActionLog.setHospDeptCode(this.hospContPerson.getHospDeptCode());
    }
  }

  public List<Person> getListContactPersons() {
    if ((this.listContactPersons == null) || this.listContactPersons.isEmpty()) {
      this.listContactPersons = Collections.emptyList();
      String officeCode = null;
      final String userOffice = this.userCredential.getOfficeCode();
      if (this.currentDevice.getSupplierOffice() != null) {
        officeCode = this.currentDevice.getSupllierOffice();
        this.listContactPersons = this.requestService.getPersonsByOfficeCode(officeCode, userOffice);
      } else {
        if ((this.currentDevice.getHospitalDept() == null) || (this.currentDevice.getHospitalDept().getHospital() == null))
          return this.listContactPersons;
        officeCode = this.currentDevice.getHospitalDept().getHospital().getOfficeCode();
        this.listContactPersons = this.requestService.getPersonsNotInOffice(officeCode, userOffice);
      }
    }
    return this.listContactPersons;
  }

  public void setListContactPersons(List<Person> pListContactPersons) {
    this.listContactPersons = pListContactPersons;
  }

  public void showDrawingPane() {
    DialogUtil.OpenDialog("drawing");
  }

  public void saveDrawing(SelectEvent json) {
    if (json.getObject() != null) {
      this.currentActionLog.setInstallConfirmImg(json.getObject().toString().getBytes());
    }
  }

  public String getInstallConfirmImgAsJson() {
    if ((this.currentActionLog != null) && (this.currentActionLog.getInstallConfirmImg() != null))
      return new String(this.currentActionLog.getInstallConfirmImg());
    return "";
  }
}
