//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BookmarkVideoMapped.class)
public class BookmarkVideoMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<BookmarkVideoMapped, java.lang.Integer> bookmarkId;
  public static volatile SingularAttribute<BookmarkVideoMapped, java.lang.String> description;
  public static volatile SingularAttribute<BookmarkVideoMapped, java.lang.Integer> documentId;
  public static volatile SingularAttribute<BookmarkVideoMapped, java.lang.Double> endSec;
  public static volatile SingularAttribute<BookmarkVideoMapped, java.lang.String> label;
  public static volatile SingularAttribute<BookmarkVideoMapped, java.lang.String> ownerCorpCode;
  public static volatile SingularAttribute<BookmarkVideoMapped, java.lang.Double> startSec;
  public static volatile SingularAttribute<BookmarkVideoMapped, java.lang.Integer> videoType;
}