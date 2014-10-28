//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ApprovalSummaryMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ApprovalSummary> getEntityClass() {
    return arrow.mems.persistence.entity.ApprovalSummary.class;
  }
  
  //default constructor
  public ApprovalSummaryMapped() {
  }
  
  @Column(name="APPROVAL_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_summary_seq_gen")
  @SequenceGenerator(name = "approval_summary_seq_gen", sequenceName = "approval_summary_approval_id_seq", allocationSize=1)
  protected int approvalId;
  
  public int getApprovalId() {
    return this.approvalId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.approvalId;
  }

  //Normal columns
  @Column(name="ITEM_CODE")
  protected java.lang.String itemCode;

  public java.lang.String getItemCode() {
    return this.itemCode;
  }

  public void setItemCode(java.lang.String itemCode) {
    this.itemCode = itemCode;
  }
  @Column(name="ITEM_LABEL")
  protected java.lang.String itemLabel;

  public java.lang.String getItemLabel() {
    return this.itemLabel;
  }

  public void setItemLabel(java.lang.String itemLabel) {
    this.itemLabel = itemLabel;
  }
  @Column(name="LEVEL_ID")
  protected int levelId;

  public int getLevelId() {
    return this.levelId;
  }

  public void setLevelId(int levelId) {
    this.levelId = levelId;
  }
  @Column(name="REQUESTOR")
  protected int requestor;

  public int getRequestor() {
    return this.requestor;
  }

  public void setRequestor(int requestor) {
    this.requestor = requestor;
  }
  @Column(name="REQUEST_AT")
  protected java.time.LocalDateTime requestAt;

  public java.time.LocalDateTime getRequestAt() {
    return this.requestAt;
  }

  public void setRequestAt(java.time.LocalDateTime requestAt) {
    this.requestAt = requestAt;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "LEVEL_ID", referencedColumnName = "LEVEL_ID", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.ApprovalLevel approvalLevel;

  public arrow.mems.persistence.entity.ApprovalLevel getApprovalLevel() {
    return this.approvalLevel;
  }

  public void setApprovalLevel(final arrow.mems.persistence.entity.ApprovalLevel approvalLevel) {
    this.approvalLevel = approvalLevel;
    if(this.approvalLevel != null){
      this.levelId = this.approvalLevel.getLevelId();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}