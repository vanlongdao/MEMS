package arrow.mems.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.CollectionUtils;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.messages.MDMMessages;
import arrow.mems.persistence.dto.HumanResourceDto;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.repository.HospitalDeptRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.service.HumanResourceManagementService;
import arrow.mems.service.UserService;

@Named
@ViewScoped
public class HumanResourceManagementBean extends AbstractApprovalBean {
  private HumanResourceDto currentHumanResource;
  private HumanResource selectedHumanResource;
  private List<HumanResource> listHumanResource;

  private String comment;
  @Inject
  HumanResourceManagementService humanResourceService;
  @Inject
  UserService userService;
  @Inject
  UserCredential userCredential;
  @Inject
  HospitalDeptRepository hospitalDeptRepo;
  @Inject
  UsersRepository usersRepository;
  @Inject
  PersonRepository personRepo;

  private List<HospitalDept> listAllActiveHospitalDepartment;
  private List<Person> listAllActivePerson;

  public void setListAllActiveHospitalDepartment(List<HospitalDept> pListAllActiveHospitalDepartment) {
    this.listAllActiveHospitalDepartment = pListAllActiveHospitalDepartment;
  }

  public boolean isEnableEdit() {
    return this.selectedHumanResource != null;
  }

  public boolean isEnableForHospitalDep() {
    return this.currentHumanResource.getHospital() != null;
  }

  public boolean isEnableDelete() {

    return (this.selectedHumanResource != null);
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }


  public HumanResource getSelectedHumanResource() {
    return this.selectedHumanResource;
  }

  public void setSelectedHumanResource(HumanResource pSelectedHumanResource) {
    this.selectedHumanResource = pSelectedHumanResource;
  }

  public HumanResourceDto getCurrentHumanResource() {
    return this.currentHumanResource;
  }

  public void setCurrentHumanResource(HumanResourceDto pCurrentHumanResource) {
    this.currentHumanResource = pCurrentHumanResource;
  }

  @PostConstruct
  public void init() {

  }

  public void toggleSelection(final HumanResource humanResource) {
    if (humanResource.isSelected()) {
      if (this.selectedHumanResource != null) {
        this.selectedHumanResource.setSelected(false);
      }
      this.selectedHumanResource = humanResource;
    } else {
      this.selectedHumanResource = null;
    }
  }

  public List<HumanResource> getListHumanResource() {
    if (this.userCredential.isLoggedIn() && (this.listHumanResource == null)) {
      final String officeCode = this.userCredential.getUserInfo().getOfficeCode();
      this.listHumanResource = this.humanResourceService.getListHumanResource(officeCode);
    }
    return this.listHumanResource;
  }

  public void setListHumanResource(List<HumanResource> listHumanResource) {
    this.listHumanResource = listHumanResource;
  }

  public void addNewHumanResource() {
    this.currentHumanResource = new HumanResourceDto();
  }

  public void editHumanResource() {
    final ServiceResult<HumanResourceDto> result = this.humanResourceService.prepareEditHumanResource(this.selectedHumanResource);
    if (result.isSuccess()) {
      this.currentHumanResource = result.getData();
    } else {
      this.currentHumanResource = null;
    }
  }

  public void deleteHumanResource() {
    if (this.selectedHumanResource.isSelected()) {
      this.humanResourceService.deleteHumanResource(this.selectedHumanResource);
    }
    this.cleanStage();
  }

  public void saveHumanResource() {
    final boolean isExistingHumanResource =
        this.humanResourceService.checkExistingHumanResource(this.currentHumanResource.getHospCode(), this.currentHumanResource.getHospDeptCode(),
            this.currentHumanResource.getPsnCode());
    final boolean isExistingHumanResourceWhenEdit =
        this.humanResourceService.checkExistingHumanResourceWhenEdit(this.currentHumanResource.getHospCode(),
            this.currentHumanResource.getHospDeptCode(), this.currentHumanResource.getPsnCode(), this.currentHumanResource.getHrId());
    if (this.selectedHumanResource == null) {
      if (isExistingHumanResource) {
        MDMMessages.MDM00028().show();
        return;
      }
    } else {
      if (isExistingHumanResourceWhenEdit) {
        MDMMessages.MDM00028().show();
        return;
      }
    }
    final ServiceResult<HumanResource> result = this.humanResourceService.saveHumanResource(this.currentHumanResource);
    if (result.isSuccess()) {
      result.showMessages();
    }
    this.cleanStage();
  }

  public List<HospitalDept> filterHospitalDeptInHosp(String query) {
    List<HospitalDept> listHospitalDept = new ArrayList<HospitalDept>();
    String hospCode = null;
    if (this.getCurrentHumanResource() != null) {
      hospCode = this.getCurrentHumanResource().getHospital().getHospCode();
    }
    if (hospCode != null)
      if (StringUtils.isEmpty(query) && StringUtils.isEmpty(hospCode)) {
        listHospitalDept = this.getListAllActiveHospitalDepartmentInHospital(hospCode);
      } else {
        listHospitalDept = CollectionUtils.filter(this.getListAllActiveHospitalDepartmentInHospital(hospCode), arg0 -> {
          final HospitalDept item = (HospitalDept) arg0;
          return StringUtils.containsIgnoreCase(item.getName(), query) || StringUtils.containsIgnoreCase(item.getDeptCode().toString(), query);
        });
      }
    return listHospitalDept;
  }

  public List<HospitalDept> getListAllActiveHospitalDepartmentInHospital(String hospCode) {
    final List<Integer> listUsersInOneOffice = this.usersRepository.findUserInOneOffice(this.userCredential.getUserInfo().getOfficeCode());
    this.listAllActiveHospitalDepartment = this.hospitalDeptRepo.findAllActiveHospitalDeptInHospitalAndInOneOfficce(listUsersInOneOffice, hospCode);
    return this.listAllActiveHospitalDepartment;
  }

  public List<Person> filterPersonInHosp(String query) {
    List<Person> listPerson = new ArrayList<Person>();
    String officeCode = null;
    if (this.getCurrentHumanResource() != null) {
      officeCode = this.getCurrentHumanResource().getHospital().getOfficeCode();
    }
    if (officeCode != null)
      if (StringUtils.isEmpty(query) && StringUtils.isEmpty(officeCode)) {
        listPerson = this.getListAllActivePersonInHospital(officeCode);
      } else {
        listPerson = CollectionUtils.filter(this.getListAllActivePersonInHospital(officeCode), arg0 -> {
          final Person item = (Person) arg0;
          return StringUtils.containsIgnoreCase(item.getName(), query) || StringUtils.containsIgnoreCase(item.getOfficeCode().toString(), query);
        });
      }
    return listPerson;
  }

  public List<Person> getListAllActivePersonInHospital(String officeCode) {
    final List<Integer> listUsersInOneOffice = this.usersRepository.findUserInOneOffice(this.userCredential.getUserInfo().getOfficeCode());
    this.listAllActivePerson = this.personRepo.findAllActivePersonInOneOffice(officeCode, listUsersInOneOffice);
    return this.listAllActivePerson;
  }


  public List<Person> getListAllActivePerson() {
    return this.listAllActivePerson;
  }

  public void setListAllActivePerson(List<Person> pListAllActivePerson) {
    this.listAllActivePerson = pListAllActivePerson;
  }

  public void closeUpdateHumanResource() {
    this.currentHumanResource = null;
  }

  public void approve() {

  }

  public void revise() {

  }

  public void reject() {

  }

  public void reset() {
    if (this.currentHumanResource == null) {
      this.currentHumanResource = new HumanResourceDto();
    } else {
      this.editHumanResource();
    }
  }

  public void cleanStage() {
    this.currentHumanResource = null;
    this.listHumanResource = null;
    if (this.selectedHumanResource != null) {
      this.selectedHumanResource = null;
    }
  }

  public void eventChangeHosptial() {
    if (this.getCurrentHumanResource().getHospitalDept() != null) {
      this.getCurrentHumanResource().setHospitalDept(null);
    }
    if (this.getCurrentHumanResource().getPerson() != null) {
      this.getCurrentHumanResource().setPerson(null);
    }
  }

  @Override
  public Object getEntityId() {
    return this.currentHumanResource.getHospCode() + ";" + this.currentHumanResource.getHospDeptCode() + ";" + this.currentHumanResource.getPsnCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.HUMAN_RESOURCE;
  }


  @Override
  public String getItemLabel() {
    return this.currentHumanResource.getFullName();
  }
}
