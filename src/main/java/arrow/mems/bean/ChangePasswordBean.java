package arrow.mems.bean;

import java.util.List;

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
import arrow.framework.validator.FieldMatch;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.messages.MessageCode;
import arrow.mems.service.ManagerUserService;
import arrow.mems.util.string.EncryptStringUtils;

@Named
@ViewScoped
@FieldMatch(first = "newPass", second = "confirmPass", message = "{" + MessageCode.MAU00009 + "}")
public class ChangePasswordBean extends AbstractBean {

  // For Change password
  @NotNull(message = "{" + MessageCode.MAU00005 + "}")
  @Size(min = 4, max = 32, message = "{" + MessageCode.MAU00008 + "}")
  private String oldPass;
  @NotNull(message = "{" + MessageCode.MAU00005 + "}")
  @Size(min = 4, max = 32, message = "{" + MessageCode.MAU00008 + "}")
  private String newPass;
  @NotNull(message = "{" + MessageCode.MAU00005 + "}")
  @Size(min = 4, max = 32, message = "{" + MessageCode.MAU00008 + "}")
  private String confirmPass;

  @Inject
  ManagerUserService managerUserService;

  @Inject
  UserCredential userCredential;

  // TODO : Must be define message errors
  public void changePassword() {
    final List<Message> errors = this.validator.validate(this);
    if (!errors.isEmpty()) {
      for (final Message message : errors) {
        message.show();
      }
      return;
    }
    final String encryptPass = EncryptStringUtils.encrypt(this.oldPass, this.userCredential.getUserInfo().getSalt());
    if (!this.userCredential.getUserInfo().getPassword().equals(encryptPass)) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Old password must be correct"));
      return;
    }


    final ServiceResult<Integer> results = this.managerUserService.updatePassword(encryptPass, this.userCredential.getUserId());
    results.showMessages();
    return;
  }

  public String getOldPass() {
    return this.oldPass;
  }

  public void setOldPass(String pOldPass) {
    this.oldPass = pOldPass;
  }

  public String getNewPass() {
    return this.newPass;
  }

  public void setNewPass(String pNewPass) {
    this.newPass = pNewPass;
  }

  public String getConfirmPass() {
    return this.confirmPass;
  }

  public void setConfirmPass(String pConfirmPass) {
    this.confirmPass = pConfirmPass;
  }


}
