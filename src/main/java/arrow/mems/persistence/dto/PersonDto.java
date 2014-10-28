//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.Person;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class PersonDto extends Person {
  private int psnId;

  @Override
  public int getPsnId() {
    return this.psnId;
  }

  public void setPsnId(final int psnId) {
    this.psnId = psnId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Override
  @NotNull(message = "{" + MessageCode.MMI00013 + "}")
  public arrow.mems.persistence.entity.Address getAddress() {
    return this.address;
  }

  @NotNull(message = "{" + MessageCode.MMI00022 + "}")
  @Override
  public java.lang.String getName() {
    return this.name;
  }

  @Override
  @NotNull(message = "{" + MessageCode.MMI00016 + "}")
  public Office getOffice() {
    return super.getOffice();
  }

  @Override
  @Email(message = "{" + MessageCode.MMI00011 + "}")
  @Size(max = 255, message = "{" + MessageCode.MMI00023 + "}")
  public String getEmail() {
    return super.getEmail();
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