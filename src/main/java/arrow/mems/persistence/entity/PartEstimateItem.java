//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.PartEstimateItemMapped;
import arrow.mems.util.FormatUtils;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="PART_ESTIMATE_ITEM")
public class PartEstimateItem extends PartEstimateItemMapped {

  public PartEstimateItem() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "PE_CODE", referencedColumnName = "PE_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected PartEstimate partEstimate;

  public PartEstimate getPartEstimate() {
    return this.partEstimate;
  }

  public void setPartEstimate(PartEstimate pPartEstimate) {
    this.partEstimate = pPartEstimate;
    this.peCode = this.partEstimate != null ? this.partEstimate.getPeCode() : null;
    if (this.partEstimate != null) {
      this.isDeleted = this.partEstimate.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "PART_CODE", referencedColumnName = "MDEV_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.MDevice mDevice;

  public arrow.mems.persistence.entity.MDevice getMDevice() {
    return this.mDevice;
  }

  public void setMDevice(final arrow.mems.persistence.entity.MDevice mDevice) {
    this.mDevice = mDevice;
    this.partCode = this.mDevice != null ? this.mDevice.getMdevCode() : null;

    if (this.mDevice != null) {
      this.isDeleted = this.mDevice.getIsDeleted();
    }

  }

  @Override
  public void setItemPrice(Double price) {
    super.setItemPrice(price);
    if (this.numItems != null) {
      this.setTotPrice(FormatUtils.roundMoney(this.itemPrice * this.numItems));
    }
  }

  @Override
  public void setNumItems(Integer qty) {
    super.setNumItems(qty);
    if (this.itemPrice != null) {
      this.setTotPrice(FormatUtils.roundMoney(this.itemPrice * this.numItems));
    }
  }

  @Override
  public void setTotPrice(Double subtotal) {
    super.setTotPrice(subtotal);
    if (this.taxRate != null) {
      this.setPriceWithTax(FormatUtils.roundMoney(this.totPrice + (this.totPrice * this.taxRate)));
    }
  }

  @Override
  public void setTaxRate(Double tax) {
    super.setTaxRate(tax);
    if (this.totPrice != null) {
      this.setPriceWithTax(FormatUtils.roundMoney(this.totPrice + (this.totPrice * this.taxRate)));
    }
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}