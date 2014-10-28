package arrow.framework.util.messages;

import arrow.framework.faces.messages.Message;


public class ErrorMessage extends Message {
  private final Severity severity = Severity.ERROR;

  public ErrorMessage(final String messageCode, final String summaryKey, final Object... params) {
    super(messageCode, summaryKey, summaryKey + Message.DETAIL_SUFFIX, params);
  }

  @Override
  public Severity getSeverity() {
    return this.severity;
  }

}
