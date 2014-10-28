//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Hospital;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class HospitalRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Hospital, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT MAX(hospCode) FROM Hospital as p WHERE p.hospCode like ?1")
  public abstract QueryResult<String> findMaxCodeByPrefix(String pLikePattern);

  @Query("SELECT p FROM Hospital p, Users u WHERE u.userId = p.createdBy AND p.isDeleted = 0 AND u.officeCode = ?1 ORDER BY p.hospCode")
  public abstract List<Hospital> findAllActiveHospitalInOneOffice(String officeCode);

  @Query("SELECT p FROM Hospital p WHERE p.isDeleted = 0 and p.hospId=?1")
  public abstract Hospital findHospitalById(int hopsId);

  @Query("SELECT p FROM Hospital p WHERE p.isDeleted = 0 and p.hospCode=?1")
  public abstract Hospital findByHospitalCode(String hopsCode);

  @Query("SELECT p FROM Hospital p WHERE p.isDeleted = 1 and p.hospCode=?1")
  public abstract Hospital findByHospitalCodeAfterDelete(String hopsCode);

  @Query("SELECT h FROM Hospital h WHERE h.isDeleted = 0 AND h.hospCode IN ?1 ORDER BY h.hospCode")
  public abstract QueryResult<Hospital> findHospitalFromListHospCode(List<String> pListHospCode);

  @Query("SELECT h FROM Hospital h WHERE h.isDeleted = ?1 and h.officeCode = ?2")
  public abstract QueryResult<Hospital> findHospitalByOfficeCode(int isDeleted, String officeCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}