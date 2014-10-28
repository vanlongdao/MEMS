package arrow.mems.debug;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import arrow.framework.bean.AbstractBean;

@Named
@ViewScoped
public class DebugDialog extends AbstractBean {
  public void viewCars() {
    RequestContext.getCurrentInstance().openDialog("viewCars");
  }
}
