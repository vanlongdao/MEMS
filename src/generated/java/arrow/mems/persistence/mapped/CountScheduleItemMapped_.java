//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CountScheduleItemMapped.class)
public class CountScheduleItemMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.Integer> countSchedItemId;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> assignedPsn;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> cklistCode;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> counterBaseCode;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> countSchedCode;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> description;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> devCode;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.time.LocalDateTime> dispOffAt;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> dispOffBy;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.time.LocalDate> endDate;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> hospDeptCode;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.Integer> isDispOff;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> schedBaseCode;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.String> schedTitle;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.time.LocalDate> startDate;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.lang.Double> targetCount;
  public static volatile SingularAttribute<CountScheduleItemMapped, java.time.LocalDate> targetDate;
}