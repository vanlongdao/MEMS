//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ChecklistItemMapped.class)
public class ChecklistItemMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<ChecklistItemMapped, java.lang.Integer> ckiLogId;
  public static volatile SingularAttribute<ChecklistItemMapped, java.lang.String> ckiLogCode;
  public static volatile SingularAttribute<ChecklistItemMapped, java.lang.String> cklistLogCode;
  public static volatile SingularAttribute<ChecklistItemMapped, java.lang.Integer> isOk;
  public static volatile SingularAttribute<ChecklistItemMapped, java.lang.String> referCkiCode;
  public static volatile SingularAttribute<ChecklistItemMapped, java.lang.String> resultDescription;
  public static volatile SingularAttribute<ChecklistItemMapped, java.lang.String> resultValue;
  public static volatile SingularAttribute<ChecklistItemMapped, java.lang.String> servPsn;
}