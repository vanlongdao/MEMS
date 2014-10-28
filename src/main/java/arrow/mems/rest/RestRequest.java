package arrow.mems.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RestRequest<T> {
  public T getData() {
    return this.data;
  }

  public void setData(T pData) {
    this.data = pData;
  }

  private T data;
}
