//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.MtCurrency;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class MtCurrencyDto extends MtCurrency {
  private int ccyId;

  @Override
  public int getCcyId() {
    return this.ccyId;
  }

  public void setCcyId(final int ccyId) {
    this.ccyId = ccyId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}