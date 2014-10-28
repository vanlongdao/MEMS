package arrow.mems.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.persistence.ArrowQuery;
import arrow.mems.persistence.entity.ActionBill;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.Fault;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.repository.ActionBillRepository;
import arrow.mems.persistence.repository.ActionLogRepository;
import arrow.mems.persistence.repository.ActionTypeMasterRepository;
import arrow.mems.persistence.repository.ChecklistItemRepository;
import arrow.mems.persistence.repository.ChecklistRepository;
import arrow.mems.persistence.repository.FaultRepository;
import arrow.mems.persistence.repository.PartEstimateItemRepository;
import arrow.mems.persistence.repository.PartEstimateRepository;
import arrow.mems.persistence.repository.PartOrderItemRepository;
import arrow.mems.persistence.repository.PartOrderRepository;
import arrow.mems.persistence.repository.ReplPartRepository;
import arrow.mems.persistence.repository.UsersRepository;

public class RepairHistoryService extends AbstractService {

  @Inject
  ActionLogRepository actionLogRepo;
  @Inject
  private UsersRepository usersRepository;
  @Inject
  ReplPartRepository replPartRepo;
  @Inject
  ChecklistItemRepository checklistItemRepo;
  @Inject
  PartEstimateItemRepository partEstimateItemRepo;
  @Inject
  PartOrderItemRepository partOrderItemRepo;
  @Inject
  ChecklistRepository checklistRepo;
  @Inject
  PartEstimateRepository partEstimatRepo;
  @Inject
  PartOrderRepository partOrderRepo;
  @Inject
  FaultRepository faultRepo;
  @Inject
  ActionBillRepository actionBillRepo;
  @Inject
  ActionTypeMasterRepository actionTypeMasterRepo;

  public List<ActionLog> getListSearchRepairHistory(String deviceCode, String deviceName, String officeCode, List<String> actionType) {
    final List<Integer> listCreatedByUserId = this.usersRepository.findUserInOneOffice(officeCode);
    final ArrowQuery<ActionLog> query = new ArrowQuery<ActionLog>(super.em);
    query.select("m").from("ActionLog m ");
    query.where(" m.isDeleted = 0");
    query.where(" m.createdBy in (?)", listCreatedByUserId);
    if (StringUtils.isNotEmpty(deviceCode)) {
      query.where(" m.device.devCode = ?", deviceCode);
    }
    if (StringUtils.isNotEmpty(deviceName)) {
      query.where(" m.device.mDevice.name = ?", deviceName);
    }
    if (!actionType.isEmpty()) {
      query.where(" m.actionType IN (?)", actionType);
    }
    query.orderBy("m.createdAt");
    return query.getResultList();
  }

  public List<Checklist> getListChecklist(String actionCode) {
    return this.checklistRepo.findActiveChecklistByActionCode(actionCode).getResultList();
  }

  public String getActionTypeByLabel(String label) {
    if (this.actionTypeMasterRepo.findByLabel(label).getAnyResult() != null)
      return this.actionTypeMasterRepo.findByLabel(label).getAnyResult().getActionTypeCode();
    return null;
  }

  public ActionBill findActionBillByActionLogCode(String actionCode) {
    return this.actionBillRepo.getActiveActionBillByActionCode(actionCode).getAnyResult();
  }

  public Fault findFaultByActionLogCode(String actionCode) {
    return this.faultRepo.getActiveFaultByActionCode(actionCode).getAnyResult();
  }

  public List<ReplPart> findReplPartByDeviceCode(String deviceCode, String actionCode) {
    return this.replPartRepo.findReplPartByDeviceCode(deviceCode, actionCode).getResultList();
  }

  public List<ChecklistItem> findChecklistItemByChkListCode(String chklistCode) {
    return this.checklistItemRepo.findActiveChecklistItemByCkiLogCode(chklistCode).getResultList();
  }

  public List<PartEstimateItem> findPartEstimateItem(String peCode) {
    return this.partEstimateItemRepo.findActiveItemByPeCode(peCode).getResultList();
  }

  public List<PartOrderItem> findPartOrderItem(String peCode) {
    return this.partOrderItemRepo.findActiveItemsByPoCode(peCode).getResultList();
  }

  public List<ChecklistItem> getListChecklistItem(String cklistLogCode) {
    return this.checklistItemRepo.findActiveChecklistItemByCklistLogCode(cklistLogCode).getResultList();
  }

  public List<PartEstimateItem> getListPartEstimate(String peCode) {
    return this.partEstimateItemRepo.findActiveItemByPeCode(peCode).getResultList();
  }

  public List<PartEstimate> getListEstimate(String actionCode) {
    return this.partEstimatRepo.findActiveEstimateByActionCode(actionCode).getResultList();
  }

  public List<PartOrder> getListPartOrder(String actionCode) {
    return this.partOrderRepo.getActiveOrderByActionCode(actionCode).getResultList();
  }

  public List<PartOrderItem> getListPartOrderItem(String poCode) {
    return this.partOrderItemRepo.findActiveItemsByPoCode(poCode).getResultList();
  }

  public List<ActionLog> getListActionLogHistoryOfDevice(String deviceCode) {
    return this.actionLogRepo.findActionByDeviceCode(deviceCode);
  }
}
