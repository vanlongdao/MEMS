//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AcquisitionMasterMapped.class)
public class AcquisitionMasterMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<AcquisitionMasterMapped, java.lang.Integer> acqId;
  public static volatile SingularAttribute<AcquisitionMasterMapped, java.lang.String> acqCode;
  public static volatile SingularAttribute<AcquisitionMasterMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<AcquisitionMasterMapped, java.lang.Integer> isNeedPrice;
  public static volatile SingularAttribute<AcquisitionMasterMapped, java.lang.String> msgLocal;
  public static volatile SingularAttribute<AcquisitionMasterMapped, java.lang.String> msgPriceEntry;
}