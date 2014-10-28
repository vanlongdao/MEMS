package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.mapped.DeviceMapped_;

public class DeviceIdGenerator extends AbstractBaseOfficeYearIdGenerator {
  public DeviceIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return DeviceMapped_.devCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return Device.class;
  }

  @Override
  public int getExpectedLength() {
    return 22;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.DEVICE;
  }
}
