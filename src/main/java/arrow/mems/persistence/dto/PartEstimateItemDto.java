//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.PartEstimateItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class PartEstimateItemDto extends PartEstimateItem {
  private int peiId;

  @Override
  public int getPeiId() {
    return this.peiId;
  }

  public void setPeiId(final int peiId) {
    this.peiId = peiId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}