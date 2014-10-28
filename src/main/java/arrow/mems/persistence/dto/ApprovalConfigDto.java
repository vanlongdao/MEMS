//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.ApprovalConfig;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ApprovalConfigDto extends ApprovalConfig {
  private int configId;

  @Override
  public int getConfigId() {
    return this.configId;
  }

  public void setConfigId(final int configId) {
    this.configId = configId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}