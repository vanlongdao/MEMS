package arrow.framework.faces.producer;

import java.io.Serializable;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class FacesContextProducer implements Serializable {
  @Produces
  @RequestScoped
  public FacesContext getFacesContext() {
    final FacesContext ctx = FacesContext.getCurrentInstance();
    if (ctx == null)
      throw new ContextNotActiveException("FacesContext is not active");
    return ctx;
  }
}
