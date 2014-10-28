//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Checklist;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ChecklistRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Checklist, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT cl FROM Checklist cl WHERE cl.actionCode =?1 AND cl.isDeleted = 0")
  public abstract QueryResult<Checklist> findActiveChecklistByActionCode(String actionCode);

  @Query("SELECT cl FROM Checklist cl, Users u WHERE u.userId = cl.createdBy AND cl.isDeleted = 0 AND cl.actionCode =?1 AND u.officeCode =?2")
  public abstract QueryResult<Checklist> findAllActiveChecklistByActionCodeInOwnedOffice(String actionCode, String officeCode);

  @Query("SELECT cl FROM Checklist cl WHERE cl.isDeleted = 0 AND cl.cklistLogCode = ?1")
  public abstract QueryResult<Checklist> getChecklistByCklistLogCode(String pCklistLogCode);

  @Query("SELECT cl FROM Checklist cl WHERE cl.isDeleted = 0 AND cl.actionCode = ?1")
  public abstract QueryResult<Checklist> getChecklistByActionCode(String actionCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}