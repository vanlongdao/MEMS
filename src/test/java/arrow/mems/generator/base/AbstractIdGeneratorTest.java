package arrow.mems.generator.base;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.annotations.Test;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.AddressIdGenerator;
import arrow.mems.generator.CorporateIdGenerator;
import arrow.mems.generator.MeaningCodeGenerator;
import arrow.mems.generator.OfficeIdGenerator;
import arrow.mems.generator.PersonIdGenerator;
import arrow.mems.generator.PresetCategoryCodeGenerator;
import arrow.mems.generator.RoleMasterIdGenerator;
import arrow.mems.test.DeploymentManager;


public class AbstractIdGeneratorTest extends DeploymentManager {
  @Test
  @UsingDataSet({"datasets/base_data.xls"})
  public void test1AddressCodeGenerator() {
    final int countryCode = 1;
    final AddressIdGenerator generator = new AddressIdGenerator(countryCode, 2014);
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo(MemsDataType.ADDRESS + String.format("%03d", countryCode) + "14" + "00001");
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void test2AddressCodeGeneratorHasData() {
    final int countryCode = 4;
    final AddressIdGenerator generator = new AddressIdGenerator(countryCode, 2014);
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo(MemsDataType.ADDRESS + String.format("%03d", countryCode) + "14" + "00005");
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls"})
  public void testCorporateCode() {
    final int countryCode = 1;
    final CorporateIdGenerator generator = new CorporateIdGenerator(countryCode, 2014);
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo(MemsDataType.CORPORATE + String.format("%03d", countryCode) + "14" + "00001");
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGenerateMeaningCode() {
    final MeaningCodeGenerator generator = new MeaningCodeGenerator(2014);
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo(MemsDataType.PRESET_PHRASES_MEANING + "140001");
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls"})
  public void testGeneratePresetCategoryCode() {
    final PresetCategoryCodeGenerator generator = new PresetCategoryCodeGenerator(2014);
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo(MemsDataType.PRESET_PHRASES_CATEGORY + "1401");
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGenerateRoleCode() {
    final RoleMasterIdGenerator generator = new RoleMasterIdGenerator();
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo("24" + "01");
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls"})
  public void testOfficeCode() {
    final int countryCode = 1;
    final OfficeIdGenerator generator = new OfficeIdGenerator(countryCode, 2014);
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo(MemsDataType.OFFICE + String.format("%03d", countryCode) + "14" + "00001");
  }

  @Test
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testPersonCode() {
    final int countryCode = 1;
    final PersonIdGenerator generator = new PersonIdGenerator(countryCode, 2014);
    final String nextCode = generator.getNext();
    Assertions.assertThat(nextCode).isEqualTo(MemsDataType.PERSON + String.format("%03d", countryCode) + "14" + "00015");
  }
}
