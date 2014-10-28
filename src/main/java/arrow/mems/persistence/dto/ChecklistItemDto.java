//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.ChecklistItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ChecklistItemDto extends ChecklistItem {
  private int ckiLogId;

  @Override
  public int getCkiLogId() {
    return this.ckiLogId;
  }

  public void setCkiLogId(final int ckiLogId) {
    this.ckiLogId = ckiLogId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}