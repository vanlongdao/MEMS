//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class BookmarkDocMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.BookmarkDoc> getEntityClass() {
    return arrow.mems.persistence.entity.BookmarkDoc.class;
  }
  
  //default constructor
  public BookmarkDocMapped() {
  }
  
  @Column(name="BOOKMARK_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookmark_doc_seq_gen")
  @SequenceGenerator(name = "bookmark_doc_seq_gen", sequenceName = "bookmark_doc_bookmark_id_seq", allocationSize=1)
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
  @Column(name="DOC_TYPE")
  protected java.lang.Integer docType;

  public java.lang.Integer getDocType() {
    return this.docType;
  }

  public void setDocType(java.lang.Integer docType) {
    this.docType = docType;
  }
  @Column(name="LABEL")
  protected java.lang.String label;

  public java.lang.String getLabel() {
    return this.label;
  }

  public void setLabel(java.lang.String label) {
    this.label = label;
  }
  @Column(name="LINK_STRING")
  protected java.lang.String linkString;

  public java.lang.String getLinkString() {
    return this.linkString;
  }

  public void setLinkString(java.lang.String linkString) {
    this.linkString = linkString;
  }
  @Column(name="OWNER_CORP_CODE")
  protected java.lang.String ownerCorpCode;

  public java.lang.String getOwnerCorpCode() {
    return this.ownerCorpCode;
  }

  public void setOwnerCorpCode(java.lang.String ownerCorpCode) {
    this.ownerCorpCode = ownerCorpCode;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}