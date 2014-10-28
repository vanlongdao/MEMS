//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AbstractEntityMapped.class)
public class AbstractEntityMapped_ {
  public static volatile SingularAttribute<AbstractEntityMapped, java.time.LocalDateTime> createdAt;
  public static volatile SingularAttribute<AbstractEntityMapped, java.lang.Integer> createdBy;
  public static volatile SingularAttribute<AbstractEntityMapped, java.lang.Integer> objectVersion;
}