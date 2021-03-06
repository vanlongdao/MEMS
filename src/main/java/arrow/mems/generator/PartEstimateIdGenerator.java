/**
 *
 */
package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.mapped.PartEstimateMapped_;

/**
 * @author tainguyen
 *         format: type(2)+office(12) +yy(2)+serial(7)
 */
public class PartEstimateIdGenerator extends AbstractBaseOfficeYearIdGenerator {


  public PartEstimateIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return PartEstimateMapped_.peCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return PartEstimate.class;
  }

  @Override
  public int getExpectedLength() {
    return 23;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.PART_ESTIMATE;
  }

}
