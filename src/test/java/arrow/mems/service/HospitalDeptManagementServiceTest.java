package arrow.mems.service;

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
import arrow.mems.persistence.dto.HospitalDeptDto;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.repository.AddressRepository;
import arrow.mems.persistence.repository.CorporateRepository;
import arrow.mems.persistence.repository.HospitalDeptRepository;
import arrow.mems.persistence.repository.HospitalRepository;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.test.DeploymentManager;


public class HospitalDeptManagementServiceTest extends DeploymentManager {
  @Inject
  HospitalDeptManagementService hospDeptService;
  @Inject
  HospitalDeptRepository repo;
  @Inject
  HospitalRepository repoHosp;
  @Inject
  AddressRepository addressRepo;
  @Inject
  UserCredential userCredentail;
  @Inject
  AuthenticationService authenService;
  @Inject
  CorporateRepository corporateRepo;
  @Inject
  OfficeRepository officeRepo;

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testGetListHospitalDept() {
    List<HospitalDept> listHospitalDept = new ArrayList<HospitalDept>();
    final String hospCode = null;
    listHospitalDept = this.hospDeptService.getListHospitalDept(hospCode);
    Assert.assertNotNull(listHospitalDept);
    Assert.assertEquals(listHospitalDept.size(), 7);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditHospitalDept() {
    ServiceResult<HospitalDeptDto> result = null;
    final String hospitalDeptCode = "122000114000031440006";
    final HospitalDept hospital = this.repo.findHospitalDeptByCode(hospitalDeptCode);
    hospital.setSelected(true);
    Assertions.assertThat(hospital.getIsDeleted()).isEqualTo(0);
    result = this.hospDeptService.prepareEditHospitalDept(hospital);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditHospitalDeptIsNotSelected() {
    ServiceResult<HospitalDeptDto> result = null;
    final String hospitalDeptCode = "122000114000031440006";
    final HospitalDept hospital = this.repo.findHospitalDeptByCode(hospitalDeptCode);
    hospital.setSelected(false);
    Assertions.assertThat(hospital.getIsDeleted()).isEqualTo(0);
    result = this.hospDeptService.prepareEditHospitalDept(hospital);
    Assert.assertNull(result);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testCreateHospitalDept() {
    final String hospitalCode = "12200011400003140001";
    final Hospital hospital = this.repoHosp.findByHospitalCode(hospitalCode);
    Assert.assertNotNull(hospital);

    final HospitalDeptDto hospitalDTO = new HospitalDeptDto();
    hospitalDTO.setHospital(hospital);
    hospitalDTO.setName("hospitalName test");
    hospitalDTO.setNumBeds(100);
    final ServiceResult<HospitalDept> result = this.hospDeptService.createHospitalDept(hospitalDTO, null, null);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHospitalDeptOnActionCreateNewHospitalDept() {
    final String hospitalCode = "12200011400003140001";
    final Hospital hospital = this.repoHosp.findByHospitalCode(hospitalCode);
    Assert.assertNotNull(hospital);


    final HospitalDeptDto hospitalDTO = new HospitalDeptDto();
    hospitalDTO.setHospital(hospital);
    hospitalDTO.setName("hospitalName test");
    hospitalDTO.setNumBeds(100);
    final ServiceResult<HospitalDept> result = this.hospDeptService.saveHospitalDept(hospitalDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHospitalDeptOnActionEditHospitalDeptNotApprove() {
    final String hospitalCode = "12200011400003140001";
    final Hospital hospital = this.repoHosp.findByHospitalCode(hospitalCode);
    Assert.assertNotNull(hospital);

    final String hospitalDeptCode = "122000114000031440006";
    final HospitalDept hospitalDept = this.repo.findHospitalDeptByCode(hospitalDeptCode);
    final HospitalDeptDto hospitalDTO = new HospitalDeptDto();
    Assert.assertNotNull(hospitalDept);
    Assert.assertNotNull(hospitalDept.getDeptId());

    hospitalDept.setName("name test for edit hospital");
    hospitalDept.setHospital(hospital);
    hospitalDept.setName("hospitalName test");
    hospitalDept.setNumBeds(100);
    BeanCopier.copy(hospitalDept, hospitalDTO);
    final ServiceResult<HospitalDept> result = this.hospDeptService.saveHospitalDept(hospitalDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHospitalDeptOnActionEditHospitalDeptApproved() {
    final String hospitalCode = "12200011400003140001";
    final Hospital hospital = this.repoHosp.findByHospitalCode(hospitalCode);
    Assert.assertNotNull(hospital);

    final String hospitalDeptCode = "122000114000031440002";
    final HospitalDept hospitalDept = this.repo.findHospitalDeptByCode(hospitalDeptCode);
    final HospitalDeptDto hospitalDTO = new HospitalDeptDto();
    Assert.assertNotNull(hospitalDept);
    Assert.assertNotNull(hospitalDept.getDeptId());

    hospitalDept.setName("name test for edit hospital");
    hospitalDept.setHospital(hospital);
    hospitalDept.setName("hospitalName test");
    hospitalDept.setNumBeds(100);
    BeanCopier.copy(hospitalDept, hospitalDTO);
    final ServiceResult<HospitalDept> result = this.hospDeptService.saveHospitalDept(hospitalDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testDeleteHospitalDept() {
    final String hospitalDeptCode = "122000114000031440006";
    HospitalDept hospital = this.repo.findHospitalDeptByCode(hospitalDeptCode);
    Assertions.assertThat(hospital.getIsDeleted()).isEqualTo(0);
    final ServiceResult<HospitalDept> result = this.hospDeptService.deleteHospitalDept(hospital);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());

    // verify data after delete
    hospital = this.repo.findHospitalDeptByCodeAfterDelete(hospitalDeptCode);
    Assertions.assertThat(hospital.getIsDeleted()).isEqualTo(1);
  }
}
