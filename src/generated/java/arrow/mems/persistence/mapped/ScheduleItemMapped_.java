//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ScheduleItemMapped.class)
public class ScheduleItemMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.Integer> schedItemId;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> assignedPsn;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> cklistCode;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> description;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> devCode;
  public static volatile SingularAttribute<ScheduleItemMapped, java.time.LocalDateTime> dispOffAt;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> dispOffBy;
  public static volatile SingularAttribute<ScheduleItemMapped, java.time.LocalDate> endDate;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> hospDeptCode;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.Integer> isDispOff;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> schedBaseCode;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> schedCode;
  public static volatile SingularAttribute<ScheduleItemMapped, java.lang.String> schedTitle;
  public static volatile SingularAttribute<ScheduleItemMapped, java.time.LocalDate> startDate;
  public static volatile SingularAttribute<ScheduleItemMapped, java.time.LocalDate> targetDate;
}