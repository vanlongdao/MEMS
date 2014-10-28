package arrow.mems.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.persistence.qualifier.ArrowDB;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.BudgetDto;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.persistence.repository.BudgetRepository;
import arrow.mems.persistence.repository.UsersRepository;

public class BudgetManagementService extends AbstractService {
  @Inject
  BudgetRepository budgetRepo;
  @Inject
  UserCredential userCredential;
  @Inject
  UsersRepository usersRepo;
  @Inject
  UserService userService;
  @Inject
  OperationLogService logService;
  @Inject
  @ArrowDB
  EntityManager emMain;
  @Inject
  UserTransaction transaction;

  public List<Budget> getListBudget(String hospCode) {
    final ArrowQuery<Budget> query = new ArrowQuery<>(super.em);
    query.select("e").from("Budget e ");
    query.where(" e.isDeleted = 0");
    query.where(" e.organizationCode = ?", hospCode);

    query.orderBy("startDate DESC");

    return query.getResultList();
  }

  public ServiceResult<BudgetDto> prepareEditBudget(Budget budget) {
    ServiceResult<BudgetDto> result = null;
    Budget getBudget = new Budget();
    if (budget.isSelected()) {
      getBudget = this.budgetRepo.findBy(budget.getBudgetId());
      final BudgetDto budgetDTO = new BudgetDto();
      BeanCopier.copy(getBudget, budgetDTO);
      result = new ServiceResult<BudgetDto>(true, budgetDTO);
    }
    return result;
  }

  public ServiceResult<Budget> saveBudget(BudgetDto currentBudget) {
    ServiceResult<Budget> result = null;
    if (currentBudget.getBudgetId() == 0) {
      result = this.createBudget(currentBudget, null, null);
    } else {
      final Budget editBudget = this.budgetRepo.findBy(currentBudget.getBudgetId());
      if (editBudget.isPassedApprovalProcess()) {
        editBudget.setIsDeleted(CommonConstants.DELETE);
        this.budgetRepo.saveAndFlush(editBudget);
        result = this.createBudget(currentBudget, this.userCredential.getUserId(), LocalDateTime.now());

      } else {
        editBudget.setIsDeleted(1);
        editBudget.setCheckedAt(LocalDateTime.now());
        editBudget.setCheckedBy(this.userCredential.getUserId());
        this.budgetRepo.saveAndFlush(editBudget);
        result = this.createBudget(currentBudget, null, null);
      }
      // add operation log after edit Budget
      this.logService.addOperationLog(Budget.class.getName(), currentBudget.getBudgetId(), result.getData().getBudgetId(), "update approve");
    }
    return result;
  }

  public ServiceResult<Budget> createBudget(BudgetDto currentBudget, Integer checkedBy, LocalDateTime checkedDate) {
    final List<Message> message = new ArrayList<Message>();
    ServiceResult<Budget> result = null;
    final Budget newBudget = new Budget();
    BeanCopier.copy(currentBudget, newBudget);

    newBudget.setOrganizationCode(currentBudget.getOrganizationCode());
    newBudget.setIsDeleted(CommonConstants.ACTIVE);
    newBudget.setCheckedAt(checkedDate);
    newBudget.setCheckedBy(checkedBy);
    newBudget.setCreatedAt(LocalDateTime.now());
    newBudget.setCreatedBy(this.userCredential.getUserId());
    this.budgetRepo.saveAndFlush(newBudget);
    message.add(MMIMessages.MMI00006());
    result = new ServiceResult<>(true, newBudget, message);
    return result;
  }

  public ServiceResult<Budget> deleteBudget(Budget selectedBudget) {
    ServiceResult<Budget> result = null;
    final Budget deleteBudget = this.budgetRepo.findBy(selectedBudget.getBudgetId());
    deleteBudget.setIsDeleted(CommonConstants.DELETE);
    this.budgetRepo.saveAndFlush(deleteBudget);
    result = new ServiceResult<>(true, null, null);
    return result;
  }

  // comm
  public int getNextBudgetIdGenerator() throws Exception {
    final Query query = this.emMain.createNativeQuery("select nextval('budget_budget_id_seq')");
    query.getSingleResult();
    return Integer.parseInt(query.getResultList().get(0).toString());
  }
}
