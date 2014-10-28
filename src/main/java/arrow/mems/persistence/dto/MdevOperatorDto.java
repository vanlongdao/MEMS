//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.MdevOperator;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class MdevOperatorDto extends MdevOperator {
  private int mdevServiceId;

  @Override
  public int getMdevServiceId() {
    return this.mdevServiceId;
  }

  public void setMdevServiceId(final int mdevServiceId) {
    this.mdevServiceId = mdevServiceId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}