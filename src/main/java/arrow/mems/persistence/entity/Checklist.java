//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.mapped.ChecklistMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="CHECKLIST")
public class Checklist extends ChecklistMapped {

  public Checklist() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "MEASURE_DEV1", referencedColumnName = "DEV_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Device measurement1;

  public arrow.mems.persistence.entity.Device getMeasurement1() {
    return this.measurement1;
  }

  public void setMeasurement1(arrow.mems.persistence.entity.Device measurement1) {
    this.measurement1 = measurement1;
    this.measureDev1 = this.measurement1 != null ? this.measurement1.getDevCode() : null;
    if (this.measurement1 != null) {
      this.isDeleted = this.measurement1.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "MEASURE_DEV2", referencedColumnName = "DEV_CODE", insertable = false, updatable = false),
      @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Device measurement2;

  public arrow.mems.persistence.entity.Device getMeasurement2() {
    return this.measurement2;
  }

  public void setMeasurement2(arrow.mems.persistence.entity.Device measurement2) {
    this.measurement2 = measurement2;
    this.measureDev2 = this.measurement2 != null ? this.measurement2.getDevCode() : null;
    if (this.measurement2 != null) {
      this.isDeleted = this.measurement2.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "REFER_CKLIST_CODE", referencedColumnName = "CKLIST_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.MdevChecklist mdevChecklist;

  public arrow.mems.persistence.entity.MdevChecklist getMdevChecklist() {
    return this.mdevChecklist;
  }

  public void setMdevChecklist(arrow.mems.persistence.entity.MdevChecklist pMdevChecklist) {
    this.mdevChecklist = pMdevChecklist;
    this.referCklistCode = this.mdevChecklist != null ? this.mdevChecklist.getCklistCode() : null;
    if (this.mdevChecklist != null) {
      this.isDeleted = this.mdevChecklist.getIsDeleted();
    }
  }

  @ManyToOne
  @JoinColumns({@JoinColumn(name = "HOSP_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  private arrow.mems.persistence.entity.Person hospitalPerson;

  public arrow.mems.persistence.entity.Person getHospitalPerson() {
    return this.hospitalPerson;
  }

  public void setHospitalPerson(arrow.mems.persistence.entity.Person hospitalPerson) {
    this.hospitalPerson = hospitalPerson;
    this.hospPsn = this.hospitalPerson != null ? this.hospitalPerson.getPsnCode() : null;
    if (this.hospitalPerson != null) {
      this.isDeleted = this.hospitalPerson.getIsDeleted();
    }
  }

  @Override
  @NotNull(message = "{" + MessageCode.MRR00027 + "}")
  public String getReferCklistCode() {
    return super.getReferCklistCode();
  }

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}