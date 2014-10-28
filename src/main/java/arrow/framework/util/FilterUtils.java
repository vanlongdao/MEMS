package arrow.framework.util;

import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

import arrow.mems.constant.CommonConstants;

public class FilterUtils {
  private static final DateTimeFormatter FULL_DATE_FORMAT_FOR_FILTERS = DateTimeFormatter.ofPattern(CommonConstants.DAY_MONTH_YEAR_FORMAT);
  private static final DateTimeFormatter YEAR_MONTH_FORMAT_FOR_FILTERS = DateTimeFormatter.ofPattern(CommonConstants.MONTH_YEAR_FORMAT);
  private static final DateTimeFormatter YEAR_ONLY_FORMAT_FOR_FILTERS = DateTimeFormatter.ofPattern(CommonConstants.YEAR_FORMAT);
  private static final String MULTIPLE_RANGES_SEPARATOR = ",";
  private static final String RANGE_FROM_TO_SEPARATOR = "-";

  /**
   *
   * @param Multiple date ranges separated by commas (,) .
   * @return List of ranges, each range is a Date[2] from-date to-date array
   * @throws ParseException
   */
  public static List<LocalDate[]> parseMultipleDateRanges(String s) throws ParseException {
    if ((s == null) || (s = s.trim()).isEmpty())
      return null;


    final List<LocalDate[]> dateRanges = new ArrayList<LocalDate[]>();

    final String[] ss = s.split(",");

    for (final String singleRangeString : ss) {
      final LocalDate[] dateRange = FilterUtils.parseSingleDateRange(singleRangeString);
      if (dateRange != null) {
        dateRanges.add(dateRange);
      }
    }

    return dateRanges;
  }


  /**
   * @param s: Single date range either in year-only, or year-month only representation, or two date
   *        representation connected by a dash (-)
   * @return A date range as a Date[2] from-date to-date array
   * @throws ParseException
   */
  public static LocalDate[] parseSingleDateRange(String s) throws ParseException {
    if ((s == null) || (s = s.trim()).isEmpty())
      return null;

    if (s.contains("-")) {
      final LocalDate[] dateRange = new LocalDate[2];
      final String[] ss = s.split("\\-");

      if (ss.length != 2)
        throw new ParseException(s, 0);

      final String fromDateString = ss[0];
      final String toDateString = ss[1];


      final LocalDate[] fromDates = FilterUtils.parseImplicitDateRange(fromDateString);
      final LocalDate[] toDates = FilterUtils.parseImplicitDateRange(toDateString);

      if ((fromDates == null) || (toDates == null))
        throw new ParseException(s, 0);

      dateRange[0] = fromDates[0];
      dateRange[1] = toDates[1];

      return dateRange;
    } else
      return FilterUtils.parseImplicitDateRange(s);
  }


  /**
   * @param s: Single date range in year-only or year-month only representation
   * @return A date range as a Date[2] from-date to-date array
   * @throws ParseException
   */
  public static LocalDate[] parseImplicitDateRange(String s) throws ParseException {
    if ((s == null) || (s = s.trim()).isEmpty())
      return null;

    final LocalDate[] dateRange = new LocalDate[2];
    int type = 0; // 1: yyyy, 2: yyyy/MM, 3: yyyy/MM/dd
    TemporalAccessor dateValue = null;
    try {
      type++;
      dateValue = FilterUtils.YEAR_ONLY_FORMAT_FOR_FILTERS.parse(s);
    } catch (final DateTimeException e) {
      try {
        type++;
        dateValue = FilterUtils.YEAR_MONTH_FORMAT_FOR_FILTERS.parse(s);
      } catch (final DateTimeException e1) {
        try {
          type++;
          dateValue = FilterUtils.FULL_DATE_FORMAT_FOR_FILTERS.parse(s);
        } catch (final DateTimeException e2) {
          // just ignore it.
        }
      }
    }



    if (type == 3) {
      final LocalDate date = LocalDate.from(dateValue);
      dateRange[0] = date;
      dateRange[1] = date;
    }

    else if (type == 2) {
      // yyyy/MM: from 1st of month to the last day of month
      final LocalDate date = LocalDate.of(dateValue.query(Year::from).getValue(), dateValue.query(Month::from), 1);
      dateRange[0] = date;
      dateRange[1] = date.withDayOfMonth(date.lengthOfMonth());
    }

    else if (type == 1) {
      // yyyy: from 1st day of year to the latest day of year
      final LocalDate date = LocalDate.of(dateValue.query(Year::from).getValue(), Month.JANUARY, 1);
      dateRange[0] = date;
      dateRange[1] = date.withDayOfYear(date.lengthOfYear());
    } else
      throw new ParseException(s, 0);


    return dateRange;
  }

  // http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
  private static final String SPLIT_BY_COMMAS_EXCEPT_THOSE_IN_QUOTE = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

  public static String[] parseStringForLikeOperator(String s) {
    if ((s == null) || (s = s.trim()).isEmpty())
      return null;

    final String[] ss = s.split(FilterUtils.SPLIT_BY_COMMAS_EXCEPT_THOSE_IN_QUOTE);

    for (int i = 0; i < ss.length; i++) {
      ss[i] = ss[i].trim().toLowerCase().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
      if (ss[i].startsWith("\"") && ss[i].endsWith("\"")) {
        ss[i] = ss[i].substring(1, ss[i].length() - 1);
      }

      ss[i] = "%" + ss[i] + "%";
    }

    return ss;
  }

  private static final String INFINITY = "f";

  public static List<Double[]> parseMultipleNumberRanges(String s) throws ParseException {
    if ((s == null) || (s = s.trim()).isEmpty())
      return null;
    final List<Double[]> numberRanges = new ArrayList<Double[]>();

    final String[] ss = s.split(FilterUtils.MULTIPLE_RANGES_SEPARATOR);

    for (final String singleRangeString : ss) {
      final Double[] numberRange = FilterUtils.parseSingleNumberRange(singleRangeString);
      if (numberRange != null) {
        numberRanges.add(numberRange);
      }
    }

    return numberRanges;
  }

  public static Double[] parseSingleNumberRange(String s) throws ParseException {
    if ((s == null) || (s = s.trim()).isEmpty())
      return null;
    final Double[] numberRange = new Double[2];
    if (s.lastIndexOf(FilterUtils.RANGE_FROM_TO_SEPARATOR) > 0) { // If "-" is not the first char,
      // then this is a range
      String tmpString = s;
      String startNegativeSign = ""; // Save the negative sign, in case the filter is not started
      // with infinity, we must added it back later
      if (tmpString.startsWith(FilterUtils.RANGE_FROM_TO_SEPARATOR)) {
        startNegativeSign = FilterUtils.RANGE_FROM_TO_SEPARATOR;
        tmpString = tmpString.substring(1);
      }
      final String[] ss = tmpString.split("-", 2);

      if (ss.length != 2)
        throw new ParseException(s, 0);

      final String fromNumberString = ss[0];
      final String toNumberString = ss[1];

      final Double fromNumber =
          FilterUtils.INFINITY.equalsIgnoreCase(fromNumberString) ? null : Double.parseDouble(startNegativeSign + fromNumberString);
      final Double toNumber = FilterUtils.INFINITY.equalsIgnoreCase(toNumberString) ? null : Double.parseDouble(toNumberString);

      if ((fromNumber == null) && (toNumber == null))
        throw new ParseException(s, 0);

      numberRange[0] = fromNumber;
      numberRange[1] = toNumber;
    }

    else { // This is a single number, mean equal
      final double number = Double.parseDouble(s);
      numberRange[0] = number;
      numberRange[1] = number;
    }
    return numberRange;
  }

  public static boolean filterDate(final LocalDate valueDate, final String filter) throws ParseException {
    final List<LocalDate[]> dateRangeList = FilterUtils.parseMultipleDateRanges(filter);
    for (final LocalDate[] dateRange : dateRangeList) {
      if (dateRange[0].equals(dateRange[1])) {
        if (dateRange[0].equals(valueDate))
          return true;
      } else {
        if ((dateRange[0].isBefore(valueDate)) && (dateRange[1].isAfter(valueDate)))
          return true;
      }
    }
    return false;
  }

  public static boolean filterNumber(final Number valueNumber, final String filter) throws ParseException {
    final double modelValue = valueNumber.doubleValue();
    final List<Double[]> numberRangeList = FilterUtils.parseMultipleNumberRanges(filter);
    for (final Double[] numberRange : numberRangeList) {
      if (numberRange[0] == null) { // From negative infinity to numberRange[1]
        if (numberRange[1] >= modelValue)
          return true;
      } else if (numberRange[1] == null) { // From numberRange[0] to infinity
        if (numberRange[0] <= modelValue)
          return true;
      } else if (numberRange[0].equals(numberRange[1])) { // Equal
        if (numberRange[0] == modelValue)
          return true;
      } else { // Between
        if ((numberRange[0] <= modelValue) && (modelValue <= numberRange[1]))
          return true;
      }
    }
    return false;
  }

  /**
   * Check if the item fail the filter constraint
   *
   * @param item: the object to check
   * @param field: the name of JavaBean properties
   * @param value: the filter value
   * @return
   */
  public static boolean itemFailedFilterConstraint(final Object item, final String field, final Object value) {
    // TODO: Check if the item fail the filter constraint
    return false;
  }
}
