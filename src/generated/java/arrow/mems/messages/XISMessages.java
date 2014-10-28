package arrow.mems.messages;

public class XISMessages {
  public static arrow.framework.faces.messages.Message XIS00001(Object... params) {
    return arrow.framework.util.messages.Notification.createErrorMessage(MessageCode.XIS00001, MessageCode.XIS00001, params);
  }
  public static arrow.framework.faces.messages.Message XIS00002(Object... params) {
    return arrow.framework.util.messages.Notification.createErrorMessage(MessageCode.XIS00002, MessageCode.XIS00002, params);
  }
}