//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ScheduleMapped.class)
public class ScheduleMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<ScheduleMapped, java.lang.Integer> schedBaseId;
  public static volatile SingularAttribute<ScheduleMapped, java.lang.String> cklistCode;
  public static volatile SingularAttribute<ScheduleMapped, java.lang.String> description;
  public static volatile SingularAttribute<ScheduleMapped, java.lang.Integer> intervalMonth;
  public static volatile SingularAttribute<ScheduleMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<ScheduleMapped, java.lang.String> name;
  public static volatile SingularAttribute<ScheduleMapped, java.lang.String> schedBaseCode;
  public static volatile SingularAttribute<ScheduleMapped, arrow.mems.persistence.entity.MdevChecklist> mdevChecklist;
}