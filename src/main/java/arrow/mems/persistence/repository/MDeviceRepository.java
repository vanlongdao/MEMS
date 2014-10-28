//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.MDevice;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class MDeviceRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<MDevice, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT m FROM PartsList p , MDevice m WHERE p.partCode = m.mdevCode and p.mdevCode = ?1 and p.isDeleted = ?2")
  public abstract QueryResult<MDevice> findDevicesFromPartListByIsDeleted(String deviceCode, int isDeleted);

  @Query("SELECT m FROM PartsList p , MDevice m WHERE p.partCode = m.mdevCode and p.mdevCode = ?1 and m.isDeleted = ?2 and p.isDeleted = ?3")
  public abstract QueryResult<MDevice> findInfoDevicesByCodeAndIsDeleted(String mdevCode, int isDeletedDevice, int isDeletedPartlist);

  @Query("SELECT m FROM MDevice m WHERE m.isDeleted = ?1")
  public abstract QueryResult<MDevice> findInfoDevicesByIsDeleted(int isDeleted);

  @Query("SELECT m FROM MDevice m, Users u WHERE u.userId = m.createdBy AND u.officeCode=?1 AND m.isDeleted=?2")
  public abstract QueryResult<MDevice> findMDeviceByOwnedOfficeCodeAndIsDeleted(String ownedOfficeCode, int isDeleted);

  @Query("SELECT m FROM MDevice m WHERE m.mdevCode = ?1 AND m.isDeleted = 0")
  public abstract MDevice findActiveMdeviceBymdevCode(String officeCode);

  @Query("SELECT m FROM MDevice m WHERE m.mdevCode = ?1 AND m.isDeleted = ?2")
  public abstract QueryResult<MDevice> findDevicesByMdevcodeAndIsDeleted(String mdevCode, int isDeleted);

  @Modifying
  @Query("UPDATE MDevice as m SET m.isDeleted = ?1  WHERE m.mdevCode = ?2")
  public abstract int updateIsDeleted(int isDeleted, String mdevCode);

  @Query("SELECT m FROM MDevice m, Users u WHERE u.userId = m.createdBy AND m.isDeleted = 0 AND u.officeCode = ?1 ORDER BY m.name")
  public abstract List<MDevice> findActiveMdeviceInOffice(String officeCode);

  @Query("SELECT m FROM MDevice m, PartsList p WHERE p.partCode = m.mdevCode AND p.isDeleted = m.isDeleted AND p.mdevCode = ?1 and p.isDeleted = ?2")
  public abstract QueryResult<MDevice> findPartsOfMasterDevice(String mdevCode, int isDeleted);

  @Query("SELECT d FROM MDevice d WHERE d.isDeleted = ?1 and d.manufacturerOffice.corpCode IN (SELECT DISTINCT u.office.corpCode FROM Users u WHERE u.officeCode = ?2 and u.isDeleted = 0)")
  public abstract QueryResult<MDevice> findMdevicesByCorpCodeAndOfficeCode(int active, String officeCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}