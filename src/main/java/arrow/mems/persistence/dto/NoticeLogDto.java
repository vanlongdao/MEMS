//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.NoticeLog;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class NoticeLogDto extends NoticeLog {
  private int noticeId;

  @Override
  public int getNoticeId() {
    return this.noticeId;
  }

  public void setNoticeId(final int noticeId) {
    this.noticeId = noticeId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}