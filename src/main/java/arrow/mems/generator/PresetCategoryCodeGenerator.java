package arrow.mems.generator;

import javax.persistence.metamodel.SingularAttribute;

import arrow.mems.constant.MemsDataType;
import arrow.mems.generator.base.AbstractBaseYearIdGenerator;
import arrow.mems.persistence.entity.AbstractEntity;
import arrow.mems.persistence.entity.PresetPhrases;
import arrow.mems.persistence.mapped.PresetPhrasesMapped_;

public class PresetCategoryCodeGenerator extends AbstractBaseYearIdGenerator {

  public PresetCategoryCodeGenerator(int pTwoYearDigits) {
    super(pTwoYearDigits);
  }

  @Override
  public SingularAttribute<?, String> getCodeField() {
    return PresetPhrasesMapped_.categoryCode;
  }

  @Override
  public Class<? extends AbstractEntity> getEntityClass() {
    return PresetPhrases.class;
  }

  @Override
  public int getExpectedLength() {
    return 6;
  }


  @Override
  public String getTypeCode() {
    return MemsDataType.PRESET_PHRASES_CATEGORY;
  }

}
