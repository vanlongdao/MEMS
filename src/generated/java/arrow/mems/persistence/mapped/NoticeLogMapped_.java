//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(NoticeLogMapped.class)
public class NoticeLogMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<NoticeLogMapped, java.lang.Integer> noticeId;
  public static volatile SingularAttribute<NoticeLogMapped, java.time.LocalDateTime> deletedAt;
  public static volatile SingularAttribute<NoticeLogMapped, java.lang.Integer> deletedBy;
  public static volatile SingularAttribute<NoticeLogMapped, java.lang.String> description;
  public static volatile SingularAttribute<NoticeLogMapped, java.lang.String> targetDevice;
  public static volatile SingularAttribute<NoticeLogMapped, java.lang.String> targetOffice;
  public static volatile SingularAttribute<NoticeLogMapped, java.lang.String> targetSchedItem;
  public static volatile SingularAttribute<NoticeLogMapped, java.lang.String> title;
  public static volatile SingularAttribute<NoticeLogMapped, arrow.mems.persistence.entity.CountScheduleItem> countScheduleItem;
  public static volatile SingularAttribute<NoticeLogMapped, arrow.mems.persistence.entity.Device> device;
  public static volatile SingularAttribute<NoticeLogMapped, arrow.mems.persistence.entity.Office> office;
  public static volatile SingularAttribute<NoticeLogMapped, arrow.mems.persistence.entity.ScheduleItem> scheduleItem;
}