//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.PartsListMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="PARTS_LIST")
public class PartsList extends PartsListMapped {

  public PartsList() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  
  @ManyToOne
  @JoinColumns({@JoinColumn(name = "MDEV_CODE", referencedColumnName = "MDEV_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.MDevice mDevice;

  public arrow.mems.persistence.entity.MDevice getMDevice() {
    return this.mDevice;
  }

  public void setMDevice(final arrow.mems.persistence.entity.MDevice mDevice) {
    this.mDevice = mDevice;
    this.mdevCode = this.mDevice != null ? this.mDevice.getMdevCode() : null;
    if (this.mDevice != null) {
      this.isDeleted = this.mDevice.getIsDeleted();
    }
  }

  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  