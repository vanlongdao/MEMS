//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MdevChecklistItemMapped.class)
public class MdevChecklistItemMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<MdevChecklistItemMapped, java.lang.Integer> ckiId;
  public static volatile SingularAttribute<MdevChecklistItemMapped, java.lang.String> ckiCode;
  public static volatile SingularAttribute<MdevChecklistItemMapped, java.lang.String> cklistCode;
  public static volatile SingularAttribute<MdevChecklistItemMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<MdevChecklistItemMapped, java.lang.String> description;
}