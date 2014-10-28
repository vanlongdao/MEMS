/**
 *
 */
package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseCountryYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.mapped.CorporateMapped_;

/**
 * @author tainguyen
 *         Code = country(3) + yy(2)+ serial(5)
 */
public class CorporateIdGenerator extends AbstractBaseCountryYearIdGenerator {

  public CorporateIdGenerator(int pCountryCode, int pYear) {
    super(pCountryCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return CorporateMapped_.corpCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return Corporate.class;
  }

  @Override
  public int getExpectedLength() {
    return 12;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.CORPORATE;
  }

}
