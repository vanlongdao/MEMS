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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class PartOrderItemMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.PartOrderItem> getEntityClass() {
    return arrow.mems.persistence.entity.PartOrderItem.class;
  }

  //default constructor
  public PartOrderItemMapped() {
  }

  @Column(name="POI_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_order_item_seq_gen")
  @SequenceGenerator(name = "part_order_item_seq_gen", sequenceName = "part_order_item_poi_id_seq", allocationSize=1)
  protected int poiId;

  public int getPoiId() {
    return this.poiId;
  }

  @Override
  public java.lang.Integer getPk() {
    return this.poiId;
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
  @Column(name="PO_CODE")
  protected java.lang.String poCode;

  public java.lang.String getPoCode() {
    return this.poCode;
  }

  public void setPoCode(java.lang.String poCode) {
    this.poCode = poCode;
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
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "PART_CODE", referencedColumnName = "MDEV_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.MDevice mDevice;

  public arrow.mems.persistence.entity.MDevice getMDevice() {
    return this.mDevice;
  }

  public void setMDevice(final arrow.mems.persistence.entity.MDevice mDevice) {
    this.mDevice = mDevice;
    this.partCode = this.mDevice != null ? this.mDevice.getMdevCode() : null;

    if(this.mDevice != null){
      this.isDeleted = this.mDevice.getIsDeleted();
    }

  }

  @OneToOne
  @JoinColumns({
    @JoinColumn(name = "PO_CODE", referencedColumnName = "PO_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.PartOrder partOrder;

  public arrow.mems.persistence.entity.PartOrder getPartOrder() {
    return this.partOrder;
  }

  public void setPartOrder(final arrow.mems.persistence.entity.PartOrder partOrder) {
    this.partOrder = partOrder;
    this.poCode = this.partOrder != null ? this.partOrder.getPoCode() : null;

    if(this.partOrder != null){
      this.isDeleted = this.partOrder.getIsDeleted();
    }
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}