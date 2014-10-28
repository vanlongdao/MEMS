package arrow.mems.messages;

public class MASMessages {
  public static arrow.framework.faces.messages.Message MAS00001(Object... params) {
    return arrow.framework.util.messages.Notification.createInfoMessage(MessageCode.MAS00001, MessageCode.MAS00001, params);
  }
}