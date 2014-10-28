package arrow.framework.helper;

import java.io.Serializable;

import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.Users;

public class AuthenticationData implements Serializable {
  private final int userId;
  private Person person;
  private Users userInfo;
  private boolean isFirstLogin;

  public AuthenticationData(final int usercode, Person person, Users user) {
    this.userId = usercode;
    this.person = person;
    this.userInfo = user;
  }

  public int getUserId() {
    return this.userId;
  }

  public boolean isFirstLogin() {
    return this.isFirstLogin;
  }

  public void setFirstLogin(final boolean firstLogin) {
    this.isFirstLogin = firstLogin;
  }

  public Person getPerson() {
    return this.person;
  }

  public Users getUserInfo() {
    return this.userInfo;
  }

  public void setUserInfo(Users pUserInfo) {
    this.userInfo = pUserInfo;
  }

}
