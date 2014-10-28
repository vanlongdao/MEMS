package arrow.mems.service;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.constant.DatabaseConstants;
import arrow.mems.persistence.dto.UsersDto;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.util.string.EncryptStringUtils;

@Test
@UsingDataSet("datasets/base_data.xls")
public class ManagerUserServiceTest extends Arquillian {

  @Inject
  ManagerUserService testInstance;

  @Inject
  UsersRepository userRepo;

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testUpdatePassword() throws Exception {
    final String newPassword = "12345678";
    final int userId = 1;

    final ServiceResult<Integer> resultSuccess = this.testInstance.updatePassword(newPassword, userId);
    Assertions.assertThat(resultSuccess.getData().intValue()).isEqualTo(1);

    final int wrongUserId = 2131;
    final ServiceResult<Integer> resultFail = this.testInstance.updatePassword(newPassword, wrongUserId);
    Assertions.assertThat(resultFail.getData().intValue()).isEqualTo(0);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testDeleteUser() throws Exception {
    final int userIdToDelete = 2;
    final ServiceResult<Users> resultSuccess = this.testInstance.deleteUser(userIdToDelete);
    Assertions.assertThat(resultSuccess.getData().getIsDeleted()).isEqualTo(DatabaseConstants.DELETED);

    // Case unsuccessful
    final int userId = 123;
    final ServiceResult<Users> resultFail = this.testInstance.deleteUser(userId);
    Assertions.assertThat(resultFail.isSuccess()).isEqualTo(false);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testUpdateUser() throws Exception {
    final Users foundUser = this.userRepo.findUserById(1).getAnyResult();
    final UsersDto updateUser = BeanCopier.copy(foundUser, UsersDto.class);
    updateUser.setLoginName("userAddnew");
    updateUser.setIsSupervisor(1);
    updateUser.setEmail("longdvtest@arrow-tech.vn");
    final ServiceResult<Users> resultSuccess = this.testInstance.updateUser(updateUser);
    Assertions.assertThat(resultSuccess.getData().getEmail()).isEqualTo("longdvtest@arrow-tech.vn");
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testAddNewUserByLicense() throws Exception {
    final UsersDto newUser = new UsersDto();
    newUser.setAccountType(1);
    newUser.setEmail("longdv@arrow-tech.vn");
    newUser.setLoginName("arrowtech");
    newUser.setName("arror technologies");
    newUser.setPassword("test");
    newUser.setIsSupervisor(DatabaseConstants.SUPPERVISOR);
    final ServiceResult<Users> result = this.testInstance.addNewUser(newUser);
    Assertions.assertThat(result.isSuccess()).isTrue();
    Assertions.assertThat(result.getData()).isNotNull();
    Assertions.assertThat(result.getData().getPassword()).isNotNull();
    Assertions.assertThat(result.getData().getPassword()).isEqualTo(EncryptStringUtils.encrypt("test", result.getData().getSalt()));
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testGetUserByLoginName() throws Exception {
    final String loginName = "vanlongdao";
    final Users exitUser = this.testInstance.getUserByLoginName(loginName, DatabaseConstants.ACTIVE);
    Assertions.assertThat(exitUser.getLoginName()).isEqualTo("vanlongdao");

    final String otherLoginName = "testnologinname";
    final Users nonExitUser = this.testInstance.getUserByLoginName(otherLoginName, DatabaseConstants.ACTIVE);
    Assertions.assertThat(nonExitUser).isEqualTo(null);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testGetAllUsersByIsDeletedAndExceptUserLogging() throws Exception {
    final List<Users> activeUser = this.testInstance.getAllUsersByIsDeletedAndExceptUserLogging(DatabaseConstants.ACTIVE, 1);
    Assertions.assertThat(activeUser.size()).isEqualTo(5);

    final List<Users> deletedUser = this.testInstance.getAllUsersByIsDeletedAndExceptUserLogging(DatabaseConstants.DELETED, 1);
    Assertions.assertThat(deletedUser.size()).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testGetListFilterUserByIsDeleted() throws Exception {
    final List<Users> result = this.testInstance.getListFilterUserByIsDeleted(DatabaseConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(6);

    final List<Users> deletedUser = this.testInstance.getListFilterUserByIsDeleted(DatabaseConstants.DELETED);
    Assertions.assertThat(deletedUser.size()).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testGetUserById() throws Exception {
    final Users result = this.testInstance.getUserById(3);
    Assertions.assertThat(result.getLoginName()).isEqualTo("arrow1");
  }
}
