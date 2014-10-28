//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MdevPurchaseMapped.class)
public class MdevPurchaseMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.Integer> mdevPurchaseId;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.String> distOffice;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.String> distPsn;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.String> leadTime;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.String> notice;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.String> payCond;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.Double> price;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.time.LocalDate> priceDate;
  public static volatile SingularAttribute<MdevPurchaseMapped, byte[]> priceEvidenceFile;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.Integer> pCcy;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.String> roleCode;
  public static volatile SingularAttribute<MdevPurchaseMapped, java.lang.Double> wholesalePrice;
}