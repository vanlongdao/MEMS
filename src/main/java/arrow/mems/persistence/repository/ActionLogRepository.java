//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;
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
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.mapped.AbstractDeletableMapped_;
import arrow.mems.persistence.mapped.AbstractEntityMapped_;
import arrow.mems.persistence.mapped.ActionLogMapped_;
import arrow.mems.persistence.mapped.UsersMapped_;
import arrow.mems.util.string.ArrowStrUtils;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ActionLogRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<ActionLog, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = 0 AND a.actionCode =?1")
  public abstract ActionLog findActionByCode(String actionCode);

  @Query("SELECT a FROM ActionLog a WHERE a.actionCode is not null AND a.devCode =?1 AND a.isDeleted = 0 ORDER BY finishDate DESC")
  public abstract List<ActionLog> findActionByDeviceCode(String deviceCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.createdBy = ?1 and a.checkedBy <> NULL and a.occurDate >= ?2 and a.occurDate <= ?3 and a.isDeleted = ?4")
  public abstract long getTotalRepairCurrentMonthOfMyTask(int userId, LocalDate startDate, LocalDate endDate, int isDeleted);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.createdBy = ?1 and a.checkedBy <> NULL and a.occurDate >= ?2 and a.occurDate <= ?3 and a.isDeleted = ?4")
  public abstract double getTotalRepairCostCurrentMonthOfMyTask(int userId, LocalDate startDate, LocalDate endDate, int isDeleted);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.createdBy = ?1 and a.checkedBy <> NULL and a.occurDate != NULL and a.contactDate != NULL and a.finishDate = NULL and a.isDeleted = ?2")
  public abstract long getTotalRepairDoingOfMyTask(int userId, int isDeleted);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.createdBy = ?1 and a.checkedBy <> NULL and a.contactDate = NULL and a.isDeleted = ?2")
  public abstract long getTotalRepairNotStartOfMyTask(int userId, int isDeleted);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?4)")
  public abstract long getTotalRepairCurrentMonthOfAllTask(LocalDate startDate, LocalDate endDate, int isDeleted, String ownedOfficeCode);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?4)")
  public abstract double getTotalRepairCostCurrentMonthOfAllTask(LocalDate firstDateOfCurrentMonth, LocalDate lastDateOfCurrentMonth, int active,
      String ownedOfficeCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.checkedBy <> NULL and a.occurDate != NULL and a.contactDate != NULL and a.finishDate = NULL and a.isDeleted = ?1 and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2)")
  public abstract long getTotalRepairDoingOfAllTask(int active, String ownedOfficeCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE checkedBy <> NULL and a.contactDate = NULL and a.isDeleted = ?1 and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2)")
  public abstract long getTotalRepairNotStartOfAllTask(int active, String ownedOfficeCode);

  @Query("SELECT a FROM ActionLog a WHERE a.createdBy = ?1 and a.checkedBy <> NULL and a.isDeleted = ?2")
  public abstract QueryResult<ActionLog> findListContactOfMyTask(int userId, int active);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.checkedBy <> NULL and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2)")
  public abstract QueryResult<ActionLog> findListContactOfAllTask(int active, String ownedOfficeCode);

  @Query("SELECT a FROM ActionLog a, Users u WHERE u.userId = a.createdBy AND a.isDeleted = 0 AND u.officeCode = ?1")
  public abstract QueryResult<ActionLog> findActiveActionLogOfOffice(String officeCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.hospDeptCode = ?1 and a.checkedBy <> NULL and a.occurDate >= ?2 and a.occurDate <= ?3 and a.isDeleted = ?4")
  public abstract long getTotalRepairCurrentMonthDepartment(String hospDeptCode, LocalDate startDate, LocalDate endDate, int isDeleted);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.hospDeptCode = ?4")
  public abstract double getTotalRepairCostCurrentMonthOfDepartment(LocalDate firstDateOfCurrentMonth, LocalDate lastDateOfCurrentMonth, int active,
      String hospDeptCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.checkedBy <> NULL and a.occurDate != NULL and a.contactDate != NULL and a.finishDate = NULL and a.isDeleted = ?1 and a.hospDeptCode = ?2")
  public abstract long getTotalRepairDoingDepartment(int active, String hospDeptCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE checkedBy <> NULL and a.contactDate = NULL and a.isDeleted = ?1 and a.hospDeptCode = ?2")
  public abstract long getTotalRepairNotStartDepartment(int active, String hospDeptCode);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.checkedBy <> NULL and a.hospDeptCode = ?2")
  public abstract QueryResult<ActionLog> findListActionLogByHospDeptCode(int active, String hospDeptCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.checkedBy <> NULL and a.occurDate >= ?1 and a.occurDate <= ?2 and a.isDeleted = ?3 and a.hospContactPsn IN (SELECT u.psnCode FROM Users u WHERE u.userId = ?4)")
  public abstract long getTotalRepairCurrentMonthOfMyTaskByHospContactPsn(LocalDate startDate, LocalDate endDate, int isDeleted, int userId);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.checkedBy <> NULL and a.occurDate != NULL and a.contactDate != NULL and a.finishDate = NULL and a.isDeleted = ?1 and a.hospContactPsn IN (SELECT u.psnCode FROM Users u WHERE u.userId = ?2)")
  public abstract long getTotalRepairDoingOfMyTaskByHospPsnCode(int isDeleted, int userId);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.checkedBy <> NULL and a.occurDate >= ?1 and a.occurDate <= ?2 and a.isDeleted = ?3 and a.hospContactPsn IN (SELECT u.psnCode FROM Users u WHERE u.userId = ?4)")
  public abstract double getTotalRepairCostCurrentMonthOfMyTaskByHospPnsCode(LocalDate startDate, LocalDate endDate, int isDeleted, int userId);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.checkedBy <> NULL and a.contactDate = NULL and a.isDeleted = ?1 and a.hospContactPsn IN (SELECT u.psnCode FROM Users u WHERE u.userId = ?2)")
  public abstract long getTotalRepairNotStartOfMyTaskByHospPnsCode(int isDeleted, int userId);

  @Query("SELECT a FROM ActionLog a WHERE a.checkedBy <> NULL and a.isDeleted = ?1 and a.hospContactPsn IN (SELECT u.psnCode FROM Users u WHERE u.userId = ?2 and u.isDeleted = 0)")
  public abstract QueryResult<ActionLog> findListContactOfMyTaskByHospPnsCode(int isDeleted, int userId);

  @Query("SELECT a FROM ActionLog a WHERE a.checkedBy <> NULL and a.isDeleted = ?1 and a.actionBill.totalPay != 0 and (a.actionBill.payDate = NULL OR a.actionBill.payDate > ?2) and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?3)")
  public abstract QueryResult<ActionLog> findActionLogByTotalPayAndPayDate(int isDeleted, LocalDate currentDate, String officeCode);

  @Query("SELECT a FROM ActionLog a WHERE a.checkedBy <> NULL and a.isDeleted = ?1 and a.actionBill.totalPay != 0 and (a.actionBill.payDate != NULL and a.actionBill.payDate < ?2) and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?3)")
  public abstract QueryResult<ActionLog> findActionLogWhenSettedTotalPayAndPayDate(int isDeleted, LocalDate currentDate, String officeCode);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.checkedBy <> NULL and a.isDeleted = ?1 and a.actionBill.totalPay != 0 and (a.actionBill.payDate != NULL and a.actionBill.payDate < ?2) and a.hospDeptCode = ?3")
  public abstract double sumTotalPayByHospDeptCode(int isDeleted, LocalDate currentDate, String hospDeptCode);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.device.isDeleted = 0 and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2)")
  public abstract QueryResult<ActionLog> findActionLogByOfficeCode(int isDeleted, String officeCode);

  @Query("SELECT a FROM ActionLog a WHERE a.actionId != ?4 and a.device.isDeleted = 0 and a.occurDate < ?3 and a.isDeleted = ?1 and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2) ORDER BY a.occurDate DESC")
  public abstract QueryResult<ActionLog> findActionLogByOfficeCodeBeforOccurDate(int isDeleted, String officeCode, LocalDate occurDate,
      int currentActionLogId);

  @Query("SELECT a FROM ActionLog a WHERE a.actionId != ?4 and a.device.isDeleted = 0 and a.occurDate > ?3 and a.isDeleted = ?1 and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2) ORDER BY a.occurDate,a.actionId DESC")
  public abstract QueryResult<ActionLog> findActionLogByOfficeCodeAfterOccurDate(int isDeleted, String officeCode, LocalDate occurDate,
      int currentActionLogId);

  public List<ActionLog> findActionLogByDevice(String officeCode, String deviceCode, String query) {
    final CriteriaBuilder cb = this.getCriteriaBuilder();
    final CriteriaQuery<ActionLog> q = cb.createQuery(ActionLog.class);
    final Root<ActionLog> root = q.from(ActionLog.class);

    final Path<Integer> createdBy = root.get(AbstractEntityMapped_.createdBy);

    final Subquery<Integer> subQuery = q.subquery(Integer.class);
    final Root<Users> u = subQuery.from(Users.class);
    subQuery.select(u.get(UsersMapped_.userId));
    Predicate subWhere = cb.equal(u.get(AbstractDeletableMapped_.isDeleted), CommonConstants.FLAG_FALSE);
    subWhere = cb.and(subWhere, cb.equal(u.get(UsersMapped_.officeCode), officeCode));
    subQuery.where(subWhere);

    Predicate where = cb.equal(root.get(AbstractDeletableMapped_.isDeleted), CommonConstants.FLAG_FALSE);
    where = cb.and(where, cb.in(createdBy).value(subQuery));
    where = cb.and(where, cb.equal(root.get(ActionLogMapped_.devCode), deviceCode));

    // where for query
    final String pattern = ArrowStrUtils.likePattern(query);

    q.orderBy(cb.asc(root.get(ActionLogMapped_.finishDate)));
    return this.entityManager().createQuery(q).getResultList();
  }

  public List<ActionLog> getAllActionLogsOfDevice(String pOfficeCode, String pDevCode, String pFilter) {
    return this.findActionLogByDevice(pOfficeCode, pDevCode, pFilter);
  }

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.hospCode = ?4")
  public abstract double getTotalRepairCostCurrentMonthOfClient(LocalDate firstDayOfCurrentYear, LocalDate lastDayOfCurrentYear, int active,
      String hospCode);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.contactPsn = ?4")
  public abstract double getTotalRepairCostCurrentMonthOfEngineer(LocalDate firstDayOfCurrentYear, LocalDate lastDayOfCurrentYear, int active,
      String personCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.hospCode = ?1 and a.checkedBy <> NULL and a.occurDate >= ?2 and a.occurDate <= ?3 and a.isDeleted = ?4")
  public abstract long getTotalRepairCurrentMonthClient(String hospCode, LocalDate firstDayOfCurrentYear, LocalDate lastDayOfCurrentYear, int active);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.contactPsn = ?1 and a.checkedBy <> NULL and a.occurDate >= ?2 and a.occurDate <= ?3 and a.isDeleted = ?4")
  public abstract long getTotalRepairCurrentMonthEngineer(String personCode, LocalDate firstDayOfCurrentYear, LocalDate lastDayOfCurrentYear,
      int active);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.checkedBy <> NULL and a.occurDate != NULL and a.contactDate != NULL and a.finishDate = NULL and a.isDeleted = ?1 and a.hospCode = ?2")
  public abstract long getTotalRepairDoingClient(int active, String hospCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.checkedBy <> NULL and a.occurDate != NULL and a.contactDate != NULL and a.finishDate = NULL and a.isDeleted = ?1 and a.contactPsn = ?2")
  public abstract long getTotalRepairDoingEngineer(int active, String personCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE checkedBy <> NULL and a.contactDate = NULL and a.isDeleted = ?1 and a.hospCode = ?2")
  public abstract long getTotalRepairNotStartClient(int active, String hospCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE checkedBy <> NULL and a.contactDate = NULL and a.isDeleted = ?1 and a.contactPsn = ?2")
  public abstract long getTotalRepairNotStartEngineer(int active, String personCode);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.checkedBy <> NULL and a.hospCode = ?2")
  public abstract QueryResult<ActionLog> findListActionLogByHospCode(int active, String hospCode);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.checkedBy <> NULL and a.contactPsn = ?2")
  public abstract QueryResult<ActionLog> findListActionLogByContactPsn(int active, String personCode);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.checkedBy <> NULL and a.device.mDevice.manufacturerOffice.corpCode IN (SELECT DISTINCT u.office.corpCode FROM Users u WHERE u.officeCode = ?2) ORDER BY a.occurDate DESC")
  public abstract QueryResult<ActionLog> findListContactOfAllTaskByCorpCode(int active, String ownedOfficeCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.device.mDevice.manufacturerOffice.corpCode IN (SELECT DISTINCT u.office.corpCode FROM Users u WHERE u.officeCode = ?4)")
  public abstract long getTotalRepairCurrentMonthOrYearOfAllTaskCorpCode(LocalDate startDate, LocalDate endDate, int isDeleted, String ownedOfficeCode);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.device.mDevice.manufacturerOffice.corpCode IN (SELECT DISTINCT u.office.corpCode FROM Users u WHERE u.officeCode = ?4)")
  public abstract double getTotalRepairCostCurrentMonthOrYearOfAllTask(LocalDate firstDateOfCurrentMonth, LocalDate lastDateOfCurrentMonth,
      int active, String ownedOfficeCode);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.device.supllierOffice = ?4 ")
  public abstract double getTotalRepairItemCostBySupplierOffice(LocalDate startDate, LocalDate endDate, int active, String supplierOffice);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.device.mDevice.modelNo = ?4 ")
  public abstract double getTotalRepairItemCostByModelNo(LocalDate startDate, LocalDate endDate, int active, String modelNo);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.device.supllierOffice = ?1 and a.checkedBy <> NULL and a.occurDate >= ?2 and a.occurDate <= ?3 and a.isDeleted = ?4")
  public abstract long getTotalRepairBySupplierOffice(String supplierOffice, LocalDate startDate, LocalDate endDate, int active);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.device.mDevice.modelNo = ?1 and a.checkedBy <> NULL and a.occurDate >= ?2 and a.occurDate <= ?3 and a.isDeleted = ?4")
  public abstract long getTotalRepairByModelNo(String modelNo, LocalDate startDate, LocalDate endDate, int active);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.checkedBy <> NULL and a.device.supllierOffice = ?2 ORDER BY a.occurDate DESC")
  public abstract QueryResult<ActionLog> findListActionLogBySupplier(int active, String supplierCode);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.checkedBy <> NULL and a.device.mDevice.modelNo = ?2 ORDER BY a.occurDate DESC")
  public abstract QueryResult<ActionLog> findListActionLogByModelNo(int active, String modelNo);


  @Query("SELECT a FROM ActionLog a, Users u WHERE a.createdBy = u.userId AND u.officeCode =?2 AND a.isDeleted = 0 AND a.actionCode =?1")
  public abstract QueryResult<ActionLog> findActionLogByActionCodeAndOfficeCode(String actionCode, String officeCode);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = 0 AND a.actionCode = ?1")
  public abstract QueryResult<ActionLog> getActiveActionLogByCode(String pActionCode);

  @Query("SELECT COUNT(a.actionId) FROM ActionLog a WHERE a.hospCode = ?1 and a.checkedBy <> NULL and a.occurDate >= ?2 and a.occurDate <= ?3 and a.isDeleted = ?4")
  public abstract long getTotalRepairByHospitalCode(String hospitalCode, LocalDate startDate, LocalDate endDate, int active);

  @Query("SELECT COALESCE(SUM(a.actionBill.totalPay),0) FROM ActionLog a WHERE a.occurDate >= ?1 and a.checkedBy <> NULL and a.occurDate <= ?2 and a.isDeleted = ?3 and a.hospCode = ?4 ")
  public abstract double getTotalRepairItemCostByHospital(LocalDate startDate, LocalDate endDate, int active, String hospitalCode);

  @Query("SELECT a FROM ActionLog a WHERE a.isDeleted = ?1 and a.checkedBy <> NULL and a.hospCode = ?2 ORDER BY a.occurDate DESC")
  public abstract QueryResult<ActionLog> findListActionLogByHospital(int active, String hospitalCode);

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}