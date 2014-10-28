//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Corporate;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class CorporateRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Corporate, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT c FROM Corporate c, Users u WHERE u.userId = c.createdBy AND c.isDeleted = 0 AND u.officeCode = ?1")
  public abstract List<Corporate> findAllActiveCorporateInOneOfficce(String officeCode);

  @Query("SELECT c FROM Corporate c WHERE c.corpCode =?1 AND c.isDeleted = 0")
  public abstract Corporate getActiveCorporateByCorporateCode(String corpCode);

  @Query("SELECT c FROM Corporate c WHERE c.corpCode =?1 AND c.isDeleted = 1")
  public abstract List<Corporate> getDisableCorporatesByCorporateCode(String corpCode);

  @Query("SELECT MAX(corpCode) FROM Corporate as c WHERE c.corpCode like ?1")
  public abstract QueryResult<String> getMaxCodeByPrefix(String prefix);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}