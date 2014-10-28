package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.mapped.MdevChecklistItemMapped_;


/**
 * The Class MdevChecklistIdGenerator.
 * format code : type(2)+yy(2)+serial(6)
 */
public class MdevChecklistItemIdGenerator extends AbstractBaseYearIdGenerator {



  public MdevChecklistItemIdGenerator(int pYear) {
    super(pYear);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return MdevChecklistItemMapped_.ckiCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return MdevChecklistItem.class;
  }

  @Override
  public int getExpectedLength() {
    return 10;
  }

  @Override
  public String getTypeCode() {
    return MemsDataType.MDEV_CHECKLIST_ITEM;
  }
}
