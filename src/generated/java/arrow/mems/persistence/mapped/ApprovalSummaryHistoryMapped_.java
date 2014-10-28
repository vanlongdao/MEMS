//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ApprovalSummaryHistoryMapped.class)
public class ApprovalSummaryHistoryMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, java.lang.Integer> historyId;
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, java.lang.String> action;
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, java.lang.String> comment;
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, java.lang.String> itemCode;
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, java.lang.String> itemLabel;
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, java.lang.Integer> levelId;
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, java.lang.Integer> requestor;
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, java.time.LocalDateTime> requestAt;
  public static volatile SingularAttribute<ApprovalSummaryHistoryMapped, arrow.mems.persistence.entity.ApprovalLevel> approvalLevel;
}