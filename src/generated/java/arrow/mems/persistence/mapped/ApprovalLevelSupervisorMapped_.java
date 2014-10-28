//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ApprovalLevelSupervisorMapped.class)
public class ApprovalLevelSupervisorMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<ApprovalLevelSupervisorMapped, java.lang.Integer> levelSupervisorId;
  public static volatile SingularAttribute<ApprovalLevelSupervisorMapped, java.lang.Integer> levelId;
  public static volatile SingularAttribute<ApprovalLevelSupervisorMapped, java.lang.Integer> supervisorId;
  public static volatile SingularAttribute<ApprovalLevelSupervisorMapped, arrow.mems.persistence.entity.ApprovalLevel> approvalLevel;
}