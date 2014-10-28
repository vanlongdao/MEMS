//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.MdevPurchase;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class MdevPurchaseDto extends MdevPurchase {
  private int mdevPurchaseId;

  @Override
  public int getMdevPurchaseId() {
    return this.mdevPurchaseId;
  }

  public void setMdevPurchaseId(final int mdevPurchaseId) {
    this.mdevPurchaseId = mdevPurchaseId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}