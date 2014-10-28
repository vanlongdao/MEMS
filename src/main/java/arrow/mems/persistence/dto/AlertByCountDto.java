//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.AlertByCount;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class AlertByCountDto extends AlertByCount {
  private int counterBaseId;

  @Override
  public int getCounterBaseId() {
    return this.counterBaseId;
  }

  public void setCounterBaseId(final int counterBaseId) {
    this.counterBaseId = counterBaseId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}