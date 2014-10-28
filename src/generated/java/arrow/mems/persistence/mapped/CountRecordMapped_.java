//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CountRecordMapped.class)
public class CountRecordMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<CountRecordMapped, java.lang.Integer> countRecordId;
  public static volatile SingularAttribute<CountRecordMapped, java.lang.String> cklistCode;
  public static volatile SingularAttribute<CountRecordMapped, java.lang.String> countBaseCode;
  public static volatile SingularAttribute<CountRecordMapped, java.lang.String> countRecCode;
  public static volatile SingularAttribute<CountRecordMapped, java.lang.String> devCode;
  public static volatile SingularAttribute<CountRecordMapped, java.lang.String> hospDeptCode;
  public static volatile SingularAttribute<CountRecordMapped, java.lang.Double> rawValue;
  public static volatile SingularAttribute<CountRecordMapped, java.time.LocalDateTime> recordAt;
  public static volatile SingularAttribute<CountRecordMapped, java.lang.String> unit;
}