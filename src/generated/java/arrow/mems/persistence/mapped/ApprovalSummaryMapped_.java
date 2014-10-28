//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ApprovalSummaryMapped.class)
public class ApprovalSummaryMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<ApprovalSummaryMapped, java.lang.Integer> approvalId;
  public static volatile SingularAttribute<ApprovalSummaryMapped, java.lang.String> itemCode;
  public static volatile SingularAttribute<ApprovalSummaryMapped, java.lang.String> itemLabel;
  public static volatile SingularAttribute<ApprovalSummaryMapped, java.lang.Integer> levelId;
  public static volatile SingularAttribute<ApprovalSummaryMapped, java.lang.Integer> requestor;
  public static volatile SingularAttribute<ApprovalSummaryMapped, java.time.LocalDateTime> requestAt;
  public static volatile SingularAttribute<ApprovalSummaryMapped, arrow.mems.persistence.entity.ApprovalLevel> approvalLevel;
}