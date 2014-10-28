package arrow.framework.util;

import java.text.ParseException;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;


public class FilterUtilsTest {
  @Test
  public void testParseDateRange() throws ParseException {
    final String input = "09/09/2013";
    final LocalDate[] parsed = FilterUtils.parseImplicitDateRange(input);
    Assertions.assertThat(parsed[0].getYear()).isEqualTo(2013);
    Assertions.assertThat(parsed[0].getMonthValue()).isEqualTo(9);
    Assertions.assertThat(parsed[0].getDayOfMonth()).isEqualTo(9);

    Assertions.assertThat(parsed[1].getYear()).isEqualTo(2013);
    Assertions.assertThat(parsed[1].getMonthValue()).isEqualTo(9);
    Assertions.assertThat(parsed[1].getDayOfMonth()).isEqualTo(9);
  }

  @Test
  public void testParseDateRangeMonthYear() throws ParseException {
    final String input = "09/2013";
    final LocalDate[] parsed = FilterUtils.parseImplicitDateRange(input);
    Assertions.assertThat(parsed[0].getYear()).isEqualTo(2013);
    Assertions.assertThat(parsed[0].getMonthValue()).isEqualTo(9);
    Assertions.assertThat(parsed[0].getDayOfMonth()).isEqualTo(1);

    Assertions.assertThat(parsed[1].getYear()).isEqualTo(2013);
    Assertions.assertThat(parsed[1].getMonthValue()).isEqualTo(9);
    Assertions.assertThat(parsed[1].getDayOfMonth()).isEqualTo(30);
  }

  @Test
  public void testParseDateRangeYear() throws ParseException {
    final String input = "2013";
    final LocalDate[] parsed = FilterUtils.parseImplicitDateRange(input);
    Assertions.assertThat(parsed[0].getYear()).isEqualTo(2013);
    Assertions.assertThat(parsed[0].getMonthValue()).isEqualTo(1);
    Assertions.assertThat(parsed[0].getDayOfMonth()).isEqualTo(1);

    Assertions.assertThat(parsed[1].getYear()).isEqualTo(2013);
    Assertions.assertThat(parsed[1].getMonthValue()).isEqualTo(12);
    Assertions.assertThat(parsed[1].getDayOfMonth()).isEqualTo(31);
  }
}
