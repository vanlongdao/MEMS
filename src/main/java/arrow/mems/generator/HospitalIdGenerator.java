package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.mapped.HospitalMapped_;

public class HospitalIdGenerator extends AbstractBaseOfficeYearIdGenerator {
  public HospitalIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return HospitalMapped_.hospCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return Hospital.class;
  }

  @Override
  public int getExpectedLength() {
    return 20;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.HOSPITAL;
  }
}
