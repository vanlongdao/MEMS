//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.HumanResource;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class HumanResourceRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<HumanResource, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT MAX(psnCode) FROM HumanResource as p WHERE p.psnCode like ?1")
  public abstract QueryResult<String> findMaxCodeByPrefix(String pLikePattern);

  @Query("SELECT p from HumanResource p WHERE p.isDeleted = 0 and p.hrId =?1")
  public abstract HumanResource findByHumanResourceId(int hrId);

  @Query("SELECT p FROM HumanResource p WHERE p.isDeleted = 0")
  public abstract List<HumanResource> getAllAvaiableHumanResource();

  @Query("SELECT h FROM HumanResource h, Users u WHERE u.userId = h.createdBy AND h.isDeleted = 0 AND h.hospCode =?1 AND u.officeCode = ?2")
  public abstract QueryResult<HumanResource> findAllHumanInOneHospital(String hospCode, String officeCode);

  @Query("SELECT h.psnCode FROM HumanResource h, Users u WHERE u.userId = h.createdBy AND h.isDeleted = 0 AND h.hospDeptCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<String> getListPersonCodeByHospDeptCode(String pHospDeptCode, String pOfficeCode);

  @Query("SELECT h.hospCode FROM HumanResource h, Users u WHERE u.userId = h.createdBy AND h.isDeleted = 0 AND u.officeCode = ?1")
  public abstract List<String> getHospCode(String pOfficeCode);

  @Query("SELECT h FROM HumanResource h WHERE h.isDeleted = 0 AND h.hospCode =?1 AND h.hospDeptCode = ?2 AND h.psnCode = ?3")
  public abstract QueryResult<HumanResource> getHumanResourcesToCheckUnique(String hospCode, String hospDeptCode, String personCode);

  @Query("SELECT h FROM HumanResource h WHERE h.isDeleted = 0 AND h.hospCode =?1 AND h.hospDeptCode = ?2 AND h.psnCode = ?3 AND h.hrId <> ?4")
  public abstract QueryResult<HumanResource> getHumanResourcesToCheckUniqueWhenEdit(String hospCode, String hospDeptCode, String personCode, int hrId);

  @Query("SELECT DISTINCT h FROM HumanResource h WHERE h.isDeleted = ?1 AND h.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2)")
  public abstract QueryResult<HumanResource> findHumanResourcesByOfficeCode(int isDeleted, String officeCode);

  @Query("SELECT h FROM HumanResource h WHERE h.isDeleted = 0 AND h.psnCode = ?1 AND h.hospCode = ?2 AND h.hospDeptCode = ?3")
  public abstract QueryResult<HumanResource> getActiveHumanOfThisActionLog(String pPsnCode, String pHospCode, String pHospDeptCode);

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}