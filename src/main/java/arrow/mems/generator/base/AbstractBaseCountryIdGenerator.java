package arrow.mems.generator.base;

import arrow.mems.generator.utils.IdGeneratorUtils;


public abstract class AbstractBaseCountryIdGenerator extends AbstractIdGenerator {

  private int countryCode;

  public AbstractBaseCountryIdGenerator(int countryCode) {
    this.countryCode = countryCode;
  }

  public int getCountryCode() {
    return this.countryCode;
  }

  @Override
  public String getPrefix() {
    return AbstractIdGenerator.TYPE_PATTERN + AbstractIdGenerator.COUNTRY_ID_PATTERN;
  }

  @Override
  public int getPrefixLength() {
    return AbstractIdGenerator.TYPE_PATTERN_LEN + AbstractIdGenerator.COUNTRY_PATTERN_LEN;
  }

  @Override
  public void validateRequiredParams() {
    IdGeneratorUtils.validateCountryCode(this.countryCode);
  }
}
