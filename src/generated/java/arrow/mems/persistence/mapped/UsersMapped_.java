//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UsersMapped.class)
public class UsersMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<UsersMapped, java.lang.Integer> userId;
  public static volatile SingularAttribute<UsersMapped, java.lang.Integer> accountType;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> email;
  public static volatile SingularAttribute<UsersMapped, java.lang.Integer> isSupervisor;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> loginName;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> name;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> officeCode;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> password;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> psnCode;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> salt;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> sessionid;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> theme;
  public static volatile SingularAttribute<UsersMapped, java.lang.String> userAppKey;
}