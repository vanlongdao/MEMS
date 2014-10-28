package arrow.mems.bean.mrr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.framework.util.CollectionUtils;
import arrow.framework.util.i18n.Messages;
import arrow.mems.bean.CommonAutoCompleteBean;
import arrow.mems.bean.ImageStreamBean;
import arrow.mems.bean.InputRepairRequestBean;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.Person;
import arrow.mems.service.PartEstimateService;
import arrow.mems.service.PersonManagementService;
import arrow.mems.util.SelectItemGenerator;
import arrow.mems.util.SelectItemGenerator.ListType;
import arrow.mems.util.file.FileUtils;

@Named
@ViewScoped
public class PartEstimationBean extends AbstractApprovalBean {

  @Inject
  InputRepairRequestBean repairRequestBean;
  @Inject
  PartEstimateService service;
  @Inject
  ImageStreamBean imageStreamBean;
  @Inject
  UserCredential userCredential;
  @Inject
  PersonManagementService personService;
  @Inject
  CommonAutoCompleteBean commonAutoCompleteBean;

  private ActionLog currentActionLog;

  private PartEstimate currentEstimate;
  private PartEstimate selectedEstimate;
  private PartEstimateItem currentEstimateItem;
  private PartEstimateItem selectedEstimateItem;

  private List<PartEstimate> listEstimateShow;
  private List<PartEstimateItem> listEstimateItemShow;

  private Map<String, List<PartEstimateItem>> mapListNewEstimateItem;

  // contains internal data
  private List<PartEstimate> listEstimateIsNew;
  private List<PartEstimate> listEstimateIsModify;
  private List<PartEstimate> listEstimateIsDelete;

  private List<PartEstimateItem> listEstimateItemIsModify;
  private List<PartEstimateItem> listEstimateItemIsDelete;

  // contains data before modify, help is find object before update
  private List<PartEstimateItem> listEstimateItemHelpModify;

  // mark part estimate is create new
  private boolean isNewEstimate;

  // mark part estimate item is create new
  private boolean isNewEstimateItem;

  // process image
  private String currentEstimateKey;
  private UploadedFile image;

  @PostConstruct
  public void init() {
    this.mapListNewEstimateItem = new HashMap<>();
    this.listEstimateIsNew = new ArrayList<>();
    this.listEstimateIsModify = new ArrayList<>();
    this.listEstimateItemIsModify = new ArrayList<>();
    this.listEstimateIsDelete = new ArrayList<>();
    this.listEstimateItemIsDelete = new ArrayList<>();
    this.listEstimateItemHelpModify = new ArrayList<>();
  }

  public void toggleSelection(PartEstimate partEstimate) {
    if (partEstimate.isSelected()) {
      if (this.currentEstimate != null) {
        this.currentEstimate.setSelected(false);
      }
      this.setCurrentEstimate(partEstimate);
      if (this.currentEstimate.isPersisted()) {
        this.currentEstimateKey = this.currentEstimate.getPeCode();
      } else {
        this.currentEstimateKey = this.currentEstimate.getFakeId();
      }
    } else {
      this.currentEstimate = null;
      this.selectedEstimate = null;
      this.currentEstimateKey = null;
    }
    this.isNewEstimate = false;
    this.resetStatusItem();
  }

  public void toggleSelectionItem(PartEstimateItem partEstimateItem) {
    if (partEstimateItem.isSelected()) {
      if (this.currentEstimateItem != null) {
        this.currentEstimateItem.setSelected(false);
      }
      this.setCurrentEstimateItem(partEstimateItem);
    } else {
      this.resetStatusItem();
    }
    this.isNewEstimateItem = false;
  }

  public void resetStatusItem() {
    this.currentEstimateItem = null;
    this.selectedEstimateItem = null;
  }

  public void savePartEstimate() {
    if (!this.listEstimateIsNew.isEmpty()) {
      final String actionCode = this.currentActionLog.getActionCode();
      final String officeCode = this.userCredential.getOfficeCode();
      this.service.saveNewPartEstimateAndNewPartEstimateItemWithIt(this.listEstimateIsNew, this.mapListNewEstimateItem, actionCode, officeCode);
    }
    if (!this.listEstimateShow.isEmpty()) {
      this.service.saveNewPartEstimateItemWhenPartEstimateIsExisted(this.listEstimateShow, this.mapListNewEstimateItem);
    }
    if (!this.listEstimateIsModify.isEmpty()) {
      this.service.updatePartEstimate(this.listEstimateIsModify);
    }
    if (!this.listEstimateItemIsModify.isEmpty()) {
      this.service.updatePartEstimateItem(this.listEstimateItemIsModify, this.listEstimateItemHelpModify);
    }
    if (!this.listEstimateIsDelete.isEmpty()) {
      this.service.deletePartEstimate(this.listEstimateIsDelete);
    }
    if (!this.listEstimateItemIsDelete.isEmpty()) {
      this.service.deletePartEstimateItem(this.listEstimateItemIsDelete);
    }
    this.discardChanges();
  }

  /*
   * fill value to two field when user selected Part
   */
  public void fillValueToFields() {
    if (!StringUtils.equalsIgnoreCase(this.selectedEstimateItem.getMDevice().getName(), Messages.get("others"))) {
      this.selectedEstimateItem.setPartName(this.selectedEstimateItem.getMDevice().getName());
    } else {
      this.selectedEstimateItem.setPartName(null);
    }
    this.selectedEstimateItem.setPartModelNo(this.selectedEstimateItem.getMDevice().getModelNo());
  }

  /*
   * when user clicked button add new part estimate, mark is add new part estimate
   */
  public void addNewEstimate() {
    if (this.currentEstimate != null) {
      this.currentEstimate.setSelected(false);
    }
    this.setCurrentEstimate(new PartEstimate());
    this.isNewEstimate = true;
    this.currentEstimateKey = this.currentEstimate.getFakeId();
  }

  /*
   * when user clicked button add new part estimate item, mark is add new part estimate item
   */
  public void addNewEstimateItem() {
    if (this.currentEstimateItem != null) {
      this.currentEstimateItem.setSelected(false);
    }
    this.setCurrentEstimateItem(new PartEstimateItem());
    this.isNewEstimateItem = true;
  }

  /*
   * when user want delete a part estimate
   * 1. delete part estimate is select
   * 2. delete part estimate item of it (part estimate is delete)
   *
   * case 1: if part estimate on internal data, remove it.
   * case 2: if part estimate on database, mark it is delete
   */
  public void deleteEstimate() {
    if (this.currentEstimate != null) {
      if (this.currentEstimate.isPersisted()) {
        this.listEstimateIsDelete.add(this.currentEstimate);
      } else {
        this.listEstimateIsNew.remove(this.currentEstimate);
      }
      this.listEstimateShow.remove(this.currentEstimate);
      final String fakeId = this.currentEstimate.getFakeId();
      final List<PartEstimateItem> listPartEstimateItem = this.mapListNewEstimateItem.get(fakeId);
      for (final PartEstimateItem item : listPartEstimateItem) {
        if (item.isPersisted()) {
          this.listEstimateItemIsDelete.add(item);
        }
      }
      this.mapListNewEstimateItem.remove(fakeId);

      this.cleanStageAfterSavePartEstimate();
    }
  }

  /*
   * same as delete part estimate
   */
  public void deleteEstimateItem() {
    if (this.currentEstimateItem != null) {
      if (this.currentEstimateItem.isPersisted()) {
        this.listEstimateItemIsDelete.add(this.currentEstimateItem);
      }
      final String fakeId = this.currentEstimate.getFakeId();
      this.mapListNewEstimateItem.get(fakeId).remove(this.currentEstimateItem);

      this.cleanStageAfterSavePartEstimateItem();
    }
  }

  /*
   * when user clicked button save part estimate
   * case 1: this is case add new estimate, add estimate to list new estimate
   * case 2: if not:
   * case 2.1: if estimate is persisted, add estimate to list modify
   * case 2.2: if not, stead current estimate by selected estimate
   */
  public void saveThisEstimate() {
    if (this.selectedEstimate != null) {
      final String actionCode = this.getCurrentActionLog().getActionCode();
      // final List<Message> errors = this.validator.validate(this.selectedPartEstimate);
      // if(!errors.isEmpty())
      final ServiceResult<PartEstimate> result = this.service.addNewEstimate(this.selectedEstimate, actionCode, this.listEstimateItemShow);
      result.showMessages();
      if (!result.isSuccess())
        return;

      this.selectedEstimate = result.getData();
      if (this.image != null) {
        this.selectedEstimate.setImageFile(this.imageStreamBean.getRequestImage(this.currentEstimateKey));
      }
      if (this.isNewEstimate) {
        this.listEstimateIsNew.add(this.selectedEstimate);
        this.listEstimateShow.add(this.selectedEstimate);
        this.isNewEstimate = false;
      } else {
        if (this.selectedEstimate.isPersisted()) {
          this.listEstimateIsModify.add(this.selectedEstimate);
        } else {
          Collections.replaceAll(this.listEstimateIsNew, this.currentEstimate, this.selectedEstimate);
        }
        Collections.replaceAll(this.listEstimateShow, this.currentEstimate, this.selectedEstimate);
      }
      this.cleanStageAfterSavePartEstimate();
    }
  }

  /*
   * same as save part estimate
   */
  public void saveThisEstimateItem() {
    if (this.selectedEstimateItem != null) {
      this.checkAndRemoveMDevice();
      final ServiceResult<PartEstimateItem> result =
          this.service.validateEstimateItem(this.selectedEstimateItem, this.currentEstimateItem, this.listEstimateItemShow);
      result.showMessages();
      if (!result.isSuccess())
        return;

      final String fakeId = this.currentEstimate.getFakeId();
      if (this.isNewEstimateItem) {
        this.mapListNewEstimateItem.get(fakeId).add(this.selectedEstimateItem);
        this.isNewEstimateItem = false;
      } else {
        if (this.selectedEstimateItem.isPersisted()) {
          this.listEstimateItemIsModify.add(this.selectedEstimateItem);
          this.listEstimateItemHelpModify.add(this.currentEstimateItem);
        }
        Collections.replaceAll(this.mapListNewEstimateItem.get(fakeId), this.currentEstimateItem, this.selectedEstimateItem);
      }
      this.cleanStageAfterSavePartEstimateItem();
    }
  }

  /*
   * Code only use when user select part is other
   * remove selected master device
   */
  private void checkAndRemoveMDevice() {
    if ((this.selectedEstimateItem.getMDevice() != null) && !this.selectedEstimateItem.getMDevice().isPersisted()) {
      this.selectedEstimateItem.setMDevice(null);
    }
  }

  /*
   * when user clicked button discard changes, delete all internal data
   */
  public void discardChanges() {
    this.mapListNewEstimateItem = new HashMap<>();
    this.listEstimateIsNew = new ArrayList<>();
    this.listEstimateIsModify = new ArrayList<>();
    this.listEstimateItemIsModify = new ArrayList<>();
    this.listEstimateIsDelete = new ArrayList<>();
    this.listEstimateItemIsDelete = new ArrayList<>();
    this.listEstimateItemHelpModify = new ArrayList<>();
    this.listEstimateShow = null;
    this.listEstimateItemShow = null;
    this.cleanStageAfterSavePartEstimate();
  }

  public void cleanStageAfterSavePartEstimate() {
    if (this.currentEstimate != null) {
      this.currentEstimate.setSelected(false);
    }
    this.currentEstimate = null;

    if (this.currentEstimateItem != null) {
      this.currentEstimateItem.setSelected(false);
    }
    this.currentEstimateItem = null;

    this.selectedEstimate = null;
    this.selectedEstimateItem = null;
    this.currentEstimateKey = null;
  }

  public void cleanStageAfterSavePartEstimateItem() {
    if (this.currentEstimateItem != null) {
      this.currentEstimateItem.setSelected(false);
    }
    this.currentEstimateItem = null;
    this.selectedEstimateItem = null;
  }

  public PartEstimate getCurrentEstimate() {
    return this.currentEstimate;
  }

  public void setCurrentEstimate(PartEstimate pCurrentEstimate) {
    this.currentEstimate = pCurrentEstimate;
    if (this.currentEstimate != null) {
      this.selectedEstimate = new PartEstimate();
      BeanCopier.copy(this.currentEstimate, this.selectedEstimate);
      this.selectedEstimate.setFakeId(this.currentEstimate.getFakeId());
    }
  }

  public PartEstimate getSelectedEstimate() {
    return this.selectedEstimate;
  }

  public void setSelectedEstimate(PartEstimate pSelectedEstimate) {
    this.selectedEstimate = pSelectedEstimate;
  }

  public PartEstimateItem getCurrentEstimateItem() {
    return this.currentEstimateItem;
  }

  public void setCurrentEstimateItem(PartEstimateItem pCurrentPartEstimateItem) {
    this.currentEstimateItem = pCurrentPartEstimateItem;
    if (this.currentEstimateItem != null) {
      this.selectedEstimateItem = new PartEstimateItem();
      BeanCopier.copy(this.currentEstimateItem, this.selectedEstimateItem);
    }
  }

  public PartEstimateItem getSelectedEstimateItem() {
    if (this.selectedEstimateItem == null)
      return this.selectedEstimateItem;

    if (this.selectedEstimateItem.getMDevice() != null)
      return this.selectedEstimateItem;

    if (!StringUtils.isEmpty(this.selectedEstimateItem.getPartName())) {
      this.selectedEstimateItem.setMDevice(this.commonAutoCompleteBean.getOtherPartItem());
    }
    return this.selectedEstimateItem;
  }

  public void setSelectedEstimateItem(PartEstimateItem pSelectedPartEstimateItem) {
    this.selectedEstimateItem = pSelectedPartEstimateItem;
  }

  public List<PartEstimate> getListEstimateShow() {
    if ((this.listEstimateShow == null) || this.listEstimateShow.isEmpty()) {
      this.listEstimateShow = new ArrayList<>();
      final String actionCode = this.repairRequestBean.getCurrentActionLog().getActionCode();
      if (!StringUtils.isEmpty(actionCode)) {
        final String officeCode = this.userCredential.getOfficeCode();
        this.listEstimateShow = this.service.getListPartEstimateShow(actionCode, officeCode);
      }
    }
    return this.listEstimateShow;
  }

  public void setListEstimateShow(List<PartEstimate> pListEstimateShow) {
    this.listEstimateShow = pListEstimateShow;
  }

  public List<PartEstimateItem> getListEstimateItemShow() {
    if (this.currentEstimate != null) {
      final String fakeId = this.currentEstimate.getFakeId();
      if (this.mapListNewEstimateItem.get(fakeId) == null) {
        final String peCode = this.currentEstimate.getPeCode();
        this.listEstimateItemShow = new ArrayList<>();
        final String officeCode = this.userCredential.getOfficeCode();
        this.listEstimateItemShow = this.service.getListPartEstimateItem(peCode, officeCode);
        this.mapListNewEstimateItem.put(fakeId, this.listEstimateItemShow);
      } else {
        this.listEstimateItemShow = this.mapListNewEstimateItem.get(fakeId);
      }

    }
    return this.listEstimateItemShow;
  }

  public void setListEstimateItemShow(List<PartEstimateItem> pListPartEstimateItemShow) {
    this.listEstimateItemShow = pListPartEstimateItemShow;
  }

  public Map<String, List<PartEstimateItem>> getMapListNewEstimateItem() {
    return this.mapListNewEstimateItem;
  }

  public void setMapListNewEstimateItem(Map<String, List<PartEstimateItem>> pMapListNewEstimateItem) {
    this.mapListNewEstimateItem = pMapListNewEstimateItem;
  }

  public List<PartEstimate> getListEstimateIsNew() {
    return this.listEstimateIsNew;
  }

  public void setListEstimateIsNew(List<PartEstimate> pListEstimateIsNew) {
    this.listEstimateIsNew = pListEstimateIsNew;
  }

  public List<PartEstimate> getListEstimateIsModify() {
    return this.listEstimateIsModify;
  }

  public void setListEstimateIsModify(List<PartEstimate> pListEstimateIsModify) {
    this.listEstimateIsModify = pListEstimateIsModify;
  }

  public List<PartEstimate> getListEstimateIsDelete() {
    return this.listEstimateIsDelete;
  }

  public void setListEstimateIsDelete(List<PartEstimate> pListEstimateIsDelete) {
    this.listEstimateIsDelete = pListEstimateIsDelete;
  }

  public List<PartEstimateItem> getListEstimateItemIsModify() {
    return this.listEstimateItemIsModify;
  }

  public void setListEstimateItemIsModify(List<PartEstimateItem> pListEstimateItemIsModify) {
    this.listEstimateItemIsModify = pListEstimateItemIsModify;
  }

  public List<PartEstimateItem> getListEstimateItemIsDelete() {
    return this.listEstimateItemIsDelete;
  }

  public void setListEstimateItemIsDelete(List<PartEstimateItem> pListEstimateItemIsDelete) {
    this.listEstimateItemIsDelete = pListEstimateItemIsDelete;
  }

  public boolean isNewEstimate() {
    return this.isNewEstimate;
  }

  public void setNewEstimate(boolean pIsNewEstimate) {
    this.isNewEstimate = pIsNewEstimate;
  }

  public boolean isNewEstimateItem() {
    return this.isNewEstimateItem;
  }

  public void setNewEstimateItem(boolean pIsNewEstimateItem) {
    this.isNewEstimateItem = pIsNewEstimateItem;
  }

  public List<SelectItem> getPartEstimationTypes() {
    return SelectItemGenerator.getListSelectItem(ListType.PART_ESTIMATION_TYPE);
  }

  public List<PartEstimateItem> getListEstimateItemHelpModify() {
    return this.listEstimateItemHelpModify;
  }

  public void setListEstimateItemHelpModify(List<PartEstimateItem> pListEstimateHelpModify) {
    this.listEstimateItemHelpModify = pListEstimateHelpModify;
  }

  public void fileImageUploadListener(FileUploadEvent e) throws IOException {
    this.setImage(e.getFile());
    final byte[] content = FileUtils.getContentFile(this.getImage().getInputstream());
    this.imageStreamBean.uploadEstimateImageTemporary(this.currentEstimateKey, content);
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
  protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
    return false;
  }


  @Override
  public String getItemLabel() {
    return null;
  }

  public String getCurrentEstimateKey() {
    return this.currentEstimateKey;
  }

  public void setCurrentEstimateKey(String pCurrentEstimateKey) {
    this.currentEstimateKey = pCurrentEstimateKey;
  }

  public UploadedFile getImage() {
    return this.image;
  }

  public void setImage(UploadedFile pImage) {
    this.image = pImage;
  }

  public ActionLog getCurrentActionLog() {
    return this.currentActionLog;
  }

  public void setCurrentActionLog(ActionLog pCurrentActionLog) {
    this.currentActionLog = pCurrentActionLog;
  }

  public List<Person> filterDistributorPerson(String query) {
    if ((this.selectedEstimate == null) || (this.selectedEstimate.getDistributorOffice() == null))
      return Collections.emptyList();
    final String officeCode = this.selectedEstimate.getDistributorOffice().getOfficeCode();
    final String userOffice = this.userCredential.getOfficeCode();
    final List<Person> listPersons = this.personService.getPersonsByOfficeCode(officeCode, userOffice);
    if (listPersons.isEmpty())
      return Collections.emptyList();
    return this.filterPersonByQuery(listPersons, query);
  }

  public List<Person> filterClientPerson(String query) {
    if ((this.selectedEstimate == null) || (this.selectedEstimate.getClientOffice() == null))
      return Collections.emptyList();
    final String officeCode = this.selectedEstimate.getClientOffice().getOfficeCode();
    final String userOffice = this.userCredential.getOfficeCode();
    final List<Person> listPersons = this.personService.getPersonsByOfficeCode(officeCode, userOffice);
    if (listPersons.isEmpty())
      return Collections.emptyList();
    return this.filterPersonByQuery(listPersons, query);
  }

  public List<Person> filterSupplier(String query) {
    if ((this.selectedEstimate == null) || (this.selectedEstimate.getSupplierOffice() == null))
      return Collections.emptyList();
    final String officeCode = this.selectedEstimate.getSupplierOffice().getOfficeCode();
    final String userOffice = this.userCredential.getOfficeCode();
    final List<Person> listPersons = this.personService.getPersonsByOfficeCode(officeCode, userOffice);
    if (listPersons.isEmpty())
      return Collections.emptyList();
    return this.filterPersonByQuery(listPersons, query);
  }

  public List<Person> filterPersonByQuery(List<Person> listPersons, String query) {
    if (StringUtils.isEmpty(query))
      return listPersons;
    return CollectionUtils.filter(listPersons, object -> {
      final Person item = (Person) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || item.getPsnCode().contains(query);
    });
  }

}
