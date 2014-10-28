//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.entity.Office;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class OfficeDto extends Office {
  private int officeId;

  @Override
  public int getOfficeId() {
    return this.officeId;
  }

  public void setOfficeId(final int officeId) {
    this.officeId = officeId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Override
  @NotNull(message = "{" + MessageCode.MMI00013 + "}")
  public Address getAddress() {
    return super.getAddress();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MMI00014 + "}")
  public Corporate getCorporate() {
    return super.getCorporate();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MMI00015 + "}")
  @Size(max = 255, message = "{" + MessageCode.MMI00023 + "}")
  public String getTaxCode() {
    return super.getTaxCode();
  }

  @Override
  @Size(max = 255, message = "{" + MessageCode.MMI00023 + "}")
  public String getName() {
    return super.getName();
  }

  @Override
  @Pattern(regexp = CommonConstants.REGEX_TEL_FAX_VIETNAM, message = "{" + MessageCode.MMI00009 + "}")
  @Size(max = 255, message = "{" + MessageCode.MMI00023 + "}")
  public String getTel() {
    return super.getTel();
  }

  @Override
  @Pattern(regexp = CommonConstants.REGEX_TEL_FAX_VIETNAM, message = "{" + MessageCode.MMI00010 + "}")
  @Size(max = 255, message = "{" + MessageCode.MMI00023 + "}")
  public String getFax() {
    return super.getFax();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}