//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class PartEstimateMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.PartEstimate> getEntityClass() {
    return arrow.mems.persistence.entity.PartEstimate.class;
  }
  
  //default constructor
  public PartEstimateMapped() {
  }
  
  @Column(name="PE_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_estimate_seq_gen")
  @SequenceGenerator(name = "part_estimate_seq_gen", sequenceName = "part_estimate_pe_id_seq", allocationSize=1)
  protected int peId;
  
  public int getPeId() {
    return this.peId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.peId;
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
  @Column(name="CLIENT_SIDE_MGMT_CODE")
  protected java.lang.String clientSideMgmtCode;

  public java.lang.String getClientSideMgmtCode() {
    return this.clientSideMgmtCode;
  }

  public void setClientSideMgmtCode(java.lang.String clientSideMgmtCode) {
    this.clientSideMgmtCode = clientSideMgmtCode;
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
  @Column(name="EXPIRE_DATE")
  protected java.time.LocalDate expireDate;

  public java.time.LocalDate getExpireDate() {
    return this.expireDate;
  }

  public void setExpireDate(java.time.LocalDate expireDate) {
    this.expireDate = expireDate;
  }
  @Column(name="IMAGE_FILE")
  protected byte[] imageFile;

  public byte[] getImageFile() {
    return this.imageFile;
  }

  public void setImageFile(byte[] imageFile) {
    this.imageFile = imageFile;
  }
  @Column(name="NOTICE")
  protected java.lang.String notice;

  public java.lang.String getNotice() {
    return this.notice;
  }

  public void setNotice(java.lang.String notice) {
    this.notice = notice;
  }
  @Column(name="PE_CODE")
  protected java.lang.String peCode;

  public java.lang.String getPeCode() {
    return this.peCode;
  }

  public void setPeCode(java.lang.String peCode) {
    this.peCode = peCode;
  }
  @Column(name="PE_TYPE")
  protected java.lang.String peType;

  public java.lang.String getPeType() {
    return this.peType;
  }

  public void setPeType(java.lang.String peType) {
    this.peType = peType;
  }
  @Column(name="PRINT_DATE")
  protected java.time.LocalDate printDate;

  public java.time.LocalDate getPrintDate() {
    return this.printDate;
  }

  public void setPrintDate(java.time.LocalDate printDate) {
    this.printDate = printDate;
  }
  @Column(name="P_CCY")
  protected java.lang.Integer pCcy;

  public java.lang.Integer getPCcy() {
    return this.pCcy;
  }

  public void setPCcy(java.lang.Integer pCcy) {
    this.pCcy = pCcy;
  }
  @Column(name="RECEIVE_DATE")
  protected java.time.LocalDate receiveDate;

  public java.time.LocalDate getReceiveDate() {
    return this.receiveDate;
  }

  public void setReceiveDate(java.time.LocalDate receiveDate) {
    this.receiveDate = receiveDate;
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
  @Column(name="REQUEST_DATE")
  protected java.time.LocalDate requestDate;

  public java.time.LocalDate getRequestDate() {
    return this.requestDate;
  }

  public void setRequestDate(java.time.LocalDate requestDate) {
    this.requestDate = requestDate;
  }
  @Column(name="REQ_DELIVERY_DATE")
  protected java.time.LocalDate reqDeliveryDate;

  public java.time.LocalDate getReqDeliveryDate() {
    return this.reqDeliveryDate;
  }

  public void setReqDeliveryDate(java.time.LocalDate reqDeliveryDate) {
    this.reqDeliveryDate = reqDeliveryDate;
  }
  @Column(name="REQ_DEST_OFFICE")
  protected java.lang.String reqDestOffice;

  public java.lang.String getReqDestOffice() {
    return this.reqDestOffice;
  }

  public void setReqDestOffice(java.lang.String reqDestOffice) {
    this.reqDestOffice = reqDestOffice;
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
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}