//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ApprovalLevelSupervisor;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ApprovalLevelSupervisorRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<ApprovalLevelSupervisor, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT s FROM ApprovalLevelSupervisor s WHERE s.levelId =?1")
  public abstract List<ApprovalLevelSupervisor> getAllSupervisorByLevelId(int levelId);

  @Query("SELECT s FROM ApprovalLevelSupervisor s WHERE s.levelId =?1 AND s.supervisorId =?2")
  public abstract QueryResult<ApprovalLevelSupervisor> findSupervisorByLevelIdAndUserId(int levelId, int userId);

  @Query("SELECT s FROM ApprovalLevelSupervisor s WHERE s.levelId =?1")
  public abstract QueryResult<ApprovalLevelSupervisor> findSupervisorByLevelId(int levelId);

  public abstract List<ApprovalLevelSupervisor> findByLevelId(int pLevelId);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}