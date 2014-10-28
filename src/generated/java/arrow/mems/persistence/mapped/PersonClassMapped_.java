//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PersonClassMapped.class)
public class PersonClassMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<PersonClassMapped, java.lang.Integer> classId;
  public static volatile SingularAttribute<PersonClassMapped, java.lang.String> classCode;
  public static volatile SingularAttribute<PersonClassMapped, java.lang.String> classLocalPost;
  public static volatile SingularAttribute<PersonClassMapped, java.lang.String> classLocalPre;
  public static volatile SingularAttribute<PersonClassMapped, java.lang.Integer> countryId;
  public static volatile SingularAttribute<PersonClassMapped, arrow.mems.persistence.entity.MtCountry> mtCountry;
}