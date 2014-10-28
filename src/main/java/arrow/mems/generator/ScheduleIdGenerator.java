package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseMDeviceIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.Schedule;
import arrow.mems.persistence.mapped.ScheduleMapped_;

/*
 * The Class ScheduleIdGenerator.
 * type(2)+m_device(8) +yy(2)+serial(4)
 */
public class ScheduleIdGenerator extends AbstractBaseMDeviceIdGenerator {

  public ScheduleIdGenerator(String mdeviceCode) {
    super(mdeviceCode);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return ScheduleMapped_.schedBaseCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return Schedule.class;
  }

  @Override
  public int getExpectedLength() {
    return 16;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.SCHEDULE;
  }

  @Override
  public String getPrefixValue() {
    return String.format(this.getPrefix(), this.getTypeCode(), this.getMdeviceCode());
  }

}
