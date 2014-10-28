//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Person;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class PersonRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Person, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT MAX(psnCode) FROM Person as p WHERE p.psnCode like ?1")
  public abstract QueryResult<String> findMaxCodeByPrefix(String pLikePattern);

  @Query("SELECT p from Person p WHERE p.isDeleted = 0 and p.psnCode =?1")
  public abstract QueryResult<Person> findByPsnCode(String psnCode);

  @Query("SELECT p from Person p WHERE p.isDeleted = 0 AND p.psnCode =?1")
  public abstract Person findByPersonCode(String psnCode);

  @Query("SELECT p FROM Person p WHERE p.isDeleted = 0 AND p.createdBy IN ?1")
  public abstract List<Person> findAllActivePersonInOneOffice(List<Integer> listUsersInOneOffice);

  @Query("SELECT DISTINCT p FROM Person p, Users u WHERE u.userId = p.createdBy AND u.officeCode =?1 AND p.isDeleted = ?2")
  public abstract QueryResult<Person> findPersonByOwnedOfficeCodeAndIsDeleted(String ownedOfficeCode, int isDeleted);

  @Query("SELECT p from Person p WHERE p.psnCode =?1 and p.isDeleted = ?2")
  public abstract QueryResult<Person> findPersonByPsnCodeAndIsDeleted(String psnCode, int isDeleted);

  @Query("SELECT p from Person p WHERE p.isDeleted = ?1")
  public abstract QueryResult<Person> findPersonByIsDeleted(int isDeleted);

  @Query("SELECT p from Person p WHERE p.officeCode = ?1 AND p.isDeleted = ?2")
  public abstract QueryResult<Person> findPersonByOfficeCodeAndIsDeleted(String officeCode, int isDeleted);

  @Query("SELECT DISTINCT p FROM Person p, Users u WHERE u.userId = p.createdBy AND u.officeCode =?1 AND p.officeCode=?2 AND p.isDeleted = ?3")
  public abstract List<Person> findByOwnedOfficeAndOfficeCodeAndIsDeleted(String pOwnedOfficeCode, String pOfficeCode, int pActive);

  @Query("SELECT p FROM Person p, Users u WHERE u.userId = p.createdBy AND p.isDeleted = 0 AND u.officeCode = ?1 ORDER BY p.psnCode")
  public abstract List<Person> findAllActivePersonInOneOffice(String officeCode);

  @Query("SELECT p FROM Person p WHERE p.isDeleted = 0 AND p.officeCode = ?1 and p.createdBy IN ?2 ORDER BY p.psnCode")
  public abstract List<Person> findAllActivePersonInOneOffice(String officeCode, List<Integer> userInOneOffice);

  @Query("SELECT p FROM Person p WHERE p.psnCode IN ?1 AND p.isDeleted = 0")
  public abstract QueryResult<Person> findAllPersonInOneDepartment(List<String> pListPersonCode);

  @Query("SELECT p FROM Person p, Users u WHERE u.userId = p.createdBy AND p.isDeleted = 0 AND p.officeCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<Person> findAllActivePersonByOffice(String pOfficeCode, String userOffice);

  @Query("SELECT p FROM Person p WHERE p.isDeleted = 0 AND p.officeCode = ?1")
  public abstract QueryResult<Person> finAlldPersonByOfficeCode(String officeCode);

  @Query("SELECT p FROM Person p, Users u WHERE u.userId = p.createdBy AND p.isDeleted = 0 AND p.officeCode <> ?1 AND u.officeCode = ?2")
  public abstract QueryResult<Person> findAllActivePersonNotInOffice(String officeCode, String userOffice);

  @Query("SELECT p FROM Person p, HumanResource h, Users u WHERE p.psnCode = h.psnCode AND p.isDeleted = 0 AND p.isDeleted = h.isDeleted AND p.createdBy = u.userId AND u.officeCode = ?2 AND h.hospDeptCode = ?1")
  public abstract QueryResult<Person> findAllPersonsOfDepartment(String departmentCode, String officeCode);

  @Query("SELECT p FROM Person p, Users u WHERE p.createdBy = u.userId AND p.isDeleted = 0 AND u.officeCode =?2 AND p.psnCode = ?1")
  public abstract QueryResult<Person> findActivePersonInOffice(String pPsnCode, String pOfficeCode);

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}