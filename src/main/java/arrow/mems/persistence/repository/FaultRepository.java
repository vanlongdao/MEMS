//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Fault;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class FaultRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Fault, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT f FROM Fault f WHERE f.actionCode =?1 AND f.isDeleted = 0")
  public abstract QueryResult<Fault> getActiveFaultByActionCode(String actionCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}