//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.ActionBill;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ActionBillDto extends ActionBill {
  private int actionBillId;

  @Override
  public int getActionBillId() {
    return this.actionBillId;
  }

  public void setActionBillId(final int actionBillId) {
    this.actionBillId = actionBillId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}