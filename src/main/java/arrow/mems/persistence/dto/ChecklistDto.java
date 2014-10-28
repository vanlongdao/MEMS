//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.Checklist;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ChecklistDto extends Checklist {
  private int cklistLogId;

  @Override
  public int getCklistLogId() {
    return this.cklistLogId;
  }

  public void setCklistLogId(final int cklistLogId) {
    this.cklistLogId = cklistLogId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}