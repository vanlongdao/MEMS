//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PartsListMapped.class)
public class PartsListMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<PartsListMapped, java.lang.Integer> partsListId;
  public static volatile SingularAttribute<PartsListMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<PartsListMapped, java.lang.String> partCode;
}