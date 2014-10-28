package arrow.mems.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.ui.ScreenMonitor;
import arrow.framework.validator.FieldMatch;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Users;
import arrow.mems.service.RecoverPasswordService;
import arrow.mems.ui.ScreenParamName;

@Named
@ViewScoped
@FieldMatch(first = "newPassword", second = "reNewPassword", message = "{" + MessageCode.MAU00009 + "}")
public class RecoverPasswordBean extends AbstractBean {

  @Inject
  ScreenMonitor screenMonitor;
  @Inject
  UserCredential userCredential;

  @NotNull(message = "{" + MessageCode.MAU00005 + "}")
  @Size(min = 4, max = 32, message = "{" + MessageCode.MAU00008 + "}")
  private String newPassword;

  @NotNull(message = "{" + MessageCode.MAU00005 + "}")
  @Size(min = 4, max = 32, message = "{" + MessageCode.MAU00008 + "}")
  private String reNewPassword;

  private boolean isRedirectHome;

  @Inject
  RecoverPasswordService recoverPassService;

  @PostConstruct
  public void init() {
    this.isRedirectHome = false;
  }

  // TODO : Currently , we don't have message class . so . we just use it draft
  public void updatePassword() {
    // TODO : Check newPassword and reNewPassword is equal ?
    final List<Message> errors = this.validator.validate(this);
    if (!errors.isEmpty()) {
      for (final Message error : errors) {
        error.show();
      }
      return;
    }
    final String token = this.screenMonitor.getCurrentAttributes().get(ScreenParamName.TOKEN).toString();
    final ServiceResult<Users> validate = this.recoverPassService.updatePassword(this.newPassword, this.reNewPassword, token);
    if (!validate.isSuccess()) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Update password unsuccessful"));
      return;
    } else {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Update password success"));
      this.isRedirectHome = true;
    }
  }


  public void setStateIsRedirectHome() {
    this.isRedirectHome = false;
  }


  public String getNewPassword() {
    return this.newPassword;
  }


  public void setNewPassword(String pNewPassword) {
    this.newPassword = pNewPassword;
  }


  public String getReNewPassword() {
    return this.reNewPassword;
  }


  public void setReNewPassword(String pReNewPassword) {
    this.reNewPassword = pReNewPassword;
  }


  public boolean isRedirectHome() {
    return this.isRedirectHome;
  }


  public void setRedirectHome(boolean pIsRedirectHome) {
    this.isRedirectHome = pIsRedirectHome;
  }
}
