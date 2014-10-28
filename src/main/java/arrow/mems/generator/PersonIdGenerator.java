package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseCountryYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.mapped.PersonMapped_;

public class PersonIdGenerator extends AbstractBaseCountryYearIdGenerator {
  public PersonIdGenerator(int pCountryCode, int pYear) {
    super(pCountryCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return PersonMapped_.psnCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return Person.class;
  }

  @Override
  public int getExpectedLength() {
    return 12;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.PERSON;
  }
}
