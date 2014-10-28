//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AbstractApprovalEntityMapped.class)
public class AbstractApprovalEntityMapped_ extends AbstractDeletableMapped_ {
  public static volatile SingularAttribute<AbstractApprovalEntityMapped, java.time.LocalDateTime> checkedAt;
  public static volatile SingularAttribute<AbstractApprovalEntityMapped, java.lang.Integer> checkedBy;
}