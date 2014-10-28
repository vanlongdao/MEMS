/**
 *
 */
package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseCountryYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.mapped.OfficeMapped_;

/**
 * @author tainguyen
 *         Code = country(3) + yy(2) + serial(5)
 */
public class OfficeIdGenerator extends AbstractBaseCountryYearIdGenerator {

  public OfficeIdGenerator(int pCountryCode, int pYear) {
    super(pCountryCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return OfficeMapped_.officeCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return Office.class;
  }

  @Override
  public int getExpectedLength() {
    return 12;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.OFFICE;
  }
}
