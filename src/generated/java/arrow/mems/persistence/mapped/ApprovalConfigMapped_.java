//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ApprovalConfigMapped.class)
public class ApprovalConfigMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<ApprovalConfigMapped, java.lang.Integer> configId;
  public static volatile SingularAttribute<ApprovalConfigMapped, java.lang.String> dataType;
  public static volatile SingularAttribute<ApprovalConfigMapped, java.lang.Integer> disableApprove;
}