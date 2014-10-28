//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(RoleMasterMapped.class)
public class RoleMasterMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<RoleMasterMapped, java.lang.Integer> roleId;
  public static volatile SingularAttribute<RoleMasterMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<RoleMasterMapped, java.lang.String> label;
  public static volatile SingularAttribute<RoleMasterMapped, java.lang.String> roleCode;
}