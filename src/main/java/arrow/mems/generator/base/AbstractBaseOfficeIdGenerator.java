package arrow.mems.generator.base;

import arrow.mems.generator.utils.IdGeneratorUtils;



public abstract class AbstractBaseOfficeIdGenerator extends AbstractIdGenerator {
  private String officeCode;

  public AbstractBaseOfficeIdGenerator(String officeCode) {
    this.officeCode = officeCode;
  }

  public String getOfficeCode() {
    return this.officeCode;
  }

  @Override
  public String getPrefix() {
    return AbstractIdGenerator.TYPE_PATTERN + AbstractIdGenerator.OFFICE_PATTERN;
  }

  @Override
  public int getPrefixLength() {
    return AbstractIdGenerator.TYPE_PATTERN_LEN + AbstractIdGenerator.OFFICE_PATTERN_LEN;
  }

  @Override
  public void validateRequiredParams() {
    IdGeneratorUtils.validateOfficeCode(this.officeCode);
  }

}
