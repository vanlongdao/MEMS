//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.AlertByCount;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class AlertByCountRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<AlertByCount, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT a FROM AlertByCount a WHERE a.mdevCode = ?1 and a.isDeleted = ?2")
  public abstract QueryResult<AlertByCount> findAlertByMdevCodeAndIsDeleted(String mdevCode, int isDeleted);

  @Query("SELECT a FROM AlertByCount a WHERE a.counterBaseCode = ?1 and a.isDeleted = ?2")
  public abstract QueryResult<AlertByCount> findSchedulAlertByBaseCodeAndIsDeleted(String counterBaseCode, int isDeleted);

  @Modifying
  @Query("UPDATE AlertByCount a SET a.isDeleted = ?1 WHERE a.counterBaseId = ?2")
  public abstract int updateIsDeletedByCounterBaseId(int isDeleted, int counterBaseId);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}