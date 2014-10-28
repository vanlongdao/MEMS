package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.persistence.mapped.ScheduleItemMapped_;

public class ScheduleItemIdGenerator extends AbstractBaseOfficeYearIdGenerator {
  public ScheduleItemIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return ScheduleItemMapped_.schedCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return ScheduleItem.class;
  }

  @Override
  public int getExpectedLength() {
    return 21;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.SCHEDULE_ITEM;
  }
}
