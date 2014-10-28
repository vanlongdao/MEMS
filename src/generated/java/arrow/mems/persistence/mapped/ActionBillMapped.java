//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ActionBillMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ActionBill> getEntityClass() {
    return arrow.mems.persistence.entity.ActionBill.class;
  }
  
  //default constructor
  public ActionBillMapped() {
  }
  
  @Column(name="ACTION_BILL_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_bill_seq_gen")
  @SequenceGenerator(name = "action_bill_seq_gen", sequenceName = "action_bill_action_bill_id_seq", allocationSize=1)
  protected int actionBillId;
  
  public int getActionBillId() {
    return this.actionBillId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.actionBillId;
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
  @Column(name="CONTRACT_CODE")
  protected java.lang.String contractCode;

  public java.lang.String getContractCode() {
    return this.contractCode;
  }

  public void setContractCode(java.lang.String contractCode) {
    this.contractCode = contractCode;
  }
  @Column(name="DISCOUNT_BY_CONTRACT")
  protected java.lang.Double discountByContract;

  public java.lang.Double getDiscountByContract() {
    return this.discountByContract;
  }

  public void setDiscountByContract(java.lang.Double discountByContract) {
    this.discountByContract = discountByContract;
  }
  @Column(name="FEE_DIAGNOSIS")
  protected java.lang.Double feeDiagnosis;

  public java.lang.Double getFeeDiagnosis() {
    return this.feeDiagnosis;
  }

  public void setFeeDiagnosis(java.lang.Double feeDiagnosis) {
    this.feeDiagnosis = feeDiagnosis;
  }
  @Column(name="FEE_HOTEL")
  protected java.lang.Double feeHotel;

  public java.lang.Double getFeeHotel() {
    return this.feeHotel;
  }

  public void setFeeHotel(java.lang.Double feeHotel) {
    this.feeHotel = feeHotel;
  }
  @Column(name="FEE_OTHER")
  protected java.lang.Double feeOther;

  public java.lang.Double getFeeOther() {
    return this.feeOther;
  }

  public void setFeeOther(java.lang.Double feeOther) {
    this.feeOther = feeOther;
  }
  @Column(name="FEE_PARTS")
  protected java.lang.Double feeParts;

  public java.lang.Double getFeeParts() {
    return this.feeParts;
  }

  public void setFeeParts(java.lang.Double feeParts) {
    this.feeParts = feeParts;
  }
  @Column(name="FEE_SHIPPING")
  protected java.lang.Double feeShipping;

  public java.lang.Double getFeeShipping() {
    return this.feeShipping;
  }

  public void setFeeShipping(java.lang.Double feeShipping) {
    this.feeShipping = feeShipping;
  }
  @Column(name="FEE_TECHNICAL")
  protected java.lang.Double feeTechnical;

  public java.lang.Double getFeeTechnical() {
    return this.feeTechnical;
  }

  public void setFeeTechnical(java.lang.Double feeTechnical) {
    this.feeTechnical = feeTechnical;
  }
  @Column(name="FEE_VISIT")
  protected java.lang.Double feeVisit;

  public java.lang.Double getFeeVisit() {
    return this.feeVisit;
  }

  public void setFeeVisit(java.lang.Double feeVisit) {
    this.feeVisit = feeVisit;
  }
  @Column(name="PAY_DATE")
  protected java.time.LocalDate payDate;

  public java.time.LocalDate getPayDate() {
    return this.payDate;
  }

  public void setPayDate(java.time.LocalDate payDate) {
    this.payDate = payDate;
  }
  @Column(name="P_CCY")
  protected java.lang.Integer pCcy;

  public java.lang.Integer getPCcy() {
    return this.pCcy;
  }

  public void setPCcy(java.lang.Integer pCcy) {
    this.pCcy = pCcy;
  }
  @Column(name="TAX")
  protected java.lang.Double tax;

  public java.lang.Double getTax() {
    return this.tax;
  }

  public void setTax(java.lang.Double tax) {
    this.tax = tax;
  }
  @Column(name="TOTAL")
  protected java.lang.Double total;

  public java.lang.Double getTotal() {
    return this.total;
  }

  public void setTotal(java.lang.Double total) {
    this.total = total;
  }
  @Column(name="TOTAL_PAY")
  protected java.lang.Double totalPay;

  public java.lang.Double getTotalPay() {
    return this.totalPay;
  }

  public void setTotalPay(java.lang.Double totalPay) {
    this.totalPay = totalPay;
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