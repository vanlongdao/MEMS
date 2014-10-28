//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class MdevPurchaseMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.MdevPurchase> getEntityClass() {
    return arrow.mems.persistence.entity.MdevPurchase.class;
  }
  
  //default constructor
  public MdevPurchaseMapped() {
  }
  
  @Column(name="MDEV_PURCHASE_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mdev_purchase_seq_gen")
  @SequenceGenerator(name = "mdev_purchase_seq_gen", sequenceName = "mdev_purchase_mdev_purchase_id_seq", allocationSize=1)
  protected int mdevPurchaseId;
  
  public int getMdevPurchaseId() {
    return this.mdevPurchaseId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.mdevPurchaseId;
  }

  //Normal columns
  @Column(name="DIST_OFFICE")
  protected java.lang.String distOffice;

  public java.lang.String getDistOffice() {
    return this.distOffice;
  }

  public void setDistOffice(java.lang.String distOffice) {
    this.distOffice = distOffice;
  }
  @Column(name="DIST_PSN")
  protected java.lang.String distPsn;

  public java.lang.String getDistPsn() {
    return this.distPsn;
  }

  public void setDistPsn(java.lang.String distPsn) {
    this.distPsn = distPsn;
  }
  @Column(name="LEAD_TIME")
  protected java.lang.String leadTime;

  public java.lang.String getLeadTime() {
    return this.leadTime;
  }

  public void setLeadTime(java.lang.String leadTime) {
    this.leadTime = leadTime;
  }
  @Column(name="MDEV_CODE")
  protected java.lang.String mdevCode;

  public java.lang.String getMdevCode() {
    return this.mdevCode;
  }

  public void setMdevCode(java.lang.String mdevCode) {
    this.mdevCode = mdevCode;
  }
  @Column(name="NOTICE")
  protected java.lang.String notice;

  public java.lang.String getNotice() {
    return this.notice;
  }

  public void setNotice(java.lang.String notice) {
    this.notice = notice;
  }
  @Column(name="PAY_COND")
  protected java.lang.String payCond;

  public java.lang.String getPayCond() {
    return this.payCond;
  }

  public void setPayCond(java.lang.String payCond) {
    this.payCond = payCond;
  }
  @Column(name="PRICE")
  protected java.lang.Double price;

  public java.lang.Double getPrice() {
    return this.price;
  }

  public void setPrice(java.lang.Double price) {
    this.price = price;
  }
  @Column(name="PRICE_DATE")
  protected java.time.LocalDate priceDate;

  public java.time.LocalDate getPriceDate() {
    return this.priceDate;
  }

  public void setPriceDate(java.time.LocalDate priceDate) {
    this.priceDate = priceDate;
  }
  @Column(name="PRICE_EVIDENCE_FILE")
  protected byte[] priceEvidenceFile;

  public byte[] getPriceEvidenceFile() {
    return this.priceEvidenceFile;
  }

  public void setPriceEvidenceFile(byte[] priceEvidenceFile) {
    this.priceEvidenceFile = priceEvidenceFile;
  }
  @Column(name="P_CCY")
  protected java.lang.Integer pCcy;

  public java.lang.Integer getPCcy() {
    return this.pCcy;
  }

  public void setPCcy(java.lang.Integer pCcy) {
    this.pCcy = pCcy;
  }
  @Column(name="ROLE_CODE")
  protected java.lang.String roleCode;

  public java.lang.String getRoleCode() {
    return this.roleCode;
  }

  public void setRoleCode(java.lang.String roleCode) {
    this.roleCode = roleCode;
  }
  @Column(name="WHOLESALE_PRICE")
  protected java.lang.Double wholesalePrice;

  public java.lang.Double getWholesalePrice() {
    return this.wholesalePrice;
  }

  public void setWholesalePrice(java.lang.Double wholesalePrice) {
    this.wholesalePrice = wholesalePrice;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}