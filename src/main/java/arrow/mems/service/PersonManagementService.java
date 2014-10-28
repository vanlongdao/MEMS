package arrow.mems.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.generator.PersonIdGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.PersonDto;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.repository.OperationLogRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.UsersRepository;

public class PersonManagementService extends AbstractService {
  @Inject
  PersonRepository personRepo;
  @Inject
  UserCredential userCredential;
  @Inject
  UsersRepository usersRepo;
  @Inject
  UserService userService;
  @Inject
  OperationLogRepository logRepo;
  @Inject
  OperationLogService logService;

  public ServiceResult<Person> createPerson(PersonDto currentPerson, Integer checkedBy, LocalDateTime checkedDate) {
    final List<Message> message = new ArrayList<Message>();
    ServiceResult<Person> result = null;
    final Person newPerson = new Person();
    BeanCopier.copy(currentPerson, newPerson);
    newPerson.setAddrCode(currentPerson.getAddress().getAddrCode());
    newPerson.setOfficeCode(currentPerson.getOffice().getOfficeCode());
    newPerson.setIsDeleted(CommonConstants.ACTIVE);
    newPerson.setCheckedAt(checkedDate);
    newPerson.setCheckedBy(checkedBy);
    newPerson.setCreatedAt(LocalDateTime.now());
    newPerson.setCreatedBy(this.userCredential.getUserId());
    this.personRepo.saveAndFlush(newPerson);
    message.add(MMIMessages.MMI00006());
    result = new ServiceResult<>(true, newPerson, message);
    return result;
  }



  public ServiceResult<Person> deletePerson(Person selectedPerson) {
    ServiceResult<Person> result = null;
    final Person deletePerson = this.personRepo.findBy(selectedPerson.getPsnId());
    deletePerson.setIsDeleted(CommonConstants.DELETE);
    this.personRepo.saveAndFlush(deletePerson);
    result = new ServiceResult<>(true, null, null);
    return result;
  }

  public List<Person> getListPerson(String officeCode) {
    final List<Integer> listCreatedByUserId = this.userService.findUserInOneOffice(officeCode);

    final ArrowQuery<Person> query = new ArrowQuery<>(super.em);
    query.select("e").from("Person e ");
    query.where(" e.isDeleted = 0");
    query.where(" e.createdBy in (?) ", listCreatedByUserId);

    // add filter and sort
    query.addFilterAndSorterForString("psnCode", "e.psnCode");
    query.addFilterAndSorterForString("personName", "e.name");
    query.addFilterAndSorterForString("officeName", "e.office.name");
    query.addFilterAndSorterForString("country", "e.address.mtCountry.name");
    query.addFilterAndSorterForString("province", "e.address.province");
    query.addFilterAndSorterForString("city", "e.address.city");
    query.addFilterAndSorterForString("address1", "e.address.address1");
    query.addFilterAndSorterForString("tel", "e.tel");
    query.addFilterAndSorterForString("fax", "e.fax");
    query.addFilterAndSorterForString("email", "e.email");

    query.orderBy("psnCode");

    return query.getResultList();
  }

  public ServiceResult<PersonDto> prepareEditPerson(Person person) {
    ServiceResult<PersonDto> result = null;
    Person getPerson = new Person();
    if (person.isSelected()) {
      getPerson = this.personRepo.findBy(person.getPsnId());
      final PersonDto personDTO = new PersonDto();
      BeanCopier.copy(getPerson, personDTO);
      result = new ServiceResult<PersonDto>(true, personDTO);
    }
    return result;
  }


  public ServiceResult<Person> savePerson(PersonDto currentPerson) {
    ServiceResult<Person> result = null;
    if (currentPerson.getPsnId() == 0) {
      final PersonIdGenerator generator = new PersonIdGenerator(currentPerson.getAddress().getCountry(), LocalDate.now().getYear());
      final String nextPersonCode = generator.getNext();
      currentPerson.setPsnCode(nextPersonCode);

      result = this.createPerson(currentPerson, null, null);
    } else {
      final Person editPerson = this.personRepo.findBy(currentPerson.getPsnId());
      if (editPerson.isPassedApprovalProcess()) {
        editPerson.setIsDeleted(CommonConstants.DELETE);
        this.personRepo.saveAndFlush(editPerson);
        result = this.createPerson(currentPerson, this.userCredential.getUserId(), LocalDateTime.now());

      } else {
        editPerson.setIsDeleted(CommonConstants.DELETE);
        this.personRepo.saveAndFlush(editPerson);
        result = this.createPerson(currentPerson, null, null);
      }
      // add operation log after edit Person
      this.logService.addOperationLog(Person.class.getName(), currentPerson.getPsnId(), result.getData().getPsnId(), "update approve");
    }
    return result;
  }

  /*
   * Tai Nguyen: this method support for tab estimate and tab order
   */
  public List<Person> getPersonsByOfficeCode(String officceCode, String userOffice) {
    return this.personRepo.findAllActivePersonByOffice(officceCode, userOffice).getResultList();
  }
}
