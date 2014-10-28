package arrow.mems.generator.base;

import arrow.mems.generator.utils.IdGeneratorUtils;

public abstract class AbstractBaseCountryYearIdGenerator extends AbstractBaseCountryIdGenerator {
  private int twoYearDigits;

  public AbstractBaseCountryYearIdGenerator(int pCountryCode, int year) {
    super(pCountryCode);
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
    return String.format(this.getPrefix(), this.getTypeCode(), this.getCountryCode(), this.getTwoYearDigits());
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
