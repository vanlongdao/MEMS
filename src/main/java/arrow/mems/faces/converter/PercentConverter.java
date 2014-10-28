package arrow.mems.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import arrow.framework.faces.util.ConverterUtils;

public class PercentConverter extends NonNegativeNumberConverter {

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    if (((Number) value).doubleValue() == 0)
      return null;
    else
      return super.getAsString(context, component, ((Number) value).doubleValue() * 100.0);
  }

  @Override
  protected Number validate(UIComponent component, Number value) {
    if (value.doubleValue() > 100.0)
      throw ConverterUtils.throwConverterException(component, "SYS00009");
    return super.validate(component, value.doubleValue() / 100.0);
  }

}
