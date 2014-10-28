package arrow.framework.exception;

import java.util.List;

import arrow.framework.faces.messages.Message;

public class ServiceException extends ArrowException {

  private List<Message> errors;

  public List<Message> getErrors() {
    return this.errors;
  }

  public void setErrors(List<Message> pErrors) {
    this.errors = pErrors;
  }

  public ServiceException(String message, List<Message> errors) {
    super(message);
    this.errors = errors;
  }
}
