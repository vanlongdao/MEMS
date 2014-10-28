package arrow.framework.util.messages;

import arrow.framework.faces.messages.Message;

public class Notification {
  public static Message createErrorMessage(final String messageCode, final String summaryKey, final Object... params) {
    return new ErrorMessage(messageCode, summaryKey, params);
  }

  public static Message createWarningMessage(final String messageCode, final String summaryKey, final Object... params) {
    return new WarnMessage(messageCode, summaryKey, params);
  }

  public static Message createInfoMessage(final String messageCode, final String summaryKey, final Object... params) {
    return new InfoMessage(messageCode, summaryKey, params);
  }

  public static Message createConfirmationMessage(final String messageCode, final String summaryKey, final Object... params) {
    return new InfoMessage(messageCode, summaryKey, params);
  }

}
