package arrow.mems.faces.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.primefaces.util.MessageFactory;

import arrow.framework.util.DateUtils;
import arrow.mems.constant.CommonConstants;

public class TimeConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext pContext, UIComponent component, String value) {
    final String submittedValue = value;
    DateTimeFormatter format = null;

    if (StringUtils.isEmpty(submittedValue))
      return null;
    final Object showTime = component.getAttributes().get("showTime");
    final String pattern =
        component.getAttributes().get("pattern") == null ? CommonConstants.DAY_MONTH_YEAR_FORMAT : component.getAttributes().get("pattern")
            .toString();

    format = DateTimeFormatter.ofPattern(pattern, Faces.getLocale());
    try {
      final TemporalAccessor parsed = format.parse(submittedValue);
      final Class<? extends Temporal> targetClass =
          (showTime == null) || StringUtils.isEmpty(showTime.toString()) ? LocalDate.class : LocalDateTime.class;
      return DateUtils.convertTimeToTargetClass(parsed, targetClass);
    } catch (final DateTimeParseException e) {
      FacesMessage message = null;
      final Object[] params = new Object[3];
      params[0] = submittedValue;
      params[1] = format.format(LocalDateTime.now());
      params[2] = MessageFactory.getLabel(pContext, component);
      message = MessageFactory.getMessage("javax.faces.converter.DateTimeConverter.DATETIME", FacesMessage.SEVERITY_ERROR, params);
      throw new ConverterException(message);
    }
  }

  @Override
  public String getAsString(FacesContext pContext, UIComponent pComponent, Object value) {
    if (value == null)
      return null;
    else {
      final String pattern = pComponent.getAttributes().get("pattern").toString();
      final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern, Faces.getLocale());
      return dateFormat.format((TemporalAccessor) value);
    }
  }

}
