//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ChecklistMapped.class)
public class ChecklistMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<ChecklistMapped, java.lang.Integer> cklistLogId;
  public static volatile SingularAttribute<ChecklistMapped, java.lang.String> actionCode;
  public static volatile SingularAttribute<ChecklistMapped, java.time.LocalDate> checkDate;
  public static volatile SingularAttribute<ChecklistMapped, java.lang.String> cklistLogCode;
  public static volatile SingularAttribute<ChecklistMapped, java.lang.String> hospPsn;
  public static volatile SingularAttribute<ChecklistMapped, java.lang.String> measureDev1;
  public static volatile SingularAttribute<ChecklistMapped, java.lang.String> measureDev2;
  public static volatile SingularAttribute<ChecklistMapped, java.lang.String> referCklistCode;
  public static volatile SingularAttribute<ChecklistMapped, java.lang.String> serviceOffice;
  public static volatile SingularAttribute<ChecklistMapped, java.lang.String> servicePsn;
}