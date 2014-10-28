/**
 *
 */
package arrow.mems.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionBill;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.Fault;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.service.ViewRepairRequestService;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class ViewRepairRequestBean extends AbstractBean {

  @Inject
  ViewRepairRequestService service;
  @Inject
  UserCredential userCredential;

  private List<String> conditionsView;
  private String[] conditions;

  private Person selectedPerson;
  private Hospital selectedHospital;
  private Checklist selectedChecklist;
  private PartEstimate selectedEstimate;
  private PartOrder selectedOrder;
  private List<ActionLog> listRepairRequest;
  private List<ActionLog> cacheListRepairRequest;

  private ActionBill currentFee;
  private Fault currentFault;
  private ActionLog selectedRepairRequest;
  private List<ReplPart> listReplPart;
  private List<Checklist> listChecklist;
  private List<ChecklistItem> listChecklistItem;
  private List<PartEstimate> listPartEstimate;
  private List<PartEstimateItem> listPartEstimateItem;
  private List<PartOrder> listPartOrder;
  private List<PartOrderItem> listPartOrderItem;

  public void searchRepairRequest() {
    this.listRepairRequest = new ArrayList<>();
    final String officeCode = this.userCredential.getOfficeCode();
    this.listRepairRequest = this.service.getListRepairRequest(officeCode, this.selectedHospital, this.selectedPerson);
    this.cacheListRepairRequest = this.service.getListRepairRequest(officeCode, this.selectedHospital, this.selectedPerson);
  }

  public void resetSearch() {
    this.listRepairRequest = null;
    this.cacheListRepairRequest = null;
    this.selectedRepairRequest = null;
    this.selectedChecklist = null;
    this.selectedEstimate = null;
    this.selectedHospital = null;
    this.selectedOrder = null;
    this.selectedPerson = null;
    this.listChecklist = null;
    this.listPartOrder = null;
    this.listPartEstimate = null;
    this.listReplPart = null;
    this.listChecklistItem = null;
    this.listPartOrderItem = null;
    this.listPartEstimateItem = null;
    this.currentFault = null;
    this.currentFee = null;
    this.conditions = null;
  }

  public Person getSelectedPerson() {
    return this.selectedPerson;
  }

  public void setSelectedPerson(Person pSelectedPerson) {
    this.selectedPerson = pSelectedPerson;
  }

  public Hospital getSelectedHospital() {
    return this.selectedHospital;
  }

  public void setSelectedHospital(Hospital pSelectedHospital) {
    this.selectedHospital = pSelectedHospital;
  }

  public List<String> getConditionsView() {
    if (this.conditionsView == null) {
      this.conditionsView = new ArrayList<String>();
      this.conditionsView.add(CommonConstants.NOT_START);
      this.conditionsView.add(CommonConstants.NOT_VISITED);
      this.conditionsView.add(CommonConstants.VISITED);
      this.conditionsView.add(CommonConstants.WAIT_PARTS);
      this.conditionsView.add(CommonConstants.COMPLETED);
    }
    return this.conditionsView;
  }

  public void setConditionsView(List<String> pConditions) {
    this.conditionsView = pConditions;
  }

  public String[] getConditions() {
    return this.conditions;
  }

  public void setConditions(String[] pConditions) {
    this.conditions = pConditions;
    this.selectedRepairRequest = null;
  }

  public List<ActionLog> getListRepairRequest() {
    this.listRepairRequest = this.cacheListRepairRequest;
    if ((this.listRepairRequest != null) && !this.listRepairRequest.isEmpty()) {
      if ((this.conditions != null) && (this.conditions.length != 0)) {
        this.listRepairRequest = new ArrayList<>();
        for (final String condition : this.conditions) {
          switch (condition) {
            case CommonConstants.NOT_START:
              this.listRepairRequest = this.filterNotStart(this.cacheListRepairRequest);
              break;
            case CommonConstants.NOT_VISITED:
              this.listRepairRequest = this.filterNotVisited(this.cacheListRepairRequest);
              break;
            case CommonConstants.VISITED:
              this.listRepairRequest = this.filterVisited(this.cacheListRepairRequest);
              break;
            case CommonConstants.WAIT_PARTS:
              this.listRepairRequest = this.filterWaitParts(this.cacheListRepairRequest);
              break;
            case CommonConstants.COMPLETED:
              this.listRepairRequest = this.filterCompleted(this.cacheListRepairRequest);
              break;
          }
        }
      }
    }
    return this.listRepairRequest;
  }

  private List<ActionLog> filterNotStart(List<ActionLog> repairRequests) {
    for (final ActionLog action : repairRequests) {
      if (!this.listRepairRequest.contains(action)) {
        if (action.getContactDate() == null) {
          this.listRepairRequest.add(action);
        }
      }
    }
    return this.listRepairRequest;
  }

  private List<ActionLog> filterNotVisited(List<ActionLog> repairRequests) {
    for (final ActionLog action : repairRequests) {
      if (!this.listRepairRequest.contains(action)) {
        if ((action.getContactDate() != null) && ((action.getActionDate() == null) || action.getActionDate().isAfter(LocalDate.now()))) {
          this.listRepairRequest.add(action);
        }
      }
    }
    return this.listRepairRequest;
  }

  private List<ActionLog> filterVisited(List<ActionLog> repairRequests) {
    for (final ActionLog action : repairRequests) {
      if (!this.listRepairRequest.contains(action)) {
        if ((action.getContactDate() != null) && (action.getActionDate() != null) && action.getActionDate().isBefore(LocalDate.now())) {
          this.listRepairRequest.add(action);
        }
      }
    }
    return this.listRepairRequest;
  }

  private List<ActionLog> filterWaitParts(List<ActionLog> repairRequests) {
    for (final ActionLog action : repairRequests) {
      if (!this.listRepairRequest.contains(action)) {
        if ((action.getFinishDate() == null) && (this.listPartOrder != null) && !this.listPartOrder.isEmpty()) {
          this.listRepairRequest.add(action);
        }
      }
    }
    return this.listRepairRequest;
  }

  private List<ActionLog> filterCompleted(List<ActionLog> repairRequests) {
    for (final ActionLog action : repairRequests) {
      if (!this.listRepairRequest.contains(action)) {
        if (action.getFinishDate() != null) {
          this.listRepairRequest.add(action);
        }
      }
    }
    return this.listRepairRequest;
  }

  public void setListRepairRequest(List<ActionLog> pListRepairRequest) {
    this.listRepairRequest = pListRepairRequest;
  }

  public ActionLog getSelectedRepairRequest() {
    return this.selectedRepairRequest;
  }

  public void setSelectedRepairRequest(ActionLog pSelectedRepairRequest) {
    this.selectedRepairRequest = pSelectedRepairRequest;
    if (this.selectedRepairRequest != null) {
      this.resetItemsWhenChangeRow();
      final String actionCode = this.selectedRepairRequest.getActionCode();
      this.currentFault = this.service.getFaultWithActionCode(actionCode);
      this.listReplPart = this.service.getListReplPart(actionCode);
      this.listChecklist = this.service.getListChecklist(actionCode);
      this.listPartEstimate = this.service.getListEstimate(actionCode);
      this.listPartOrder = this.service.getListPartOrder(actionCode);
      this.currentFee = this.service.getActionBillWithActionCode(actionCode);
    }
  }

  public void resetItemsWhenChangeRow() {
    this.listChecklistItem = null;
    this.listPartEstimateItem = null;
    this.listPartOrderItem = null;
    this.selectedChecklist = null;
    this.selectedEstimate = null;
    this.selectedOrder = null;
  }

  public Fault getCurrentFault() {
    return this.currentFault;
  }

  public void setCurrentFault(Fault pCurrentFault) {
    this.currentFault = pCurrentFault;
  }

  public List<ReplPart> getListReplPart() {
    return this.listReplPart;
  }

  public void setListReplPart(List<ReplPart> pListReplPart) {
    this.listReplPart = pListReplPart;
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

  public Checklist getSelectedChecklist() {
    return this.selectedChecklist;
  }

  public void setSelectedChecklist(Checklist pSelectedChecklist) {
    this.selectedChecklist = pSelectedChecklist;
    if (this.selectedChecklist != null) {
      this.listChecklistItem = this.service.getListChecklistItem(this.selectedChecklist.getCklistLogCode());
    }
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

  public PartEstimate getSelectedEstimate() {
    return this.selectedEstimate;
  }

  public void setSelectedEstimate(PartEstimate pSelectedEstimate) {
    this.selectedEstimate = pSelectedEstimate;
    if (this.selectedEstimate != null) {
      this.listPartEstimateItem = this.service.getListPartEstimate(this.selectedEstimate.getPeCode());
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
      this.listPartOrderItem = this.service.getListPartOrderItem(this.selectedOrder.getPoCode());
    }
  }

  public ActionBill getCurrentFee() {
    return this.currentFee;
  }

  public void setCurrentFee(ActionBill pCurrentFee) {
    this.currentFee = pCurrentFee;
  }

  public List<ActionLog> getCacheListRepairRequest() {
    return this.cacheListRepairRequest;
  }

  public void setCacheListRepairRequest(List<ActionLog> pCacheListRepairRequest) {
    this.cacheListRepairRequest = pCacheListRepairRequest;
  }

}
