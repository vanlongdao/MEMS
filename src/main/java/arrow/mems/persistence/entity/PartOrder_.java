//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.mems.persistence.mapped.PartOrderMapped;
import arrow.mems.persistence.mapped.PartOrderMapped_;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@StaticMetamodel(PartOrder.class)
public class PartOrder_ extends PartOrderMapped_ {

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  public static volatile ListAttribute<PartOrderMapped, arrow.mems.persistence.entity.PartOrderItem> partOrderItems;

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}