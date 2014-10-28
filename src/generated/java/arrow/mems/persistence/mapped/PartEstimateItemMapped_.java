//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PartEstimateItemMapped.class)
public class PartEstimateItemMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.Integer> peiId;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.Double> itemPrice;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.Integer> numItems;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.String> partCode;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.String> partModelNo;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.String> partName;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.String> peCode;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.Double> priceWithTax;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.Double> tax;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.Double> taxRate;
  public static volatile SingularAttribute<PartEstimateItemMapped, java.lang.Double> totPrice;
}