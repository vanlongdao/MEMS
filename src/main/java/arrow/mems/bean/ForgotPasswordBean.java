package arrow.mems.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.helper.ServiceResult;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Users;
import arrow.mems.service.RecoverPasswordService;
import freemarker.template.TemplateException;

@Named
@ViewScoped
public class ForgotPasswordBean extends AbstractBean {
  @NotNull(message = "{" + MessageCode.MAU00014 + "}")
  @Email(regexp = CommonConstants.REGEX_EMAIL, message = "{" + MessageCode.MAU00015 + "}")
  private String email;

  @NotNull(message = "{" + MessageCode.MAU00002 + "}")
  @Size(min = 6, max = 16, message = "{" + MessageCode.MAU00007 + "}")
  private String loginName;

  @Inject
  RecoverPasswordService recoverPassService;

  // TODO : Currently , message has not support
  public void sendRecoveryPasswordInfo() {
    try {
      final ServiceResult<Users> results = this.recoverPassService.sendRecoveryPasswordToUser(this.email, this.loginName);
      if (results.isSuccess()) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Send mail success . Please check mail"));
      } else {
        results.showMessages();
      }
    } catch (IOException | TemplateException | MessagingException e) {
      this.log.debug(e.getMessage());
    }
  }


  public String getEmail() {
    return this.email;
  }

  public void setEmail(String pEmail) {
    this.email = pEmail;
  }

  public String getLoginName() {
    return this.loginName;
  }

  public void setLoginName(String pLoginName) {
    this.loginName = pLoginName;
  }


}
