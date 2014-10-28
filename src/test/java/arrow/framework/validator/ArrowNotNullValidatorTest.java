package arrow.framework.validator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.testng.annotations.Test;

@Test
public class ArrowNotNullValidatorTest {
  @Test(enabled = true)
  public void testMethod() {
    final ClassToTest testInstance = new ClassToTest();
    testInstance.method(null);
    testInstance.method("1");

  }

  private class ClassToTest {
    public void method(@NotNull @Size(min = 2) String value) {
      System.out.println(value);
    }
  }
}
