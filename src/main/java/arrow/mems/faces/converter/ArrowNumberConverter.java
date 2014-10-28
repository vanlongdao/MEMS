package arrow.mems.faces.converter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.NumberConverter;

import org.omnifaces.util.Components;

import arrow.framework.faces.util.ConverterUtils;
import arrow.framework.util.Instance;
import arrow.framework.util.i18n.LocaleSelector;
import arrow.mems.messages.MessageCode;

public class ArrowNumberConverter extends NumberConverter {


  /**
   * Subclasses need to provide specific implementation of this method. By default this method
   * assumes that the value is valid.
   *
   * @param component
   * @param value
   * @return
   */
  protected Number validate(final UIComponent component, final Number value) {
    return value;
  }


  private static boolean isConvertToLongType(final String convertTo) {
    return (convertTo != null) && convertTo.trim().equalsIgnoreCase("Long");
  }

  private static boolean isConvertToIntegerType(final String convertTo) {
    return (convertTo != null) && convertTo.trim().equalsIgnoreCase("Integer");
  }


  private Object getAsObjectForEmptyValue(final UIComponent component) {
    final Boolean required = Components.getAttribute(component, "required");
    final String convertTo = Components.getAttribute(component, "convertTo");

    if ((required != null) && required)
      throw ConverterUtils.throwConverterException(component, MessageCode.SYS00002);

    Number value = null;

    if (ArrowNumberConverter.isConvertToLongType(convertTo)) {
      value = new Long(0);
    } else if (ArrowNumberConverter.isConvertToIntegerType(convertTo)) {
      value = new Integer(0);
    } else {
      value = new Double(0);
    }

    return this.validate(component, value);
  }

  @Override
  public Object getAsObject(final FacesContext context, final UIComponent component, final String input) {
    if ((input == null) || (input.trim().length() == 0))
      return this.getAsObjectForEmptyValue(component);


    final String convertTo = Components.getAttribute(component, "convertTo");
    try {
      final DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Instance.get(LocaleSelector.class).getLocale());
      final String noGSInput = input.replace(((Character) dfs.getGroupingSeparator()).toString(), "");
      final double doubleValue = NumberFormat.getInstance().parse(noGSInput).doubleValue();

      if (ArrowNumberConverter.isConvertToLongType(convertTo)) {
        if ((doubleValue > Long.MAX_VALUE) || (doubleValue < Long.MIN_VALUE))
          throw ConverterUtils.throwConverterException(component, MessageCode.SYS00004);

        Long.parseLong(noGSInput);
      } else if (ArrowNumberConverter.isConvertToIntegerType(convertTo)) {
        if ((doubleValue > Integer.MAX_VALUE) || (doubleValue < Integer.MIN_VALUE))
          throw ConverterUtils.throwConverterException(component, MessageCode.SYS00004);

        Integer.parseInt(noGSInput);
      }
    } catch (final NumberFormatException | ParseException e) {
      throw ConverterUtils.throwConverterException(component, e, MessageCode.SYS00003);
    }

    Number converted = null;
    Number rounded = null;

    try {
      converted = (Number) super.getAsObject(context, component, input);
      rounded = (Number) super.getAsObject(context, component, super.getAsString(context, component, converted));
    }

    catch (final ConverterException e) {
      throw ConverterUtils.throwConverterException(component, e, MessageCode.SYS00003);
    }

    if (!converted.equals(rounded))
      throw ConverterUtils.throwConverterException(component, MessageCode.SYS00005);

    if (ArrowNumberConverter.isConvertToLongType(convertTo))
      return converted.longValue();
    else if (ArrowNumberConverter.isConvertToIntegerType(convertTo))
      return this.validate(component, converted.intValue());
    else
      return this.validate(component, converted.doubleValue());
  }

  private static final String ROUNDING_PATTERN_NGS = "###0"; // NGS: no group separator


  protected static String getRoundingPatternNGS(int decimalPlaces) {
    if (decimalPlaces <= 0)
      return ArrowNumberConverter.ROUNDING_PATTERN_NGS;
    String pattern = ArrowNumberConverter.ROUNDING_PATTERN_NGS + ".";
    for (int i = 0; i < decimalPlaces; i++) {
      pattern += "0";
    }
    return pattern;
  }

  public static void main(String[] args) {
    final DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi"));
    // test formatter
    final String pattern = "#" + symbols.getDecimalSeparator() + "00";
    final NumberFormat formatter = new DecimalFormat(pattern, symbols);

    System.out.println(formatter.format(1));
    System.out.println(formatter.format(100));
    System.out.println(formatter.format(10000));
    System.out.println(formatter.format(0.1));
  }
}
