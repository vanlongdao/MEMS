package arrow.framework.validator;

import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import arrow.framework.util.i18n.Messages;
import arrow.mems.messages.MessageCode;

@FacesValidator("dateRangeValidator")
public class DateRangeValidator implements Validator {


  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    if (value == null)
      return; // Let required="true" handle.

    final UIInput startDateComponent = (UIInput) component.getAttributes().get("startDateComponent");

    if (!startDateComponent.isValid())
      return; // Already invalidated. Don't care about it then.

    final LocalDate startDate = (LocalDate) startDateComponent.getValue();

    if (startDate == null)
      return; // Let required="true" handle.

    final LocalDate endDate = (LocalDate) value;

    if (startDate.isAfter(endDate)) {
      startDateComponent.setValid(false);
      throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get(MessageCode.MFM00012), null));
    }
  }
}
