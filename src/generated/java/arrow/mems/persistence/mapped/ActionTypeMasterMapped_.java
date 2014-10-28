//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ActionTypeMasterMapped.class)
public class ActionTypeMasterMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<ActionTypeMasterMapped, java.lang.Integer> actionTypeId;
  public static volatile SingularAttribute<ActionTypeMasterMapped, java.lang.String> actionTypeCode;
  public static volatile SingularAttribute<ActionTypeMasterMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<ActionTypeMasterMapped, java.lang.String> label;
}