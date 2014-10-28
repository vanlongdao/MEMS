package arrow.mems.generator.base;

import arrow.mems.generator.utils.IdGeneratorUtils;


public abstract class AbstractBaseOfficeYearIdGenerator extends AbstractBaseOfficeIdGenerator {

  private int twoYearDigits;

  public AbstractBaseOfficeYearIdGenerator(String officeCode, int year) {
    super(officeCode);
    this.twoYearDigits = IdGeneratorUtils.getTwoDigitsOfYear(year);
  }

  @Override
  public String getPrefix() {
    return super.getPrefix() + AbstractIdGenerator.YEAR_PATTERN;
  }

  @Override
  public int getPrefixLength() {
    return super.getPrefixLength() + AbstractIdGenerator.YEAR_PATTERN_LEN;
  }

  @Override
  public String getPrefixValue() {
    return String.format(this.getPrefix(), this.getTypeCode(), this.getOfficeCode(), this.getTwoYearDigits());
  }

  public int getTwoYearDigits() {
    return this.twoYearDigits;
  }

  @Override
  public void validateRequiredParams() {
    super.validateRequiredParams();
    IdGeneratorUtils.validateTwoYearDigits(this.twoYearDigits);
  }
}
