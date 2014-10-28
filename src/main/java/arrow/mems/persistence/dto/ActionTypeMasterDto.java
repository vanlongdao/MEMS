//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.ActionTypeMaster;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ActionTypeMasterDto extends ActionTypeMaster {
  private int actionTypeId;

  @Override
  public int getActionTypeId() {
    return this.actionTypeId;
  }

  public void setActionTypeId(final int actionTypeId) {
    this.actionTypeId = actionTypeId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}