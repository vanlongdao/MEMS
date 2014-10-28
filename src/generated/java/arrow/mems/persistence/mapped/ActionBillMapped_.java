//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ActionBillMapped.class)
public class ActionBillMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Integer> actionBillId;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.String> contractCode;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> discountByContract;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> feeDiagnosis;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> feeHotel;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> feeOther;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> feeParts;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> feeShipping;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> feeTechnical;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> feeVisit;
  public static volatile SingularAttribute<ActionBillMapped, java.time.LocalDate> payDate;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Integer> pCcy;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> tax;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> total;
  public static volatile SingularAttribute<ActionBillMapped, java.lang.Double> totalPay;
  public static volatile SingularAttribute<ActionBillMapped, arrow.mems.persistence.entity.ActionLog> actionLog;
}