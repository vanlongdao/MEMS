//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Office;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class OfficeRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Office, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT o FROM Office o, Users u WHERE u.userId = o.createdBy AND o.isDeleted = 0 AND u.officeCode = ?1 ORDER BY u.officeCode ASC")
  public abstract List<Office> findAllActiveOffice(String officeCode);

  @Query("SELECT o FROM Office o WHERE o.officeCode = ?1 AND o.isDeleted = 0")
  public abstract Office findActiveOfficeByOfficeCode(String officeCode);

  @Query("SELECT o FROM Office o WHERE o.officeCode = ?1")
  public abstract Office findOfficeByOfficeCode(String officeCode);

  @Query("SELECT o FROM Office o WHERE o.officeCode = ?1 AND o.isDeleted = 1")
  public abstract List<Office> findDisableOfficesByOfficeCode(String officeCode);

  @Query("SELECT DISTINCT o FROM Office o WHERE o.officeCode = ?1 and o.isDeleted = ?2")
  public abstract QueryResult<Office> findOfficeByOfficeCodeAndIsDeleted(String officeCode, int isDeleted);

  @Query("SELECT DISTINCT o FROM Office o WHERE o.isDeleted = ?1")
  public abstract QueryResult<Office> findOfficeManufacturerByIsDeleted(int isDeleted);

  public abstract List<Office> findByIsDeleted(int pActive);

  @Query("SELECT o FROM Office o, Users u WHERE u.userId = o.createdBy AND u.officeCode=?1 AND o.isDeleted=?2")
  public abstract QueryResult<Office> findOfficeByOwnedOfficeCodeAndIsDeleted(String ownedOfficeCode, int isDeleted);

  @Query("SELECT o FROM Office o WHERE o.officeCode = ?1 AND o.isDeleted = 0")
  public abstract QueryResult<Office> findActiveOfficeByOffCode(String officeCode);

  @Query("SELECT o FROM Office o WHERE o.officeId = ?1")
  public abstract Office findOfficeById(int pOfficeId);

  @Query("SELECT DISTINCT o FROM Device d, Office o WHERE d.isDeleted = ?1 and d.supllierOffice = o.officeCode and d.mDevice.manufacturerOffice.corpCode IN (SELECT DISTINCT u.office.corpCode FROM Users u WHERE u.officeCode = ?2)")
  public abstract QueryResult<Office> findOfficeByCorpCodeAndOfficeCode(int isDeleted, String officeCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}