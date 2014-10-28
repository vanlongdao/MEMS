/**
 *
 */
package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.mapped.PartOrderMapped_;

/**
 * @author tainguyen
 *         format: type(2)+office(12) +yy(2)+serial(7)
 */
public class PartOrderIdGenerator extends AbstractBaseOfficeYearIdGenerator {

  public PartOrderIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return PartOrderMapped_.poCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return PartOrder.class;
  }

  @Override
  public int getExpectedLength() {
    return 23;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.PART_ORDER;
  }

}
