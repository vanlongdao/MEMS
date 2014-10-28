//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.MdevChecklistItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class MdevChecklistItemRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<MdevChecklistItem, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT cli FROM MdevChecklistItem cli WHERE cli.cklistId = ?1 and cli.isDeleted = ?2")
  public abstract QueryResult<MdevChecklistItem> findMdevChecklistItemByChecklistIdAndIsDeleted(int checklistId, int isDeleted);

  @Query("SELECT cli FROM MdevChecklistItem cli WHERE cli.cklistCode = ?1 and cli.isDeleted = ?2")
  public abstract QueryResult<MdevChecklistItem> findMdevChecklistItemByCklistCodeAndIsDeleted(String cklistCode, int pIsDeleted);

  @Query("SELECT cli FROM MdevChecklistItem cli WHERE cli.ckiId = ?1")
  public abstract QueryResult<MdevChecklistItem> findMdevChecklistItemByChecklistId(int checklistId);

  @Modifying
  @Query("UPDATE MdevChecklistItem as cli SET cli.isDeleted = ?1  WHERE cli.cklistCode = ?2")
  public abstract int updateIsDeletedByCklistCode(int isDeleted, String cklistCode);

  @Modifying
  @Query("UPDATE MdevChecklistItem as cli SET cli.isDeleted = ?1  WHERE cli.ckiCode = ?2")
  public abstract int updateIsDeletedByCkiCode(int isDeleted, String ckiCode);

  @Query("SELECT c FROM MdevChecklistItem c, Users u WHERE u.userId = c.createdBy AND c.isDeleted = 0 AND c.cklistCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<MdevChecklistItem> getAllActiveMdevChecklistItemContainChecklistCode(String cklistCode, String officeCode);

  @Query("SELECT c.ckiCode FROM MdevChecklistItem c, Users u WHERE u.userId = c.createdBy AND c.isDeleted = 0 AND c.cklistCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<String> getAllActiveMdevCkiCodeContainChecklistCode(String cklistCode, String officeCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}