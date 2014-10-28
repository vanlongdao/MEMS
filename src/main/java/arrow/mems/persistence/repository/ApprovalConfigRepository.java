//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ApprovalConfig;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ApprovalConfigRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<ApprovalConfig, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT ac FROM ApprovalConfig ac WHERE ac.configId =?1")
  public abstract ApprovalConfig getApprovalById(int configId);

  @Query("SELECT ac FROM ApprovalConfig ac ORDER BY ac.dataType ASC")
  public abstract List<ApprovalConfig> findAllOrderByDataTypeAsc();


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}