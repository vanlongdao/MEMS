package arrow.framework.validator.producer;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;

import arrow.framework.validator.ArrowValidator;

@ApplicationScoped
public class ArrowValidatorProducer implements Serializable {

  @Inject
  Validator validator;

  @Produces
  @RequestScoped
  public ArrowValidator produceArrowValidator() {
    return new ArrowValidator(Validation.buildDefaultValidatorFactory().getValidator());
  }
}
