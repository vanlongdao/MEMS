//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class PartEstimateItemMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.PartEstimateItem> getEntityClass() {
    return arrow.mems.persistence.entity.PartEstimateItem.class;
  }
  
  //default constructor
  public PartEstimateItemMapped() {
  }
  
  @Column(name="PEI_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_estimate_item_seq_gen")
  @SequenceGenerator(name = "part_estimate_item_seq_gen", sequenceName = "part_estimate_item_pei_id_seq", allocationSize=1)
  protected int peiId;
  
  public int getPeiId() {
    return this.peiId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.peiId;
  }

  //Normal columns
  @Column(name="ITEM_PRICE")
  protected java.lang.Double itemPrice;

  public java.lang.Double getItemPrice() {
    return this.itemPrice;
  }

  public void setItemPrice(java.lang.Double itemPrice) {
    this.itemPrice = itemPrice;
  }
  @Column(name="NUM_ITEMS")
  protected java.lang.Integer numItems;

  public java.lang.Integer getNumItems() {
    return this.numItems;
  }

  public void setNumItems(java.lang.Integer numItems) {
    this.numItems = numItems;
  }
  @Column(name="PART_CODE")
  protected java.lang.String partCode;

  public java.lang.String getPartCode() {
    return this.partCode;
  }

  public void setPartCode(java.lang.String partCode) {
    this.partCode = partCode;
  }
  @Column(name="PART_MODEL_NO")
  protected java.lang.String partModelNo;

  public java.lang.String getPartModelNo() {
    return this.partModelNo;
  }

  public void setPartModelNo(java.lang.String partModelNo) {
    this.partModelNo = partModelNo;
  }
  @Column(name="PART_NAME")
  protected java.lang.String partName;

  public java.lang.String getPartName() {
    return this.partName;
  }

  public void setPartName(java.lang.String partName) {
    this.partName = partName;
  }
  @Column(name="PE_CODE")
  protected java.lang.String peCode;

  public java.lang.String getPeCode() {
    return this.peCode;
  }

  public void setPeCode(java.lang.String peCode) {
    this.peCode = peCode;
  }
  @Column(name="PRICE_WITH_TAX")
  protected java.lang.Double priceWithTax;

  public java.lang.Double getPriceWithTax() {
    return this.priceWithTax;
  }

  public void setPriceWithTax(java.lang.Double priceWithTax) {
    this.priceWithTax = priceWithTax;
  }
  @Column(name="TAX")
  protected java.lang.Double tax;

  public java.lang.Double getTax() {
    return this.tax;
  }

  public void setTax(java.lang.Double tax) {
    this.tax = tax;
  }
  @Column(name="TAX_RATE")
  protected java.lang.Double taxRate;

  public java.lang.Double getTaxRate() {
    return this.taxRate;
  }

  public void setTaxRate(java.lang.Double taxRate) {
    this.taxRate = taxRate;
  }
  @Column(name="TOT_PRICE")
  protected java.lang.Double totPrice;

  public java.lang.Double getTotPrice() {
    return this.totPrice;
  }

  public void setTotPrice(java.lang.Double totPrice) {
    this.totPrice = totPrice;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}