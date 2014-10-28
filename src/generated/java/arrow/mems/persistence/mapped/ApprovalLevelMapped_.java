//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ApprovalLevelMapped.class)
public class ApprovalLevelMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<ApprovalLevelMapped, java.lang.Integer> levelId;
  public static volatile SingularAttribute<ApprovalLevelMapped, java.lang.Integer> configId;
  public static volatile SingularAttribute<ApprovalLevelMapped, java.lang.Integer> levelIndex;
  public static volatile SingularAttribute<ApprovalLevelMapped, arrow.mems.persistence.entity.ApprovalConfig> approvalConfig;
}