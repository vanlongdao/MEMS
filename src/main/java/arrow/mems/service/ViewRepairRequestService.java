/**
 *
 */
package arrow.mems.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.data.api.QueryResult;

import arrow.mems.persistence.entity.ActionBill;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.Fault;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.repository.ActionBillRepository;
import arrow.mems.persistence.repository.ActionLogRepository;
import arrow.mems.persistence.repository.ChecklistItemRepository;
import arrow.mems.persistence.repository.ChecklistRepository;
import arrow.mems.persistence.repository.FaultRepository;
import arrow.mems.persistence.repository.PartEstimateItemRepository;
import arrow.mems.persistence.repository.PartEstimateRepository;
import arrow.mems.persistence.repository.PartOrderItemRepository;
import arrow.mems.persistence.repository.PartOrderRepository;
import arrow.mems.persistence.repository.ReplPartRepository;

/**
 * @author tainguyen
 *
 */
public class ViewRepairRequestService extends AbstractService {

  @Inject
  ActionLogRepository actionLogRepository;
  @Inject
  FaultRepository faultRepository;
  @Inject
  ReplPartRepository replPartRepository;
  @Inject
  ChecklistRepository checklistRepository;
  @Inject
  ChecklistItemRepository checklistItemRepository;
  @Inject
  PartEstimateItemRepository estimateItemRepository;
  @Inject
  PartEstimateRepository estimateRepository;
  @Inject
  PartOrderRepository orderRepository;
  @Inject
  PartOrderItemRepository orderItemRepository;
  @Inject
  ActionBillRepository billRepository;

  public List<ActionLog> getListRepairRequest(String officeCode, Hospital hospital, Person person) {
    final StringBuilder sb = new StringBuilder();
    sb.append("SELECT a FROM ActionLog a, Users u WHERE u.userId = a.createdBy AND a.isDeleted = 0 AND u.officeCode = '" + officeCode).append("'");
    if (hospital != null) {
      sb.append(" AND a.hospCode = '" + hospital.getHospCode() + "'");
    }
    if (person != null) {
      sb.append(" AND a.contactPsn = '" + person.getPsnCode() + "'");
    }
    return super.em.createQuery(sb.toString(), ActionLog.class).getResultList();
  }

  public Fault getFaultWithActionCode(String actionCode) {
    final QueryResult<Fault> result = this.faultRepository.getActiveFaultByActionCode(actionCode);
    if (result.getAnyResult() != null)
      return result.getAnyResult();
    return new Fault();
  }

  public List<ReplPart> getListReplPart(String actionCode) {
    return this.replPartRepository.findAllActiveReplPartByActionCode(actionCode).getResultList();
  }

  public List<Checklist> getListChecklist(String actionCode) {
    return this.checklistRepository.findActiveChecklistByActionCode(actionCode).getResultList();
  }

  public List<ChecklistItem> getListChecklistItem(String cklistLogCode) {
    return this.checklistItemRepository.findActiveChecklistItemByCklistLogCode(cklistLogCode).getResultList();
  }

  public List<PartEstimateItem> getListPartEstimate(String peCode) {
    return this.estimateItemRepository.findActiveItemByPeCode(peCode).getResultList();
  }

  public List<PartEstimate> getListEstimate(String actionCode) {
    return this.estimateRepository.findActiveEstimateByActionCode(actionCode).getResultList();
  }

  public List<PartOrder> getListPartOrder(String actionCode) {
    return this.orderRepository.getActiveOrderByActionCode(actionCode).getResultList();
  }

  public List<PartOrderItem> getListPartOrderItem(String poCode) {
    return this.orderItemRepository.findActiveItemsByPoCode(poCode).getResultList();
  }

  public ActionBill getActionBillWithActionCode(String actionCode) {
    final QueryResult<ActionBill> result = this.billRepository.getActiveActionBillByActionCode(actionCode);
    if (result.getAnyResult() != null)
      return result.getAnyResult();
    return new ActionBill();
  }
}
