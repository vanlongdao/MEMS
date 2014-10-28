//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.ScheduleItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ScheduleItemDto extends ScheduleItem {
  private int schedItemId;

  @Override
  public int getSchedItemId() {
    return this.schedItemId;
  }

  public void setSchedItemId(final int schedItemId) {
    this.schedItemId = schedItemId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}