package arrow.framework.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import arrow.framework.faces.messages.Message;
import arrow.framework.util.messages.Notification;

@Vetoed
public class ArrowValidator implements Serializable {
  private Validator wrapped;

  public ArrowValidator() {}

  public void setWrapper(Validator globalValidator) {
    this.wrapped = globalValidator;
  }

  public ArrowValidator(Validator globalValidator) {
    this.wrapped = globalValidator;
  }

  public <E> List<Message> validate(E entity) {
    final Set<ConstraintViolation<E>> violations = this.wrapped.validate(entity);

    final List<Message> errors = new ArrayList<Message>();
    for (final ConstraintViolation<E> violation : violations) {
      final Message error = Notification.createErrorMessage(violation.getMessageTemplate().replace("{", "").replace("}", ""), violation.getMessage());
      errors.add(error);
    }
    return errors;
  }

  public <E> List<Message> validate(E entity, Class<?>... groups) {
    final Set<ConstraintViolation<E>> violations = this.wrapped.validate(entity, groups);

    final List<Message> errors = new ArrayList<Message>();
    for (final ConstraintViolation<E> violation : violations) {
      final Message error = Notification.createErrorMessage(violation.getMessageTemplate(), violation.getMessage());
      errors.add(error);
    }
    return errors;
  }
}
