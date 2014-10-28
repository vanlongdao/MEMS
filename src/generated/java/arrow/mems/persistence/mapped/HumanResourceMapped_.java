//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(HumanResourceMapped.class)
public class HumanResourceMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<HumanResourceMapped, java.lang.Integer> hrId;
  public static volatile SingularAttribute<HumanResourceMapped, java.lang.String> classCode;
  public static volatile SingularAttribute<HumanResourceMapped, java.lang.String> hospCode;
  public static volatile SingularAttribute<HumanResourceMapped, java.lang.String> hospDeptCode;
  public static volatile SingularAttribute<HumanResourceMapped, java.lang.String> psnCode;
  public static volatile SingularAttribute<HumanResourceMapped, arrow.mems.persistence.entity.HospitalDept> hospitalDept;
  public static volatile SingularAttribute<HumanResourceMapped, arrow.mems.persistence.entity.Hospital> hospital;
  public static volatile SingularAttribute<HumanResourceMapped, arrow.mems.persistence.entity.PersonClass> personClass;
  public static volatile SingularAttribute<HumanResourceMapped, arrow.mems.persistence.entity.Person> person;
}