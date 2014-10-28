package arrow.mems.bean;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.DateUtils;
import arrow.mems.config.AppConfig;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ActionBill;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.Fault;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.service.PrintRepairService;
import arrow.mems.util.SelectItemGenerator;
import arrow.mems.util.string.ArrowStrUtils;

@Named
@ViewScoped
public class PrintRepairBean extends AbstractBean {
  private Map<String, Object> dataRepair;



  // Data that be transfer from MMR03
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

  @Inject
  private PrintRepairService printRepairService;

  private Map<String, Object> content;

  public void exportToExcel() {
    try {
      this.setValueForPrintRepair();
      this.printRepairService.exportToExcel(this.content, this.listReplPart, this.listPartOrder, this.listChecklist, this.listPartOrderItem);
    } catch (final IOException e) {
      super.log.debug(e.getMessage(), e);
    }
  }

  @Inject
  private ViewRepairRequestBean viewRequestBean;

  public void setValueForPrintRepair() {
    this.currentFault = this.viewRequestBean.getCurrentFault();
    this.currentFee = this.viewRequestBean.getCurrentFee();
    this.selectedRepairRequest = this.viewRequestBean.getSelectedRepairRequest();
    this.listChecklist = this.viewRequestBean.getListChecklist();
    this.listChecklistItem = this.viewRequestBean.getListChecklistItem();
    this.listPartEstimate = this.viewRequestBean.getListPartEstimate();
    this.listPartEstimateItem = this.viewRequestBean.getListPartEstimateItem();
    this.listPartOrder = this.viewRequestBean.getListPartOrder();
    this.listPartOrderItem = this.viewRequestBean.getListPartOrderItem();
    this.listReplPart = this.viewRequestBean.getListReplPart();

    this.content = this.setDataContent();

  }

  public Map<String, Object> setDataContent() {
    final Map<String, Object> content = new HashMap<String, Object>();
    content.put(CommonConstants.PRINT_REPAIR_CURRENT_DATE, LocalDate.now());
    if (this.selectedRepairRequest.getHospitalDept() == null) {
      content.put(CommonConstants.PRINT_REPAIR_DEPARTMENT_NAME, null);
    } else {
      content.put(CommonConstants.PRINT_REPAIR_DEPARTMENT_NAME, this.selectedRepairRequest.getHospitalDept().getName());
    }
    content.put(CommonConstants.PRINT_REPAIR_REQUEST_DATE, this.selectedRepairRequest.getContactDate());
    content.put(CommonConstants.PRINT_REPAIR_DOCUMENT_NUMBER, this.selectedRepairRequest.getActionCode());
    content.put(CommonConstants.PRINT_REPAIR_OCCUR_DATE, this.selectedRepairRequest.getOccurDate());
    if (this.selectedRepairRequest.getHospitalContactPsn() == null) {
      content.put(CommonConstants.PRINT_REPAIR_CUSTOMER_NAME, null);
      content.put(CommonConstants.PRINT_REPAIR_CUSTOMER_EMAIL, null);
      content.put(CommonConstants.PRINT_REPAIR_HEAD_PERSON, null);
    } else {
      content.put(CommonConstants.PRINT_REPAIR_CUSTOMER_NAME, this.selectedRepairRequest.getHospitalContactPsn().getName());
      content.put(CommonConstants.PRINT_REPAIR_CUSTOMER_EMAIL, this.selectedRepairRequest.getHospitalContactPsn().getEmail());
    }
    if (this.selectedRepairRequest.getHospitalContactPerson() == null) {
      content.put(CommonConstants.PRINT_REPAIR_HEAD_PERSON, null);
    } else {
      if (this.selectedRepairRequest.getHospitalContactPerson() == null) {
        content.put(CommonConstants.PRINT_REPAIR_HEAD_PERSON, null);
      } else {
        content.put(CommonConstants.PRINT_REPAIR_HEAD_PERSON, this.selectedRepairRequest.getHospitalContactPerson().getName());
      }
    }
    content.put(CommonConstants.PRINT_REPAIR_DATE_ACCEPTANCE_REPAIR, this.selectedRepairRequest.getContactDate());
    if (this.selectedRepairRequest.getContactPerson() == null) {
      content.put(CommonConstants.PRINT_REPAIR_RESPONSE_PERSON, null);
    } else if (this.selectedRepairRequest.getContactPerson() == null) {
      content.put(CommonConstants.PRINT_REPAIR_RESPONSE_PERSON, null);
    } else {
      content.put(CommonConstants.PRINT_REPAIR_RESPONSE_PERSON, this.selectedRepairRequest.getContactPerson().getName());
    }
    content.put(CommonConstants.PRINT_REPAIR_FEEDBACK_PROBLEMS, this.selectedRepairRequest.getComplaint());
    content.put(CommonConstants.PRINT_REPAIR_DESCRIPTIPON_ERROR, this.currentFault.getDescription());
    content.put(CommonConstants.PRINT_REPAIR_CONTENT_REPAIR, this.currentFault.getRepair());
    content.put(CommonConstants.PRINT_REPAIR_PREVENTION_DESCRIPTION, this.currentFault.getPrevention());
    content.put(CommonConstants.PRINT_REPAIR_CAUSE_PREVENTION, this.currentFault.getCause());
    if ((this.selectedRepairRequest.getFinishDate() == null) || (this.selectedRepairRequest.getActionDate() == null)) {
      content.put(CommonConstants.PRINT_REPAIR_NUMBER_DATE_OF_REPAIR, 0);
    } else {
      content.put(CommonConstants.PRINT_REPAIR_NUMBER_DATE_OF_REPAIR,
          DateUtils.minusTwoDateReturnNumber(this.selectedRepairRequest.getFinishDate(), this.selectedRepairRequest.getActionDate()) + 1);
    }
    if (this.currentFee.getFeeParts() != null) {
      content.put(CommonConstants.PRINT_REPAIR_FEE_PARTS, String.format("%.3f", this.currentFee.getFeeParts()));
    } else {
      content.put(CommonConstants.PRINT_REPAIR_FEE_PARTS, 0.0);
    }
    if (this.currentFee.getFeeOther() != null) {
      content.put(CommonConstants.PRINT_REPAIR_FEE_ACTUAL, String.format("%.3f", this.currentFee.getFeeOther()));
    } else {
      content.put(CommonConstants.PRINT_REPAIR_FEE_ACTUAL, 0.0);
    }
    if (this.currentFee.getFeeShipping() != null) {
      content.put(CommonConstants.PRINT_REPAIR_FEE_SHIPPING, String.format("%.3f", this.currentFee.getFeeShipping()));
    } else {
      content.put(CommonConstants.PRINT_REPAIR_FEE_SHIPPING, 0.0);
    }
    if (this.currentFee.getTotal() != null) {
      content.put(CommonConstants.PRINT_REPAIR_TOTALBILL, String.format("%.3f", this.currentFee.getTotal()));
    } else {
      content.put(CommonConstants.PRINT_REPAIR_TOTALBILL, 0.0);
    }
    content.put(CommonConstants.PRINT_REPAIR_PAID_OR_NOTPAID, this.getStatusOfActionBill(this.currentFee));
    content.put(CommonConstants.PRINT_REPAIR_OFFICE_NAME_REQUEST_REPAIR, this.selectedRepairRequest.getCreatedByUser().getOffice().getName());
    content.put(CommonConstants.PRINT_CHECK, this.getActionType(this.selectedRepairRequest));
    content.put(CommonConstants.PRINT_REPAIR_PERIODIC_CHECK, this.getActionType(this.selectedRepairRequest));
    content.put(CommonConstants.PRINT_REPAIR_MAINTENCE, this.getActionType(this.selectedRepairRequest));
    content.put(CommonConstants.PRINT_REPAIR_REPAIR, this.getActionType(this.selectedRepairRequest));
    content.put(CommonConstants.PRINT_REPAIR_IMAGE_FILE, this.selectedRepairRequest.getInstallConfirmImg());
    content.put(CommonConstants.PRINT_REPAIR_FINISH_DATE, this.selectedRepairRequest.getFinishDate());
    // content.put(CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE,
    // CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE);
    // content.put(CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE_MEASURE,
    // CommonConstants.PRINT_REPAIR_FLAG_POSITION_DEVICE_MEASURE);
    return content;
  }

  private String getStatusOfActionBill(ActionBill currentFee) {
    final double totalPay = currentFee.getTotalPay();
    if (totalPay == 0)
      return SelectItemGenerator.getListSelectItem(SelectItemGenerator.ListType.STATUS_ACTIONBILL).get(CommonConstants.BILL_NOT_PAID).getLabel();
    else
      return SelectItemGenerator.getListSelectItem(SelectItemGenerator.ListType.STATUS_ACTIONBILL).get(CommonConstants.BILL_PAID).getLabel();
  }

  public String getActionType(ActionLog selectedLog) {
    if (selectedLog.getActionTypeMaster() == null)
      return "";
    if (ArrowStrUtils.isEmpty(selectedLog.getActionTypeMaster().getLabel()))
      return "";
    else {
      switch (selectedLog.getActionType()) {
        case AppConfig.ACTION_TYPE_CHECK:
          return "o";
        case AppConfig.ACTION_TYPE_MAINTENACE:
          return "o";
        case AppConfig.ACTION_TYPE_PERIODIC_CHECK:
          return "o";
        case AppConfig.ACTION_TYPE_REPAIR:
          return "o";
        default:
          return "";
      }
    }
  }

  public Map<String, Object> getDataRepair() {
    return this.dataRepair;
  }

  public void setDataRepair(Map<String, Object> pDataRepair) {
    this.dataRepair = pDataRepair;
  }

  public List<ReplPart> getListReplPart() {
    return this.listReplPart;
  }

  public void setListReplPart(List<ReplPart> pListReplPart) {
    this.listReplPart = pListReplPart;
  }

  public List<PartOrder> getListPartOrder() {
    return this.listPartOrder;
  }

  public void setListPartOrder(List<PartOrder> pListPartOrder) {
    this.listPartOrder = pListPartOrder;
  }

  public ActionBill getCurrentFee() {
    return this.currentFee;
  }

  public void setCurrentFee(ActionBill pCurrentFee) {
    this.currentFee = pCurrentFee;
  }

  public Fault getCurrentFault() {
    return this.currentFault;
  }

  public void setCurrentFault(Fault pCurrentFault) {
    this.currentFault = pCurrentFault;
  }

  public ActionLog getSelectedRepairRequest() {
    return this.selectedRepairRequest;
  }

  public void setSelectedRepairRequest(ActionLog pSelectedRepairRequest) {
    this.selectedRepairRequest = pSelectedRepairRequest;
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

  public List<PartOrderItem> getListPartOrderItem() {
    return this.listPartOrderItem;
  }

  public void setListPartOrderItem(List<PartOrderItem> pListPartOrderItem) {
    this.listPartOrderItem = pListPartOrderItem;
  }


}
