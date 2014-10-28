/**
 *
 */
package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseOfficeYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.mapped.ChecklistItemMapped_;

/**
 * @author tainguyen
 *
 */
public class ChecklistItemIdGenerator extends AbstractBaseOfficeYearIdGenerator {

  public ChecklistItemIdGenerator(String pOfficeCode, int pYear) {
    super(pOfficeCode, pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return ChecklistItemMapped_.ckiLogCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return ChecklistItem.class;
  }

  @Override
  public int getExpectedLength() {
    return 23;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.CHECK_ITEM;
  }
}
