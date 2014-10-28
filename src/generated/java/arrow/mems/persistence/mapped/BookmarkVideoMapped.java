//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class BookmarkVideoMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.BookmarkVideo> getEntityClass() {
    return arrow.mems.persistence.entity.BookmarkVideo.class;
  }
  
  //default constructor
  public BookmarkVideoMapped() {
  }
  
  @Column(name="BOOKMARK_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookmark_video_seq_gen")
  @SequenceGenerator(name = "bookmark_video_seq_gen", sequenceName = "bookmark_video_bookmark_id_seq", allocationSize=1)
  protected int bookmarkId;
  
  public int getBookmarkId() {
    return this.bookmarkId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.bookmarkId;
  }

  //Normal columns
  @Column(name="DESCRIPTION")
  protected java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }
  @Column(name="DOCUMENT_ID")
  protected int documentId;

  public int getDocumentId() {
    return this.documentId;
  }

  public void setDocumentId(int documentId) {
    this.documentId = documentId;
  }
  @Column(name="END_SEC")
  protected java.lang.Double endSec;

  public java.lang.Double getEndSec() {
    return this.endSec;
  }

  public void setEndSec(java.lang.Double endSec) {
    this.endSec = endSec;
  }
  @Column(name="LABEL")
  protected java.lang.String label;

  public java.lang.String getLabel() {
    return this.label;
  }

  public void setLabel(java.lang.String label) {
    this.label = label;
  }
  @Column(name="OWNER_CORP_CODE")
  protected java.lang.String ownerCorpCode;

  public java.lang.String getOwnerCorpCode() {
    return this.ownerCorpCode;
  }

  public void setOwnerCorpCode(java.lang.String ownerCorpCode) {
    this.ownerCorpCode = ownerCorpCode;
  }
  @Column(name="START_SEC")
  protected java.lang.Double startSec;

  public java.lang.Double getStartSec() {
    return this.startSec;
  }

  public void setStartSec(java.lang.Double startSec) {
    this.startSec = startSec;
  }
  @Column(name="VIDEO_TYPE")
  protected java.lang.Integer videoType;

  public java.lang.Integer getVideoType() {
    return this.videoType;
  }

  public void setVideoType(java.lang.Integer videoType) {
    this.videoType = videoType;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}