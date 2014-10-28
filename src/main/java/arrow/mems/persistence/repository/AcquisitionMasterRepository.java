//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.AcquisitionMaster;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class AcquisitionMasterRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<AcquisitionMaster, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  

  @Query("SELECT m FROM AcquisitionMaster m WHERE m.acqCode =?1")
  public abstract QueryResult<AcquisitionMaster> findByAcqCode(String pAcqCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}