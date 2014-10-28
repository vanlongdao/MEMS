//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.CountRecord;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class CountRecordRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<CountRecord, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT c FROM CountRecord c WHERE c.devCode =?1 AND c.isDeleted = 0")
  public abstract QueryResult<CountRecord> getCountRecordByDeviceCode(String deviceCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}