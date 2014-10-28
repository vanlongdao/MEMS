//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RepairmanMapped.class)
public class RepairmanMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<RepairmanMapped, java.lang.Integer> id;
  public static volatile SingularAttribute<RepairmanMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<RepairmanMapped, java.lang.String> serviceOffice;
  public static volatile SingularAttribute<RepairmanMapped, java.lang.String> servicePsn;
}