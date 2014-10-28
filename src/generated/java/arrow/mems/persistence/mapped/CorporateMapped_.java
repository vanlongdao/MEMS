//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CorporateMapped.class)
public class CorporateMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<CorporateMapped, java.lang.Integer> corpId;
  public static volatile SingularAttribute<CorporateMapped, java.lang.String> corpCode;
  public static volatile SingularAttribute<CorporateMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<CorporateMapped, java.lang.String> name;
  public static volatile SingularAttribute<CorporateMapped, arrow.mems.persistence.entity.MtCountry> mtCountry;
}