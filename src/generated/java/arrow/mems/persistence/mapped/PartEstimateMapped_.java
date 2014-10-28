//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PartEstimateMapped.class)
public class PartEstimateMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.Integer> peId;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> clientSideMgmtCode;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> distOffice;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> distPsn;
  public static volatile SingularAttribute<PartEstimateMapped, java.time.LocalDate> expireDate;
  public static volatile SingularAttribute<PartEstimateMapped, byte[]> imageFile;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> notice;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> peCode;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> peType;
  public static volatile SingularAttribute<PartEstimateMapped, java.time.LocalDate> printDate;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.Integer> pCcy;
  public static volatile SingularAttribute<PartEstimateMapped, java.time.LocalDate> receiveDate;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> requesterOffice;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> requesterPsn;
  public static volatile SingularAttribute<PartEstimateMapped, java.time.LocalDate> requestDate;
  public static volatile SingularAttribute<PartEstimateMapped, java.time.LocalDate> reqDeliveryDate;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> reqDestOffice;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> serviceOffice;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.String> servicePsn;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.Double> tax;
  public static volatile SingularAttribute<PartEstimateMapped, java.lang.Double> totalPrice;
}