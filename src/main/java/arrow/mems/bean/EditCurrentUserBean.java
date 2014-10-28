package arrow.mems.bean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.constant.DatabaseConstants;
import arrow.mems.messages.MAUMessages;
import arrow.mems.persistence.dto.UsersDto;
import arrow.mems.persistence.entity.Users;
import arrow.mems.service.ManagerUserService;
import arrow.mems.util.string.EncryptStringUtils;

@Named
@ViewScoped
public class EditCurrentUserBean extends AbstractBean {

  private UsersDto currentUser;


  private String password;

  private boolean supervisor;
  @Inject
  ManagerUserService userService;

  @Inject
  UserCredential userCredential;

  @PostConstruct
  public void init() {
    this.resetFieldBean(this.userCredential.getUserInfo());
  }

  public void updateProfile() {
    // set password enter from view page
    String newPassword = null;
    if (!this.password.isEmpty())
      if ((this.password.length() < CommonConstants.PASSWORD_MIN_LENGTH) || (this.password.length() > CommonConstants.PASSWORD_MAX_LENGTH)) {
        MAUMessages.MAU00025().show();
        return;
      } else {
        newPassword = this.password;
      }
    if (newPassword == null) {
      newPassword = this.userService.getUserById(this.currentUser.getUserId()).getPassword();
    } else {
      newPassword = EncryptStringUtils.encrypt(this.password, this.currentUser.getSalt());
    }

    // Set user to update it
    //
    // final Users editedUser = new Users(this.currentUser.getUserId());
    // BeanCopier.copy(this.currentUser, editedUser);
    // editedUser.setPassword(newPassword);
    // if (this.supervisor) {
    // editedUser.setIsSupervisor(DatabaseConstants.SUPPERVISOR);
    // } else {
    // editedUser.setIsSupervisor(DatabaseConstants.NON_SUPPERVISOR);
    // }
    //
    // // update user and showMessage
    // final ServiceResult<Users> results = this.userService.updateUser(editedUser);
    // results.showMessages();
  }

  public void resetFieldBean(Users user) {
    this.currentUser = new UsersDto();
    this.currentUser.setUserId(user.getUserId());
    BeanCopier.copy(user, this.currentUser);
    if (user.getIsSupervisor() == DatabaseConstants.SUPPERVISOR) {
      this.supervisor = true;
    } else {
      this.supervisor = false;
    }
  }

  public UsersDto getCurrentUser() {
    return this.currentUser;
  }

  public void setCurrentUser(UsersDto pCurrentUser) {
    this.currentUser = pCurrentUser;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String pPassword) {
    this.password = pPassword;
  }

  public boolean isSupervisor() {
    return this.supervisor;
  }

  public void setSupervisor(boolean pSupervisor) {
    this.supervisor = pSupervisor;
  }

}
