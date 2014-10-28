//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DocumentMapped.class)
public class DocumentMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<DocumentMapped, java.lang.Integer> docId;
  public static volatile SingularAttribute<DocumentMapped, java.lang.Integer> country;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> inst01;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> instV;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> mdevCode;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> performance01;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> performanceV;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> replacePartV;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> safety01;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> safetyV;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> service01;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> service02;
  public static volatile SingularAttribute<DocumentMapped, java.lang.String> softwareRev;
  public static volatile SingularAttribute<DocumentMapped, arrow.mems.persistence.entity.MDevice> mDevice;
}