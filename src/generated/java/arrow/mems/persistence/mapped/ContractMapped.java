//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ContractMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Contract> getEntityClass() {
    return arrow.mems.persistence.entity.Contract.class;
  }
  
  //default constructor
  public ContractMapped() {
  }
  
  @Column(name="CONTRACT_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_seq_gen")
  @SequenceGenerator(name = "contract_seq_gen", sequenceName = "contract_contract_id_seq", allocationSize=1)
  protected int contractId;
  
  public int getContractId() {
    return this.contractId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.contractId;
  }

  //Normal columns
  @Column(name="CONTRACT_CODE")
  protected java.lang.String contractCode;

  public java.lang.String getContractCode() {
    return this.contractCode;
  }

  public void setContractCode(java.lang.String contractCode) {
    this.contractCode = contractCode;
  }
  @Column(name="CONTRACT_DATE")
  protected java.time.LocalDate contractDate;

  public java.time.LocalDate getContractDate() {
    return this.contractDate;
  }

  public void setContractDate(java.time.LocalDate contractDate) {
    this.contractDate = contractDate;
  }
  @Column(name="CONTRACT_TYPE")
  protected java.lang.String contractType;

  public java.lang.String getContractType() {
    return this.contractType;
  }

  public void setContractType(java.lang.String contractType) {
    this.contractType = contractType;
  }
  @Column(name="DESCRIPTION")
  protected java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }
  @Column(name="END_DATE")
  protected java.time.LocalDate endDate;

  public java.time.LocalDate getEndDate() {
    return this.endDate;
  }

  public void setEndDate(java.time.LocalDate endDate) {
    this.endDate = endDate;
  }
  @Column(name="FEE_DISCOUNT_PRICE")
  protected java.lang.Double feeDiscountPrice;

  public java.lang.Double getFeeDiscountPrice() {
    return this.feeDiscountPrice;
  }

  public void setFeeDiscountPrice(java.lang.Double feeDiscountPrice) {
    this.feeDiscountPrice = feeDiscountPrice;
  }
  @Column(name="FEE_DISCOUNT_RATE")
  protected java.lang.Double feeDiscountRate;

  public java.lang.Double getFeeDiscountRate() {
    return this.feeDiscountRate;
  }

  public void setFeeDiscountRate(java.lang.Double feeDiscountRate) {
    this.feeDiscountRate = feeDiscountRate;
  }
  @Column(name="HOSP_CODE")
  protected java.lang.String hospCode;

  public java.lang.String getHospCode() {
    return this.hospCode;
  }

  public void setHospCode(java.lang.String hospCode) {
    this.hospCode = hospCode;
  }
  @Column(name="NAME")
  protected java.lang.String name;

  public java.lang.String getName() {
    return this.name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }
  @Column(name="PART_DISCOUNT_PRICE")
  protected java.lang.Double partDiscountPrice;

  public java.lang.Double getPartDiscountPrice() {
    return this.partDiscountPrice;
  }

  public void setPartDiscountPrice(java.lang.Double partDiscountPrice) {
    this.partDiscountPrice = partDiscountPrice;
  }
  @Column(name="PART_DISCOUNT_RATE")
  protected java.lang.Double partDiscountRate;

  public java.lang.Double getPartDiscountRate() {
    return this.partDiscountRate;
  }

  public void setPartDiscountRate(java.lang.Double partDiscountRate) {
    this.partDiscountRate = partDiscountRate;
  }
  @Column(name="PRICE")
  protected java.lang.Double price;

  public java.lang.Double getPrice() {
    return this.price;
  }

  public void setPrice(java.lang.Double price) {
    this.price = price;
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
  @Column(name="START_DATE")
  protected java.time.LocalDate startDate;

  public java.time.LocalDate getStartDate() {
    return this.startDate;
  }

  public void setStartDate(java.time.LocalDate startDate) {
    this.startDate = startDate;
  }
  @Column(name="TAX")
  protected java.lang.Double tax;

  public java.lang.Double getTax() {
    return this.tax;
  }

  public void setTax(java.lang.Double tax) {
    this.tax = tax;
  }
  @Column(name="TOT_DISCOUNT_PRICE")
  protected java.lang.Double totDiscountPrice;

  public java.lang.Double getTotDiscountPrice() {
    return this.totDiscountPrice;
  }

  public void setTotDiscountPrice(java.lang.Double totDiscountPrice) {
    this.totDiscountPrice = totDiscountPrice;
  }
  @Column(name="TOT_DISCOUNT_RATE")
  protected java.lang.Double totDiscountRate;

  public java.lang.Double getTotDiscountRate() {
    return this.totDiscountRate;
  }

  public void setTotDiscountRate(java.lang.Double totDiscountRate) {
    this.totDiscountRate = totDiscountRate;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}