//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.OperationLog;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class OperationLogRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<OperationLog, java.lang.Long> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT l FROM OperationLog l WHERE l.opId =?1")
  public abstract OperationLog findLogById(long logId);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}