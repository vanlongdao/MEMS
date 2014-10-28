//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.RoleMaster;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class RoleMasterRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<RoleMaster, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT MAX(e.roleCode) FROM RoleMaster e WHERE e.roleCode like ?1")
  public abstract QueryResult<String> getMaxCodeByPrefix(String pLikePattern);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}