//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SearchConditionMapped.class)
public class SearchConditionMapped_ extends AbstractDeletableMapped_ {
  public static volatile SingularAttribute<SearchConditionMapped, java.lang.Integer> searchCondId;
  public static volatile SingularAttribute<SearchConditionMapped, java.lang.String> condition;
  public static volatile SingularAttribute<SearchConditionMapped, java.lang.Integer> condFormatVer;
  public static volatile SingularAttribute<SearchConditionMapped, java.lang.Integer> creatorDisplay;
  public static volatile SingularAttribute<SearchConditionMapped, java.lang.String> label;
  public static volatile SingularAttribute<SearchConditionMapped, java.lang.String> officeCode;
}