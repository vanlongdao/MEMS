//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.mapped.MDeviceMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="M_DEVICE")
public class MDevice extends MDeviceMapped {

  public MDevice() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @OneToOne
  @JoinColumns({@JoinColumn(name = "SUPPLIER_CODE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Office supplierOffice;
  @OneToOne
  @JoinColumns({@JoinColumn(name = "SUPPLIER_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Person supplierPerson;

  public arrow.mems.persistence.entity.Office getSupplierOffice() {
    return this.supplierOffice;
  }

  public void setSupplierOffice(arrow.mems.persistence.entity.Office pSupplierOffice) {
    this.supplierOffice = pSupplierOffice;
    if (this.supplierOffice != null) {
      this.supplierCode = this.supplierOffice.getOfficeCode();
      this.isDeleted = this.supplierOffice.getIsDeleted();
    } else {
      this.supplierCode = null;
    }

  }

  public arrow.mems.persistence.entity.Person getSupplierPerson() {
    return this.supplierPerson;
  }

  public void setSupplierPerson(arrow.mems.persistence.entity.Person pSupplierPerson) {
    this.supplierPerson = pSupplierPerson;
    if (this.supplierPerson != null) {
      this.supplierPsn = this.supplierPerson.getPsnCode();
      this.isDeleted = this.supplierPerson.getIsDeleted();
    } else {
      this.supplierPsn = null;
    }
  }

  @OneToOne
  @JoinColumns({@JoinColumn(name = "MANUFACTURER_CODE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Office manufacturerOffice;

  public arrow.mems.persistence.entity.Office getManufacturerOffice() {
    return this.manufacturerOffice;
  }

  public void setManufacturerOffice(final arrow.mems.persistence.entity.Office office) {
    this.manufacturerOffice = office;
    if (this.manufacturerOffice != null) {
      this.manufacturerCode = this.manufacturerOffice.getOfficeCode();
      this.isDeleted = this.manufacturerOffice.getIsDeleted();
    } else {
      this.manufacturerCode = null;
    }

  }

  @OneToOne
  @JoinColumns({@JoinColumn(name = "MANUFACTURER_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Person manufacturerPerson;

  public arrow.mems.persistence.entity.Person getManufacturerPerson() {
    return this.manufacturerPerson;
  }

  public void setManufacturerPerson(final arrow.mems.persistence.entity.Person person) {
    this.manufacturerPerson = person;
    if (this.manufacturerPerson != null) {
      this.manufacturerPsn = this.manufacturerPerson.getPsnCode();
      this.isDeleted = this.manufacturerPerson.getIsDeleted();
    } else {
      this.manufacturerPsn = null;
    }
  }

  public boolean isPartType() {
    return this.mdevType == CommonConstants.MDevTypeConstants.PARTSLIST;
  }

  @Override
  @NotNull(message = MessageCode.MDM00026)
  public Integer getMdevType() {
    return super.getMdevType();
  }



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}