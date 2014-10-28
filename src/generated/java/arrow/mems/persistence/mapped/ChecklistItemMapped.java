//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ChecklistItemMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ChecklistItem> getEntityClass() {
    return arrow.mems.persistence.entity.ChecklistItem.class;
  }
  
  //default constructor
  public ChecklistItemMapped() {
  }
  
  @Column(name="CKI_LOG_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checklist_item_seq_gen")
  @SequenceGenerator(name = "checklist_item_seq_gen", sequenceName = "checklist_item_cki_log_id_seq", allocationSize=1)
  protected int ckiLogId;
  
  public int getCkiLogId() {
    return this.ckiLogId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.ckiLogId;
  }

  //Normal columns
  @Column(name="CKI_LOG_CODE")
  protected java.lang.String ckiLogCode;

  public java.lang.String getCkiLogCode() {
    return this.ckiLogCode;
  }

  public void setCkiLogCode(java.lang.String ckiLogCode) {
    this.ckiLogCode = ckiLogCode;
  }
  @Column(name="CKLIST_LOG_CODE")
  protected java.lang.String cklistLogCode;

  public java.lang.String getCklistLogCode() {
    return this.cklistLogCode;
  }

  public void setCklistLogCode(java.lang.String cklistLogCode) {
    this.cklistLogCode = cklistLogCode;
  }
  @Column(name="IS_OK")
  protected java.lang.Integer isOk;

  public java.lang.Integer getIsOk() {
    return this.isOk;
  }

  public void setIsOk(java.lang.Integer isOk) {
    this.isOk = isOk;
  }
  @Column(name="REFER_CKI_CODE")
  protected java.lang.String referCkiCode;

  public java.lang.String getReferCkiCode() {
    return this.referCkiCode;
  }

  public void setReferCkiCode(java.lang.String referCkiCode) {
    this.referCkiCode = referCkiCode;
  }
  @Column(name="RESULT_DESCRIPTION")
  protected java.lang.String resultDescription;

  public java.lang.String getResultDescription() {
    return this.resultDescription;
  }

  public void setResultDescription(java.lang.String resultDescription) {
    this.resultDescription = resultDescription;
  }
  @Column(name="RESULT_VALUE")
  protected java.lang.String resultValue;

  public java.lang.String getResultValue() {
    return this.resultValue;
  }

  public void setResultValue(java.lang.String resultValue) {
    this.resultValue = resultValue;
  }
  @Column(name="SERV_PSN")
  protected java.lang.String servPsn;

  public java.lang.String getServPsn() {
    return this.servPsn;
  }

  public void setServPsn(java.lang.String servPsn) {
    this.servPsn = servPsn;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}