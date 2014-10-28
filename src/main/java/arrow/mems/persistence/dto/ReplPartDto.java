//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/


import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.ReplPart;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class ReplPartDto extends ReplPart {
  private int replPartId;

  @Override
  public int getReplPartId() {
    return this.replPartId;
  }

  public void setReplPartId(final int replPartId) {
    this.replPartId = replPartId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Override
  @NotNull(message = "{" + MessageCode.MRR00001 + "}")
  public String getDeviceCode() {
    return super.getDeviceCode();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}