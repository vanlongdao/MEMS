//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.PartOrderItemMapped;
import arrow.mems.util.FormatUtils;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="PART_ORDER_ITEM")
public class PartOrderItem extends PartOrderItemMapped {

  public PartOrderItem() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */


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