//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import arrow.mems.constant.AuthenticationConstants;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.mapped.UsersMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="USERS")
public class Users extends UsersMapped {

  public Users() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  // Foreign keys
  @ManyToOne
  @JoinColumns({@JoinColumn(name = "OFFICE_CODE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  @NotNull(message = "{" + MessageCode.MAU00028 + "}")
  protected arrow.mems.persistence.entity.Office office;

  public arrow.mems.persistence.entity.Office getOffice() {
    return this.office;
  }

  public void setOffice(final arrow.mems.persistence.entity.Office office) {
    this.office = office;

    if (this.office != null) {
      this.officeCode = this.office.getOfficeCode();
      this.isDeleted = this.office.getIsDeleted();
    }
  }

  @OneToOne
  @JoinColumns({@JoinColumn(name = "PSN_CODE", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Person person;

  public arrow.mems.persistence.entity.Person getPerson() {
    return this.person;
  }

  public void setPerson(final arrow.mems.persistence.entity.Person person) {
    this.person = person;

    if (this.person != null) {
      this.psnCode = this.person.getPsnCode();
      this.isDeleted = this.person.getIsDeleted();
    }
  }

  public boolean isSuppervisor() {
    if (this.isSupervisor == AuthenticationConstants.SUPPERVISOR)
      return true;
    return false;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}