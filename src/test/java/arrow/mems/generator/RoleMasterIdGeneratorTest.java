package arrow.mems.generator;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.annotations.Test;

public class RoleMasterIdGeneratorTest extends Arquillian {
  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGenerateCode() {
    final RoleMasterIdGenerator generator = new RoleMasterIdGenerator();
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo("24" + "01");
  }
}
