//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.PartEstimate;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class PartEstimateRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<PartEstimate, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT e FROM PartEstimate e WHERE e.actionCode = ?1")
  public abstract QueryResult<PartEstimate> findAllByActionLog(String actionCode);

  @Query("SELECT p FROM PartEstimate p, Users u WHERE u.userId = p.createdBy AND p.isDeleted = 0 AND p.actionCode = ?1 AND u.officeCode = ?2 ORDER BY p.requestDate")
  public abstract QueryResult<PartEstimate> getAllActivePartEstimateUseActionCodeInOneOffice(String pActionCode, String pOfficeCode);

  @Query("SELECT p FROM PartEstimate p WHERE p.isDeleted = 0 AND p.peCode = ?1")
  public abstract QueryResult<PartEstimate> getActiveEstimateByPeCode(String pPeCode);

  @Query("SELECT p FROM PartEstimate p WHERE p.isDeleted = 0 AND p.actionCode = ?1")
  public abstract QueryResult<PartEstimate> findActiveEstimateByActionCode(String pActionCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}