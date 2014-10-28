package arrow.mems.faces.converter;

import javax.faces.component.UIComponent;

import arrow.framework.faces.util.ConverterUtils;

public class NonNegativeNumberConverter extends ArrowNumberConverter {

  @Override
  protected Number validate(UIComponent pComponent, Number pValue) {
    if (pValue.doubleValue() < 0.0)
      throw ConverterUtils.throwConverterException(pComponent, "valueMustBeNonNegative");
    return super.validate(pComponent, pValue);
  }
}
