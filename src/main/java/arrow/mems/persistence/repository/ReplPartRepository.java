//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ReplPart;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ReplPartRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<ReplPart, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT rp FROM ReplPart rp, Users u WHERE u.userId = rp.createdBy AND rp.isDeleted = 0 AND rp.actionCode =?1 AND u.officeCode = ?2")
  public abstract QueryResult<ReplPart> findAllActiveReplacedPartsInOneOffice(String actionCode, String officeCode);

  @Query("SELECT r FROM ReplPart r WHERE r.isDeleted = 0 AND r.actionCode = ?1 AND r.deviceCode = ?2 AND r.removedDevCode = ?3")
  public abstract QueryResult<ReplPart> findActivePartByActionCodeAndNewDeviceAndOldDevice(String pActionCode, String pDevCode, String pRemoveDevCode);

  @Query("SELECT rp FROM ReplPart rp WHERE rp.isDeleted = 0 AND rp.deviceCode =?1 AND rp.actionCode =?2")
  public abstract QueryResult<ReplPart> findReplPartByDeviceCode(String deviceCode, String actionCode);

  @Query("SELECT r FROM ReplPart r WHERE r.id = ?1")
  public abstract QueryResult<ReplPart> findReplPartById(int id);

  @Query("SELECT r FROM ReplPart r WHERE r.isDeleted = 0 AND r.actionCode = ?1 AND r.deviceCode = ?2")
  public abstract ReplPart findReplPartByActionCodeAndDeviceCode(String actionCode, String deviceCode);

  @Query("SELECT r FROM ReplPart r WHERE r.isDeleted = 0 AND r.actionCode = ?1")
  public abstract QueryResult<ReplPart> findAllActiveReplPartByActionCode(String actionCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}