package arrow.mems.util;

import arrow.framework.util.NumberUtils;
import arrow.mems.config.AppConfig;

public class FormatUtils {
  public static Double roundMoney(Double value) {
    return NumberUtils.round(value, AppConfig.MONEY_DECIMAL_PLACES);
  }

  public static Double roundPercent(Double value) {
    return NumberUtils.round(value, AppConfig.PERCENT_DECIMAL_PLACES);
  }
}
