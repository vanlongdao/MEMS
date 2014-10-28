package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseCountryYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.mapped.AddressMapped_;

/*
 * Code = type(2) + country(3) + yy(2) + serial(5)
 */

public class AddressIdGenerator extends AbstractBaseCountryYearIdGenerator {

  public AddressIdGenerator(int pCountryCode, int pYear) {
    super(pCountryCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return AddressMapped_.addrCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return Address.class;
  }

  @Override
  public int getExpectedLength() {
    return 12;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.ADDRESS;
  }
}
