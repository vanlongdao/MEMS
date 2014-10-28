package arrow.mems.service;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.annotations.Test;

import arrow.framework.helper.AuthenticationData;
import arrow.framework.helper.ServiceResult;

@Test
@UsingDataSet("datasets/base_data.xls")
public class AuthenticationServiceTest extends Arquillian {

  @Inject
  AuthenticationService testInstance;

  @Test(enabled = true)
  @UsingDataSet("datasets/user_management_service_data.xls")
  public void testLogin() throws Exception {
    final String loginName = "arrow1";
    final String password = "123456";
    final String wrongPassword = "1234562313";
    final ServiceResult<AuthenticationData> resultSuccess = this.testInstance.login(loginName, password);
    Assertions.assertThat(resultSuccess.getData().getUserId()).isEqualTo(3);

    final ServiceResult<AuthenticationData> resultFail = this.testInstance.login(loginName, wrongPassword);
    Assertions.assertThat(resultFail.isSuccess()).isEqualTo(false);
  }

}
