//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.ApprovalSummaryHistory;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ApprovalSummaryHistoryDto extends ApprovalSummaryHistory {
  private int historyId;

  @Override
  public int getHistoryId() {
    return this.historyId;
  }

  public void setHistoryId(final int historyId) {
    this.historyId = historyId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}