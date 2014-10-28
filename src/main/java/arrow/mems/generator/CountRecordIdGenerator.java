package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.CountRecord;
import arrow.mems.persistence.mapped.CountRecordMapped_;

/*
 * Code = type(2) + country(3) + yy(2) + serial(5)
 */

public class CountRecordIdGenerator extends AbstractBaseOfficeYearIdGenerator {

  public CountRecordIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return CountRecordMapped_.countRecCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return CountRecord.class;
  }

  @Override
  public int getExpectedLength() {
    return 23;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.COUNT_RECORD;
  }
}
