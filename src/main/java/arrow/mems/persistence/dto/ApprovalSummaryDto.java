//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.ApprovalSummary;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ApprovalSummaryDto extends ApprovalSummary {
  private int approvalId;

  @Override
  public int getApprovalId() {
    return this.approvalId;
  }

  public void setApprovalId(final int approvalId) {
    this.approvalId = approvalId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}