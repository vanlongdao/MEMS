package arrow.framework.helper;

import java.util.Collections;
import java.util.List;

import arrow.framework.faces.messages.Message;

public class ServiceResult<T> {
  private boolean success;

  private T data;

  private List<Message> errors;

  public ServiceResult(boolean isSuccess, T data) {
    this.success = isSuccess;
    this.data = data;
    this.setErrors(Collections.emptyList());
  }

  public ServiceResult(boolean isSuccess, T data, List<Message> errors) {
    this.success = isSuccess;
    this.data = data;
    this.setErrors(errors);
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public List<Message> getErrors() {
    return this.errors;
  }

  public void setErrors(List<Message> pErrors) {
    this.errors = pErrors;
  }

  public void showMessages() {
    if ((this.errors != null) && !this.errors.isEmpty()) {
      for (final Message message : this.getErrors()) {
        message.show();
      }
    }
  }
}
