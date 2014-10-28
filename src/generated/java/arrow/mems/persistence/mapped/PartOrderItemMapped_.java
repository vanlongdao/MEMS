//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PartOrderItemMapped.class)
public class PartOrderItemMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.Integer> poiId;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.Double> itemPrice;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.Integer> numItems;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.String> partCode;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.String> partModelNo;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.String> partName;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.String> poCode;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.Double> priceWithTax;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.Double> tax;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.Double> taxRate;
  public static volatile SingularAttribute<PartOrderItemMapped, java.lang.Double> totPrice;
  public static volatile SingularAttribute<PartOrderItemMapped, arrow.mems.persistence.entity.MDevice> mDevice;
  public static volatile SingularAttribute<PartOrderItemMapped, arrow.mems.persistence.entity.PartOrder> partOrder;
}