//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ApprovalLevel;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ApprovalLevelRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<ApprovalLevel, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT al FROM ApprovalLevel al WHERE al.configId =?1")
  public abstract List<ApprovalLevel> getListLevelFromConfigId(int configId);

  @Query("SELECT al FROM ApprovalLevel al WHERE al.approvalConfig.dataType = ?1")
  public abstract QueryResult<ApprovalLevel> findApprovalLevelByDataType(String dataType);

  @Query("SELECT al FROM ApprovalLevel al WHERE al.approvalConfig.dataType = ?1 and al.levelIndex IN (SELECT DISTINCT MIN(apl.levelIndex) FROM ApprovalLevel apl WHERE apl.approvalConfig.dataType = ?1 GROUP BY apl.configId)")
  public abstract QueryResult<ApprovalLevel> findApprovalLevelByDataTypeAndLevelIndexMin(String dataType);

  @Query("SELECT al FROM ApprovalLevel al WHERE al.levelIndex = ?1 and al.configId = ?2")
  public abstract QueryResult<ApprovalLevel> findNextApprovalLevel(int nextLevelIndex, int configId);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}