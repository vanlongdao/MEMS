//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.CountScheduleItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class CountScheduleItemDto extends CountScheduleItem {
  private int countSchedItemId;

  @Override
  public int getCountSchedItemId() {
    return this.countSchedItemId;
  }

  public void setCountSchedItemId(final int countSchedItemId) {
    this.countSchedItemId = countSchedItemId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}