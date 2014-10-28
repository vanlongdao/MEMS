//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import arrow.framework.util.Instance;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.mapped.ApprovalSummaryMapped;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.util.string.ArrowStrUtils;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="APPROVAL_SUMMARY")
public class ApprovalSummary extends ApprovalSummaryMapped {

  public ApprovalSummary() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  
  @Transient
  protected String comment;

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  @Transient
  protected String actionType;

  public String getActionType() {
    if (ArrowStrUtils.isEmpty(this.actionType)) {
      this.actionType = CommonConstants.NONE;
    }
    return this.actionType;
  }

  public void setActionType(String pActionType) {
    this.actionType = pActionType;
  }

  public Users getRequestorUser() {
    return Instance.get(UsersRepository.class).findBy(this.requestor);
  }

  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  