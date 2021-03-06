package arrow.mems.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.AuthenticationData;
import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.DatabaseConstants;
import arrow.mems.messages.MAUMessages;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.util.string.EncryptStringUtils;

public class AuthenticationService extends AbstractService {
  @Inject
  UserCredential userCridential;
  @Inject
  UsersRepository userRepo;
  @Inject
  PersonRepository personRepo;

  public ServiceResult<AuthenticationData> login(final String loginName, final String password) {
    final List<Message> messages = new ArrayList<Message>();
    final Users entity = this.userRepo.findUserByLoginName(loginName, DatabaseConstants.ACTIVE).getAnyResult();
    if (entity != null) {
      final String passEncrypted = EncryptStringUtils.encrypt(password, entity.getSalt());
      if (passEncrypted.equals(entity.getPassword())) {
        // login success
        final AuthenticationData authData = new AuthenticationData(entity.getUserId(), entity.getPerson(), entity);

        // detach then clear data for security
        this.em.detach(entity);
        entity.setPassword(StringUtils.EMPTY);
        authData.setUserInfo(entity);
        messages.add(MAUMessages.MAU00001(1));
        return new ServiceResult<>(true, authData, messages);
      }
    }
    messages.add(MAUMessages.MAU00006(1));
    return new ServiceResult<>(false, null, messages);
  }
}
