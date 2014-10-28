//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class PartOrderMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.PartOrder> getEntityClass() {
    return arrow.mems.persistence.entity.PartOrder.class;
  }

  //default constructor
  public PartOrderMapped() {
  }

  @Column(name="PO_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_order_seq_gen")
  @SequenceGenerator(name = "part_order_seq_gen", sequenceName = "part_order_po_id_seq", allocationSize=1)
  protected int poId;

  public int getPoId() {
    return this.poId;
  }

  @Override
  public java.lang.Integer getPk() {
    return this.poId;
  }

  //Normal columns
  @Column(name="ACTION_CODE")
  protected java.lang.String actionCode;

  public java.lang.String getActionCode() {
    return this.actionCode;
  }

  public void setActionCode(java.lang.String actionCode) {
    this.actionCode = actionCode;
  }
  @Column(name="DEST_OFFICE")
  protected java.lang.String destOffice;

  public java.lang.String getDestOffice() {
    return this.destOffice;
  }

  public void setDestOffice(java.lang.String destOffice) {
    this.destOffice = destOffice;
  }
  @Column(name="DIST_MGMT_CODE")
  protected java.lang.String distMgmtCode;

  public java.lang.String getDistMgmtCode() {
    return this.distMgmtCode;
  }

  public void setDistMgmtCode(java.lang.String distMgmtCode) {
    this.distMgmtCode = distMgmtCode;
  }
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
  @Column(name="ESTIMATE_CODE")
  protected java.lang.String estimateCode;

  public java.lang.String getEstimateCode() {
    return this.estimateCode;
  }

  public void setEstimateCode(java.lang.String estimateCode) {
    this.estimateCode = estimateCode;
  }
  @Column(name="ETA_DATE")
  protected java.time.LocalDate etaDate;

  public java.time.LocalDate getEtaDate() {
    return this.etaDate;
  }

  public void setEtaDate(java.time.LocalDate etaDate) {
    this.etaDate = etaDate;
  }
  @Column(name="IMAGE_FILE")
  protected byte[] imageFile;

  public byte[] getImageFile() {
    return this.imageFile;
  }

  public void setImageFile(byte[] imageFile) {
    this.imageFile = imageFile;
  }
  @Column(name="ORDER_DATE")
  protected java.time.LocalDate orderDate;

  public java.time.LocalDate getOrderDate() {
    return this.orderDate;
  }

  public void setOrderDate(java.time.LocalDate orderDate) {
    this.orderDate = orderDate;
  }
  @Column(name="PAYMENT_TERMS")
  protected java.lang.String paymentTerms;

  public java.lang.String getPaymentTerms() {
    return this.paymentTerms;
  }

  public void setPaymentTerms(java.lang.String paymentTerms) {
    this.paymentTerms = paymentTerms;
  }
  @Column(name="PAY_DATE")
  protected java.time.LocalDate payDate;

  public java.time.LocalDate getPayDate() {
    return this.payDate;
  }

  public void setPayDate(java.time.LocalDate payDate) {
    this.payDate = payDate;
  }
  @Column(name="PO_CODE")
  protected java.lang.String poCode;

  public java.lang.String getPoCode() {
    return this.poCode;
  }

  public void setPoCode(java.lang.String poCode) {
    this.poCode = poCode;
  }
  @Column(name="P_CCY")
  protected java.lang.Integer pCcy;

  public java.lang.Integer getPCcy() {
    return this.pCcy;
  }

  public void setPCcy(java.lang.Integer pCcy) {
    this.pCcy = pCcy;
  }
  @Column(name="REQUESTER_OFFICE")
  protected java.lang.String requesterOffice;

  public java.lang.String getRequesterOffice() {
    return this.requesterOffice;
  }

  public void setRequesterOffice(java.lang.String requesterOffice) {
    this.requesterOffice = requesterOffice;
  }
  @Column(name="REQUESTER_PSN")
  protected java.lang.String requesterPsn;

  public java.lang.String getRequesterPsn() {
    return this.requesterPsn;
  }

  public void setRequesterPsn(java.lang.String requesterPsn) {
    this.requesterPsn = requesterPsn;
  }
  @Column(name="SERVICE_OFFICE")
  protected java.lang.String serviceOffice;

  public java.lang.String getServiceOffice() {
    return this.serviceOffice;
  }

  public void setServiceOffice(java.lang.String serviceOffice) {
    this.serviceOffice = serviceOffice;
  }
  @Column(name="SERVICE_PSN")
  protected java.lang.String servicePsn;

  public java.lang.String getServicePsn() {
    return this.servicePsn;
  }

  public void setServicePsn(java.lang.String servicePsn) {
    this.servicePsn = servicePsn;
  }
  @Column(name="STATUS")
  protected java.lang.Integer status;

  public java.lang.Integer getStatus() {
    return this.status;
  }

  public void setStatus(java.lang.Integer status) {
    this.status = status;
  }
  @Column(name="TAX")
  protected java.lang.Double tax;

  public java.lang.Double getTax() {
    return this.tax;
  }

  public void setTax(java.lang.Double tax) {
    this.tax = tax;
  }
  @Column(name="TOTAL_PRICE")
  protected java.lang.Double totalPrice;

  public java.lang.Double getTotalPrice() {
    return this.totalPrice;
  }

  public void setTotalPrice(java.lang.Double totalPrice) {
    this.totalPrice = totalPrice;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "ACTION_CODE", referencedColumnName = "ACTION_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.ActionLog actionLog;

  public arrow.mems.persistence.entity.ActionLog getActionLog() {
    return this.actionLog;
  }

  public void setActionLog(final arrow.mems.persistence.entity.ActionLog actionLog) {
    this.actionLog = actionLog;
    this.actionCode = this.actionLog != null ? this.actionLog.getActionCode() : null;

    if(this.actionLog != null){
      this.isDeleted = this.actionLog.getIsDeleted();
    }

  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}