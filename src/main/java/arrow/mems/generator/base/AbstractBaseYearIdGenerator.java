package arrow.mems.generator.base;

import arrow.mems.generator.utils.IdGeneratorUtils;

public abstract class AbstractBaseYearIdGenerator extends AbstractIdGenerator {
  private int twoYearDigits;

  public AbstractBaseYearIdGenerator(int year) {
    this.twoYearDigits = IdGeneratorUtils.getTwoDigitsOfYear(year);
  }

  @Override
  public String getPrefix() {
    return AbstractIdGenerator.TYPE_PATTERN + AbstractIdGenerator.YEAR_PATTERN;
  }

  @Override
  public int getPrefixLength() {
    return AbstractIdGenerator.TYPE_PATTERN_LEN + AbstractIdGenerator.YEAR_PATTERN_LEN;
  }

  @Override
  public String getPrefixValue() {
    return String.format(this.getPrefix(), this.getTypeCode(), this.getTwoYearDigits());
  }

  public int getTwoYearDigits() {
    return this.twoYearDigits;
  }

  public void setTwoYearDigits(int pTwoYearDigits) {
    this.twoYearDigits = pTwoYearDigits;
  }

  @Override
  public void validateRequiredParams() {
    IdGeneratorUtils.validateTwoYearDigits(this.twoYearDigits);
  }
}
