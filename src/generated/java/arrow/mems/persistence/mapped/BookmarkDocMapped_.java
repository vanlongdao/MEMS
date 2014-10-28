//@formatter:off
package arrow.mems.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BookmarkDocMapped.class)
public class BookmarkDocMapped_ extends AbstractApprovalEntityMapped_ {
  public static volatile SingularAttribute<BookmarkDocMapped, java.lang.Integer> bookmarkId;
  public static volatile SingularAttribute<BookmarkDocMapped, java.lang.String> description;
  public static volatile SingularAttribute<BookmarkDocMapped, java.lang.Integer> documentId;
  public static volatile SingularAttribute<BookmarkDocMapped, java.lang.Integer> docType;
  public static volatile SingularAttribute<BookmarkDocMapped, java.lang.String> label;
  public static volatile SingularAttribute<BookmarkDocMapped, java.lang.String> linkString;
  public static volatile SingularAttribute<BookmarkDocMapped, java.lang.String> ownerCorpCode;
}