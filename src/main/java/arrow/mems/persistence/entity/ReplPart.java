//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import arrow.mems.persistence.mapped.ReplPartMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="REPL_PART")
public class ReplPart extends ReplPartMapped {

  public ReplPart() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "DEVICE_CODE", referencedColumnName = "DEV_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Device device;

  public Device getDevice() {
    return this.device;
  }

  public void setDevice(final Device device) {
    this.device = device;
    this.deviceCode = this.device != null ? this.device.getDevCode() : null;
    if (this.device != null) {
      this.isDeleted = this.device.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "REMOVED_DEV_CODE", referencedColumnName = "DEV_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Device removeDevice;

  public Device getRemoveDevice() {
    return this.removeDevice;
  }

  public void setRemoveDevice(final Device device) {
    this.removeDevice = device;
    this.removedDevCode = this.removeDevice != null ? this.removeDevice.getDevCode() : null;
    if (this.removeDevice != null) {
      this.isDeleted = this.removeDevice.getIsDeleted();
    }
  }

  public String getModelNo() {
    return this.modelNo;
  }

  public void setModelNo(String pModelNo) {
    this.modelNo = pModelNo;
  }

  public String getPartCode() {
    return this.partCode;
  }

  public void setPartCode(String pPartCode) {
    this.partCode = pPartCode;
  }

  @Transient
  private String modelNo;

  @Transient
  private String partCode;

  @Transient
  private String fakeId;

  public String getFakeId() {
    if (this.fakeId == null) {
      this.fakeId = UUID.randomUUID().toString();
    }
    return this.fakeId;
  }

  public void setFakeId(String pFakeId) {
    this.fakeId = pFakeId;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}