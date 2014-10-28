//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ReplPartMapped.class)
public class ReplPartMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<ReplPartMapped, java.lang.Integer> replPartId;
  public static volatile SingularAttribute<ReplPartMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<ReplPartMapped, java.lang.String> deliveryMethod;
  public static volatile SingularAttribute<ReplPartMapped, java.lang.String> deviceCode;
  public static volatile SingularAttribute<ReplPartMapped, byte[]> imageFile;
  public static volatile SingularAttribute<ReplPartMapped, java.lang.String> memoRemoved;
  public static volatile SingularAttribute<ReplPartMapped, java.lang.String> removedDevCode;
}