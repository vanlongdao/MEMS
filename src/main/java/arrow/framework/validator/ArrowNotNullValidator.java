package arrow.framework.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArrowNotNullValidator implements ConstraintValidator<ArrowNotNull, Object> {

  @Override
  public void initialize(ArrowNotNull pArg0) {

  }

  @Override
  public boolean isValid(Object pArg0, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("test").addParameterNode(1).addConstraintViolation();
    return false;
  }

}
