package arrow.mems.faces.converter;

import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;

import arrow.mems.config.AppConfig;

public class ArrowConverterFactory {
  public static Converter getQuatityConverter() {
    final NonNegativeNumberConverter converter = new NonNegativeNumberConverter();
    converter.setPattern(ArrowNumberConverter.getRoundingPatternNGS(AppConfig.QUATITY_DECIMAL_PLACES));
    return converter;
  }

  public static Converter getMoneyConverter() {
    final NumberConverter converter = new ArrowMoneyConverter();
    converter.setPattern(ArrowNumberConverter.getRoundingPatternNGS(AppConfig.MONEY_DECIMAL_PLACES));
    return converter;
  }

  public static Converter getPercentConverter() {
    final NumberConverter converter = new PercentConverter();
    converter.setPattern(ArrowNumberConverter.getRoundingPatternNGS(AppConfig.PERCENT_DECIMAL_PLACES));
    return converter;
  }
}
