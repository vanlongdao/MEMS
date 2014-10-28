//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AddressMapped.class)
public class AddressMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<AddressMapped, java.lang.Integer> addrId;
  public static volatile SingularAttribute<AddressMapped, java.lang.String> address1;
  public static volatile SingularAttribute<AddressMapped, java.lang.String> address2;
  public static volatile SingularAttribute<AddressMapped, java.lang.String> addrCode;
  public static volatile SingularAttribute<AddressMapped, java.lang.String> city;
  public static volatile SingularAttribute<AddressMapped, java.lang.String> contactMethod;
  public static volatile SingularAttribute<AddressMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<AddressMapped, java.lang.String> province;
  public static volatile SingularAttribute<AddressMapped, java.lang.String> zipCode;
  public static volatile SingularAttribute<AddressMapped, arrow.mems.persistence.entity.MtCountry> mtCountry;
}