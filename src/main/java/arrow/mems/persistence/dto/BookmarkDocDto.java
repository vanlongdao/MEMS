//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.BookmarkDoc;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class BookmarkDocDto extends BookmarkDoc {
  private int bookmarkId;

  @Override
  public int getBookmarkId() {
    return this.bookmarkId;
  }

  public void setBookmarkId(final int bookmarkId) {
    this.bookmarkId = bookmarkId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}