//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class UsersMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Users> getEntityClass() {
    return arrow.mems.persistence.entity.Users.class;
  }
  
  //default constructor
  public UsersMapped() {
  }
  
  @Column(name="USER_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
  @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_user_id_seq", allocationSize=1)
  protected int userId;
  
  public int getUserId() {
    return this.userId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.userId;
  }

  //Normal columns
  @Column(name="ACCOUNT_TYPE")
  protected java.lang.Integer accountType;

  public java.lang.Integer getAccountType() {
    return this.accountType;
  }

  public void setAccountType(java.lang.Integer accountType) {
    this.accountType = accountType;
  }
  @Column(name="EMAIL")
  protected java.lang.String email;

  public java.lang.String getEmail() {
    return this.email;
  }

  public void setEmail(java.lang.String email) {
    this.email = email;
  }
  @Column(name="IS_SUPERVISOR")
  protected java.lang.Integer isSupervisor;

  public java.lang.Integer getIsSupervisor() {
    return this.isSupervisor;
  }

  public void setIsSupervisor(java.lang.Integer isSupervisor) {
    this.isSupervisor = isSupervisor;
  }
  @Column(name="LOGIN_NAME")
  protected java.lang.String loginName;

  public java.lang.String getLoginName() {
    return this.loginName;
  }

  public void setLoginName(java.lang.String loginName) {
    this.loginName = loginName;
  }
  @Column(name="NAME")
  protected java.lang.String name;

  public java.lang.String getName() {
    return this.name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }
  @Column(name="OFFICE_CODE")
  protected java.lang.String officeCode;

  public java.lang.String getOfficeCode() {
    return this.officeCode;
  }

  public void setOfficeCode(java.lang.String officeCode) {
    this.officeCode = officeCode;
  }
  @Column(name="PASSWORD")
  protected java.lang.String password;

  public java.lang.String getPassword() {
    return this.password;
  }

  public void setPassword(java.lang.String password) {
    this.password = password;
  }
  @Column(name="PSN_CODE")
  protected java.lang.String psnCode;

  public java.lang.String getPsnCode() {
    return this.psnCode;
  }

  public void setPsnCode(java.lang.String psnCode) {
    this.psnCode = psnCode;
  }
  @Column(name="SALT")
  protected java.lang.String salt;

  public java.lang.String getSalt() {
    return this.salt;
  }

  public void setSalt(java.lang.String salt) {
    this.salt = salt;
  }
  @Column(name="SESSIONID")
  protected java.lang.String sessionid;

  public java.lang.String getSessionid() {
    return this.sessionid;
  }

  public void setSessionid(java.lang.String sessionid) {
    this.sessionid = sessionid;
  }
  @Column(name="THEME")
  protected java.lang.String theme;

  public java.lang.String getTheme() {
    return this.theme;
  }

  public void setTheme(java.lang.String theme) {
    this.theme = theme;
  }
  @Column(name="USER_APP_KEY")
  protected java.lang.String userAppKey;

  public java.lang.String getUserAppKey() {
    return this.userAppKey;
  }

  public void setUserAppKey(java.lang.String userAppKey) {
    this.userAppKey = userAppKey;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}