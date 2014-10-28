//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.entity.MtCountry;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class CorporateDto extends Corporate {
  private int corpId;

  @Override
  public int getCorpId() {
    return this.corpId;
  }

  public void setCorpId(final int corpId) {
    this.corpId = corpId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Override
  @NotNull(message = "{" + MessageCode.MMI00012 + "}")
  public MtCountry getMtCountry() {
    return super.getMtCountry();
  }

  @Override
  @Size(max = 255, message = "{" + MessageCode.MMI00023 + "}")
  public String getName() {
    return super.getName();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}