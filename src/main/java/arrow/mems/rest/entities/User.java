package arrow.mems.rest.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MessageCode;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
  private int userID;
  private String username;
  private String password;
  private int accounttype;
  private String officeCode;
  private String psnCode;
  private String userAppKey;
  private String token;

  public String getToken() {
    return this.token;
  }

  public void setToken(String pToken) {
    this.token = pToken;
  }

  @JsonIgnore
  public String getUserAppKey() {
    return this.userAppKey;
  }

  public void setUserAppKey(String pUserAppKey) {
    this.userAppKey = pUserAppKey;
  }

  public String getPsnCode() {
    return this.psnCode;
  }

  public void setPsnCode(String pPsnCode) {
    this.psnCode = pPsnCode;
  }

  public int getUserID() {
    return this.userID;
  }

  public void setUserID(int pUserID) {
    this.userID = pUserID;
  }

  @NotNull(message = "{" + MessageCode.MAU00002 + "}")
  @Size(min = CommonConstants.LOGIN_NAME_MIN_LENGTH, max = CommonConstants.LOGIN_NAME_MAX_LENGTH, message = "{" + MessageCode.MAU00007 + "}")
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String pUsername) {
    this.username = pUsername;
  }

  @Size(min = CommonConstants.PASSWORD_MIN_LENGTH, max = CommonConstants.PASSWORD_MAX_LENGTH, message = "{" + MessageCode.MAU00008 + "}")
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String pPassword) {
    this.password = pPassword;
  }

  public int getAccounttype() {
    return this.accounttype;
  }

  public void setAccounttype(int pAccounttype) {
    this.accounttype = pAccounttype;
  }

  public String getOfficeCode() {
    return this.officeCode;
  }

  public void setOfficeCode(String pOfficeCode) {
    this.officeCode = pOfficeCode;
  }


}
