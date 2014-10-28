//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.ApprovalLevel;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ApprovalLevelDto extends ApprovalLevel {
  private int levelId;

  @Override
  public int getLevelId() {
    return this.levelId;
  }

  public void setLevelId(final int levelId) {
    this.levelId = levelId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}