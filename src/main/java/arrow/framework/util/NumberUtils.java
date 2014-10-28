package arrow.framework.util;

import java.math.BigDecimal;

public class NumberUtils {
  public static Object convertInputType(final String input, final Class<?> type) {
    if (Integer.class.equals(type) || int.class.equals(type))
      return Integer.valueOf(input);
    else if (Double.class.equals(type) || double.class.equals(type))
      return Double.valueOf(input);
    return input;
  }

  public static double round(final Double value, final int decimalPlaces) {
    if (value == null)
      return 0.0;

    return BigDecimal.valueOf(value).setScale(10, BigDecimal.ROUND_HALF_UP).setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP).doubleValue();
  }
}
