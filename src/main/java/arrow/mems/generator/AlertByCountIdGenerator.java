package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseMDeviceIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.AlertByCount;
import arrow.mems.persistence.mapped.AlertByCountMapped_;

/*
 * The Class AlertByCountIdGenerator.
 * type(2)+m_device(8) +yy(2)+serial(4)
 */
public class AlertByCountIdGenerator extends AbstractBaseMDeviceIdGenerator {
  public AlertByCountIdGenerator(String pMdeviceCode) {
    super(pMdeviceCode);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return AlertByCountMapped_.counterBaseCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return AlertByCount.class;
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
