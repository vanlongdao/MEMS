//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Users;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class UsersDto extends Users {
  private int userId;

  @Override
  public int getUserId() {
    return this.userId;
  }

  public void setUserId(final int userId) {
    this.userId = userId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Override
  @NotNull(message = "{" + MessageCode.MAU00018 + "}")
  public Integer getAccountType() {
    return super.getAccountType();
  }

  @Override
  @Email(regexp = CommonConstants.REGEX_EMAIL, message = "{" + MessageCode.MAU00015 + "}")
  public String getEmail() {
    return super.getEmail();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MAU00002 + "}")
  @Size(min = CommonConstants.LOGIN_NAME_MIN_LENGTH, max = CommonConstants.LOGIN_NAME_MAX_LENGTH, message = "{" + MessageCode.MAU00007 + "}")
  public String getLoginName() {
    return super.getLoginName();
  }

  @Override
  @Size(min = CommonConstants.PASSWORD_MIN_LENGTH, max = CommonConstants.PASSWORD_MAX_LENGTH, message = "{" + MessageCode.MAU00008 + "}")
  public String getPassword() {
    return super.getPassword();
  }

  public boolean isSupervisor() {
    return ((this.getIsSupervisor() != null) && (CommonConstants.ACTIVE == this.getIsSupervisor()));
  }

  public void setSupervisor(boolean value) {
    this.setIsSupervisor(value ? CommonConstants.ACTIVE : CommonConstants.DELETE);;
  }

  private boolean isPersisted;

  @Override
  public boolean isPersisted() {
    return this.isPersisted;
  }

  @Override
  public void setPersisted(boolean pIsPersisted) {
    this.isPersisted = pIsPersisted;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}