package arrow.mems.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.omnifaces.util.Faces;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.mems.constant.DatabaseConstants;
import arrow.mems.constant.ServiceConstants;
import arrow.mems.messages.MAUMessages;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.ui.ScreenCodes;
import arrow.mems.util.mail.EmailHelper;
import arrow.mems.util.string.EncryptStringUtils;
import freemarker.template.TemplateException;

@Named
public class RecoverPasswordService extends AbstractService {

  @Inject
  UsersRepository userRepo;

  public ServiceResult<Users> sendRecoveryPasswordToUser(final String email, final String loginName) throws IOException, TemplateException,
      MessagingException {
    final List<Message> errors = new ArrayList<>();
    final Users user = this.userRepo.findUserByLoginNameAndEmail(email, loginName, DatabaseConstants.ACTIVE).getAnyResult();

    if (user != null) {
      final String sessionId = UUID.randomUUID().toString();
      if (this.updateSessionId(email, loginName, sessionId).isSuccess()) {
        final StringBuilder url = new StringBuilder();
        url.append(Faces.getRequestBaseURL().toString());
        url.append("index.xhtml?");
        url.append("screenCode=" + ScreenCodes.RECOVER_PASSWORD_FORM).append("&");
        url.append("token=" + sessionId);
        EmailHelper.sendRecoverPassword(user, email, url.toString());
      }
      return new ServiceResult<Users>(true, user, errors);
    } else {
      // Add errors message here
      errors.add(MAUMessages.MAU00012());
      return new ServiceResult<Users>(false, null, errors);
    }
  }

  public ServiceResult<Boolean> updateSessionId(final String email, String loginName, final String sessionId) {
    final List<Message> errors = new ArrayList<>();
    final int result = this.userRepo.updateSessionId(sessionId, email, loginName);
    if (result == ServiceConstants.UPDATE_SUCCESS_BY_REPO)
      return new ServiceResult<>(true, null, errors);
    else
      return new ServiceResult<>(false, null, errors);
  }

  public ServiceResult<Users> updatePassword(String newPassword, String reNewPassword, String sessionId) {
    final List<Message> errors = new ArrayList<>();
    final Users user = this.userRepo.findUserBySessionId(sessionId).getAnyResult();
    if (user != null) {
      try {
        this.userRepo.updatePasswordAndSessionById(EncryptStringUtils.encrypt(newPassword, user.getSalt()), null, user.getUserId());
      } catch (final PersistenceException e) {
        super.logger.debug("EXCEPTION WHEN CHANGE PASSWORD , MAY BE NOT ENCRYPT PASSWORD OR INSERT", e);
        return new ServiceResult<>(false, user, errors);
      }
      return new ServiceResult<>(true, user, errors);
    } else {
      // TODO: Set errors message here
    }
    return new ServiceResult<>(false, null, errors);
  }
}
