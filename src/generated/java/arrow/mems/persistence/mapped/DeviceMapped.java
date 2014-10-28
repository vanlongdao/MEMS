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
import javax.persistence.SequenceGenerator;

import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class DeviceMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Device> getEntityClass() {
    return arrow.mems.persistence.entity.Device.class;
  }
  
  //default constructor
  public DeviceMapped() {
  }
  
  @Column(name="DEV_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_seq_gen")
  @SequenceGenerator(name = "device_seq_gen", sequenceName = "device_dev_id_seq", allocationSize=1)
  protected int devId;
  
  public int getDevId() {
    return this.devId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.devId;
  }

  //Normal columns
  @Column(name="ACCEPTANCE_DATE")
  protected java.time.LocalDate acceptanceDate;

  public java.time.LocalDate getAcceptanceDate() {
    return this.acceptanceDate;
  }

  public void setAcceptanceDate(java.time.LocalDate acceptanceDate) {
    this.acceptanceDate = acceptanceDate;
  }
  @Column(name="ACQ_CODE")
  protected java.lang.String acqCode;

  public java.lang.String getAcqCode() {
    return this.acqCode;
  }

  public void setAcqCode(java.lang.String acqCode) {
    this.acqCode = acqCode;
  }
  @Column(name="CLIENT_MGMT_CODE")
  protected java.lang.String clientMgmtCode;

  public java.lang.String getClientMgmtCode() {
    return this.clientMgmtCode;
  }

  public void setClientMgmtCode(java.lang.String clientMgmtCode) {
    this.clientMgmtCode = clientMgmtCode;
  }
  @Column(name="DEV_CODE")
  protected java.lang.String devCode;

  public java.lang.String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(java.lang.String devCode) {
    this.devCode = devCode;
  }
  @Column(name="EXPIRE_DATE")
  protected java.time.LocalDate expireDate;

  public java.time.LocalDate getExpireDate() {
    return this.expireDate;
  }

  public void setExpireDate(java.time.LocalDate expireDate) {
    this.expireDate = expireDate;
  }
  @Column(name="HOSP_DEPT_CODE")
  protected java.lang.String hospDeptCode;

  public java.lang.String getHospDeptCode() {
    return this.hospDeptCode;
  }

  public void setHospDeptCode(java.lang.String hospDeptCode) {
    this.hospDeptCode = hospDeptCode;
  }
  @Column(name="IMAGE_FILE")
  protected byte[] imageFile;

  public byte[] getImageFile() {
    return this.imageFile;
  }

  public void setImageFile(byte[] imageFile) {
    this.imageFile = imageFile;
  }
  @Column(name="INSTALL_DATE")
  protected java.time.LocalDate installDate;

  public java.time.LocalDate getInstallDate() {
    return this.installDate;
  }

  public void setInstallDate(java.time.LocalDate installDate) {
    this.installDate = installDate;
  }
  @Column(name="LAST_SERVICE_OFFICE")
  protected java.lang.String lastServiceOffice;

  public java.lang.String getLastServiceOffice() {
    return this.lastServiceOffice;
  }

  public void setLastServiceOffice(java.lang.String lastServiceOffice) {
    this.lastServiceOffice = lastServiceOffice;
  }
  @Column(name="LAST_SERVICE_PSN")
  protected java.lang.String lastServicePsn;

  public java.lang.String getLastServicePsn() {
    return this.lastServicePsn;
  }

  public void setLastServicePsn(java.lang.String lastServicePsn) {
    this.lastServicePsn = lastServicePsn;
  }
  @Column(name="LOCATION")
  protected java.lang.String location;

  public java.lang.String getLocation() {
    return this.location;
  }

  public void setLocation(java.lang.String location) {
    this.location = location;
  }
  @Column(name="LOCATION_IMAGE")
  protected byte[] locationImage;

  public byte[] getLocationImage() {
    return this.locationImage;
  }

  public void setLocationImage(byte[] locationImage) {
    this.locationImage = locationImage;
  }
  @Column(name="MDEV_CODE")
  protected java.lang.String mdevCode;

  public java.lang.String getMdevCode() {
    return this.mdevCode;
  }

  public void setMdevCode(java.lang.String mdevCode) {
    this.mdevCode = mdevCode;
  }
  @Column(name="NO_SERIAL_CONFIRM")
  protected java.lang.Integer noSerialConfirm;

  public java.lang.Integer getNoSerialConfirm() {
    return this.noSerialConfirm;
  }

  public void setNoSerialConfirm(java.lang.Integer noSerialConfirm) {
    this.noSerialConfirm = noSerialConfirm;
  }
  @Column(name="PRICE")
  protected java.lang.Double price;

  public java.lang.Double getPrice() {
    return this.price;
  }

  public void setPrice(java.lang.Double price) {
    this.price = price;
  }
  @Column(name="P_CCY")
  protected java.lang.Integer pCcy;

  public java.lang.Integer getPCcy() {
    return this.pCcy;
  }

  public void setPCcy(java.lang.Integer pCcy) {
    this.pCcy = pCcy;
  }
  @Column(name="SERIAL_NO")
  protected java.lang.String serialNo;

  public java.lang.String getSerialNo() {
    return this.serialNo;
  }

  public void setSerialNo(java.lang.String serialNo) {
    this.serialNo = serialNo;
  }
  @Column(name="SOFTWARE_REV")
  protected java.lang.String softwareRev;

  public java.lang.String getSoftwareRev() {
    return this.softwareRev;
  }

  public void setSoftwareRev(java.lang.String softwareRev) {
    this.softwareRev = softwareRev;
  }
  @Column(name="SUPLLIER_OFFICE")
  protected java.lang.String supllierOffice;

  public java.lang.String getSupllierOffice() {
    return this.supllierOffice;
  }

  public void setSupllierOffice(java.lang.String supllierOffice) {
    this.supllierOffice = supllierOffice;
  }
  @Column(name="SUPPLIER_PSN")
  protected java.lang.String supplierPsn;

  public java.lang.String getSupplierPsn() {
    return this.supplierPsn;
  }

  public void setSupplierPsn(java.lang.String supplierPsn) {
    this.supplierPsn = supplierPsn;
  }
  @Column(name="TARGET_DEV_CODE")
  protected java.lang.String targetDevCode;

  public java.lang.String getTargetDevCode() {
    return this.targetDevCode;
  }

  public void setTargetDevCode(java.lang.String targetDevCode) {
    this.targetDevCode = targetDevCode;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "MDEV_CODE", referencedColumnName = "MDEV_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.MDevice mDevice;

  public arrow.mems.persistence.entity.MDevice getMDevice() {
    return this.mDevice;
  }

  public void setMDevice(final arrow.mems.persistence.entity.MDevice mDevice) {
    this.mDevice = mDevice;
    this.mdevCode = this.mDevice != null ? this.mDevice.getMdevCode() : null;
    
    if(this.mDevice != null){
      this.isDeleted = this.mDevice.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}