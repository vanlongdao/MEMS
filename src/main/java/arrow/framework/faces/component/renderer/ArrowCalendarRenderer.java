package arrow.framework.faces.component.renderer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.calendar.CalendarRenderer;
import org.primefaces.util.MessageFactory;

import arrow.framework.util.DateUtils;

public class ArrowCalendarRenderer extends CalendarRenderer {

  private String getValueAsString(FacesContext context, Calendar calendar) {
    final Object submittedValue = calendar.getSubmittedValue();
    if (submittedValue != null)
      return submittedValue.toString();

    final Object value = calendar.getValue();
    if (value == null)
      return null;
    else {
      // first ask the converter
      if (calendar.getConverter() != null)
        return calendar.getConverter().getAsString(context, calendar, value);
      else {
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(calendar.calculatePattern(), calendar.calculateLocale(context));
        return dateFormat.withZone(calendar.calculateTimeZone().toZoneId()).format((TemporalAccessor) value);
      }
    }
  }

  private String getTimeOnlyValueAsString(FacesContext context, Calendar calendar) {
    final Object value = calendar.getValue();
    if (value == null)
      return null;

    if (value instanceof String)
      return (String) value;
    else if ((value instanceof TemporalAccessor)) {
      final DateTimeFormatter format = DateTimeFormatter.ofPattern(calendar.calculateTimeOnlyPattern(), calendar.calculateLocale(context));
      return format.withZone(calendar.calculateTimeZone().toZoneId()).format((TemporalAccessor) calendar.getValue());
    } else
      throw new FacesException("Value could be either String or java.time.LocalDate or java.time.LocalTime or java.time.LocalDateTime");
  }

  @Override
  public void encodeEnd(FacesContext pContext, UIComponent pComponent) throws IOException {
    final Calendar calendar = (Calendar) pComponent;
    final String markupValue = this.getValueAsString(pContext, calendar);
    final String widgetValue = calendar.isTimeOnly() ? this.getTimeOnlyValueAsString(pContext, calendar) : markupValue;
    this.encodeMarkup(pContext, calendar, markupValue);
    this.encodeScript(pContext, calendar, widgetValue);

  }

  @Override
  public Object getConvertedValue(FacesContext context, UIComponent component, Object value) throws ConverterException {
    final Calendar calendar = (Calendar) component;
    final Object showTime = component.getAttributes().get("showTime");
    final String submittedValue = (String) value;
    DateTimeFormatter format = null;

    if (this.isValueBlank(submittedValue))
      return null;

    // Delegate to user supplied converter if defined
    try {
      final Converter converter = calendar.getConverter();
      if (converter != null)
        return converter.getAsObject(context, calendar, submittedValue);
    } catch (final ConverterException e) {
      calendar.setConversionFailed(true);

      throw e;
    }

    // Use built-in converter
    format = DateTimeFormatter.ofPattern(calendar.calculatePattern(), calendar.calculateLocale(context));
    format = format.withZone(calendar.calculateTimeZone().toZoneId());
    try {
      final TemporalAccessor parsed = format.parse(submittedValue);
      final Class<? extends Temporal> targetClass =
          (showTime == null) || StringUtils.isEmpty(showTime.toString()) ? LocalDate.class : LocalDateTime.class;
      return DateUtils.convertTimeToTargetClass(parsed, targetClass);
    } catch (final DateTimeParseException e) {
      calendar.setConversionFailed(true);

      FacesMessage message = null;
      final Object[] params = new Object[3];
      params[0] = submittedValue;
      params[1] = format.format(LocalDateTime.now());
      params[2] = MessageFactory.getLabel(context, calendar);

      if (calendar.isTimeOnly()) {
        message = MessageFactory.getMessage("javax.faces.converter.DateTimeConverter.TIME", FacesMessage.SEVERITY_ERROR, params);
      } else if (calendar.hasTime()) {
        message = MessageFactory.getMessage("javax.faces.converter.DateTimeConverter.DATETIME", FacesMessage.SEVERITY_ERROR, params);
      } else {
        message = MessageFactory.getMessage("javax.faces.converter.DateTimeConverter.DATE", FacesMessage.SEVERITY_ERROR, params);
      }

      throw new ConverterException(message);
    }
  }

}
