//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MtCurrencyMapped.class)
public class MtCurrencyMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<MtCurrencyMapped, java.lang.Integer> ccyId;
  public static volatile SingularAttribute<MtCurrencyMapped, java.lang.String> ccyLocalPost;
  public static volatile SingularAttribute<MtCurrencyMapped, java.lang.String> ccyLocalPre;
  public static volatile SingularAttribute<MtCurrencyMapped, java.lang.String> ccyName;
}