package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.RoleMaster;
import arrow.mems.persistence.mapped.RoleMasterMapped_;

/**
 * Code format = type(2) + serial(2)
 *
 * @author michael
 *
 */
public class RoleMasterIdGenerator extends AbstractIdGenerator {

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return RoleMasterMapped_.roleCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return RoleMaster.class;
  }

  @Override
  public int getExpectedLength() {
    return 4;
  }

  @Override
  public String getPrefix() {
    return AbstractIdGenerator.TYPE_PATTERN;
  }

  @Override
  public int getPrefixLength() {
    return AbstractIdGenerator.TYPE_PATTERN_LEN;
  }

  @Override
  public String getPrefixValue() {
    return String.format(this.getPrefix(), this.getTypeCode());
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.ROLE_MASTER;
  }

  @Override
  public void validateRequiredParams() {
    // do nothing
  }

}
