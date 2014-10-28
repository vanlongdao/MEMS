//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.PartsList;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class PartsListDto extends PartsList {
  private int partsListId;

  @Override
  public int getPartsListId() {
    return this.partsListId;
  }

  public void setPartsListId(final int partsListId) {
    this.partsListId = partsListId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}