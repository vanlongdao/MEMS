package arrow.mems.bean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import arrow.framework.bean.AbstractBean;
import arrow.framework.helper.AuthenticationData;
import arrow.framework.helper.ServiceResult;
import arrow.mems.constant.AuthenticationConstants;
import arrow.mems.service.SupervisorAuthenticationService;
import arrow.mems.util.dialog.DialogUtil;

@Named
@ViewScoped
public class SupervisorAuthenticationBean extends AbstractBean {

  private String username;

  private String password;

  @Inject
  private SupervisorAuthenticationService supervisorAuthService;

  public void login() {
    final ServiceResult<AuthenticationData> result = this.supervisorAuthService.login(this.username, this.password);
    if (result.isSuccess()) {
      final AuthenticationData supervisorAuthData = result.getData();
      final HttpSession currentSession = Faces.getSession(false);
      if (currentSession == null)
        throw new IllegalArgumentException("Cannot get session");

      if (supervisorAuthData != null) {
        currentSession.setAttribute(AuthenticationConstants.SUPERVISOR_SESSION_KEY, supervisorAuthData.getUserId());
        DialogUtil.CloseDialog(supervisorAuthData);
      }
    }
    result.showMessages();
  }

  public void cancel() {
    DialogUtil.CloseDialog();
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
