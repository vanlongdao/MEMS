//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.DeviceMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="DEVICE")
public class Device  extends DeviceMapped implements Cloneable{

  public Device() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "SUPLLIER_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Office supplierOffice;

  public arrow.mems.persistence.entity.Office getSupplierOffice() {
    return this.supplierOffice;
  }

  public void setSupplierOffice(arrow.mems.persistence.entity.Office office) {
    this.supplierOffice = office;
    if (this.supplierOffice != null) {
      this.supllierOffice = this.supplierOffice.getOfficeCode();
      this.isDeleted = this.supplierOffice.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "LAST_SERVICE_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Office repairedByOffice;

  public arrow.mems.persistence.entity.Office getRepairedByOffice() {
    return this.repairedByOffice;
  }

  public void setRepairedByOffice(arrow.mems.persistence.entity.Office repairedByOfficer) {
    this.repairedByOffice = repairedByOfficer;
    if (this.repairedByOffice != null) {
      this.lastServiceOffice = this.repairedByOffice.getOfficeCode();
      this.isDeleted = this.repairedByOffice.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "SUPPLIER_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Person supplierPerson;

  public arrow.mems.persistence.entity.Person getSupplierPerson() {
    return this.supplierPerson;
  }

  public void setSupplierPerson(arrow.mems.persistence.entity.Person supplierPerson) {
    this.supplierPerson = supplierPerson;
    if (this.supplierPerson != null) {
      this.supplierPsn = this.supplierPerson.getPsnCode();
      this.isDeleted = this.supplierPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "LAST_SERVICE_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Person repairedPerson;

  public arrow.mems.persistence.entity.Person getRepairedPerson() {
    return this.repairedPerson;
  }

  public void setRepairedPerson(arrow.mems.persistence.entity.Person repairedPerson) {
    this.repairedPerson = repairedPerson;
    if (this.repairedPerson != null) {
      this.lastServicePsn = this.repairedPerson.getPsnCode();
      this.isDeleted = this.repairedPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "HOSP_DEPT_CODE", referencedColumnName = "DEPT_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.HospitalDept hospitalDept;

  public arrow.mems.persistence.entity.HospitalDept getHospitalDept() {
    return this.hospitalDept;
  }

  public void setHospitalDept(arrow.mems.persistence.entity.HospitalDept hospitalDept) {
    this.hospitalDept = hospitalDept;
    if (this.hospitalDept != null) {
      this.hospDeptCode = this.hospitalDept.getDeptCode();
      this.isDeleted = this.hospitalDept.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "SOFTWARE_REV", referencedColumnName = "SOFTWARE_REV", insertable = false, updatable = false),
      @JoinColumn(name = "MDEV_CODE", referencedColumnName = "MDEV_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Document softwareRevison;

  public arrow.mems.persistence.entity.Document getSoftwareRevison() {
    return this.softwareRevison;
  }

  public void setSoftwareRevison(arrow.mems.persistence.entity.Document softwareRevison) {
    this.softwareRevison = softwareRevison;
    if (this.softwareRevison != null) {
      this.softwareRev = this.softwareRevison.getSoftwareRev();
      this.isDeleted = this.softwareRevison.getIsDeleted();
      this.mdevCode = this.softwareRevison.getMdevCode();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "ACQ_CODE", referencedColumnName = "ACQ_CODE", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.AcquisitionMaster acquiMaster;

  public arrow.mems.persistence.entity.AcquisitionMaster getAcquiMaster() {
    return this.acquiMaster;
  }

  public void setAcquiMaster(arrow.mems.persistence.entity.AcquisitionMaster acquiMaster) {
    this.acquiMaster = acquiMaster;
    if (this.acquiMaster != null) {
      this.acqCode = this.acquiMaster.getAcqCode();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "TARGET_DEV_CODE", referencedColumnName = "DEV_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Device targetDevice;

  public arrow.mems.persistence.entity.Device getTargetDevice() {
    return this.targetDevice;
  }

  public void setTargetDevice(arrow.mems.persistence.entity.Device targetDevice) {
    this.targetDevice = targetDevice;
    if (this.targetDevice != null) {
      this.targetDevCode = this.targetDevice.getDevCode();
    }
  }

  public String getMasterDeviceName() {
    if (this.targetDevice != null)
      return this.targetDevice.getMDevice().getName();
    return null;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}