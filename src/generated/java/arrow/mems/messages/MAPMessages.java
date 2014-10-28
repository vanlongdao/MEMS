package arrow.mems.messages;

public class MAPMessages {
  public static arrow.framework.faces.messages.Message MAP00001(Object... params) {
    return arrow.framework.util.messages.Notification.createErrorMessage(MessageCode.MAP00001, MessageCode.MAP00001, params);
  }
  public static arrow.framework.faces.messages.Message MAP00002(Object... params) {
    return arrow.framework.util.messages.Notification.createConfirmationMessage(MessageCode.MAP00002, MessageCode.MAP00002, params);
  }
  public static arrow.framework.faces.messages.Message MAP00003(Object... params) {
    return arrow.framework.util.messages.Notification.createConfirmationMessage(MessageCode.MAP00003, MessageCode.MAP00003, params);
  }
  public static arrow.framework.faces.messages.Message MAP00004(Object... params) {
    return arrow.framework.util.messages.Notification.createInfoMessage(MessageCode.MAP00004, MessageCode.MAP00004, params);
  }
  public static arrow.framework.faces.messages.Message MAP00005(Object... params) {
    return arrow.framework.util.messages.Notification.createErrorMessage(MessageCode.MAP00005, MessageCode.MAP00005, params);
  }
}