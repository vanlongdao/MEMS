package arrow.mems.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ArrowMoneyConverter extends NonNegativeNumberConverter {
  @Override
  public String getAsString(FacesContext pContext, UIComponent pComponent, Object pValue) {
    // TODO: override to print Currency Code.
    return super.getAsString(pContext, pComponent, pValue);
  }

}
