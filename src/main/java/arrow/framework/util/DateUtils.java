package arrow.framework.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class DateUtils {

  public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
  public static final LocalDate MAX_DATE = LocalDate.of(9999, 12, 31);

  public static boolean isValidDate(final LocalDate date) {
    if (date == null)
      return false;
    if (DateUtils.MIN_DATE.isAfter(date) || DateUtils.MAX_DATE.isBefore(date))
      return false;
    return true;
  }

  public static LocalDate[] parseDateRange(final String range) {
    final String[] tokens = range.split("-");
    if ((tokens.length != 1) && (tokens.length != 2))
      return null;
    final LocalDate dateFrom = LocalDate.parse(tokens[0].trim());
    if (!DateUtils.isValidDate(dateFrom))
      return null;

    final LocalDate dateTo = tokens.length == 2 ? LocalDate.parse(tokens[1]) : dateFrom;
    if (!DateUtils.isValidDate(dateTo))
      return null;
    if (dateFrom.isAfter(dateTo))
      return null;
    return new LocalDate[] {dateFrom, dateTo};
  }


  public static Date convertJavaTimeToJavaDate(LocalDateTime time) {
    return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date convertLocalDateToDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDate convertDateToLocalDate(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static int compareDate(final Date startDate, final Date endDate) {
    if (startDate.compareTo(endDate) > 0)
      return 1;
    else if (startDate.compareTo(endDate) < 0)
      return -1;
    else if (startDate.compareTo(endDate) == 0)
      return 0;
    return -1;
  }

  @SuppressWarnings("unchecked")
  public static <T extends Temporal> T convertTimeToTargetClass(TemporalAccessor timeValue, Class<T> targetClass) throws IllegalArgumentException {
    Assert.notNull(timeValue, "timeValue cannot be null");
    Assert.notNull(targetClass, "targetClass cannot be null");

    if (targetClass.equals(LocalDate.class))
      return (T) timeValue.query(LocalDate::from);
    else if (targetClass.equals(LocalDateTime.class))
      return (T) timeValue.query(LocalDateTime::from);
    else if (targetClass.equals(LocalTime.class))
      return (T) timeValue.query(LocalTime::from);
    else
      throw new IllegalArgumentException("Could not convert time value:" + timeValue.toString() + "to " + targetClass.getName());

  }

  public static boolean isOverlapping(final LocalDate start, final LocalDate end, final LocalDate rangeBegin, final LocalDate rangeEnd) {
    final LocalDate start1 = (start != null ? start : DateUtils.MIN_DATE);
    final LocalDate end1 = (end != null ? end : DateUtils.MAX_DATE);
    final LocalDate start2 = (rangeBegin != null ? rangeBegin : DateUtils.MIN_DATE);
    final LocalDate end2 = (rangeEnd != null ? rangeEnd : DateUtils.MAX_DATE);
    if (DateUtils.isDateInRange(start1, start2, end2) || DateUtils.isDateInRange(end1, start2, end2))
      return true;
    if ((start1.isBefore(start2) && end1.isAfter(end2)) || (start1.isAfter(start2) && end1.isBefore(end2)))
      return true;
    return false;
  }

  public static boolean isDateInRange(final LocalDate date, final LocalDate dateFrom, final LocalDate dateTo) {
    return ((date != null) && (dateFrom != null) && (date.equals(dateFrom) || date.equals(dateTo) || (date.isAfter(dateFrom) && ((dateTo == null) || date
        .isBefore(dateTo)))));
  }


  public static LocalDate getFirstDateOfCurrentMonth() {
    return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
  }

  public static LocalDate getLastDateOfCurrentMonth() {
    return LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
  }

  public static LocalDate getNextDateFromCurrentDay(int number) {
    return LocalDate.now().plusDays(number);
  }

  public static LocalDate getNextDateFromFirstDayOfMonth(int number) {
    return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1).plusDays(number);
  }

  public static LocalDate getFirstDayOfCurrentYear() {
    return LocalDate.of(LocalDate.now().getYear(), 1, 1);
  }

  public static LocalDate getLastDayOfCurrentYear() {
    return LocalDate.of(LocalDate.now().getYear(), 12, LocalDate.MAX.getDayOfMonth());
  }

  public static LocalDate getDateByInputNumber(int year, int month, int dayOfMonth) {
    return LocalDate.of(year, month, dayOfMonth);
  }

  public static int minusTwoDateReturnNumber(LocalDate fromDate, LocalDate toDate) {
    int numDate = 0;
    if (fromDate.getYear() == toDate.getYear()) {
      numDate = fromDate.getDayOfYear() - toDate.getDayOfYear();
    } else {
      final int date1 = LocalDate.of(fromDate.getYear(), 12, LocalDate.MAX.getDayOfMonth()).getDayOfYear() - fromDate.getDayOfYear();
      final int date2 = toDate.getDayOfYear() - LocalDate.of(toDate.getYear(), 1, 1).getDayOfYear();
      numDate = date1 + date2;
    }
    return numDate;
  }
}
