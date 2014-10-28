//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(OperationLogMapped.class)
public class OperationLogMapped_ extends AbstractEntityMapped_ {
  public static volatile SingularAttribute<OperationLogMapped, java.lang.Long> opId;
  public static volatile SingularAttribute<OperationLogMapped, java.lang.Integer> approvedBy;
  public static volatile SingularAttribute<OperationLogMapped, java.lang.String> description;
  public static volatile SingularAttribute<OperationLogMapped, java.lang.Integer> newId;
  public static volatile SingularAttribute<OperationLogMapped, java.lang.Integer> oldId;
  public static volatile SingularAttribute<OperationLogMapped, java.time.LocalDateTime> operatedAt;
  public static volatile SingularAttribute<OperationLogMapped, java.lang.Integer> operatedBy;
  public static volatile SingularAttribute<OperationLogMapped, java.lang.String> targetTable;
}