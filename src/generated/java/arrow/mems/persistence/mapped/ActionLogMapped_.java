//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ActionLogMapped.class)
public class ActionLogMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<ActionLogMapped, java.lang.Integer> actionId;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<ActionLogMapped, java.time.LocalDate> actionDate;
  public static volatile SingularAttribute<ActionLogMapped, byte[]> actionStartConfirmImg;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> actionType;
  public static volatile SingularAttribute<ActionLogMapped, byte[]> checkStartConfirmImg;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> clientSideMgmtCode;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> complaint;
  public static volatile SingularAttribute<ActionLogMapped, java.time.LocalDate> contactDate;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> contactPsn;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> deliveryMethod;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> devCode;
  public static volatile SingularAttribute<ActionLogMapped, java.time.LocalDate> finishDate;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> hospCode;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> hospContactPsn;
  public static volatile SingularAttribute<ActionLogMapped, java.lang.String> hospDeptCode;
  public static volatile SingularAttribute<ActionLogMapped, byte[]> installConfirmImg;
  public static volatile SingularAttribute<ActionLogMapped, java.time.LocalDate> occurDate;
  public static volatile SingularAttribute<ActionLogMapped, arrow.mems.persistence.entity.HospitalDept> hospitalDept;
  public static volatile SingularAttribute<ActionLogMapped, arrow.mems.persistence.entity.Hospital> hospital;
  public static volatile SingularAttribute<ActionLogMapped, arrow.mems.persistence.entity.Person> person;
}