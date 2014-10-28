package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.mapped.HospitalDeptMapped_;

public class HospitalDeptIdGenerator extends AbstractBaseOfficeYearIdGenerator {
  public HospitalDeptIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return HospitalDeptMapped_.deptCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return HospitalDept.class;
  }

  @Override
  public int getExpectedLength() {
    return 21;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.HOSPITAL_DEPARTMENT;
  }
}
