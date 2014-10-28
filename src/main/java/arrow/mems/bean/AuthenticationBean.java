package arrow.mems.bean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.cdi.qualifiers.Login;
import arrow.framework.cdi.qualifiers.Logout;
import arrow.framework.helper.AuthenticationData;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.CDIUtils;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.messages.MessageCode;
import arrow.mems.service.AuthenticationService;

@Named
@ViewScoped
public class AuthenticationBean extends AbstractBean {

  @NotNull(message = "{" + MessageCode.MAU00002 + "}")
  private String username;
  @NotNull(message = "{" + MessageCode.MAU00005 + "}")
  private String password;

  @Inject
  UserCredential userCredential;

  @Inject
  private AuthenticationService authService;

  public void login() {
    final ServiceResult<AuthenticationData> result = this.authService.login(this.username, this.password);
    if (result.isSuccess()) {
      this.userCredential.updateAuthData(result.getData());
      CDIUtils.getBeanManager().fireEvent(this.userCredential, Login.LITERAL);
    }
    result.showMessages();
  }

  public void logout() {
    this.userCredential.updateAuthData(null);
    CDIUtils.getBeanManager().fireEvent(this.userCredential, Logout.LITERAL);
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String pUserLoginName) {
    this.username = pUserLoginName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String pPassword) {
    this.password = pPassword;
  }
}
