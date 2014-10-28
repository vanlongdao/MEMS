//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.persistence.entity.PresetPhrases;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class PresetPhrasesDto extends PresetPhrases {
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
  

  @Override
  @NotNull(message = "{" + MessageCode.MMI00012 + "}")
  public MtCountry getMtCountry() {
    return super.getMtCountry();
  }

  @Override
  @NotNull(message = "{" + MessageCode.XIS00001 + "}")
  public String getInputStr() {
    return super.getInputStr();
  }

  @Override
  @NotNull(message = "{" + MessageCode.XIS00001 + "}")
  public String getShowStr() {
    return super.getShowStr();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}