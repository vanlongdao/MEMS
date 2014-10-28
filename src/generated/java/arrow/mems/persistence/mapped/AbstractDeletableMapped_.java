//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AbstractDeletableMapped.class)
public class AbstractDeletableMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<AbstractDeletableMapped, java.lang.Integer> isDeleted;
}