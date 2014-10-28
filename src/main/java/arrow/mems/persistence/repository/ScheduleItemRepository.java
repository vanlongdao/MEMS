//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;
import java.util.List;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ScheduleItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ScheduleItemRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<ScheduleItem, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT s FROM ScheduleItem s WHERE s.isDeleted = ?1 and s.checkedBy <> NULL and s.startDate >= ?2 and s.startDate <= ?3 and s.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?4)")
  public abstract QueryResult<ScheduleItem> findScheItemsFilterByStartToEndDate(int pActive, LocalDate startDate, LocalDate nextDateFromNumberDate,
      String ownedOfficeCode);

  @Query("SELECT d FROM ScheduleItem d WHERE d.devCode = ?1 AND d.isDeleted = 0")
  public abstract List<ScheduleItem> findScheduleItemByDeviceCode(String deviceCode);

  @Query("SELECT s FROM ScheduleItem s, Device d WHERE s.devCode = d.devCode AND d.mdevCode = ?1 AND s.isDeleted = 0 AND d.isDeleted = 0")
  public abstract List<ScheduleItem> findScheduleItemByMDeviceCode(String mDeviceCode);

  @Query("SELECT st FROM ScheduleItem st, Schedule s WHERE st.actionCode is null AND st.devCode = ?1 AND st.isDeleted = 0 ORDER BY st.targetDate ASC")
  public abstract List<ScheduleItem> findScheduleItemByDevCode(String deviceCode);

  @Modifying
  @Query("UPDATE ScheduleItem as st SET st.isDeleted = ?1 WHERE st.schedCode = ?2")
  public abstract int updateScheduleItemByCode(int isDeleted, String scheduleCode);

  @Query("SELECT s FROM ScheduleItem s WHERE s.isDeleted = 0 AND s.schedCode = ?1")
  public abstract QueryResult<ScheduleItem> getScheduleItemBySchedCode(String schedCode);

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}