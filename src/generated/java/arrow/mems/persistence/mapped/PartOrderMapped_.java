//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PartOrderMapped.class)
public class PartOrderMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<PartOrderMapped, java.lang.Integer> poId;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> destOffice;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> distMgmtCode;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> distOffice;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> distPsn;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> estimateCode;
  public static volatile SingularAttribute<PartOrderMapped, java.time.LocalDate> etaDate;
  public static volatile SingularAttribute<PartOrderMapped, byte[]> imageFile;
  public static volatile SingularAttribute<PartOrderMapped, java.time.LocalDate> orderDate;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> paymentTerms;
  public static volatile SingularAttribute<PartOrderMapped, java.time.LocalDate> payDate;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> poCode;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.Integer> pCcy;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> requesterOffice;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> requesterPsn;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> serviceOffice;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.String> servicePsn;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.Integer> status;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.Double> tax;
  public static volatile SingularAttribute<PartOrderMapped, java.lang.Double> totalPrice;
  public static volatile SingularAttribute<PartOrderMapped, arrow.mems.persistence.entity.ActionLog> actionLog;
}