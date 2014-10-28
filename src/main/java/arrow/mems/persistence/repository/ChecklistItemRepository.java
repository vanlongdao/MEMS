//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ChecklistItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ChecklistItemRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<ChecklistItem, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  

  @Query("SELECT ci FROM ChecklistItem ci WHERE ci.isDeleted = 0 AND ci.referCkiCode IN ?1")
  public abstract QueryResult<ChecklistItem> getAllActiveChecklistItemContainCkiCode(List<String> pListCkiCode);

  @Query("SELECT ci FROM ChecklistItem ci WHERE ci.isDeleted = 0 AND ci.ckiLogCode = ?1")
  public abstract QueryResult<ChecklistItem> findActiveChecklistItemByCkiLogCode(String pCkiLogCode);

  @Query("SELECT c FROM ChecklistItem c WHERE c.isDeleted = 0 AND c.cklistLogCode = ?1")
  public abstract QueryResult<ChecklistItem> findActiveChecklistItemByCklistLogCode(String cklistLogCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}