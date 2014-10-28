/**
 *
 */
package arrow.mems.bean;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.framework.util.CollectionUtils;
import arrow.framework.util.i18n.Messages;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.repository.PartOrderRepository;
import arrow.mems.service.PartEstimateService;
import arrow.mems.service.PartsOrderService;
import arrow.mems.service.PersonManagementService;
import arrow.mems.util.SelectItemGenerator;
import arrow.mems.util.SelectItemGenerator.ListType;
import arrow.mems.util.file.FileUtils;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class PartsOrderBean extends AbstractApprovalBean {
  @Inject
  InputRepairRequestBean inputRepairRequestBean;
  @Inject
  PartsOrderService orderService;
  @Inject
  PartOrderRepository partOrderRepository;
  @Inject
  UserCredential userCredential;
  @Inject
  CommonAutoCompleteBean commonAutoCompleteBean;
  @Inject
  ImageStreamBean imageStreamBean;
  @Inject
  PartEstimateService estimateService;
  @Inject
  PersonManagementService personService;

  private ActionLog currentActionLog;

  private PartOrder currentPartOrder;
  private PartOrder selectedPartOrder;
  private PartOrderItem currentPartOrderItem;
  private PartOrderItem selectedPartOrderItem;

  private List<PartOrder> listPartsOrderShow;
  private List<PartOrderItem> listPartOrderItemShow;

  private Map<String, List<PartOrderItem>> mapListNewPartOrderItem;

  // contains internal data
  private List<PartOrder> listPartOrderIsNew;
  private List<PartOrder> listPartOrderIsModify;
  private List<PartOrder> listPartOrderIsDelete;

  private List<PartOrderItem> listPartOrderItemIsModify;
  private List<PartOrderItem> listPartOrderItemIsDelete;

  // contains data before save modify data, it help find object in database before update
  private List<PartOrderItem> listPartOrderItemsHelpModify;

  // mark part order is create new
  private boolean isNewPartOrder;

  // mark part order item is create new
  private boolean isNewPartOrderItem;

  // process image
  private UploadedFile image;
  private String currentOrderKey;

  // support pop-up
  private List<PartEstimate> listEstimateShow;
  private List<PartEstimateItem> listEstimateItemShow;
  private Map<String, List<PartEstimateItem>> mapListEstimateItem;
  private String estimateType;
  private PartEstimate currentEstimate;

  @PostConstruct
  public void init() {
    this.mapListNewPartOrderItem = new HashMap<>();
    this.listPartOrderIsNew = new ArrayList<>();
    this.listPartOrderIsModify = new ArrayList<>();
    this.listPartOrderItemIsModify = new ArrayList<>();
    this.listPartOrderIsDelete = new ArrayList<>();
    this.listPartOrderItemIsDelete = new ArrayList<>();
    this.listPartOrderItemsHelpModify = new ArrayList<>();
    this.mapListEstimateItem = new HashMap<>();
  }

  public void toggleSelection(PartOrder partOrder) {
    if (partOrder.isSelected()) {
      if (this.currentPartOrder != null) {
        this.currentPartOrder.setSelected(false);
      }
      this.setCurrentPartOrder(partOrder);
      if (this.currentPartOrder.isPersisted()) {
        this.currentOrderKey = this.currentPartOrder.getPoCode();
      } else {
        this.currentOrderKey = this.currentPartOrder.getFakeId();
      }
    } else {
      this.currentPartOrder = null;
      this.selectedPartOrder = null;
      this.currentOrderKey = null;
    }
    this.isNewPartOrder = false;
    this.resetStatusItem();
  }

  public void toggleSelectionItem(PartOrderItem partOrderItem) {
    if (partOrderItem.isSelected()) {
      if (this.currentPartOrderItem != null) {
        this.currentPartOrderItem.setSelected(false);
      }
      this.setCurrentPartOrderItem(partOrderItem);
    } else {
      this.resetStatusItem();
    }
    this.isNewPartOrderItem = false;
  }

  public void savePartOrder() {
    if (!this.listPartOrderIsNew.isEmpty()) {
      final String actionCode = this.currentActionLog.getActionCode();
      final String officeCode = this.userCredential.getOfficeCode();
      this.orderService.saveNewPartOrderAndNewPartOrderItem(this.listPartOrderIsNew, this.mapListNewPartOrderItem, actionCode, officeCode);
    }
    if (!this.listPartsOrderShow.isEmpty()) {

    }
    if (!this.listPartOrderIsModify.isEmpty()) {
      this.orderService.updatePartOrder(this.listPartOrderIsModify);
    }
    if (!this.listPartOrderItemIsModify.isEmpty()) {
      this.orderService.updatePartOrderItem(this.listPartOrderItemIsModify, this.listPartOrderItemsHelpModify);
    }
    if (!this.listPartOrderIsDelete.isEmpty()) {
      this.orderService.deletePartOrder(this.listPartOrderIsDelete);
    }
    if (!this.listPartOrderItemIsDelete.isEmpty()) {
      this.orderService.deletePartOrderItem(this.listPartOrderItemIsDelete);
    }
    this.discardChanges();
  }

  public void resetStatusItem() {
    this.currentPartOrderItem = null;
    this.selectedPartOrderItem = null;
  }

  public void fillValueToFields() {
    if (!StringUtils.equalsIgnoreCase(this.selectedPartOrderItem.getMDevice().getName(), Messages.get("others"))) {
      this.selectedPartOrderItem.setPartName(this.selectedPartOrderItem.getMDevice().getName());
    } else {
      this.selectedPartOrderItem.setPartName(null);
    }
    this.selectedPartOrderItem.setPartModelNo(this.selectedPartOrderItem.getMDevice().getModelNo());
  }

  public void addNewPartOrder() {
    if (this.currentPartOrder != null) {
      this.currentPartOrder.setSelected(false);
    }
    this.setCurrentPartOrder(new PartOrder());
    this.isNewPartOrder = true;
    this.currentOrderKey = this.currentPartOrder.getFakeId();
  }

  public void addNewPartOrderItem() {
    if (this.currentPartOrderItem != null) {
      this.currentPartOrderItem.setSelected(false);
    }
    this.setCurrentPartOrderItem(new PartOrderItem());
    this.isNewPartOrderItem = true;
  }

  /*
   * when user want delete a part order
   * 1. delete part order is select
   * 2. delete part order item of it (part order is delete)
   *
   * case 1: if part order on internal data, remove it.
   * case 2: if part order on database, mark it is delete
   */
  public void deletePartOrder() {
    if (this.currentPartOrder != null) {
      if (this.currentPartOrder.isPersisted()) {
        this.getListPartOrderIsDelete().add(this.currentPartOrder);
      } else {
        this.listPartOrderIsNew.remove(this.currentPartOrder);
      }
      this.listPartsOrderShow.remove(this.currentPartOrder);
      final String fakeId = this.currentPartOrder.getFakeId();
      final List<PartOrderItem> listPartOrderItem = this.mapListNewPartOrderItem.get(fakeId);
      if (listPartOrderItem != null) {
        for (final PartOrderItem item : listPartOrderItem) {
          if (item.isPersisted()) {
            this.listPartOrderItemIsDelete.add(item);
          }
        }
      }
      this.mapListNewPartOrderItem.remove(fakeId);

      this.cleanStageAfterSavePartOrder();
    }
  }

  /*
   * same as delete part order
   */
  public void deletePartOrderItem() {
    if (this.currentPartOrderItem != null) {
      if (this.currentPartOrderItem.isPersisted()) {
        this.listPartOrderItemIsDelete.add(this.currentPartOrderItem);
      }
      final String fakeId = this.currentPartOrder.getFakeId();
      this.mapListNewPartOrderItem.get(fakeId).remove(this.currentPartOrderItem);

      this.cleanStageAfterSavePartOrderItem();
    }
  }

  /*
   * if have selectedPartOrder (selectedPartOrder != null), this case is edit part order
   * if no, this case is add new part order
   * in list mofidy, only contains objects is persisted
   */
  public void saveThisOrder() {
    if (this.selectedPartOrder != null) {
      final ServiceResult<PartOrder> result = this.orderService.validateOrder(this.selectedPartOrder, this.listPartOrderItemShow);
      result.showMessages();

      if (!result.isSuccess())
        return;

      if (this.image != null) {
        this.selectedPartOrder.setImageFile(this.imageStreamBean.getRequestImage(this.currentOrderKey));
      }

      if (this.isNewPartOrder) {
        final ServiceResult<PartOrder> orderResult = this.orderService.checkDuplicateEstimate(this.selectedPartOrder, this.listPartsOrderShow);
        orderResult.showMessages();
        if (!orderResult.isSuccess())
          return;
        this.listPartOrderIsNew.add(this.selectedPartOrder);
        this.listPartsOrderShow.add(this.selectedPartOrder);
        this.isNewPartOrder = false;
      } else {
        if (this.selectedPartOrder.isPersisted()) {
          this.listPartOrderIsModify.add(this.selectedPartOrder);
        } else {
          Collections.replaceAll(this.listPartOrderIsNew, this.currentPartOrder, this.selectedPartOrder);
        }
        Collections.replaceAll(this.listPartsOrderShow, this.currentPartOrder, this.selectedPartOrder);
      }

      this.cleanStageAfterSavePartOrder();
    }
  }

  public void saveThisOrderItem() {
    if (this.selectedPartOrderItem != null) {
      this.checkAndRemoveMDevice();
      final ServiceResult<PartOrderItem> result =
          this.orderService.validateOrderItem(this.selectedPartOrderItem, this.currentPartOrderItem, this.listPartOrderItemShow);
      result.showMessages();
      if (!result.isSuccess())
        return;

      final String fakeId = this.currentPartOrder.getFakeId();
      if (this.isNewPartOrderItem) {
        this.mapListNewPartOrderItem.get(fakeId).add(this.selectedPartOrderItem);
        this.isNewPartOrderItem = false;
      } else {
        if (this.selectedPartOrderItem.isPersisted()) {
          this.listPartOrderItemIsModify.add(this.selectedPartOrderItem);
          this.listPartOrderItemsHelpModify.add(this.currentPartOrderItem);
        }
        Collections.replaceAll(this.mapListNewPartOrderItem.get(fakeId), this.currentPartOrderItem, this.selectedPartOrderItem);
      }
      this.cleanStageAfterSavePartOrderItem();
    }
  }

  /*
   * Code only use when user select part is other
   * remove selected master device
   */
  private void checkAndRemoveMDevice() {
    if ((this.selectedPartOrderItem.getMDevice() != null) && !this.selectedPartOrderItem.getMDevice().isPersisted()) {
      this.selectedPartOrderItem.setMDevice(null);
    }
  }

  public void cleanStageAfterSavePartOrder() {
    if (this.currentPartOrder != null) {
      this.currentPartOrder.setSelected(false);
    }
    this.currentPartOrder = null;

    if (this.currentPartOrderItem != null) {
      this.currentPartOrderItem.setSelected(false);
    }
    this.currentPartOrderItem = null;

    this.selectedPartOrder = null;
    this.selectedPartOrderItem = null;
    this.currentOrderKey = null;
  }

  public void cleanStageAfterSavePartOrderItem() {
    if (this.currentPartOrderItem != null) {
      this.currentPartOrderItem.setSelected(false);
    }
    this.currentPartOrderItem = null;
    this.selectedPartOrderItem = null;
  }

  public void discardChanges() {
    this.mapListNewPartOrderItem = new HashMap<>();
    this.listPartOrderIsNew = new ArrayList<>();
    this.listPartOrderIsModify = new ArrayList<>();
    this.listPartOrderItemIsModify = new ArrayList<>();
    this.listPartOrderIsDelete = new ArrayList<>();
    this.listPartOrderItemIsDelete = new ArrayList<>();
    this.listPartOrderItemsHelpModify = new ArrayList<>();
    this.mapListEstimateItem = new HashMap<>();
    this.listPartsOrderShow = null;
    this.listPartOrderItemShow = null;
    this.listEstimateShow = null;
    this.listEstimateItemShow = null;
    this.estimateType = null;
    this.currentEstimate = null;

    this.cleanStageAfterSavePartOrder();
  }

  public PartOrder getCurrentPartOrder() {
    return this.currentPartOrder;
  }

  public void setCurrentPartOrder(PartOrder pCurrentPartOrder) {
    this.currentPartOrder = pCurrentPartOrder;
    if (this.currentPartOrder != null) {
      this.selectedPartOrder = new PartOrder();
      BeanCopier.copy(this.currentPartOrder, this.selectedPartOrder);
      this.selectedPartOrder.setFakeId(this.currentPartOrder.getFakeId());
    }
  }

  public List<PartOrderItem> getListPartOrderItemShow() {
    if (this.currentPartOrder != null) {
      final String fakeId = this.currentPartOrder.getFakeId();
      if (this.mapListNewPartOrderItem.get(fakeId) == null) {
        final String poCode = this.currentPartOrder.getPoCode();
        this.listPartOrderItemShow = new ArrayList<>();
        final String officeCode = this.userCredential.getOfficeCode();
        this.listPartOrderItemShow = this.orderService.getListPartOrderItem(poCode, officeCode);
        this.mapListNewPartOrderItem.put(fakeId, this.listPartOrderItemShow);
      } else {
        this.listPartOrderItemShow = this.mapListNewPartOrderItem.get(fakeId);
      }
    }
    return this.listPartOrderItemShow;
  }

  public void setListPartOrderItemShow(List<PartOrderItem> pListPartOrderItem) {
    this.listPartOrderItemShow = pListPartOrderItem;
  }

  public Map<String, List<PartOrderItem>> getMapListNewPartOrderItem() {
    return this.mapListNewPartOrderItem;
  }

  public void setMapListNewPartOrderItem(Map<String, List<PartOrderItem>> pMapListNewPartOrderItem) {
    this.mapListNewPartOrderItem = pMapListNewPartOrderItem;
  }

  public PartOrderItem getCurrentPartOrderItem() {
    return this.currentPartOrderItem;
  }

  public void setCurrentPartOrderItem(PartOrderItem pCurrentPartOrderItem) {
    this.currentPartOrderItem = pCurrentPartOrderItem;
    if (this.currentPartOrderItem != null) {
      this.selectedPartOrderItem = new PartOrderItem();
      BeanCopier.copy(this.currentPartOrderItem, this.selectedPartOrderItem);
    }
  }

  public PartOrder getSelectedPartOrder() {
    return this.selectedPartOrder;
  }

  public void setSelectedPartOrder(PartOrder pSelectedPartOrder) {
    this.selectedPartOrder = pSelectedPartOrder;
  }

  public List<PartOrder> getListPartOrderIsNew() {
    return this.listPartOrderIsNew;
  }

  public void setListPartOrderIsNew(List<PartOrder> pListPartOrderIsNew) {
    this.listPartOrderIsNew = pListPartOrderIsNew;
  }

  public List<PartOrder> getListPartOrderIsModify() {
    return this.listPartOrderIsModify;
  }

  public void setListPartOrderIsModify(List<PartOrder> pListPartOrderIsModify) {
    this.listPartOrderIsModify = pListPartOrderIsModify;
  }

  public List<PartOrder> getListPartsOrderShow() {
    if (this.listPartsOrderShow == null) {
      this.listPartsOrderShow = new ArrayList<>();
      final String actionCode = this.getCurrentActionLog().getActionCode();
      if (!StringUtils.isEmpty(actionCode)) {
        final String officeCode = this.userCredential.getOfficeCode();
        this.listPartsOrderShow = this.orderService.getListPartsOrderShow(actionCode, officeCode);
      }
    }
    return this.listPartsOrderShow;
  }

  public void setListPartsOrderShow(List<PartOrder> pListPartsOrder) {
    this.listPartsOrderShow = pListPartsOrder;
  }

  public PartOrderItem getSelectedPartOrderItem() {
    if (this.selectedPartOrderItem == null)
      return this.selectedPartOrderItem;

    if (this.selectedPartOrderItem.getMDevice() != null)
      return this.selectedPartOrderItem;

    if (!StringUtils.isEmpty(this.selectedPartOrderItem.getPartName())) {
      this.selectedPartOrderItem.setMDevice(this.commonAutoCompleteBean.getOtherPartItem());
    }
    return this.selectedPartOrderItem;
  }

  public void setSelectedPartOrderItem(PartOrderItem pEditPartOrderItem) {
    this.selectedPartOrderItem = pEditPartOrderItem;
  }

  public List<PartOrderItem> getListPartOrderItemIsModify() {
    return this.listPartOrderItemIsModify;
  }

  public void setListPartOrderItemIsModify(List<PartOrderItem> pListPartOrderItemIsModify) {
    this.listPartOrderItemIsModify = pListPartOrderItemIsModify;
  }

  public boolean isNewPartOrder() {
    return this.isNewPartOrder;
  }

  public void setNewPartOrder(boolean pIsNewPartOrder) {
    this.isNewPartOrder = pIsNewPartOrder;
  }

  public boolean isNewPartOrderItem() {
    return this.isNewPartOrderItem;
  }

  public void setNewPartOrderItem(boolean pIsNewPartOrderItem) {
    this.isNewPartOrderItem = pIsNewPartOrderItem;
  }

  public List<PartOrderItem> getListPartOrderItemIsDelete() {
    return this.listPartOrderItemIsDelete;
  }

  public void setListPartOrderItemIsDelete(List<PartOrderItem> pListPartOrderItemIsDelete) {
    this.listPartOrderItemIsDelete = pListPartOrderItemIsDelete;
  }

  public List<PartOrder> getListPartOrderIsDelete() {
    return this.listPartOrderIsDelete;
  }

  public void setListPartOrderIsDelete(List<PartOrder> pListPartOrderIsDelete) {
    this.listPartOrderIsDelete = pListPartOrderIsDelete;
  }

  public UploadedFile getImage() {
    return this.image;
  }

  public void setImage(UploadedFile pImage) {
    this.image = pImage;
  }

  public String getCurrentOrderKey() {
    return this.currentOrderKey;
  }

  public void setCurrentOrderKey(String pCurrentOrderKey) {
    this.currentOrderKey = pCurrentOrderKey;
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

  public void fileImageUploadListener(FileUploadEvent e) throws IOException {
    this.setImage(e.getFile());
    final byte[] content = FileUtils.getContentFile(this.getImage().getInputstream());
    this.imageStreamBean.uploadEstimateImageTemporary(this.currentOrderKey, content);
  }

  public List<PartOrderItem> getListPartOrderItemsHelpModify() {
    return this.listPartOrderItemsHelpModify;
  }

  public void setListPartOrderItemsHelpModify(List<PartOrderItem> pListPartOrderItemsHelpModify) {
    this.listPartOrderItemsHelpModify = pListPartOrderItemsHelpModify;
  }

  public List<PartEstimate> getListEstimateShow() {
    if (this.listEstimateShow == null) {
      this.listEstimateShow = new ArrayList<>();
      final String actionCode = this.getCurrentActionLog().getActionCode();
      if (!StringUtils.isEmpty(actionCode)) {
        final String officeCode = this.userCredential.getOfficeCode();
        this.listEstimateShow = this.estimateService.getListPartEstimateShow(actionCode, officeCode);
      }
    }
    if (StringUtils.isEmpty(this.estimateType))
      return this.listEstimateShow;
    return CollectionUtils.filter(this.listEstimateShow, object -> {
      final PartEstimate item = (PartEstimate) object;
      return StringUtils.equalsIgnoreCase(item.getPeType(), this.estimateType);
    });
  }

  public void setListEstimateShow(List<PartEstimate> pListEstimateShow) {
    this.listEstimateShow = pListEstimateShow;
  }

  public String getEstimateType() {
    return this.estimateType;
  }

  public void setEstimateType(String pEstimateType) {
    this.estimateType = pEstimateType;
    if (!StringUtils.isEmpty(this.estimateType)) {
      this.hiddenPanelInPopup();
    }
  }

  public void hiddenPanelInPopup() {
    if ((this.currentEstimate != null) && this.currentEstimate.isSelected()) {
      this.currentEstimate.setSelected(false);
    }
    this.currentEstimate = null;
  }

  public List<SelectItem> getPartEstimationTypes() {
    return SelectItemGenerator.getListSelectItem(ListType.PART_ESTIMATION_TYPE);
  }

  public PartEstimate getCurrentEstimate() {
    return this.currentEstimate;
  }

  public void setCurrentEstimate(PartEstimate pCurrentEstimate) {
    this.currentEstimate = pCurrentEstimate;
  }

  public void toggleSelectionEstimate(PartEstimate estimate) {
    if (estimate.isSelected()) {
      if (this.currentEstimate != null) {
        this.currentEstimate.setSelected(false);
      }
      this.currentEstimate = estimate;
    } else {
      this.currentEstimate = null;
    }
  }

  public List<PartEstimateItem> getListEstimateItemShow() {
    if (this.currentEstimate != null) {
      final String peCode = this.currentEstimate.getPeCode();
      if (this.mapListEstimateItem.get(peCode) == null) {
        this.listEstimateItemShow = new ArrayList<>();
        final String officeCode = this.userCredential.getOfficeCode();
        this.listEstimateItemShow = this.estimateService.getListPartEstimateItem(peCode, officeCode);
        this.mapListEstimateItem.put(peCode, this.listEstimateItemShow);
      } else {
        this.listEstimateItemShow = this.mapListEstimateItem.get(peCode);
      }
    }
    return this.listEstimateItemShow;
  }

  public void setListEstimateItemShow(List<PartEstimateItem> pListEstimateItemShow) {
    this.listEstimateItemShow = pListEstimateItemShow;
  }

  public Map<String, List<PartEstimateItem>> getMapListEstimateItem() {
    return this.mapListEstimateItem;
  }

  public void setMapListEstimateItem(Map<String, List<PartEstimateItem>> pMapListEstimateItem) {
    this.mapListEstimateItem = pMapListEstimateItem;
  }

  public void copyFromEstimate() {
    if (this.currentEstimate != null) {
      this.addNewPartOrder();
      final ServiceResult<PartOrder> result = this.orderService.copyDataFromEstimateToOrder(this.currentEstimate, this.listPartsOrderShow);
      result.showMessages();
      if (result.isSuccess()) {
        result.getData().setFakeId(this.selectedPartOrder.getFakeId());
        this.selectedPartOrder = result.getData();
        this.selectedPartOrder.setOrderDate(LocalDate.now());
        if (!this.listEstimateItemShow.isEmpty()) {
          this.listPartOrderItemShow = this.getListPartOrderItemShow();
          for (final PartEstimateItem item : this.listEstimateItemShow) {
            this.addNewPartOrderItem();
            this.copyItemFromEstimate(item);
            this.saveThisOrderItem();
          }
        }
        this.saveThisOrder();
      }
    }
  }

  public void copyItemFromEstimate(PartEstimateItem item) {
    final ServiceResult<PartOrderItem> result = this.orderService.copyDataFromEstimateItemToOrderItem(item);
    this.selectedPartOrderItem = result.getData();
  }

  public ActionLog getCurrentActionLog() {
    return this.currentActionLog;
  }

  public void setCurrentActionLog(ActionLog pCurrentActionLog) {
    this.currentActionLog = pCurrentActionLog;
  }

  public List<Person> filterDistributorPerson(String query) {
    if ((this.selectedPartOrder == null) || (this.selectedPartOrder.getDistributorOffice() == null))
      return Collections.emptyList();
    final String officeCode = this.selectedPartOrder.getDistributorOffice().getOfficeCode();
    final String userOffice = this.userCredential.getOfficeCode();
    final List<Person> listPersons = this.personService.getPersonsByOfficeCode(officeCode, userOffice);
    if (listPersons.isEmpty())
      return Collections.emptyList();
    return this.filterPersonByQuery(listPersons, query);
  }

  public List<Person> filterServicePerson(String query) {
    if ((this.selectedPartOrder == null) || (this.selectedPartOrder.getEntityServiceOffice() == null))
      return Collections.emptyList();
    final String officeCode = this.selectedPartOrder.getEntityServiceOffice().getOfficeCode();
    final String userOffice = this.userCredential.getOfficeCode();
    final List<Person> listPersons = this.personService.getPersonsByOfficeCode(officeCode, userOffice);
    if (listPersons.isEmpty())
      return Collections.emptyList();
    return this.filterPersonByQuery(listPersons, query);
  }

  public List<Person> filterRequestPerson(String query) {
    if ((this.selectedPartOrder == null) || (this.selectedPartOrder.getEntityRequestOffice() == null))
      return Collections.emptyList();
    final String officeCode = this.selectedPartOrder.getEntityRequestOffice().getOfficeCode();
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
