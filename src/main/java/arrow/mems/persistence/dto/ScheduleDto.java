//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.Schedule;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ScheduleDto extends Schedule {
  private int schedBaseId;

  @Override
  public int getSchedBaseId() {
    return this.schedBaseId;
  }

  public void setSchedBaseId(final int schedBaseId) {
    this.schedBaseId = schedBaseId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}