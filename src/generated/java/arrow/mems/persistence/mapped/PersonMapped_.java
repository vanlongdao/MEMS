//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PersonMapped.class)
public class PersonMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<PersonMapped, java.lang.Integer> psnId;
  public static volatile SingularAttribute<PersonMapped, java.lang.String> addrCode;
  public static volatile SingularAttribute<PersonMapped, java.lang.String> contactMethod;
  public static volatile SingularAttribute<PersonMapped, java.lang.String> email;
  public static volatile SingularAttribute<PersonMapped, java.lang.String> fax;
  public static volatile SingularAttribute<PersonMapped, java.lang.String> name;
  public static volatile SingularAttribute<PersonMapped, java.lang.String> officeCode;
  public static volatile SingularAttribute<PersonMapped, java.lang.String> psnCode;
  public static volatile SingularAttribute<PersonMapped, java.lang.String> tel;
  public static volatile SingularAttribute<PersonMapped, arrow.mems.persistence.entity.Address> address;
  public static volatile SingularAttribute<PersonMapped, arrow.mems.persistence.entity.Office> office;
}