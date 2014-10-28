//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.MdevChecklistItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class MdevChecklistItemDto extends MdevChecklistItem {
  private int ckiId;

  @Override
  public int getCkiId() {
    return this.ckiId;
  }

  public void setCkiId(final int ckiId) {
    this.ckiId = ckiId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}