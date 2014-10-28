package arrow.mems.rest;

import java.util.List;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;

public class RestResult<T> extends ServiceResult<T> {

  public RestResult(boolean pIsSuccess, T pData) {
    super(pIsSuccess, pData);
  }

  public RestResult(boolean pIsSuccess, T pData, List<Message> errors) {
    super(pIsSuccess, pData, errors);
  }
}
