package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.mapped.ActionLogMapped_;

/**
 * type(2)+office(12) +yy(2)+serial(8)
 * **/
public class ActionLogIdGenerator extends AbstractBaseOfficeYearIdGenerator {

  public ActionLogIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return ActionLogMapped_.actionCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return ActionLog.class;
  }

  @Override
  public int getExpectedLength() {
    return 24;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.ACTION_LOG;
  }

}
