//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(FaultMapped.class)
public class FaultMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<FaultMapped, java.lang.Integer> faultId;
  public static volatile SingularAttribute<FaultMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<FaultMapped, java.lang.String> cause;
  public static volatile SingularAttribute<FaultMapped, java.lang.String> description;
  public static volatile SingularAttribute<FaultMapped, byte[]> imageFile;
  public static volatile SingularAttribute<FaultMapped, java.lang.String> prevention;
  public static volatile SingularAttribute<FaultMapped, java.lang.String> repair;
}