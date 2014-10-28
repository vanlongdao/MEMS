//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class HospitalDeptMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.HospitalDept> getEntityClass() {
    return arrow.mems.persistence.entity.HospitalDept.class;
  }
  
  //default constructor
  public HospitalDeptMapped() {
  }
  
  @Column(name="DEPT_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_dept_seq_gen")
  @SequenceGenerator(name = "hospital_dept_seq_gen", sequenceName = "hospital_dept_dept_id_seq", allocationSize=1)
  protected int deptId;
  
  public int getDeptId() {
    return this.deptId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.deptId;
  }

  //Normal columns
  @Column(name="CHECK_DAYS_MARGIN")
  protected java.lang.Integer checkDaysMargin;

  public java.lang.Integer getCheckDaysMargin() {
    return this.checkDaysMargin;
  }

  public void setCheckDaysMargin(java.lang.Integer checkDaysMargin) {
    this.checkDaysMargin = checkDaysMargin;
  }
  @Column(name="DEPT_CODE")
  protected java.lang.String deptCode;

  public java.lang.String getDeptCode() {
    return this.deptCode;
  }

  public void setDeptCode(java.lang.String deptCode) {
    this.deptCode = deptCode;
  }
  @Column(name="HOSP_CODE")
  protected java.lang.String hospCode;

  public java.lang.String getHospCode() {
    return this.hospCode;
  }

  public void setHospCode(java.lang.String hospCode) {
    this.hospCode = hospCode;
  }
  @Column(name="NAME")
  protected java.lang.String name;

  public java.lang.String getName() {
    return this.name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }
  @Column(name="NOTICE_DAYS_BEFORE")
  protected java.lang.Integer noticeDaysBefore;

  public java.lang.Integer getNoticeDaysBefore() {
    return this.noticeDaysBefore;
  }

  public void setNoticeDaysBefore(java.lang.Integer noticeDaysBefore) {
    this.noticeDaysBefore = noticeDaysBefore;
  }
  @Column(name="NUM_BEDS")
  protected java.lang.Integer numBeds;

  public java.lang.Integer getNumBeds() {
    return this.numBeds;
  }

  public void setNumBeds(java.lang.Integer numBeds) {
    this.numBeds = numBeds;
  }
  @Column(name="PICKUP_DAYS")
  protected java.lang.Integer pickupDays;

  public java.lang.Integer getPickupDays() {
    return this.pickupDays;
  }

  public void setPickupDays(java.lang.Integer pickupDays) {
    this.pickupDays = pickupDays;
  }
  //Foreign keys
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

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}