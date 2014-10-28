//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.mems.persistence.mapped.MDeviceMapped;
import arrow.mems.persistence.mapped.MDeviceMapped_;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@StaticMetamodel(MDevice.class)
public class MDevice_ extends MDeviceMapped_ {

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  public static volatile SingularAttribute<MDeviceMapped, arrow.mems.persistence.entity.Office> supplierOffice;
  public static volatile SingularAttribute<MDeviceMapped, arrow.mems.persistence.entity.Person> supplierPerson;

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}