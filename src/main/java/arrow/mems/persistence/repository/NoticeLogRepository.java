//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.NoticeLog;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class NoticeLogRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<NoticeLog, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT n FROM NoticeLog n WHERE n.isDeleted = ?1 and n.checkedBy <> NULL and n.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2 and u.isDeleted = 0)")
  public abstract QueryResult<NoticeLog> findNoticeLogByOfficeCode(int active, String ownedOfficeCode);

  @Modifying
  @Query("UPDATE NoticeLog as n SET n.isDeleted = ?1 WHERE n.noticeId = ?2")
  public abstract int updateNoticeLogById(int active, int noticeLogId);
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}