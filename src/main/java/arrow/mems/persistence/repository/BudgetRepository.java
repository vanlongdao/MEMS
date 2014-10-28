//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Budget;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class BudgetRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Budget, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT p FROM Budget p WHERE p.isDeleted = 0 and p.budgetId=?1")
  public abstract Budget findByBudgetId(int budgetId);

  @Query("SELECT p FROM Budget p WHERE p.isDeleted = 1 and p.budgetId=?1")
  public abstract Budget findByBudgetIdAfterDelete(int budgetId);

  @Query("SELECT b FROM Budget b WHERE b.checkedBy != 0 and b.isDeleted = ?1 and b.startDate <= ?2 and b.endDate >= ?2 and b.organizationCode IN (SELECT a.hospDeptCode FROM ActionLog a WHERE a.isDeleted = ?1 and a.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?3))")
  public abstract QueryResult<Budget> findBudgetByDateAndHospDeptCode(int isDeleted, LocalDate currentDate, String officeCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}