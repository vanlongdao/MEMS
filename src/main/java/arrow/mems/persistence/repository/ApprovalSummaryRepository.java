//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.ApprovalConfig;
import arrow.mems.persistence.entity.ApprovalLevel;
import arrow.mems.persistence.entity.ApprovalLevelSupervisor;
import arrow.mems.persistence.entity.ApprovalLevel_;
import arrow.mems.persistence.entity.ApprovalSummary;
import arrow.mems.persistence.mapped.ApprovalConfigMapped_;
import arrow.mems.persistence.mapped.ApprovalLevelMapped_;
import arrow.mems.persistence.mapped.ApprovalLevelSupervisorMapped_;
import arrow.mems.persistence.mapped.ApprovalSummaryMapped_;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class ApprovalSummaryRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<ApprovalSummary, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT aps FROM ApprovalSummary aps, ApprovalLevelSupervisor als WHERE aps.approvalLevel.levelId = als.levelId and als.supervisorId = ?1")
  public abstract QueryResult<ApprovalSummary> findApprovalSummaryBySuppervisor(int suppervisorId);

  @Query("SELECT aps FROM ApprovalSummary aps WHERE aps.itemCode = ?1 and aps.approvalLevel.approvalConfig.dataType = ?2")
  public abstract QueryResult<ApprovalSummary> findApprovalSummaryByItemCodeAndItemType(String itemCode, String itemType);

  public List<ApprovalSummary> findApprovalSummaryByItemInfoAndSupervisor(String pItemCode, String pDataType, Integer pSupervisorId) {
    final CriteriaBuilder cb = this.entityManager().getCriteriaBuilder();
    final CriteriaQuery<ApprovalSummary> query = this.criteriaQuery();
    final Root<ApprovalSummary> root = query.from(ApprovalSummary.class);
    final Join<ApprovalSummary, ApprovalLevel> approvalLevel = root.join(ApprovalSummaryMapped_.approvalLevel);
    final Join<ApprovalLevel, ApprovalConfig> approvalConfig = approvalLevel.join(ApprovalLevelMapped_.approvalConfig);
    final ListJoin<ApprovalLevel, ApprovalLevelSupervisor> approvalLevelSupervisor = approvalLevel.join(ApprovalLevel_.listSupervisors);

    Predicate whereCondition = cb.equal(root.get(ApprovalSummaryMapped_.itemCode), pItemCode);
    whereCondition = cb.and(whereCondition, cb.equal(approvalConfig.get(ApprovalConfigMapped_.dataType), pDataType));
    whereCondition = cb.and(whereCondition, cb.equal(approvalLevelSupervisor.get(ApprovalLevelSupervisorMapped_.supervisorId), pSupervisorId));
    return this.entityManager().createQuery(query).getResultList();
  }

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}