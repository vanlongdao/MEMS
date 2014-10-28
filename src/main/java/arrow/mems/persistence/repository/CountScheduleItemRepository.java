//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.CountScheduleItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class CountScheduleItemRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<CountScheduleItem, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT c FROM CountScheduleItem c WHERE c.isDeleted = ?1 and c.checkedBy <> NULL and c.startDate >= ?2 and c.startDate <= ?3 and c.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?4)")
  public abstract QueryResult<CountScheduleItem> findCountScheItemsFilterByStartToEndDate(int active, LocalDate startDate,
      LocalDate nextDateFromNumberDate, String pOwnedOfficeCode);

  @Modifying
  @Query("UPDATE CountScheduleItem as c SET c.isDeleted = ?1 WHERE c.countSchedCode = ?2")
  public abstract int updateCountScheduleItemByCode(int isDeleted, String scheduleCode);

  @Query("SELECT c FROM CountScheduleItem c WHERE c.isDeleted = 0 AND c.countSchedCode = ?1")
  public abstract QueryResult<CountScheduleItem> getCountScheduleItemByCountSchedCode(String countSchedCode);

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}