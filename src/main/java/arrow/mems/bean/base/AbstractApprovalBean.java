package arrow.mems.bean.base;

import javax.inject.Inject;

import arrow.framework.bean.AbstractBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.ApprovalLevelSupervisor;
import arrow.mems.persistence.entity.ApprovalSummary;
import arrow.mems.service.ApprovalService;

public abstract class AbstractApprovalBean extends AbstractBean {
  public abstract Object getEntityId();

  public abstract String getDataType();

  public abstract String getItemLabel();

  @Inject
  private UserCredential currentUser;
  @Inject
  private ApprovalService approvalService;


  public boolean isAllowProcess(AbstractApprovalEntity targetEntity) {
    if (!targetEntity.isPersisted() || !this.currentUser.isSupervisor())
      return false;

    // TODO: check approval configuration to determine current user can process targetEntity or not.

    return this.canProcess(targetEntity);
  }

  // protected abstract boolean canProcess(AbstractApprovalEntity pTargetEntity);
  protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
    final ApprovalSummary dataNeedApproval =
        this.approvalService.getApprovalSummaryByItemCodeAndDataType(this.getEntityId().toString(), this.getDataType());
    if (dataNeedApproval == null)
      return false;
    final ApprovalLevelSupervisor levelSupervisor =
        this.approvalService.getLevelSuppervisorBySupervisorIdAndLevelId(dataNeedApproval.getLevelId(), this.currentUser.getUserId());
    if (levelSupervisor == null)
      return false;
    return true;
  }
}
