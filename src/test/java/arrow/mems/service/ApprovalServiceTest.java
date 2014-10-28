package arrow.mems.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.data.api.QueryResult;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.mems.persistence.entity.ApprovalConfig;
import arrow.mems.persistence.entity.ApprovalLevel;
import arrow.mems.persistence.entity.ApprovalLevelSupervisor;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.ApprovalConfigRepository;
import arrow.mems.persistence.repository.ApprovalLevelSupervisorRepository;
import arrow.mems.test.DeploymentManager;


public class ApprovalServiceTest extends DeploymentManager {

  @Inject
  ApprovalService approvalService;
  @Inject
  ApprovalConfigRepository approvalRepository;
  @Inject
  ApprovalLevelSupervisorRepository supervisorRepository;

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testListAllApprovalConfigs() throws Exception {
    final List<ApprovalConfig> approvalConfigs = this.approvalService.listAllApprovalConfig();
    Assert.assertNotNull(approvalConfigs);
    Assert.assertEquals(approvalConfigs.size(), 26);
  }

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testListAllApprovalLevelss() throws Exception {
    final List<ApprovalLevel> levels = this.approvalService.listAllApprovalLevels(1);
    Assert.assertNotNull(levels);
    Assert.assertEquals(levels.size(), 4);
  }

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testAddNewLevel() throws Exception {
    final ApprovalConfig approval = this.approvalRepository.getApprovalById(1);
    final ServiceResult<ApprovalLevel> result = this.approvalService.addNewLevel(approval, 5);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testAddNewSupervisorFailed() throws Exception {
    final ServiceResult<ApprovalLevelSupervisor> result = this.approvalService.addNewSupervisor(1, 3);
    Assert.assertFalse(result.isSuccess());
  }

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testAddNewSupervisorSuccess() throws Exception {
    final ServiceResult<ApprovalLevelSupervisor> result = this.approvalService.addNewSupervisor(5, 3);
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetAllSupervisorByLevelId() throws Exception {
    final List<ApprovalLevelSupervisor> supervisors = this.approvalService.getAllSupervisorByLevelId(2);
    Assert.assertNotNull(supervisors);
    Assert.assertEquals(supervisors.size(), 2);
  }

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testFindActiveUserById() throws Exception {
    final Users user = this.approvalService.findActiveUserById(3);
    Assert.assertNotNull(user);
    Assert.assertEquals(user.getUserId(), 3);
  }

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteSupervisor() throws Exception {
    final QueryResult<ApprovalLevelSupervisor> result = this.supervisorRepository.findSupervisorByLevelIdAndUserId(1, 3);
    final ApprovalLevelSupervisor supervisor = result.getAnyResult();
    final ServiceResult<ApprovalLevelSupervisor> deleteResult = this.approvalService.deleteSupervisor(supervisor);
    Assert.assertTrue(deleteResult.isSuccess());
    Assert.assertEquals(supervisor.getSupervisorId(), deleteResult.getData().getSupervisorId());
  }

  @Test(enabled = true, singleThreaded = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testChangeStatusOfApproval() throws Exception {
    ApprovalConfig approval = this.approvalRepository.getApprovalById(1);
    approval.setDisableApprove(1);
    this.approvalService.changeStatusOfApproval(approval);
    approval = this.approvalRepository.getApprovalById(1);
    Assert.assertNotNull(approval);
    Assert.assertEquals(approval.getDisableApprove(), 1);
  }

}
