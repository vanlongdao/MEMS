//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.MdevChecklist;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class MdevChecklistRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<MdevChecklist, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT cl FROM MdevChecklist cl WHERE cl.mdevCode = ?1 and cl.isDeleted = ?2")
  public abstract QueryResult<MdevChecklist> findMevChecklistByMdevcodeAndisDeleted(String mdevCode, int isDeleted);

  @Query("SELECT cl FROM MdevChecklist cl WHERE cl.cklistId = ?1")
  public abstract QueryResult<MdevChecklist> findMevChecklistByChecklistId(int checklistId);

  @Query("SELECT cl FROM MdevChecklist cl WHERE cl.cklistCode = ?1 and cl.isDeleted = ?2")
  public abstract QueryResult<MdevChecklist> findMevChecklistByChecklistCode(String cklistCode, int inDeleted);

  @Modifying
  @Query("UPDATE MdevChecklist as cl SET cl.isDeleted = ?1  WHERE cl.cklistCode = ?2")
  public abstract int updateIsDeleted(int isDeleted, String cklistCode);

  @Modifying
  @Query("UPDATE MdevChecklist as cl SET cl.isDeleted = ?1  WHERE cl.cklistCode = ?2 and cl.cklistId = ?3")
  public abstract int updateIsDeletedByCklistCodeAndId(int isDeleted, String cklistCode, int checklistId);

  @Query("SELECT m FROM MdevChecklist m, Users u WHERE u.userId = m.createdBy AND m.isDeleted = 0 AND m.mdevCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<MdevChecklist> findAllActiveMdevChecklistInOneOffice(String mdevCode, String officeCode);

  @Query("SELECT m.cklistCode FROM MdevChecklist m, Users u WHERE u.userId = m.createdBy AND m.isDeleted = 0 AND m.mdevCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<String> findAllCklistCodeByMdevCode(String pMdevCode, String officeCode);

  @Query("SELECT cl FROM MdevChecklist cl WHERE cl.mdevCode = ?1 and cl.isDeleted = 0")
  public abstract QueryResult<MdevChecklist> findActiveMevChecklistByMdevcode(String mdevCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}