//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.Office;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class HospitalDto extends Hospital {
  private int hospId;

  @Override
  public int getHospId() {
    return this.hospId;
  }

  public void setHospId(final int hospId) {
    this.hospId = hospId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */


  @Override
  @NotNull(message = "{" + MessageCode.MFM00014 + "}")
  public String getName() {
    return super.getName();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MMI00016 + "}")
  public Office getOffice() {
    return super.getOffice();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}