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
public class HumanResourceMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.HumanResource> getEntityClass() {
    return arrow.mems.persistence.entity.HumanResource.class;
  }

  //default constructor
  public HumanResourceMapped() {
  }

  @Column(name="HR_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "human_resource_seq_gen")
  @SequenceGenerator(name = "human_resource_seq_gen", sequenceName = "human_resource_hr_id_seq", allocationSize=1)
  protected int hrId;

  public int getHrId() {
    return this.hrId;
  }

  @Override
  public java.lang.Integer getPk() {
    return this.hrId;
  }

  //Normal columns
  @Column(name="CLASS_CODE")
  protected java.lang.String classCode;

  public java.lang.String getClassCode() {
    return this.classCode;
  }

  public void setClassCode(java.lang.String classCode) {
    this.classCode = classCode;
  }
  @Column(name="HOSP_CODE")
  protected java.lang.String hospCode;

  public java.lang.String getHospCode() {
    return this.hospCode;
  }

  public void setHospCode(java.lang.String hospCode) {
    this.hospCode = hospCode;
  }
  @Column(name="HOSP_DEPT_CODE")
  protected java.lang.String hospDeptCode;

  public java.lang.String getHospDeptCode() {
    return this.hospDeptCode;
  }

  public void setHospDeptCode(java.lang.String hospDeptCode) {
    this.hospDeptCode = hospDeptCode;
  }
  @Column(name="PSN_CODE")
  protected java.lang.String psnCode;

  public java.lang.String getPsnCode() {
    return this.psnCode;
  }

  public void setPsnCode(java.lang.String psnCode) {
    this.psnCode = psnCode;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "HOSP_DEPT_CODE", referencedColumnName = "DEPT_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.HospitalDept hospitalDept;

  public arrow.mems.persistence.entity.HospitalDept getHospitalDept() {
    return this.hospitalDept;
  }

  public void setHospitalDept(final arrow.mems.persistence.entity.HospitalDept hospitalDept) {
    this.hospitalDept = hospitalDept;
    this.hospDeptCode = this.hospitalDept != null ? this.hospitalDept.getDeptCode() : null;

    if(this.hospitalDept != null){
      this.isDeleted = this.hospitalDept.getIsDeleted();
    }

  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "HOSP_CODE", referencedColumnName = "HOSP_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Hospital hospital;

  public arrow.mems.persistence.entity.Hospital getHospital() {
    return this.hospital;
  }

  public void setHospital(final arrow.mems.persistence.entity.Hospital hospital) {
    this.hospital = hospital;
    this.hospCode = this.hospital != null ? this.hospital.getHospCode() : null;

    if(this.hospital != null){
      this.isDeleted = this.hospital.getIsDeleted();
    }

  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "CLASS_CODE", referencedColumnName = "CLASS_CODE", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.PersonClass personClass;

  public arrow.mems.persistence.entity.PersonClass getPersonClass() {
    return this.personClass;
  }

  public void setPersonClass(final arrow.mems.persistence.entity.PersonClass personClass) {
    this.personClass = personClass;
    this.classCode = this.personClass != null ? this.personClass.getClassCode() : null;

  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "PSN_CODE", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Person person;

  public arrow.mems.persistence.entity.Person getPerson() {
    return this.person;
  }

  public void setPerson(final arrow.mems.persistence.entity.Person person) {
    this.person = person;
    this.psnCode = this.person != null ? this.person.getPsnCode() : null;

    if(this.person != null){
      this.isDeleted = this.person.getIsDeleted();
    }

  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}