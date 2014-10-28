package arrow.framework.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import arrow.framework.faces.util.UIComponentUtils;
import arrow.framework.logging.ArrowLogger;
import arrow.framework.validator.ArrowValidator;
import arrow.mems.config.AppConfig;

public class AbstractBean implements Serializable {
  @Inject
  protected ArrowLogger log;

  @Inject
  @RequestScoped
  protected ArrowValidator validator;

  @Inject
  protected AppConfig appConfig;

  public void resetStage(ActionEvent actionEvent) {
    // actionEvent should be called by Close button.
    UIComponentUtils.resetComponentStatus(actionEvent.getComponent().getParent());
  }
}
