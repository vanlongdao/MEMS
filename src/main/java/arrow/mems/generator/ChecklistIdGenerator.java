/**
 *
 */
package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.mapped.ChecklistMapped_;

/**
 * @author tainguyen
 *
 */
public class ChecklistIdGenerator extends AbstractBaseOfficeYearIdGenerator {

  public ChecklistIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return ChecklistMapped_.cklistLogCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return Checklist.class;
  }

  @Override
  public int getExpectedLength() {
    return 23;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.CHECK;
  }

}
