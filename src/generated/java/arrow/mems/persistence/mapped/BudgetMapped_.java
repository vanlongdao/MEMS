//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BudgetMapped.class)
public class BudgetMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<BudgetMapped, java.lang.Integer> budgetId;
  public static volatile SingularAttribute<BudgetMapped, java.lang.Double> budget;
  public static volatile SingularAttribute<BudgetMapped, java.time.LocalDate> endDate;
  public static volatile SingularAttribute<BudgetMapped, java.lang.String> organizationCode;
  public static volatile SingularAttribute<BudgetMapped, java.time.LocalDate> startDate;
  public static volatile SingularAttribute<BudgetMapped, arrow.mems.persistence.entity.HospitalDept> hospitalDept;
  public static volatile SingularAttribute<BudgetMapped, arrow.mems.persistence.entity.Hospital> hospital;
}