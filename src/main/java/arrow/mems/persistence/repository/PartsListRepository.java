//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.PartsList;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class PartsListRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<PartsList, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT p FROM PartsList p WHERE p.mdevCode = ?1 and p.isDeleted = ?2")
  public abstract QueryResult<PartsList> findPartlistByMdevcodeAndIsDeleted(String mdevCode, int isDeleted);

  @Modifying
  @Query("UPDATE PartsList p SET p.isDeleted = ?1 WHERE p.mdevCode = ?2")
  public abstract int updateIsDeleted(int isDeleted, String mdevCode);

  @Modifying
  @Query("UPDATE PartsList p SET p.isDeleted = ?1 WHERE p.partCode = ?2")
  public abstract int updateIsDeletedByPartcode(int isDeleted, String mdevCode);

  @Modifying
  @Query("UPDATE PartsList p SET p.isDeleted = ?1 WHERE p.mdevCode = ?2 and p.partCode = ?3")
  public abstract int updateIsDeletedByMdevcodeAndPartcode(int isDeleted, String mdevCode, String partCode);

  @Query("SELECT pl.mdevCode FROM PartsList pl, Users u WHERE u.userId = pl.createdBy AND pl.isDeleted = 0 AND pl.mdevCode IN ?1 AND u.officeCode = ?2")
  public abstract QueryResult<String> getAllMDevCodeOfActivePartsList(List<String> listMDevCode, String officeCode);

  @Query("SELECT pl FROM PartsList pl, Users u WHERE u.userId = pl.createdBy AND pl.isDeleted = 0 AND pl.mdevCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<PartsList> getAllPartsListByMDevCode(String pMdevCode, String pOfficeCode);

  @Query("SELECT p.partCode FROM PartsList p, Users u WHERE u.userId = p.createdBy AND p.isDeleted = 0 AND p.mdevCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<String> getAllPartCodeByMdevCode(String pMdevCode, String pOfficeCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}