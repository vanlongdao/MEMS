//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ActionTypeMaster;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ActionTypeMasterRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<ActionTypeMaster, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */


  @Query("SELECT a FROM ActionTypeMaster a WHERE upper(a.label) = ?1")
  public abstract QueryResult<ActionTypeMaster> findByLabel(String label);

  @Query("SELECT a FROM ActionTypeMaster a WHERE a.actionTypeCode = ?1")
  public abstract QueryResult<ActionTypeMaster> getActiveTypeMasterByCode(String pTypeMaintenance);

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}