//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MdevOperatorMapped.class)
public class MdevOperatorMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<MdevOperatorMapped, java.lang.Integer> mdevServiceId;
  public static volatile SingularAttribute<MdevOperatorMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<MdevOperatorMapped, java.lang.String> roleCode;
  public static volatile SingularAttribute<MdevOperatorMapped, java.lang.String> serviceOffice;
  public static volatile SingularAttribute<MdevOperatorMapped, java.lang.String> servicePsn;
}