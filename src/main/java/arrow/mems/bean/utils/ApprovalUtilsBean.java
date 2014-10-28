package arrow.mems.bean.utils;

import java.time.LocalDateTime;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.bean.AbstractBean;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.Instance;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MMIXMessages;
import arrow.mems.persistence.entity.ApprovalSummary;
import arrow.mems.persistence.repository.ApprovalSummaryRepository;
import arrow.mems.service.ApprovalService;

@Named
@ViewScoped
public class ApprovalUtilsBean extends AbstractBean {
  @Inject
  UserCredential currentUser;

  /**
   * Saved but not request approve yet.
   *
   * @return
   */
  public boolean isPending() {
    final ApprovalSummary itemInPending =
        this.approvalService.getApprovalSummaryByItemCodeAndDataType(this.getEntityId().toString(), this.getDataType());
    return null != itemInPending;

  }

  public boolean isAllowProcess() {
    final ApprovalSummary itemCanProcess =
        this.approvalService.checkSupervisorAndPendingItem(this.getEntityId().toString(), this.getDataType(), this.currentUser.getUserId());

    return null != itemCanProcess;
  }

  private String approvalComment;

  public String getApprovalComment() {
    return this.approvalComment;
  }

  public void setApprovalComment(String pApprovalComment) {
    this.approvalComment = pApprovalComment;
  }

  private Object entityId;

  private String dataType;

  private String itemLabel;


  public String getItemLabel() {
    return this.itemLabel;
  }

  public void setItemLabel(String pItemLabel) {
    this.itemLabel = pItemLabel;
  }

  public String getDataType() {
    return this.dataType;
  }

  public void setDataType(String pDataType) {
    this.dataType = pDataType;
  }

  public Object getEntityId() {
    return this.entityId;
  }

  public void setEntityId(Object pEntityId) {
    this.entityId = pEntityId;
  }

  @Inject
  private ApprovalService approvalService;

  public void requestApprove() {
    final ServiceResult<ApprovalSummary> result =
        this.approvalService.requestApproval(this.getDataType(), this.getEntityId().toString(), this.currentUser.getUserId(), this.getItemLabel());
    if (result.isSuccess()) {
      MMIXMessages.MMIX00004().show();
    } else {
      // MMIXMessages.MMIX00005().show();
      result.showMessages();
    }
  }

  public void approve(int supervisorId) {
    final ApprovalSummary dataNeedApproval =
        Instance.get(ApprovalSummaryRepository.class).findApprovalSummaryByItemCodeAndItemType(this.entityId.toString(), this.dataType)
        .getAnyResult();
    ServiceResult<ApprovalSummary> result = new ServiceResult<ApprovalSummary>(false, null);
    if (dataNeedApproval != null) {
      dataNeedApproval.setComment(this.approvalComment);
      result = Instance.get(ApprovalService.class).approve(dataNeedApproval, supervisorId, LocalDateTime.now(), CommonConstants.APPROVAL);
    }
    if (result.isSuccess()) {
      this.approvalComment = null;
      MMIXMessages.MMIX00001().show();
    } else {
      MMIXMessages.MMIX00002().show();
    }

  }

  public void revise(int supervisorId) {
    final ApprovalSummary dataNeedApproval =
        Instance.get(ApprovalSummaryRepository.class).findApprovalSummaryByItemCodeAndItemType(this.entityId.toString(), this.dataType)
        .getAnyResult();
    ServiceResult<ApprovalSummary> result = new ServiceResult<ApprovalSummary>(false, null);
    if (dataNeedApproval != null) {
      dataNeedApproval.setComment(this.approvalComment);
      result = Instance.get(ApprovalService.class).revise(dataNeedApproval, supervisorId, LocalDateTime.now(), CommonConstants.APPROVAL);
    }
    if (result.isSuccess()) {
      this.approvalComment = null;
      MMIXMessages.MMIX00001().show();
    } else {
      MMIXMessages.MMIX00002().show();
    }

  }

  public void reject(int supervisorId) {
    final ApprovalSummary dataNeedApproval =
        Instance.get(ApprovalSummaryRepository.class).findApprovalSummaryByItemCodeAndItemType(this.entityId.toString(), this.dataType)
            .getAnyResult();
    ServiceResult<ApprovalSummary> result = new ServiceResult<ApprovalSummary>(false, null);
    if (dataNeedApproval != null) {
      dataNeedApproval.setComment(this.approvalComment);
      result = Instance.get(ApprovalService.class).reject(dataNeedApproval, supervisorId, LocalDateTime.now(), CommonConstants.APPROVAL);
    }
    if (result.isSuccess()) {
      this.approvalComment = null;
      MMIXMessages.MMIX00001().show();
    } else {
      MMIXMessages.MMIX00002().show();
    }

  }

}
