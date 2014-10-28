//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PresetPhrasesMapped.class)
public class PresetPhrasesMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<PresetPhrasesMapped, java.lang.Integer> id;
  public static volatile SingularAttribute<PresetPhrasesMapped, java.lang.String> categoryCode;
  public static volatile SingularAttribute<PresetPhrasesMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<PresetPhrasesMapped, java.lang.String> inputStr;
  public static volatile SingularAttribute<PresetPhrasesMapped, java.lang.String> meaningCode;
  public static volatile SingularAttribute<PresetPhrasesMapped, java.lang.String> showStr;
}