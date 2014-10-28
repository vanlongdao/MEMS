//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MdevChecklistMapped.class)
public class MdevChecklistMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<MdevChecklistMapped, java.lang.Integer> cklistId;
  public static volatile SingularAttribute<MdevChecklistMapped, java.lang.String> cklistCode;
  public static volatile SingularAttribute<MdevChecklistMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<MdevChecklistMapped, java.lang.String> name;
  public static volatile SingularAttribute<MdevChecklistMapped, arrow.mems.persistence.entity.MDevice> mDevice;
}