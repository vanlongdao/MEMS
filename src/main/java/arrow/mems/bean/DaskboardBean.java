package arrow.mems.bean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import arrow.framework.bean.AbstractBean;
import arrow.mems.bean.data.UserCredential;

@Named
@ViewScoped
public class DaskboardBean extends AbstractBean {

  @Inject
  UserCredential userCredential;
  private DashboardModel model;

  public DashboardModel getModel() {
    return this.model;
  }

  public void setModel(final DashboardModel model) {
    this.model = model;
  }

  // TODO: this code will work later
  @PostConstruct
  public void init() {
    this.model = new DefaultDashboardModel();
    final DashboardColumn column1 = new DefaultDashboardColumn();
    // Only Show for Head Officer
    // if (this.userCredential.isHeadQuarterOfficer())
    // {
    column1.addWidget("notification");
    // }
    this.model.addColumn(column1);
  }

  // Test push
  private String message;

  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

}
