package arrow.mems.service;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.dto.HumanResourceDto;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.PersonClass;
import arrow.mems.persistence.repository.HospitalDeptRepository;
import arrow.mems.persistence.repository.HospitalRepository;
import arrow.mems.persistence.repository.HumanResourceRepository;
import arrow.mems.persistence.repository.PersonClassRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.test.DeploymentManager;


public class HumanResourceManagementServiceTest extends DeploymentManager {
  @Inject
  HumanResourceManagementService humanService;
  @Inject
  HumanResourceRepository repo;
  @Inject
  HospitalRepository hospRepo;
  @Inject
  HospitalDeptRepository hospDeptRepo;
  @Inject
  UserCredential userCredentail;
  @Inject
  AuthenticationService authenService;
  @Inject
  PersonRepository personRepo;
  @Inject
  PersonClassRepository personClassRepo;
  @Inject
  HumanResourceRepository humanRepo;

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testGetListHumanResource() {
    final String officeCode = "30061400001";
    final List<HumanResource> listHumanResource = this.humanService.getListHumanResource(officeCode);
    Assert.assertNotNull(listHumanResource);
    Assert.assertEquals(listHumanResource.size(), 6);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditHumanResource() {
    ServiceResult<HumanResourceDto> result = null;
    final int humanResourceId = 2;
    final HumanResource humanResource = this.repo.findByHumanResourceId(humanResourceId);
    humanResource.setSelected(true);
    Assertions.assertThat(humanResource.getIsDeleted()).isEqualTo(0);
    result = this.humanService.prepareEditHumanResource(humanResource);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditHumanResourceNotSelected() {
    ServiceResult<HumanResourceDto> result = null;
    final int humanResourceId = 2;
    final HumanResource humanResource = this.repo.findByHumanResourceId(humanResourceId);
    humanResource.setSelected(false);
    Assertions.assertThat(humanResource.getIsDeleted()).isEqualTo(0);
    result = this.humanService.prepareEditHumanResource(humanResource);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testCreateHumanResource() {

    final String hospitalCode = "12200011400003140001";
    Hospital hospital = new Hospital();
    hospital = this.hospRepo.findByHospitalCode(hospitalCode);
    Assert.assertNotNull(hospital);

    final String hospitalDeptCode = "122000114000031440006";
    final HospitalDept hospitalDept = this.hospDeptRepo.findHospitalDeptByCode(hospitalDeptCode);
    Assert.assertNotNull(hospitalDept);
    Assert.assertNotNull(hospitalDept.getDeptId());

    final String personCode = "200011400002";
    Person person = new Person();
    person = this.personRepo.findByPersonCode(personCode);
    Assert.assertNotNull(person);

    final String personClassId = "200011400002";
    PersonClass personClass = new PersonClass();
    personClass = this.personClassRepo.findPersonClassByClassCode(personClassId);
    Assert.assertNotNull(personClass);

    final HumanResourceDto humanResourceDTO = new HumanResourceDto();
    humanResourceDTO.setHospital(hospital);
    humanResourceDTO.setHospitalDept(hospitalDept);
    humanResourceDTO.setPerson(person);
    humanResourceDTO.setPersonClass(personClass);
    final ServiceResult<HumanResource> result = this.humanService.createHumanResource(humanResourceDTO, null, null);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }


  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHumanResourceOnActionCreateNewHumanResource() {
    final String hospitalCode = "12200011400003140001";
    Hospital hospital = new Hospital();
    hospital = this.hospRepo.findByHospitalCode(hospitalCode);
    Assert.assertNotNull(hospital);

    final String hospitalDeptCode = "122000114000031440006";
    final HospitalDept hospitalDept = this.hospDeptRepo.findHospitalDeptByCode(hospitalDeptCode);
    Assert.assertNotNull(hospitalDept);
    Assert.assertNotNull(hospitalDept.getDeptId());

    final String personCode = "200011400002";
    Person person = new Person();
    person = this.personRepo.findByPersonCode(personCode);
    Assert.assertNotNull(person);

    final String personClassId = "200011400002";
    PersonClass personClass = new PersonClass();
    personClass = this.personClassRepo.findPersonClassByClassCode(personClassId);
    Assert.assertNotNull(personClass);

    final HumanResourceDto humanResourceDTO = new HumanResourceDto();
    humanResourceDTO.setHospital(hospital);
    humanResourceDTO.setHospitalDept(hospitalDept);
    humanResourceDTO.setPerson(person);
    humanResourceDTO.setPersonClass(personClass);
    final ServiceResult<HumanResource> result = this.humanService.saveHumanResource(humanResourceDTO);

    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHumanResourceOnActionEditHumanResourceNotApprove() {
    final String hospitalCode = "12200011400003140001";
    Hospital hospital = new Hospital();
    hospital = this.hospRepo.findByHospitalCode(hospitalCode);
    Assert.assertNotNull(hospital);

    final String hospitalDeptCode = "122000114000031440006";
    final HospitalDept hospitalDept = this.hospDeptRepo.findHospitalDeptByCode(hospitalDeptCode);
    Assert.assertNotNull(hospitalDept);
    Assert.assertNotNull(hospitalDept.getDeptId());

    final String personCode = "200011400006";
    Person person = new Person();
    person = this.personRepo.findByPersonCode(personCode);
    Assert.assertNotNull(person);

    final String personClassId = "200011400002";
    PersonClass personClass = new PersonClass();
    personClass = this.personClassRepo.findPersonClassByClassCode(personClassId);
    Assert.assertNotNull(personClass);

    final int editHumanId = 1;
    final HumanResourceDto humanDTO = new HumanResourceDto();
    final HumanResource humanResource = this.humanRepo.findByHumanResourceId(editHumanId);
    humanResource.setHospital(hospital);
    humanResource.setHospitalDept(hospitalDept);
    humanResource.setPerson(person);
    humanResource.setPersonClass(personClass);
    BeanCopier.copy(humanResource, humanDTO);
    final ServiceResult<HumanResource> result = this.humanService.saveHumanResource(humanDTO);

    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSaveHumanResourceOnActionEditHumanResourceApproved() {
    final String hospitalCode = "12200011400003140001";
    Hospital hospital = new Hospital();
    hospital = this.hospRepo.findByHospitalCode(hospitalCode);
    Assert.assertNotNull(hospital);

    final String hospitalDeptCode = "122000114000031440006";
    final HospitalDept hospitalDept = this.hospDeptRepo.findHospitalDeptByCode(hospitalDeptCode);
    Assert.assertNotNull(hospitalDept);
    Assert.assertNotNull(hospitalDept.getDeptId());

    final String personCode = "200011400003";
    Person person = new Person();
    person = this.personRepo.findByPersonCode(personCode);
    Assert.assertNotNull(person);

    final String personClassId = "200011400002";
    PersonClass personClass = new PersonClass();
    personClass = this.personClassRepo.findPersonClassByClassCode(personClassId);
    Assert.assertNotNull(personClass);

    final int editHumanId = 2;
    final HumanResourceDto humanDTO = new HumanResourceDto();
    final HumanResource humanResource = this.humanRepo.findByHumanResourceId(editHumanId);
    humanResource.setHospital(hospital);
    humanResource.setHospitalDept(hospitalDept);
    humanResource.setPerson(person);
    humanResource.setPersonClass(personClass);
    BeanCopier.copy(humanResource, humanDTO);
    final ServiceResult<HumanResource> result = this.humanService.saveHumanResource(humanDTO);

    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testDeleteHumanResource() {
    final int humanResourceId = 2;
    final HumanResource humanResource = this.repo.findByHumanResourceId(humanResourceId);
    Assertions.assertThat(humanResource).isNotNull();
    Assertions.assertThat(humanResource.getIsDeleted()).isEqualTo(0);
    final ServiceResult<HumanResource> result = this.humanService.deleteHumanResource(humanResource);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }
}
