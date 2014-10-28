package arrow.mems.service;


import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.jboss.arquillian.testng.Arquillian;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import pl.itcrowd.arquillian.mock_contexts.MockFacesContextProducer;
import arrow.framework.util.Instance;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.RoleMaster;
import arrow.mems.persistence.repository.RoleMasterRepository;

public class RoleMasterServiceTest extends Arquillian {
  @MockFacesContextProducer
  public FacesContext createFacesContext() {
    final FacesContext context = Mockito.mock(FacesContext.class);
    final HttpSession session = Mockito.mock(HttpSession.class);
    final ExternalContext externalContext = Mockito.mock(ExternalContext.class);
    Mockito.when(context.getExternalContext()).thenReturn(externalContext);
    Mockito.when(context.getExternalContext().getSession(false)).thenReturn(session);

    return context;
  }

  private void insertNew() {
    final UserCredential user = Mockito.mock(UserCredential.class);
    Mockito.when(user.isLoggedIn()).thenReturn(true);
    Mockito.when(user.getUserId()).thenReturn(1);

    final RoleMaster role = new RoleMaster();
    role.setRoleCode("Test");
    Instance.get(RoleMasterRepository.class).saveAndFlush(role);
  }

  @Test
  public void testInsert() {
    this.insertNew();
    this.insertNew();
    this.insertNew();
    this.insertNew();
  }
}
