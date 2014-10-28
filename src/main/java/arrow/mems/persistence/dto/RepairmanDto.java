//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.Repairman;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class RepairmanDto extends Repairman {
  private int id;

  @Override
  public int getId() {
    return this.id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}