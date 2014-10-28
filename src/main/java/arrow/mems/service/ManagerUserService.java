package arrow.mems.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.BeanCopier;
import arrow.mems.constant.DatabaseConstants;
import arrow.mems.constant.ServiceConstants;
import arrow.mems.messages.MAUMessages;
import arrow.mems.persistence.dto.UsersDto;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.util.string.EncryptStringUtils;

@Named
public class ManagerUserService extends AbstractService {
  @Inject
  UsersRepository userRepo;
  private final static int SALT_INDEX = 0;
  private final static int PASSWORD_INDEX = 1;

  public ServiceResult<Integer> updatePassword(String newPassword, int userId) {
    final List<Message> errors = new ArrayList<>();
    final int result = this.userRepo.updatePasswordById(newPassword, userId);
    if (result == ServiceConstants.UPDATE_SUCCESS_BY_REPO) {
      errors.add(MAUMessages.MAU00011());
      return new ServiceResult<Integer>(true, result, errors);
    } else {
      errors.add(MAUMessages.MAU00010());
      return new ServiceResult<Integer>(false, result, errors);
    }
  }

  public ServiceResult<Users> deleteUser(int userId) {
    final List<Message> messages = new ArrayList<>();
    final int result = this.userRepo.updateIsDeleted(DatabaseConstants.DELETED, userId);
    if (ServiceConstants.UPDATE_SUCCESS_BY_REPO == result) {
      messages.add(MAUMessages.MAU00019());
      return new ServiceResult<Users>(true, this.userRepo.findUserById(userId).getAnyResult(), messages);
    } else {
      messages.add(MAUMessages.MAU00020());
      return new ServiceResult<Users>(false, null, messages);
    }
  }

  private List<Message> validateBeforeAdd(UsersDto newUser) {
    final List<Message> messages = new ArrayList<>();
    if (StringUtils.isEmpty(newUser.getPassword())) {
      messages.add(MAUMessages.MAU00005());
    }

    final Users oldUser = this.getUserByLoginName(newUser.getLoginName(), DatabaseConstants.ACTIVE);
    if (oldUser != null) {
      messages.add(MAUMessages.MAU00021());
    }

    return messages;
  }

  public ServiceResult<Users> addNewUser(UsersDto newUser) {
    final List<Message> messages = new ArrayList<>();

    messages.addAll(this.validateBeforeAdd(newUser));
    if (CollectionUtils.isEmpty(messages)) {

      final Users addingUser = new Users();
      BeanCopier.copy(newUser, addingUser);
      final String[] encryptPass = EncryptStringUtils.encryptPassword(newUser.getPassword());
      addingUser.setPassword(encryptPass[ManagerUserService.PASSWORD_INDEX]);
      addingUser.setSalt(encryptPass[ManagerUserService.SALT_INDEX]);


      // Generate User App Key = Varchar(32)
      addingUser.setUserAppKey(RandomStringUtils.random(32, "abcdefghyjklmnopqrstuvwxyz1234567890"));


      this.userRepo.saveAndFlush(addingUser);
      messages.add(MAUMessages.MAU00016());
      return new ServiceResult<Users>(true, addingUser, messages);
    } else
      return new ServiceResult<Users>(false, null, messages);
  }

  public Users getUserByLoginName(String loginName, int isDeleted) {
    return this.userRepo.findUserByLoginName(loginName, isDeleted).getAnyResult();
  }

  public List<Users> getAllUsersByIsDeletedAndExceptUserLogging(int isActive, int userId) {
    return this.userRepo.findAllUserByIsDeletedExceptUserLogging(isActive, userId).getResultList();
  }

  public List<Users> getListFilterUserByIsDeleted(int isDeleted) {
    final ArrowQuery<Users> query = new ArrowQuery<>(super.em);
    query.select("e").from("Users e").leftJoin("e.person p").leftJoin("e.office o");
    query.where("e.isDeleted = " + isDeleted);

    // add filter and sort
    query.addFilterAndSorterForString("e.email");
    query.addFilterAndSorterForString("e.loginName");
    query.addFilterAndSorterForString("e.name");
    query.addFilterAndSorterForString("psnName", "p.name");
    query.addSorter("userId", "e.userId");
    query.addFilterAndSorterForString("manufacturerOffice", "o.name");

    query.orderBy("e.createdAt DESC");

    return query.getResultList();
  }

  /**
   * Update current user.
   *
   * @param pEditedUser the edited user on form
   * @return the service result
   */
  public ServiceResult<Users> updateUser(UsersDto editedUser) {
    final List<Message> errors = new ArrayList<>();
    errors.addAll(this.validateBeforeUpdate(editedUser));

    if (CollectionUtils.isEmpty(errors)) {
      final Users existingUser = this.userRepo.findBy(editedUser.getUserId());
      final String oldPassword = existingUser.getPassword();
      final String oldSalt = existingUser.getSalt();
      BeanCopier.copy(editedUser, existingUser, "password", "salt");
      // if password changed, and passed validation
      if (StringUtils.isNotEmpty(editedUser.getPassword())) {
        final String[] encryptPass = EncryptStringUtils.encryptPassword(editedUser.getPassword());
        existingUser.setPassword(encryptPass[ManagerUserService.PASSWORD_INDEX]);
        existingUser.setSalt(encryptPass[ManagerUserService.SALT_INDEX]);
      } else {
        existingUser.setPassword(oldPassword);
        existingUser.setSalt(oldSalt);
      }
      this.userRepo.save(existingUser);
      errors.add(MAUMessages.MAU00011());
      return new ServiceResult<Users>(true, existingUser, errors);
    }
    return new ServiceResult<Users>(false, null, errors);
  }

  private List<Message> validateBeforeUpdate(UsersDto pEditedUser) {
    final List<Message> errors = new ArrayList<>();
    final Users existingUser = this.userRepo.findBy(pEditedUser.getUserId());
    if (existingUser == null) {
      errors.add(MAUMessages.MAU00026());
    } else {
      if (StringUtils.isNotEmpty(pEditedUser.getPassword())) {
        final String encryptedPass = EncryptStringUtils.encrypt(pEditedUser.getPassword(), pEditedUser.getSalt());
        if (StringUtils.equals(encryptedPass, pEditedUser.getPassword())) {
          errors.add(MAUMessages.MAU00027());
        }
      }
    }
    return errors;
  }

  public Users getUserById(int userId) {
    return this.userRepo.findUserById(userId).getAnyResult();
  }

}
