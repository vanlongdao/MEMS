//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.mapped.AbstractDeletableMapped_;
import arrow.mems.persistence.mapped.AbstractEntityMapped_;
import arrow.mems.persistence.mapped.HospitalDeptMapped_;
import arrow.mems.persistence.mapped.UsersMapped_;
import arrow.mems.util.string.ArrowStrUtils;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class HospitalDeptRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<HospitalDept, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT p FROM HospitalDept p, Users u WHERE u.userId = p.createdBy AND p.isDeleted = 0 AND u.officeCode = ?1 ORDER BY p.deptCode ASC")
  public abstract List<HospitalDept> findAllActiveHospitalDeptInOneOfficce(String officeCode);

  @Query("SELECT p FROM HospitalDept p WHERE p.isDeleted = 0 AND p.createdBy IN ?1 AND p.hospCode =?2 ORDER BY p.deptCode ASC")
  public abstract List<HospitalDept> findAllActiveHospitalDeptInHospitalAndInOneOfficce(List<Integer> listUsersInOneOffice, String hospCode);

  @Query("SELECT p FROM HospitalDept p WHERE p.isDeleted = 0 and p.deptCode = ?1")
  public abstract HospitalDept findHospitalDeptByCode(String deptCode);

  @Query("SELECT p FROM HospitalDept p WHERE p.isDeleted = 0 and p.hospCode = ?1")
  public abstract QueryResult<HospitalDept> findHospitalDeptByHospCode(String hospCode);

  @Query("SELECT p FROM HospitalDept p WHERE p.isDeleted = 1 and p.deptCode = ?1")
  public abstract HospitalDept findHospitalDeptByCodeAfterDelete(String deptCode);

  @Query("SELECT p FROM HospitalDept p WHERE p.isDeleted = 0 and p.deptCode = ?1")
  public abstract QueryResult<HospitalDept> findHospitalDeptByHospDeptCode(String deptCode);

  // @Query("SELECT p FROM HospitalDept p WHERE p.isDeleted = ?1 and p.hospCode IN (SELECT h.hospCode FROM Hospital h WHERE h.isDeleted = 0 and h.officeCode IN (SELECT u.person.psnCode FROM Users u WHERE u.userId = ?2 and u.isDeleted = 0))")
  // public abstract QueryResult<HospitalDept> findHospitalDeptByUserId(int isDeleted, int userId);

  // @Query("SELECT p FROM HospitalDept p WHERE p.isDeleted = ?1 and p.hospCode IN (SELECT h.hospCode FROM HumanResource h WHERE h.isDeleted = 0 and h.psnCode IN (SELECT u.person.psnCode FROM Users u WHERE u.userId = ?2 and u.isDeleted = 0))")
  // public abstract QueryResult<HospitalDept> findHospitalDeptByUserId(int isDeleted, int userId);
  @Query("SELECT p FROM HospitalDept p WHERE p.isDeleted = ?1 and p.deptCode IN (SELECT DISTINCT h.hospDeptCode FROM HumanResource h WHERE h.isDeleted = 0 and h.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2 and u.isDeleted = 0))")
  public abstract QueryResult<HospitalDept> findHospitalDeptByUserId(int isDeleted, String officeCode);

  public List<HospitalDept> findDepartmentByOfficeCodeAndQuery(String officeCode, String query) {
    final CriteriaQuery<HospitalDept> q = this.criteriaQuery();
    final CriteriaBuilder cb = this.getCriteriaBuilder();
    final Root<HospitalDept> root = q.from(HospitalDept.class);

    final Path<Integer> createdBy = root.get(AbstractEntityMapped_.createdBy);

    final Subquery<Integer> subQuery = q.subquery(Integer.class);
    final Root<Users> u = subQuery.from(Users.class);
    subQuery.select(u.get(UsersMapped_.userId));
    Predicate subWhere = cb.equal(u.get(AbstractDeletableMapped_.isDeleted), CommonConstants.FLAG_FALSE);
    subWhere = cb.and(subWhere, cb.equal(u.get(UsersMapped_.officeCode), officeCode));
    subQuery.where(subWhere);

    Predicate where = cb.equal(root.get(AbstractDeletableMapped_.isDeleted), CommonConstants.FLAG_FALSE);
    where = cb.and(where, cb.in(createdBy).value(subQuery));

    // where for query
    final String pattern = ArrowStrUtils.likePattern(query);
    Predicate filter = cb.like(root.get(HospitalDeptMapped_.deptCode), pattern);
    filter = cb.or(filter, cb.like(cb.upper(root.get(HospitalDeptMapped_.name)), pattern));

    q.where(cb.and(where, filter));

    return this.entityManager().createQuery(q).getResultList();

  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}