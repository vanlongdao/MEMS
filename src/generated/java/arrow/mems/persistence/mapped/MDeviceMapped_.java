//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MDeviceMapped.class)
public class MDeviceMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<MDeviceMapped, java.lang.Integer> mdevId;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> catalog;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> catName;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> manufacturerCode;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> manufacturerPsn;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.Integer> mdevType;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> modelNo;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> name;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> notice;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> ownerCorpCode;
  public static volatile SingularAttribute<MDeviceMapped, byte[]> picture;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> specification;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> supplierCode;
  public static volatile SingularAttribute<MDeviceMapped, java.lang.String> supplierPsn;
  public static volatile SingularAttribute<MDeviceMapped, arrow.mems.persistence.entity.MtCountry> mtCountry;
}