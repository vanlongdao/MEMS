package arrow.mems.service;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.annotations.Test;

import arrow.mems.persistence.entity.Users;

@Test
public class UserServiceTest extends Arquillian {

  @Inject
  UserService testInstance;

  @Test
  public void testFindUserById() throws Exception {
    final Users userLogin = this.testInstance.repo.findBy(9999999);
    Assertions.assertThat(userLogin).isNotNull();
  }
  //
  // @Test
  // public void testFindAllUser() {
  // final List<UserLogin> allUser = this.testInstance.findAllUser();
  // Assertions.assertThat(allUser.size()).isEqualTo(66);
  // }
  //
  // @Test
  // public void testFindUserByCondition() {
  // final List<UserLogin> user = this.testInstance.findUserByCondition(9999999);
  // Assertions.assertThat(user.get(0).getUlPassword()).isEqualTo("triarrow");
  // }
  //
  // @Test(expectedExceptions = ArrowException.class)
  // public void testUpdateUser2() throws ArrowException {
  // final int u = this.testInstance.updateUser(LocalDateTime.now(), 9999999);
  // Assertions.assertThat(u).isEqualTo(1);
  // }
  //
  // @Test(expectedExceptions = ArrowException.class)
  // public void testUpdateUser() throws Exception {
  // final String newValue = "abcdefg";
  // UserLogin userLogin = this.testInstance.repo.findBy(new UserLoginMapped.Pk(9999999));
  // userLogin.setUlPassword(newValue);
  // final LocalDateTime current = LocalDateTime.now();
  //
  // final ServiceResult<UserLogin> result = this.testInstance.updateUserInfo(userLogin);
  // Assertions.assertThat(result.isSuccess()).isTrue();
  // Assertions.assertThat(result.getData()).isNotNull();
  // Assertions.assertThat(result.getData().getPk().getUlUserCode()).isEqualTo(9999999);
  // Assertions.assertThat(result.getData().getUlPassword()).isEqualTo(newValue);
  //
  // userLogin = this.testInstance.repo.findBy(new UserLoginMapped.Pk(9999999));
  // Assertions.assertThat(result.getData().getUlPassword()).isEqualTo(newValue);
  //
  // Assertions.assertThat(current.compareTo(result.getData().getLastUpdatedAt()) < 0).isTrue();
  // }
  //
  // @Test
  // public void testValidateRoleInfo() throws Exception {
  // final RoleMasterDto dto = new RoleMasterDto();
  // dto.setCountry(11);
  // final ServiceResult<RoleMasterDto> result = this.testInstance.validateRoleInfo(dto);
  //
  // Assertions.assertThat(result.isSuccess()).isFalse();
  // Assertions.assertThat(result.getErrors().get(0).getMessageCode()).isEqualTo("roleCode");
  // Assertions.assertThat(result.getErrors().get(0).createFaceMessage().getSummary()).isEqualTo("roleCode: TEST");
  //
  // }
}
