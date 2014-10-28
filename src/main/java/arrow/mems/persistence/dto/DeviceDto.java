//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.Office;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class DeviceDto extends Device {
  private int devId;

  @Override
  public int getDevId() {
    return this.devId;
  }

  public void setDevId(final int devId) {
    this.devId = devId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Override
  @NotNull(message = "{" + MessageCode.MPM00007 + "}")
  public Office getSupplierOffice() {
    return super.getSupplierOffice();
  }

  @Override
  public String getSerialNo() {
    return super.getSerialNo();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MPM00004 + "}")
  public LocalDate getExpireDate() {
    return super.getExpireDate();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MPM00001 + "}")
  public MDevice getMDevice() {
    return super.getMDevice();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MPM00016 + "}")
  public HospitalDept getHospitalDept() {
    return super.getHospitalDept();
  }

  private String mdevModelNo;

  private String mdevCatName;


  public String getMdevCatName() {
    return this.mdevCatName;
  }

  public void setMdevCatName(String pMdevCatName) {
    this.mdevCatName = pMdevCatName;
  }

  public String getMdevModelNo() {
    return this.mdevModelNo;
  }

  public void setMdevModelNo(String pMdevModelNo) {
    this.mdevModelNo = pMdevModelNo;
  }

  public String getMdevName() {
    return this.mdevName;
  }

  public void setMdevName(String pMdevName) {
    this.mdevName = pMdevName;
  }

  private String mdevName;

  private String manufactureName;

  public String getManufactureName() {
    return this.manufactureName;
  }

  public void setManufactureName(String pManufactureName) {
    this.manufactureName = pManufactureName;
  }

  public String getSupplierName() {
    return this.supplierName;
  }

  public void setSupplierName(String pSupplierName) {
    this.supplierName = pSupplierName;
  }

  public String getHospitalDeparmentName() {
    return this.hospitalDeparmentName;
  }

  public void setHospitalDeparmentName(String pHospitalDeparmentName) {
    this.hospitalDeparmentName = pHospitalDeparmentName;
  }

  private String supplierName;

  private String hospitalDeparmentName;

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}