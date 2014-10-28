//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ActionBill;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ActionBillRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<ActionBill, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT a FROM ActionBill a WHERE a.isDeleted = 0 AND a.actionCode = ?1")
  public abstract QueryResult<ActionBill> getActiveActionBillByActionCode(String pActionCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}