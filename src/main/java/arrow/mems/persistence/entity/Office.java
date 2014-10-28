//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import arrow.mems.persistence.mapped.OfficeMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="OFFICE")
public class Office extends OfficeMapped {

  public Office() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "MANAGER_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Person managerPerson;

  public Person getManagerPerson() {
    return this.managerPerson;
  }

  public void setManagerPerson(final Person managerPerson) {
    this.managerPerson = managerPerson;
    this.managerPsn = this.managerPerson != null ? this.managerPerson.getPsnCode() : null;
    if (this.managerPerson != null) {
      this.isDeleted = this.managerPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "ACCOUNTANT_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Person accountantPerson;

  public Person getAccountantPerson() {
    return this.accountantPerson;
  }

  public void setAccountantPerson(final Person accountantPerson) {
    this.accountantPerson = accountantPerson;
    this.accountantPsn = this.accountantPerson != null ? this.accountantPerson.getPsnCode() : null;
    if (this.accountantPerson != null) {
      this.isDeleted = this.accountantPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "TECHNICIAN_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Person technicianPerson;

  public Person getTechnicianPerson() {
    return this.technicianPerson;
  }

  public void setTechnicianPerson(final Person technicianPerson) {
    this.technicianPerson = technicianPerson;
    this.technicianPsn = this.technicianPerson != null ? this.technicianPerson.getPsnCode() : null;
    if (this.technicianPerson != null) {
      this.isDeleted = this.technicianPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "EQUIPMENT_MGR_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Person equipmentPerson;

  public Person getEquipmentPerson() {
    return this.equipmentPerson;
  }

  public void setEquipmentPerson(final Person equipmentPerson) {
    this.equipmentPerson = equipmentPerson;
    this.equipmentMgrPsn = this.equipmentPerson != null ? this.equipmentPerson.getPsnCode() : null;
    if (this.equipmentPerson != null) {
      this.isDeleted = this.equipmentPerson.getIsDeleted();
    }
  }

  public String getFullNameSupplier() {
    final StringBuffer fullNameSupplier = new StringBuffer();
    fullNameSupplier.append(this.getName());
    if (StringUtils.isNotEmpty(this.getTel()) && (this.getTel() != null)) {
      fullNameSupplier.append(", " + this.getTel());
    }
    if (StringUtils.isNotEmpty(this.getFax()) && (this.getFax() != null)) {
      fullNameSupplier.append(", " + this.getFax());
    }
    return fullNameSupplier.toString();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}