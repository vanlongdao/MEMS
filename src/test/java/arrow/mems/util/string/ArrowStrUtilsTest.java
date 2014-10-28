package arrow.mems.util.string;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;


public class ArrowStrUtilsTest {
  @Test
  public void testLikePattern() {
    final String input = "Bao Ke 1";
    final String output = ArrowStrUtils.likePattern(input);
    Assertions.assertThat(output).isEqualTo("%BAO KE 1%");
  }
}
