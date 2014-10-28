//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AlertByCountMapped.class)
public class AlertByCountMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.Integer> counterBaseId;
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.String> cklistCode;
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.String> counterBaseCode;
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.String> description;
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.Integer> intervalCount;
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.Integer> limitCount;
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.String> name;
  public static volatile SingularAttribute<AlertByCountMapped, java.lang.String> unit;
  public static volatile SingularAttribute<AlertByCountMapped, arrow.mems.persistence.entity.MdevChecklist> mdevChecklist;
}