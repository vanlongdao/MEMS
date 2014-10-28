package arrow.mems.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.dto.BudgetDto;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.persistence.repository.AddressRepository;
import arrow.mems.persistence.repository.BudgetRepository;
import arrow.mems.test.DeploymentManager;

public class BudgetManagementServiceTest extends DeploymentManager {
  @Inject
  BudgetManagementService service;
  @Inject
  BudgetRepository repo;
  @Inject
  AddressRepository addressRepo;
  @Inject
  UserCredential userCredentail;
  @Inject
  AuthenticationService authenService;

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testGetListBudget() {
    final String hospCode = "12200011400003140003";
    List<Budget> listBudget = new ArrayList<Budget>();
    listBudget = this.service.getListBudget(hospCode);
    Assert.assertNotNull(listBudget);
    Assert.assertEquals(listBudget.size(), 1);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditBudget() {
    ServiceResult<BudgetDto> result = null;
    final int budgetId = 1;
    final Budget budget = this.repo.findByBudgetId(budgetId);
    budget.setSelected(true);
    Assertions.assertThat(budget.getIsDeleted()).isEqualTo(0);
    result = this.service.prepareEditBudget(budget);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditBudgetIsNotSelected() {
    ServiceResult<BudgetDto> result = null;
    final int budgetId = 1;
    final Budget budget = this.repo.findByBudgetId(budgetId);
    budget.setSelected(false);
    Assertions.assertThat(budget.getIsDeleted()).isEqualTo(0);
    result = this.service.prepareEditBudget(budget);
    Assert.assertNull(result);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testCreateBudget() {
    final Double budget = (double) 1232313313;
    final LocalDate startDate = LocalDate.now();
    final LocalDate endDate = LocalDate.now();

    final BudgetDto budgetDTO = new BudgetDto();
    budgetDTO.setBudget(budget);
    budgetDTO.setStartDate(startDate);
    budgetDTO.setEndDate(endDate);
    final ServiceResult<Budget> result = this.service.createBudget(budgetDTO, null, null);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveBudgetOnActionCreateNewBudget() {
    final Double budget = (double) 1232313313;
    final LocalDate startDate = LocalDate.now();
    final LocalDate endDate = LocalDate.now();

    final BudgetDto budgetDTO = new BudgetDto();
    budgetDTO.setBudget(budget);
    budgetDTO.setStartDate(startDate);
    budgetDTO.setEndDate(endDate);

    final ServiceResult<Budget> result = this.service.saveBudget(budgetDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveBudgetOnActionEditBudgetNotApprove() {
    final Double budgetNum = (double) 1232313313;
    final LocalDate startDate = LocalDate.now();
    final LocalDate endDate = LocalDate.now();

    final int budgetId = 1;
    final Budget budget = this.repo.findByBudgetId(budgetId);
    final BudgetDto budgetDTO = new BudgetDto();
    Assert.assertNotNull(budget);
    Assert.assertNotNull(budget.getBudgetId());
    budgetDTO.setBudget(budgetNum);
    budgetDTO.setStartDate(startDate);
    budgetDTO.setEndDate(endDate);

    BeanCopier.copy(budget, budgetDTO);
    final ServiceResult<Budget> result = this.service.saveBudget(budgetDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveBudgetOnActionEditBudgetApproved() {
    final Double budgetNum = (double) 1232313313;
    final LocalDate startDate = LocalDate.now();
    final LocalDate endDate = LocalDate.now();

    final int budgetId = 2;
    final Budget budget = this.repo.findByBudgetId(budgetId);
    final BudgetDto budgetDTO = new BudgetDto();
    Assert.assertNotNull(budget);
    Assert.assertNotNull(budget.getBudgetId());
    budgetDTO.setBudget(budgetNum);
    budgetDTO.setStartDate(startDate);
    budgetDTO.setEndDate(endDate);

    BeanCopier.copy(budget, budgetDTO);
    final ServiceResult<Budget> result = this.service.saveBudget(budgetDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testDeleteBudget() {
    final int budgetId = 1;
    Budget budget = this.repo.findByBudgetId(budgetId);
    Assertions.assertThat(budget.getIsDeleted()).isEqualTo(0);
    final ServiceResult<Budget> result = this.service.deleteBudget(budget);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
    // verify data after delete
    budget = this.repo.findByBudgetIdAfterDelete(budgetId);
    Assertions.assertThat(budget.getIsDeleted()).isEqualTo(1);
  }
}
