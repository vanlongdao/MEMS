package arrow.mems.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import pl.itcrowd.arquillian.mock_contexts.FacesContextRequired;
import pl.itcrowd.arquillian.mock_contexts.MockFacesContextProducer;
import arrow.framework.helper.ServiceResult;
import arrow.mems.persistence.entity.Users;
import arrow.mems.util.string.EncryptStringUtils;

@Test
@UsingDataSet("datasets/base_data.xls")
public class RecoverPasswordServiceTest extends Arquillian {

  @Inject
  RecoverPasswordService testInstance;

  // Here is how to create Mock of FacesContext
  @MockFacesContextProducer
  public FacesContext createFacesContext() {
    final FacesContext context = Mockito.mock(FacesContext.class);
    final Application app = Mockito.mock(Application.class);
    final ExternalContext extContext = Mockito.mock(ExternalContext.class);

    Mockito.when(context.getExternalContext()).thenReturn(extContext);

    final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    // InputStream inputStream = Mockito.mock(InputStream.class);


    // Mockito.when(inputStream.getClass().getResourceAsStream("arrow/templates/recoverPassword_ja.ftl")).thenReturn(inputStream);
    Mockito.when(request.getContextPath()).thenReturn("mems");
    Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("https://localhost/"));
    Mockito.when(request.getRequestURI()).thenReturn("/index.jsf");
    Mockito.when(context.getExternalContext().getRequest()).thenReturn(request);
    Mockito.when(context.getApplication()).thenReturn(app);
    final List<Locale> supportedLocales = new ArrayList<Locale>();
    supportedLocales.add(Locale.JAPANESE);
    Mockito.when(context.getApplication().getSupportedLocales()).thenReturn(supportedLocales.iterator());
    Mockito.when(context.getApplication().getDefaultLocale()).thenReturn(Locale.JAPANESE);
    // Servlets test = Mockito.mock(Servlets.class);
    // Mockito.when(Servlets.getRequestBaseURL(request)).thenReturn("test");
    return context;
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/data.xls")
  public void testUpdateSessionId() throws Exception {
    final String email = "longdv@arrow-tech.vn";
    final String loginName = "vanlongdao";
    final String sessionId = "8ab3fb4f-4236-4e07-bad3-68ca25daf8bb";
    final ServiceResult<Boolean> results = this.testInstance.updateSessionId(email, loginName, sessionId);
    Assertions.assertThat(results.isSuccess()).isEqualTo(true);

    // Case false :
    final String wrongLoginName = "longdvtest";
    final ServiceResult<Boolean> resultFail = this.testInstance.updateSessionId(email, wrongLoginName, sessionId);
    Assertions.assertThat(resultFail.isSuccess()).isEqualTo(false);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/data.xls")
  public void testUpdatePassword() throws Exception {
    final String newPassword = "1234";
    final String reNewPassword = "1234";
    final String sessionId = "9da35610-e819-4730-acaf-646e932fc9d2";
    final String nonSessionId = "8ab3fb4f-4236-4e07-bad3-68ca25da";
    final ServiceResult<Users> resultFail = this.testInstance.updatePassword(newPassword, reNewPassword, nonSessionId);
    Assertions.assertThat(resultFail.isSuccess()).isEqualTo(false);
    final ServiceResult<Users> resultSuccess = this.testInstance.updatePassword(newPassword, reNewPassword, sessionId);
    Assertions.assertThat(resultSuccess.getData().getPassword()).isEqualTo(EncryptStringUtils.encrypt("1234", resultSuccess.getData().getSalt()));
  }

  @Test(enabled = true)
  @FacesContextRequired
  public void testSendEmployeeInfo() throws Exception {
    final String email = "longdv@arrow-tech.vn";
    final String loginName = "vanlongdao";
    final ServiceResult<Users> results = this.testInstance.sendRecoveryPasswordToUser(email, loginName);
    Assertions.assertThat(results.getData().getName()).isEqualTo("vanlongdao");
  }

}
