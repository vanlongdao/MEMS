//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.MtCountry;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class AddressDto extends Address {
  private int addrId;

  @Override
  public int getAddrId() {
    return this.addrId;
  }

  public void setAddrId(final int addrId) {
    this.addrId = addrId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  

  @Override
  @NotNull(message = "{" + MessageCode.MMI00012 + "}")
  public MtCountry getMtCountry() {
    return super.getMtCountry();
  }

  @Override
  @Size(max = CommonConstants.TEXT_MAX_LENGTH, message = "{" + MessageCode.MMI00023 + "}")
  public String getAddress1() {
    return super.getAddress1();
  }

  @Override
  @Size(max = CommonConstants.TEXT_MAX_LENGTH, message = "{" + MessageCode.MMI00023 + "}")
  public String getAddress2() {
    return super.getAddress2();
  }

  @Override
  @Size(max = CommonConstants.TEXT_MAX_LENGTH, message = "{" + MessageCode.MMI00023 + "}")
  public String getCity() {
    return super.getCity();
  }

  @Override
  @Size(max = CommonConstants.TEXT_MAX_LENGTH, message = "{" + MessageCode.MMI00023 + "}")
  public String getProvince() {
    return super.getProvince();
  }

  @Override
  @Size(max = CommonConstants.CODE_MAX_LENGTH, message = "{" + MessageCode.MMI00023 + "}")
  public String getZipCode() {
    return super.getZipCode();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}