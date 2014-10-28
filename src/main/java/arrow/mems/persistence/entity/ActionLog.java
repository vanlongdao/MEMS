//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.mapped.ActionLogMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="ACTION_LOG")
public class ActionLog extends ActionLogMapped {

  public ActionLog() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "ACTION_CODE", referencedColumnName = "ACTION_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.ActionBill actionBill;

  public arrow.mems.persistence.entity.ActionBill getActionBill() {
    return this.actionBill;
  }

  public void setActionBill(final arrow.mems.persistence.entity.ActionBill actionBill) {
    this.actionBill = actionBill;
    this.actionCode = this.actionBill != null ? this.actionBill.getActionCode() : null;

    if (this.actionBill != null) {
      this.isDeleted = this.actionBill.getIsDeleted();
    }

  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "ACTION_TYPE", referencedColumnName = "ACTION_TYPE_CODE", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.ActionTypeMaster actionTypeMaster;

  public arrow.mems.persistence.entity.ActionTypeMaster getActionTypeMaster() {
    return this.actionTypeMaster;
  }

  public void setActionTypeMaster(final arrow.mems.persistence.entity.ActionTypeMaster actionTypeMaster) {
    this.actionTypeMaster = actionTypeMaster;
    if (this.actionTypeMaster != null) {
      this.actionType = this.actionTypeMaster.getActionTypeCode();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "HOSP_CONTACT_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Person hospitalContactPerson;

  public arrow.mems.persistence.entity.Person getHospitalContactPerson() {
    return this.hospitalContactPerson;
  }

  public void setHospitalContactPerson(arrow.mems.persistence.entity.Person pHospitalContactPerson) {
    this.hospitalContactPerson = pHospitalContactPerson;
    if (this.hospitalContactPerson != null) {
      this.hospContactPsn = this.hospitalContactPerson.getPsnCode();
      this.isDeleted = this.hospitalContactPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "CONTACT_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Person contactPerson;

  public arrow.mems.persistence.entity.Person getContactPerson() {
    return this.contactPerson;
  }

  public void setContactPerson(arrow.mems.persistence.entity.Person contactPerson) {
    this.contactPerson = contactPerson;
    if (this.contactPerson != null) {
      this.contactPsn = this.contactPerson.getPsnCode();
      this.isDeleted = this.contactPerson.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "DEV_CODE", referencedColumnName = "DEV_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Device device;

  public arrow.mems.persistence.entity.Device getDevice() {
    return this.device;
  }

  public void setDevice(arrow.mems.persistence.entity.Device device) {
    this.device = device;
    if (this.device != null) {
      this.devCode = this.device.getDevCode();
      this.isDeleted = this.device.getIsDeleted();
    }
  }

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

  @Override
  @NotNull(message = "{" + MessageCode.MRR00019 + "}")
  public String getHospContactPsn() {
    return super.getHospContactPsn();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00028 + "}")
  public LocalDate getOccurDate() {
    return super.getOccurDate();
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00016 + "}")
  public String getActionType() {
    return super.getActionType();
  }

  @OneToOne
  @JoinColumns({@JoinColumn(name = "ACTION_CODE", referencedColumnName = "ACTION_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected Fault fault;

  public Fault getFault() {
    return this.fault;
  }

  public void setFault(Fault pFault) {
    this.fault = pFault;
  }

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}