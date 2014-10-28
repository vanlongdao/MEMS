//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(HospitalMapped.class)
public class HospitalMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<HospitalMapped, java.lang.Integer> hospId;
  public static volatile SingularAttribute<HospitalMapped, java.lang.String> corpCode;
  public static volatile SingularAttribute<HospitalMapped, java.lang.String> hospCode;
  public static volatile SingularAttribute<HospitalMapped, java.lang.String> name;
  public static volatile SingularAttribute<HospitalMapped, java.lang.Integer> numBeds;
  public static volatile SingularAttribute<HospitalMapped, java.lang.String> officeCode;
  public static volatile SingularAttribute<HospitalMapped, arrow.mems.persistence.entity.Corporate> corporate;
  public static volatile SingularAttribute<HospitalMapped, arrow.mems.persistence.entity.Office> office;
}