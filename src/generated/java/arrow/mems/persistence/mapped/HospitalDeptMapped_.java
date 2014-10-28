//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(HospitalDeptMapped.class)
public class HospitalDeptMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<HospitalDeptMapped, java.lang.Integer> deptId;
  public static volatile SingularAttribute<HospitalDeptMapped, java.lang.Integer> checkDaysMargin;
  public static volatile SingularAttribute<HospitalDeptMapped, java.lang.String> deptCode;
  public static volatile SingularAttribute<HospitalDeptMapped, java.lang.String> hospCode;
  public static volatile SingularAttribute<HospitalDeptMapped, java.lang.String> name;
  public static volatile SingularAttribute<HospitalDeptMapped, java.lang.Integer> noticeDaysBefore;
  public static volatile SingularAttribute<HospitalDeptMapped, java.lang.Integer> numBeds;
  public static volatile SingularAttribute<HospitalDeptMapped, java.lang.Integer> pickupDays;
  public static volatile SingularAttribute<HospitalDeptMapped, arrow.mems.persistence.entity.Hospital> hospital;
}