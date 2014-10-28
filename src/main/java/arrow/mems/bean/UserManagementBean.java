package arrow.mems.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.framework.exception.ArrowException;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.framework.util.CollectionUtils;
import arrow.framework.util.Instance;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.config.AppConfig;
import arrow.mems.constant.CommonConstants;
import arrow.mems.constant.DatabaseConstants;
import arrow.mems.persistence.dto.UsersDto;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.service.ManagerUserService;

@Named
@ViewScoped
public class UserManagementBean extends AbstractBean {

  private Users selectedUser;
  private List<Office> listOffice;
  private List<Users> listUsers;
  private List<Person> listPersons;

  @Inject
  private ManagerUserService userService;
  private UsersDto currentUser;

  @Inject
  UserCredential userCredential;

  @Inject
  UsersRepository userRepo;

  @Inject
  OfficeRepository officeRepo;

  @Inject
  PersonRepository personRepository;

  public void cancel(ActionEvent ae) {
    this.resetStage(ae);
    this.currentUser = null;
  }

  public void cancelEditUser() {
    this.selectedUser = null;
    this.listUsers = null;
  }

  public void clickAddNew() {
    this.currentUser = new UsersDto();
  }

  public void deleteSelectedUser() {
    final ServiceResult<Users> result = this.userService.deleteUser(this.selectedUser.getUserId());
    if (result.isSuccess()) {
      this.selectedUser = null;
      this.listUsers = null;
      this.currentUser = null;
    }
    result.showMessages();
  }

  public void editSelectedUser() throws ArrowException {
    this.currentUser = new UsersDto();
    BeanCopier.copy(this.selectedUser, this.currentUser);
    this.currentUser.setPassword(StringUtils.EMPTY);
  }

  public List<Office> filterOffice(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListOffice();

    return this.getListOffice().stream()
        .filter(o -> StringUtils.containsIgnoreCase(o.getName(), query) || StringUtils.containsIgnoreCase(o.getOfficeCode(), query))
        .collect(Collectors.toList());
  }

  public String getAccountTypeName(int accountType) {
    return Instance.get(AppConfig.class).getAccountTypes().get(accountType).getLabel();
  }

  public List<SelectItem> getAccountTypes() {
    return Instance.get(AppConfig.class).getAccountTypes();
  }

  public UsersDto getCurrentUser() {
    return this.currentUser;
  }

  public List<Office> getListOffice() {
    if (this.listOffice == null) {
      this.listOffice = this.officeRepo.findByIsDeleted(CommonConstants.ACTIVE);
    }
    return this.listOffice;
  }

  public List<Users> getListUsers() {
    if (this.listUsers == null) {
      this.listUsers = this.userService.getListFilterUserByIsDeleted(DatabaseConstants.ACTIVE);
    }
    return this.listUsers;
  }

  public String getNameOfCreatedBy(Users user) {
    final Users userResult = this.userService.getUserById(user.getCreatedBy());
    if (userResult == null)
      return null;
    else
      return userResult.getName();
  }

  public Users getSelectedUser() {
    return this.selectedUser;
  }

  public boolean isEnableBtnDelete() {
    return this.selectedUser != null;
  }

  public void saveUser() {
    ServiceResult<Users> result = null;
    if (!this.currentUser.isPersisted()) {
      result = this.userService.addNewUser(this.currentUser);
    } else {
      result = this.userService.updateUser(this.currentUser);
    }
    if (result.isSuccess()) {
      this.currentUser = null;
      this.listUsers = null;
    }
    result.showMessages();
  }

  public void setCurrentUser(UsersDto pCurrentUser) {
    this.currentUser = pCurrentUser;
  }

  public void setListOffice(List<Office> pListOffice) {
    this.listOffice = pListOffice;
  }

  public void setListUsers(List<Users> pListUsers) {
    this.listUsers = pListUsers;
  }

  public void setSelectedUser(Users pSelectedUser) {
    this.selectedUser = pSelectedUser;
  }

  public void toggleSelection(final Users user) {
    if (user.isSelected()) {
      if (this.selectedUser != null) {
        this.selectedUser.setSelected(false);
      }
      this.selectedUser = user;
    } else {
      this.selectedUser = null;
    }
  }

  public List<Person> filterPerson(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListPersons();
    return CollectionUtils.filter(this.getListPersons(), object -> {
      final Person item = (Person) object;
      return StringUtils.containsIgnoreCase(item.getName(), query) || item.getPsnCode().contains(query);
    });
  }

  public List<Person> getListPersons() {
    if (this.listPersons == null) {
      this.listPersons = new ArrayList<Person>();
    }
    if (!StringUtils.isEmpty(this.currentUser.getOfficeCode())) {
      final String officeCode = this.currentUser.getOfficeCode();
      // final String userOffice = this.userCredential.getOfficeCode();
      // this.listPersons = this.personRepository.findAllActivePersonByOffice(officeCode,
      // userOffice).getResultList();
      this.listPersons = this.personRepository.finAlldPersonByOfficeCode(officeCode).getResultList();
    }
    return this.listPersons;
  }

  public void setListPersons(List<Person> pListPersons) {
    this.listPersons = pListPersons;
  }
}
