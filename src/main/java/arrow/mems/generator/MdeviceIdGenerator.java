package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.mapped.MDeviceMapped_;


/**
 * The Class MdevChecklistIdGenerator.
 * format code : type(2)+yy(2)+serial(4)
 */
public class MdeviceIdGenerator extends AbstractBaseYearIdGenerator {


  public MdeviceIdGenerator(int pYear) {
    super(pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return MDeviceMapped_.mdevCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return MDevice.class;
  }

  @Override
  public int getExpectedLength() {
    return 8;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.MDEVICE;
  }
}
