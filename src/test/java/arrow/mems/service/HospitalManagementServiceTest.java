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
import arrow.mems.persistence.dto.HospitalDto;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.repository.AddressRepository;
import arrow.mems.persistence.repository.CorporateRepository;
import arrow.mems.persistence.repository.HospitalRepository;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.test.DeploymentManager;

public class HospitalManagementServiceTest extends DeploymentManager {
  @Inject
  HospitalManagementService hospService;
  @Inject
  HospitalRepository repo;
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
  public void testGetListHospital() {
    List<Hospital> listHospital = new ArrayList<Hospital>();
    listHospital = this.hospService.getListHospital();
    Assert.assertNotNull(listHospital);
    Assert.assertEquals(listHospital.size(), 4);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditHospital() {
    ServiceResult<HospitalDto> result = null;
    final String hospitalCode = "12200011400003140001";
    final Hospital hospital = this.repo.findByHospitalCode(hospitalCode);
    hospital.setSelected(true);
    Assertions.assertThat(hospital.getIsDeleted()).isEqualTo(0);
    result = this.hospService.prepareEditHospital(hospital);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditHospitalIsNotSelected() {
    ServiceResult<HospitalDto> result = null;
    final String hospitalCode = "12200011400003140001";
    final Hospital hospital = this.repo.findByHospitalCode(hospitalCode);
    hospital.setSelected(false);
    Assertions.assertThat(hospital.getIsDeleted()).isEqualTo(0);
    result = this.hospService.prepareEditHospital(hospital);
    Assert.assertNull(result);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testCreateHospital() {
    final String corporateCode = "61001400002";
    Corporate corporate = new Corporate();
    corporate = this.corporateRepo.getActiveCorporateByCorporateCode(corporateCode);
    final String officeCode = "30031400003";
    Office office = new Office();
    office = this.officeRepo.findActiveOfficeByOfficeCode(officeCode);

    final HospitalDto hospitalDTO = new HospitalDto();
    hospitalDTO.setCorporate(corporate);
    hospitalDTO.setOffice(office);
    hospitalDTO.setName("hospitalName test");
    final ServiceResult<Hospital> result = this.hospService.createHospital(hospitalDTO, null, null);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHospitalOnActionCreateNewHospital() {
    final String corporateCode = "61001400002";
    Corporate corporate = new Corporate();
    corporate = this.corporateRepo.getActiveCorporateByCorporateCode(corporateCode);
    final String officeCode = "30031400003";
    Office office = new Office();
    office = this.officeRepo.findActiveOfficeByOfficeCode(officeCode);

    final HospitalDto hospitalDTO = new HospitalDto();
    hospitalDTO.setCorporate(corporate);
    hospitalDTO.setOffice(office);
    final ServiceResult<Hospital> result = this.hospService.saveHospital(hospitalDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHospitalOnActionEditHospitalNotApprove() {
    final String corporateCode = "61001400002";
    Corporate corporate = new Corporate();
    corporate = this.corporateRepo.getActiveCorporateByCorporateCode(corporateCode);
    final String officeCode = "30031400003";
    Office office = new Office();
    office = this.officeRepo.findActiveOfficeByOfficeCode(officeCode);

    final String hospitalCode = "12200011400003140001";
    final Hospital hospital = this.repo.findByHospitalCode(hospitalCode);
    final HospitalDto hospitalDTO = new HospitalDto();
    Assert.assertNotNull(hospital);
    Assert.assertNotNull(hospital.getHospId());

    hospital.setName("name test for edit hospital");
    hospital.setOffice(office);
    hospital.setCorporate(corporate);
    BeanCopier.copy(hospital, hospitalDTO);
    final ServiceResult<Hospital> result = this.hospService.saveHospital(hospitalDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHospitalOnActionEditHospitalApproved() {
    final String corporateCode = "61001400002";
    Corporate corporate = new Corporate();
    corporate = this.corporateRepo.getActiveCorporateByCorporateCode(corporateCode);
    final String officeCode = "30031400003";
    Office office = new Office();
    office = this.officeRepo.findActiveOfficeByOfficeCode(officeCode);

    final String hospitalCode = "12200011400003140007";
    final Hospital hospital = this.repo.findByHospitalCode(hospitalCode);
    final HospitalDto hospitalDTO = new HospitalDto();
    Assert.assertNotNull(hospital);
    Assert.assertNotNull(hospital.getHospId());

    hospital.setName("name test for edit hospital");
    hospital.setOffice(office);
    hospital.setCorporate(corporate);
    BeanCopier.copy(hospital, hospitalDTO);
    final ServiceResult<Hospital> result = this.hospService.saveHospital(hospitalDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testDeleteHospital() {
    final String hospitalCode = "12200011400003140001";
    Hospital hospital = this.repo.findByHospitalCode(hospitalCode);
    Assertions.assertThat(hospital.getIsDeleted()).isEqualTo(0);
    final ServiceResult<Hospital> result = this.hospService.deleteHospital(hospital);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());

    // verify data after delete
    hospital = this.repo.findByHospitalCodeAfterDelete(hospitalCode);
    Assertions.assertThat(hospital.getIsDeleted()).isEqualTo(1);
  }

}
