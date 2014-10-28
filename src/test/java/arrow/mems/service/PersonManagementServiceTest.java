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
import arrow.mems.persistence.dto.PersonDto;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.repository.AddressRepository;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.test.DeploymentManager;


public class PersonManagementServiceTest extends DeploymentManager {
  @Inject
  PersonManagementService service;
  @Inject
  PersonRepository repo;
  @Inject
  AddressRepository addressRepo;
  @Inject
  OfficeRepository officeRepo;
  @Inject
  UserCredential userCredentail;
  @Inject
  AuthenticationService authenService;


  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testGetListPerson() {
    final String officeCode = "30061400001";
    List<Person> listPerson = new ArrayList<Person>();
    listPerson = this.service.getListPerson(officeCode);
    Assert.assertNotNull(listPerson);
    Assert.assertEquals(listPerson.size(), 8);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditPerson() {
    ServiceResult<PersonDto> result = null;
    final String personCode = "200011400002";
    final Person person = this.repo.findByPersonCode(personCode);
    person.setSelected(true);
    Assertions.assertThat(person.getIsDeleted()).isEqualTo(0);
    result = this.service.prepareEditPerson(person);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testPrepareEditPersonIsNotSelected() {
    ServiceResult<PersonDto> result = null;
    final String personCode = "200011400002";
    final Person person = this.repo.findByPersonCode(personCode);
    person.setSelected(false);
    Assertions.assertThat(person.getIsDeleted()).isEqualTo(0);
    result = this.service.prepareEditPerson(person);
    Assert.assertNull(result);
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testCreatePerson() {
    final String addressCode = "030011400001";
    Address address = new Address();
    address = this.addressRepo.getAddressByAddressCode(addressCode);
    Assert.assertNotNull(address);
    final String officeCode = "30031400003";
    Office office = new Office();
    office = this.officeRepo.findActiveOfficeByOfficeCode(officeCode);
    Assert.assertNotNull(office);

    final PersonDto personDTO = new PersonDto();
    personDTO.setAddrCode(addressCode);
    personDTO.setName("personName test");
    personDTO.setAddress(address);
    personDTO.setOffice(office);
    final ServiceResult<Person> result = this.service.createPerson(personDTO, null, null);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSavePersonOnActionCreateNewPerson() {
    final String addressCode = "030011400001";
    Address address = new Address();
    address = this.addressRepo.getAddressByAddressCode(addressCode);
    Assert.assertNotNull(address);
    final String officeCode = "30031400003";
    Office office = new Office();
    office = this.officeRepo.findActiveOfficeByOfficeCode(officeCode);
    Assert.assertNotNull(office);

    final PersonDto personDTO = new PersonDto();
    personDTO.setAddrCode(addressCode);
    personDTO.setName("personName test");
    personDTO.setAddress(address);
    personDTO.setOffice(office);
    final ServiceResult<Person> result = this.service.savePerson(personDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSavePersonOnActionEditPersonNotApprove() {
    final String addressCode = "030011400001";
    Address address = new Address();
    address = this.addressRepo.getAddressByAddressCode(addressCode);
    final String personCode = "200011400003";
    final Person person = this.repo.findByPersonCode(personCode);
    final PersonDto personDTO = new PersonDto();
    Assert.assertNotNull(person);
    Assert.assertNotNull(person.getPsnId());
    final String officeCode = "30031400003";
    Office office = new Office();
    office = this.officeRepo.findActiveOfficeByOfficeCode(officeCode);
    Assert.assertNotNull(office);

    person.setContactMethod("send by email");
    person.setName("name test for edit");
    person.setAddress(address);
    person.setOffice(office);
    BeanCopier.copy(person, personDTO);
    final ServiceResult<Person> result = this.service.savePerson(personDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testSavePersonOnActionEditPersonApproved() {
    final String addressCode = "030011400001";
    Address address = new Address();
    address = this.addressRepo.getAddressByAddressCode(addressCode);
    final String personCode = "200011400002";
    final Person person = this.repo.findByPersonCode(personCode);
    final PersonDto personDTO = new PersonDto();
    Assert.assertNotNull(person);
    Assert.assertNotNull(person.getPsnId());
    final String officeCode = "30061400001";
    Office office = new Office();
    office = this.officeRepo.findActiveOfficeByOfficeCode(officeCode);
    Assert.assertNotNull(office);

    person.setContactMethod("send by email");
    person.setName("name test for edit");
    person.setAddress(address);
    person.setOffice(office);
    BeanCopier.copy(person, personDTO);
    final ServiceResult<Person> result = this.service.savePerson(personDTO);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data_cuongp.xls"})
  public void testDeletePerson() {
    final String personCode = "200011400003";
    Person person = this.repo.findByPersonCode(personCode);
    Assertions.assertThat(person.getIsDeleted()).isEqualTo(0);
    final ServiceResult<Person> result = this.service.deletePerson(person);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());

    // verify data after delete
    person = this.repo.findByPersonCode(personCode);
    Assertions.assertThat(person.getIsDeleted()).isEqualTo(1);
  }
}
