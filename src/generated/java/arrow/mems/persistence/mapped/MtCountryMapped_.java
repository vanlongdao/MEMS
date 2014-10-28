//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MtCountryMapped.class)
public class MtCountryMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<MtCountryMapped, java.lang.Integer> countryId;
  public static volatile SingularAttribute<MtCountryMapped, java.lang.String> name;
  public static volatile SingularAttribute<MtCountryMapped, java.lang.String> nameEn;
}