//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Schedule;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ScheduleRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Schedule, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT s FROM Schedule s WHERE s.mdevCode = ?1 and s.isDeleted = ?2")
  public abstract QueryResult<Schedule> findSchedulByMdevCodeAndIsDeleted(String mdevCode, int isDeleted);

  @Query("SELECT s FROM Schedule s WHERE s.schedBaseCode = ?1 and s.isDeleted = ?2")
  public abstract QueryResult<Schedule> findSchedulByScheduleCodeAndIsDeleted(String mdevCode, int isDeleted);

  @Modifying
  @Query("UPDATE Schedule s SET s.isDeleted = ?1 WHERE s.schedBaseId = ?2")
  public abstract int updateIsDeletedByScheduleId(int isDeleted, int scheduleId);

  @Query("SELECT s FROM Schedule s WHERE s.mdevCode = ?1 AND s.isDeleted = 0")
  public abstract QueryResult<Schedule> findAllScheduleByMDeviceCode(String mDeviceCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}