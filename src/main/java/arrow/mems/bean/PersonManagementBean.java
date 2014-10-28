package arrow.mems.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.persistence.dto.PersonDto;
import arrow.mems.persistence.entity.Person;
import arrow.mems.service.PersonManagementService;
import arrow.mems.service.UserService;

@Named
@ViewScoped
public class PersonManagementBean extends AbstractApprovalBean {
  private PersonDto currentPerson;
  private Person selectedPerson;


  private List<Person> listPerson;
  private String comment;
  @Inject
  PersonManagementService personService;
  @Inject
  UserService userService;
  @Inject
  UserCredential userCredential;

  public boolean isEnableEdit() {
    return this.selectedPerson != null;
  }

  public boolean isEnableDelete() {

    return (this.selectedPerson != null);
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  public Person getSelectedPerson() {
    return this.selectedPerson;
  }

  public void setSelectedPerson(Person pSelectedPerson) {
    this.selectedPerson = pSelectedPerson;
  }

  public PersonDto getCurrentPerson() {
    return this.currentPerson;
  }

  public void setCurrentPerson(PersonDto pCurrentPerson) {
    this.currentPerson = pCurrentPerson;
  }



  @PostConstruct
  public void init() {}

  public void toggleSelection(final Person person) {
    if (person.isSelected()) {
      if (this.selectedPerson != null) {
        this.selectedPerson.setSelected(false);
      }
      this.selectedPerson = person;
    } else {
      this.selectedPerson = null;
    }
  }

  public List<Person> getListPerson() {
    if (this.userCredential.isLoggedIn() && (this.listPerson == null)) {
      final String officeCode = this.userCredential.getUserInfo().getOfficeCode();
      this.listPerson = this.personService.getListPerson(officeCode);
    }
    return this.listPerson;
  }

  public void setListPerson(List<Person> listPerson) {
    this.listPerson = listPerson;
  }

  public void addNewPerson() {
    this.currentPerson = new PersonDto();
  }

  public void editPerson() {
    final ServiceResult<PersonDto> result = this.personService.prepareEditPerson(this.selectedPerson);
    if (result.isSuccess()) {
      this.currentPerson = result.getData();
    } else {
      this.currentPerson = null;
    }
  }

  public void deletePerson() {
    if (this.selectedPerson.isSelected()) {
      this.personService.deletePerson(this.selectedPerson);
    }
    this.cleanStage();
  }

  public void savePerson() {
    final ServiceResult<Person> result = this.personService.savePerson(this.currentPerson);
    if (result.isSuccess()) {
      result.showMessages();
    }
    this.cleanStage();
  }

  public void closeUpdatePerson(ActionEvent ae) {
    this.currentPerson = null;
    this.resetStage(ae);
  }

  public void approve() {

  }

  public void revise() {

  }

  public void reject() {

  }

  public void reset() {
    if (this.currentPerson == null) {
      this.currentPerson = new PersonDto();
    } else {
      this.editPerson();
    }
  }

  public void cleanStage() {
    this.currentPerson = null;
    this.listPerson = null;
    if (this.selectedPerson != null) {
      this.selectedPerson = null;
    }
  }

  @Override
  public Object getEntityId() {
    return this.currentPerson.getPsnCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.PERSON;
  }

  @Override
  public String getItemLabel() {
    return this.currentPerson.getName();
  }

}
