package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.mapped.MdevChecklistMapped_;

/**
 * The Class MdevChecklistIdGenerator.
 * format code : type(2)+yy(2)+serial(4)
 */
public class MdevChecklistIdGenerator extends AbstractBaseYearIdGenerator {


  public MdevChecklistIdGenerator(int pYear) {
    super(pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return MdevChecklistMapped_.cklistCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return MdevChecklist.class;
  }

  @Override
  public int getExpectedLength() {
    return 8;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.MDEV_CHECKLIST;
  }
}
