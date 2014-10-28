package arrow.mems.generator.base;

import arrow.mems.generator.utils.IdGeneratorUtils;

public abstract class AbstractBaseMDeviceIdGenerator extends AbstractIdGenerator {
  private String mdeviceCode;

  public AbstractBaseMDeviceIdGenerator(String mdeviceCode) {
    this.mdeviceCode = mdeviceCode;
  }

  public String getMdeviceCode() {
    return this.mdeviceCode;
  }

  @Override
  public String getPrefix() {
    return AbstractIdGenerator.TYPE_PATTERN + AbstractIdGenerator.MDEVICE_PATTERN;
  }

  @Override
  public int getPrefixLength() {
    return AbstractIdGenerator.TYPE_PATTERN_LEN + AbstractIdGenerator.MDEVICE_PATTERN_LEN;
  }

  @Override
  public void validateRequiredParams() {
    IdGeneratorUtils.validateMdevCode(this.mdeviceCode);
  }

}
