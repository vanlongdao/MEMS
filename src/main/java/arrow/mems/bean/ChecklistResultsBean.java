/**
 *
 */
package arrow.mems.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.framework.util.CollectionUtils;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.service.ChecklistResultService;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class ChecklistResultsBean extends AbstractBean {

  @Inject
  ChecklistResultService service;
  @Inject
  InputRepairRequestBean repairRequestBean;
  @Inject
  UserCredential userCredential;

  private ActionLog currentActionLog;

  private Checklist currentChecklist;
  private Checklist selectedChecklist;
  private Checklist newChecklist;
  private ChecklistItem currentChecklistItem;
  private ChecklistItem selectedChecklistItem;
  private List<Checklist> listChecklistShow;
  private List<Checklist> listChecklistIsNew;
  private List<Checklist> listChecklistIsDelete;
  private List<Checklist> listChecklistIsModify;
  private List<ChecklistItem> listChecklistItemShow;
  private Map<String, List<ChecklistItem>> mapListChecklistItemIsNew;
  private List<ChecklistItem> listChecklistItemIsDelete;
  private List<ChecklistItem> listChecklistItemIsModify;
  private List<String> verifies;
  private String verify;

  private List<MdevChecklist> listMdevChecklist;

  private boolean isSaveItem;

  @PostConstruct
  public void init() {
    this.newChecklist = new Checklist();
    this.listChecklistIsNew = new ArrayList<Checklist>();
    this.listChecklistIsDelete = new ArrayList<>();
    this.listChecklistIsModify = new ArrayList<>();
    this.mapListChecklistItemIsNew = new HashMap<>();
    this.listChecklistItemIsDelete = new ArrayList<>();
    this.listChecklistItemIsModify = new ArrayList<>();
  }

  // created new checklist after choose one item on list setupChecklist
  // created new checklist item from cklistCode refer to ckiCode
  public void createdNewChecklist() {
    final String officeCode = this.userCredential.getOfficeCode();
    if (this.userCredential.getPerson() != null) {
      this.newChecklist.setServicePsn(this.userCredential.getPerson().getPsnCode());
    }

    final ServiceResult<Checklist> result = this.service.createdNewChecklist(this.newChecklist, this.listChecklistShow, officeCode);
    result.showMessages();
    if (!result.isSuccess()) {
      this.newChecklist = new Checklist();
      return;
    }

    this.listChecklistIsNew.add(result.getData());
    this.listChecklistShow.add(result.getData());
    if (this.newChecklist.getMdevChecklist() != null) {
      final String cklistCode = this.newChecklist.getMdevChecklist().getCklistCode();
      final List<ChecklistItem> newChecklistItem = this.service.createLocalDataAfterCreatedNewChecklist(cklistCode, officeCode);
      if (!newChecklistItem.isEmpty()) {
        this.mapListChecklistItemIsNew.put(cklistCode, newChecklistItem);
      }
    }
    this.newChecklist = new Checklist();
  }

  /*
   * save checklist result when click button save of screen
   * - get list checklist is delete and delete them.
   * - get list checklist is new record and save them into database
   * - get list checklist is modify and check:
   * + if checklist is persisted, delete record on database and save new record is edit
   * + if checklist is'nt persisted, save new record
   */
  public void saveChecklistResult() {
    if (!this.listChecklistIsNew.isEmpty()) {
      final String actionCode = this.currentActionLog.getActionCode();
      final String officeCode = this.userCredential.getOfficeCode();
      this.service.saveNewChecklistAndChecklistItem(this.listChecklistIsNew, this.mapListChecklistItemIsNew, actionCode, officeCode);
    }
    if (!this.listChecklistIsModify.isEmpty()) {
      this.service.updateChecklist(this.listChecklistIsModify);
    }
    if (!this.listChecklistItemIsModify.isEmpty()) {
      this.service.updateChecklistItem(this.listChecklistItemIsModify);
    }
    if (!this.listChecklistIsDelete.isEmpty()) {
      this.service.deleteChecklist(this.listChecklistIsDelete);
    }
    if (!this.listChecklistItemIsDelete.isEmpty()) {
      this.service.deleteChecklistItem(this.listChecklistItemIsDelete);
    }
    this.discardChanges();
  }

  // filter of setup checklist
  public List<Checklist> filterChecklist(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListChecklistShow();
    return CollectionUtils.filter(
        this.getListChecklistShow(),
        object -> {
          final Checklist item = (Checklist) object;
          return StringUtils.containsIgnoreCase(item.getMdevChecklist().getName(), query) || StringUtils.containsIgnoreCase(item.getCheckDate()
              .toString(), query);
        });
  }

  // delete currentChecklist
  public void deleteThisChecklist() {
    if (this.currentChecklist != null) {
      if (this.currentChecklist.isPersisted()) {
        this.listChecklistIsDelete.add(this.currentChecklist);
        this.listChecklistItemIsDelete.addAll(this.listChecklistItemShow);
      } else {
        this.listChecklistIsNew.remove(this.currentChecklist);
        this.listChecklistIsModify.remove(this.currentChecklist);
      }
      this.mapListChecklistItemIsNew.remove(this.currentChecklist.getMdevChecklist().getCklistCode());
      this.listChecklistItemShow = new ArrayList<>();
      this.listChecklistShow.remove(this.currentChecklist);
      this.currentChecklist = null;
      this.currentChecklistItem = null;
      this.selectedChecklist = null;
      this.selectedChecklistItem = null;
    }
  }

  // discard all changes
  public void discardChanges() {
    this.listChecklistShow = null;
    this.listChecklistItemShow = null;
    this.newChecklist = new Checklist();
    this.listChecklistIsNew = new ArrayList<Checklist>();
    this.listChecklistIsDelete = new ArrayList<>();
    this.listChecklistIsModify = new ArrayList<>();
    this.mapListChecklistItemIsNew = new HashMap<>();
    this.listChecklistItemIsDelete = new ArrayList<>();
    this.listChecklistItemIsModify = new ArrayList<>();
    this.currentChecklist = null;
    this.currentChecklistItem = null;
    this.selectedChecklist = null;
    this.selectedChecklistItem = null;
    this.verify = null;
  }

  /*
   * 1. validate checklist, if it pass, next step :))
   * 2:
   * case 1: if checklist is persisted, add it to list checklist modify
   * case 2: if not, stead current checklist on list new checklist by select checklist
   * 3. instead current checklist by selected checklist on listChecklistShow
   * (because after save checklist, panel is not hidden, set current checklist = selected
   * checklist)
   */
  public void saveThisChecklist() {
    if (this.currentChecklist != null) {
      final ServiceResult<Checklist> result = this.service.validateChecklist(this.selectedChecklist);
      if (!this.isSaveItem) {
        result.showMessages();
      }
      if (!result.isSuccess())
        return;

      if (this.selectedChecklist.isPersisted()) {
        this.listChecklistIsModify.add(this.selectedChecklist);
      } else {
        Collections.replaceAll(this.listChecklistIsNew, this.currentChecklist, this.selectedChecklist);
      }
      Collections.replaceAll(this.listChecklistShow, this.currentChecklist, this.selectedChecklist);
      this.currentChecklist = this.selectedChecklist;

      if (!this.isSaveItem) {
        this.cleanStageAfterSaveChecklist();
      }
    }
  }

  /*
   * 1.
   * case 1: if checklist item is persisted, add it to list checklist modify
   * case 2: if not, stead current checklist item by selected checklist item
   * 2. because after save item, panel is not hidden, set current item = selected item
   * 3. set check date of checklist after checklist item persisted.
   * 4. call method save Checklist after change field in selected checklist
   */
  public void saveThisChecklistItem() {
    if (this.currentChecklistItem == null)
      return;

    final ServiceResult<ChecklistItem> result = this.service.validateChecklistItem(this.selectedChecklistItem);
    result.showMessages();
    if (!result.isSuccess())
      return;

    final String cklistCode = this.currentChecklist.getMdevChecklist().getCklistCode();
    if (this.selectedChecklistItem.isPersisted()) {
      this.listChecklistItemIsModify.add(this.selectedChecklistItem);
    } else {
      Collections.replaceAll(this.mapListChecklistItemIsNew.get(cklistCode), this.currentChecklistItem, this.selectedChecklistItem);
    }
    this.currentChecklistItem = this.selectedChecklistItem;
    this.selectedChecklist.setCheckDate(LocalDate.now());
    this.isSaveItem = true;
    this.saveThisChecklist();
    this.cleanStageAfterSaveChecklistItem();
  }

  public void cleanStageAfterSaveChecklist() {
    this.setCurrentChecklist(null);
    this.selectedChecklist = null;
  }

  public void cleanStageAfterSaveChecklistItem() {
    this.selectedChecklistItem = null;
    this.currentChecklistItem = null;
    this.isSaveItem = false;
  }

  // get list checklist to show on screen( show when to choose checklist from table (2))
  public List<Checklist> getListChecklistShow() {
    if (this.listChecklistShow == null) {
      this.listChecklistShow = new ArrayList<>();
      final String actionCode = this.currentActionLog.getActionCode();

      if (StringUtils.isEmpty(actionCode))
        return this.listChecklistShow;

      final String officeCode = this.userCredential.getOfficeCode();
      this.listChecklistShow = this.service.getListChecklist(actionCode, officeCode);
    }
    return this.listChecklistShow;
  }

  public void setListChecklistShow(List<Checklist> pListChecklist) {
    this.listChecklistShow = pListChecklist;
  }

  public Checklist getCurrentChecklist() {
    return this.currentChecklist;
  }

  // when select checklist, set selected checklist equal current checklist
  public void setCurrentChecklist(Checklist pCurrentChecklist) {
    this.currentChecklist = pCurrentChecklist;
    if (this.currentChecklist != null) {
      this.selectedChecklist = new Checklist();
      BeanCopier.copy(this.currentChecklist, this.selectedChecklist);
    }
    this.currentChecklistItem = null;
    this.selectedChecklistItem = null;
  }

  public Checklist getNewChecklist() {
    return this.newChecklist;
  }

  public void setNewChecklist(Checklist pNewChecklist) {
    this.newChecklist = pNewChecklist;
  }

  public List<Checklist> getListNewChecklist() {
    return this.listChecklistIsNew;
  }

  public void setListNewChecklist(List<Checklist> pListNewChecklist) {
    this.listChecklistIsNew = pListNewChecklist;
  }

  // get list checklist item to show (show when choose checklist item on table (5))
  public List<ChecklistItem> getListChecklistItemShow() {
    if (this.currentChecklist != null) {
      if (this.currentChecklist.getMdevChecklist() == null)
        return Collections.emptyList();

      final String cklistCode = this.currentChecklist.getMdevChecklist().getCklistCode();
      if (this.mapListChecklistItemIsNew.get(cklistCode) == null) {
        final String officeCode = this.userCredential.getOfficeCode();
        this.listChecklistItemShow = this.service.getListChecklistItem(cklistCode, officeCode);
        this.mapListChecklistItemIsNew.put(cklistCode, this.listChecklistItemShow);
      } else {
        this.listChecklistItemShow = this.mapListChecklistItemIsNew.get(cklistCode);
      }
    }
    return this.listChecklistItemShow;
  }

  public void setListChecklistItemShow(List<ChecklistItem> pListChecklistItem) {
    this.listChecklistItemShow = pListChecklistItem;
  }

  public ChecklistItem getCurrentChecklistItem() {
    return this.currentChecklistItem;
  }

  // when selected checklist item, set selected checklist item equal current checklist item
  public void setCurrentChecklistItem(ChecklistItem currentChecklistItem) {
    this.currentChecklistItem = currentChecklistItem;
    if (this.currentChecklistItem != null) {
      this.selectedChecklistItem = new ChecklistItem();
      BeanCopier.copy(this.currentChecklistItem, this.selectedChecklistItem);
    }
  }

  public Map<String, List<ChecklistItem>> getMapNewListChecklistItem() {
    return this.mapListChecklistItemIsNew;
  }

  public void setMapNewListChecklistItem(Map<String, List<ChecklistItem>> pMapNewListChecklistItem) {
    this.mapListChecklistItemIsNew = pMapNewListChecklistItem;
  }

  public Checklist getSelectedChecklist() {
    return this.selectedChecklist;
  }

  public void setSelectedChecklist(Checklist pSelectedChecklist) {
    this.selectedChecklist = pSelectedChecklist;
  }

  public ChecklistItem getSelectedChecklistItem() {
    return this.selectedChecklistItem;
  }

  public void setSelectedChecklistItem(ChecklistItem pSelectedChecklistItem) {
    this.selectedChecklistItem = pSelectedChecklistItem;
  }

  public List<Checklist> getListChecklistIsDelete() {
    return this.listChecklistIsDelete;
  }

  public void setListChecklistIsDelete(List<Checklist> pListChecklistIsDelete) {
    this.listChecklistIsDelete = pListChecklistIsDelete;
  }

  public List<Checklist> getListChecklistIsModify() {
    return this.listChecklistIsModify;
  }

  public void setListChecklistIsModify(List<Checklist> pListChecklistModify) {
    this.listChecklistIsModify = pListChecklistModify;
  }

  public List<ChecklistItem> getListChecklistItemIsModify() {
    return this.listChecklistItemIsModify;
  }

  public void setListChecklistItemIsModify(List<ChecklistItem> pListChecklistItemIsModify) {
    this.listChecklistItemIsModify = pListChecklistItemIsModify;
  }

  public List<ChecklistItem> getListChecklistItemIsDelete() {
    return this.listChecklistItemIsDelete;
  }

  public void setListChecklistItemIsDelete(List<ChecklistItem> pListChecklistItemIsDelete) {
    this.listChecklistItemIsDelete = pListChecklistItemIsDelete;
  }

  public List<String> getVerifies() {
    if (this.verifies == null) {
      this.verifies = new ArrayList<>();
      this.verifies.add(CommonConstants.PASSED_LABEL);
      this.verifies.add(CommonConstants.NG_LABEL);
    }
    return this.verifies;
  }

  public void setVerifies(List<String> pVerifies) {
    this.verifies = pVerifies;
  }

  public String getVerify() {
    if ((this.selectedChecklistItem != null) && (this.selectedChecklistItem.getIsOk() != null)) {
      final int isOk = this.selectedChecklistItem.getIsOk();
      if (isOk == CommonConstants.PASSED) {
        this.verify = CommonConstants.PASSED_LABEL;
      } else {
        this.verify = CommonConstants.NG_LABEL;
      }
    } else {
      this.verify = null;
    }
    return this.verify;
  }

  public void setVerify(String pVerify) {
    this.verify = pVerify;
    if (!StringUtils.isEmpty(this.verify)) {
      if (this.verify.equalsIgnoreCase(CommonConstants.PASSED_LABEL)) {
        this.selectedChecklistItem.setIsOk(CommonConstants.PASSED);
      } else {
        this.selectedChecklistItem.setIsOk(CommonConstants.NG);
      }
    }
    this.verify = null;
  }

  public ActionLog getCurrentActionLog() {
    return this.currentActionLog;
  }

  public void setCurrentActionLog(ActionLog pCurrentActionLog) {
    this.currentActionLog = pCurrentActionLog;
  }

  public List<MdevChecklist> getListMdevChecklist() {
    if (this.listMdevChecklist == null) {
      final String officeCode = this.userCredential.getUserInfo().getOfficeCode();
      this.listMdevChecklist = new ArrayList<>();
      if (this.currentActionLog.getDevice().getMDevice() != null) {
        final String mdevCode = this.currentActionLog.getDevice().getMdevCode();
        this.listMdevChecklist = this.service.getAllMdevChecklist(mdevCode, officeCode);
      }
    }
    return this.listMdevChecklist;
  }

  public void setListMdevChecklist(List<MdevChecklist> pListMdevChecklist) {
    this.listMdevChecklist = pListMdevChecklist;
  }

  public List<MdevChecklist> filterMdevChecklist(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListMdevChecklist();
    return CollectionUtils.filter(this.getListMdevChecklist(), object -> {
      final MdevChecklist item = (MdevChecklist) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || item.getCklistCode().contains(query);
    });
  }

  public boolean isSaveItem() {
    return this.isSaveItem;
  }

  public void setSaveItem(boolean pIsSaveItem) {
    this.isSaveItem = pIsSaveItem;
  }
}
