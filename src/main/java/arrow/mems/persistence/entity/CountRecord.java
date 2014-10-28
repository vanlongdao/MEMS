//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.CountRecordMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="COUNT_RECORD")
public class CountRecord extends CountRecordMapped {

  public CountRecord() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "DEV_CODE", referencedColumnName = "DEV_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Device device;

  public arrow.mems.persistence.entity.Device getDevice() {
    return this.device;
  }

  public void setDevice(arrow.mems.persistence.entity.Device office) {
    this.device = office;
    if (this.device != null) {
      this.devCode = this.device.getDevCode();
      this.isDeleted = this.device.getIsDeleted();
    }
  }

  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  