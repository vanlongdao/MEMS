package arrow.framework.faces.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.convert.ConverterException;

import arrow.framework.util.i18n.Messages;

public class ConverterUtils {

  public static ConverterException throwConverterException(final UIComponent component, final String msgKey, final Object... params) {
    final String translatedMsg = Messages.get(msgKey, params);
    return ConverterUtils.throwConverterException(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, translatedMsg, translatedMsg));
  }

  public static ConverterException throwConverterException(final UIComponent component, final Throwable cause, final String msgKey,
      final Object... params) {
    final String translatedMsg = Messages.get(msgKey, params);
    return ConverterUtils.throwConverterException(component, new FacesMessage(FacesMessage.SEVERITY_ERROR, translatedMsg, translatedMsg), cause);
  }

  private static ConverterException throwConverterException(final UIComponent component, final FacesMessage facesMessage, final Throwable cause) {
    // Make sure the cause was shown out somewhere at server log.
    cause.printStackTrace();
    return ConverterUtils.throwConverterException(component, new ConverterException(facesMessage, cause));
  }

  private static ConverterException throwConverterException(final UIComponent component, final FacesMessage facesMessage) {
    return ConverterUtils.throwConverterException(component, new ConverterException(facesMessage));
  }

  private static ConverterException throwConverterException(final UIComponent component, final ConverterException e) {
    UIComponentUtils.resetInputValueIfPossible(component);
    return e;
  }
}
