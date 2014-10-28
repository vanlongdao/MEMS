package arrow.framework.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import arrow.framework.logging.ArrowLogger;
import arrow.framework.logging.ArrowLoggerProducer;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
  private String firstFieldName;
  private String secondFieldName;

  private static final ArrowLogger LOGGER = ArrowLoggerProducer.getLogger();

  @Override
  public void initialize(final FieldMatch constraintAnnotation) {
    this.firstFieldName = constraintAnnotation.first();
    this.secondFieldName = constraintAnnotation.second();
  }

  @Override
  public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    Object firstObj;
    try {
      firstObj = BeanUtils.getProperty(value, this.firstFieldName);
      final Object secondObj = BeanUtils.getProperty(value, this.secondFieldName);
      return ((firstObj == null) && (secondObj == null)) || ((firstObj != null) && firstObj.equals(secondObj));
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      FieldMatchValidator.LOGGER.debug("Ignore this exception", e);
    }
    return true;
  }
}
