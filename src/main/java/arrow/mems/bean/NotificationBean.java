package arrow.mems.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.mems.bean.data.UserCredential;

@Named
@ViewScoped
public class NotificationBean extends AbstractBean {

  @Inject
  UserCredential userCredential;

}
