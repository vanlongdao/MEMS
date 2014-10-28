//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class BudgetMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Budget> getEntityClass() {
    return arrow.mems.persistence.entity.Budget.class;
  }
  
  //default constructor
  public BudgetMapped() {
  }
  
  @Column(name="BUDGET_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "budget_seq_gen")
  @SequenceGenerator(name = "budget_seq_gen", sequenceName = "budget_budget_id_seq", allocationSize=1)
  protected int budgetId;
  
  public int getBudgetId() {
    return this.budgetId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.budgetId;
  }

  //Normal columns
  @Column(name="BUDGET")
  protected java.lang.Double budget;

  public java.lang.Double getBudget() {
    return this.budget;
  }

  public void setBudget(java.lang.Double budget) {
    this.budget = budget;
  }
  @Column(name="END_DATE")
  protected java.time.LocalDate endDate;

  public java.time.LocalDate getEndDate() {
    return this.endDate;
  }

  public void setEndDate(java.time.LocalDate endDate) {
    this.endDate = endDate;
  }
  @Column(name="ORGANIZATION_CODE")
  protected java.lang.String organizationCode;

  public java.lang.String getOrganizationCode() {
    return this.organizationCode;
  }

  public void setOrganizationCode(java.lang.String organizationCode) {
    this.organizationCode = organizationCode;
  }
  @Column(name="START_DATE")
  protected java.time.LocalDate startDate;

  public java.time.LocalDate getStartDate() {
    return this.startDate;
  }

  public void setStartDate(java.time.LocalDate startDate) {
    this.startDate = startDate;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "ORGANIZATION_CODE", referencedColumnName = "DEPT_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.HospitalDept hospitalDept;

  public arrow.mems.persistence.entity.HospitalDept getHospitalDept() {
    return this.hospitalDept;
  }

  public void setHospitalDept(final arrow.mems.persistence.entity.HospitalDept hospitalDept) {
    this.hospitalDept = hospitalDept;
    this.organizationCode = this.hospitalDept != null ? this.hospitalDept.getDeptCode() : null;
    
    if(this.hospitalDept != null){
      this.isDeleted = this.hospitalDept.getIsDeleted();
    }
    
  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "ORGANIZATION_CODE", referencedColumnName = "HOSP_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Hospital hospital;

  public arrow.mems.persistence.entity.Hospital getHospital() {
    return this.hospital;
  }

  public void setHospital(final arrow.mems.persistence.entity.Hospital hospital) {
    this.hospital = hospital;
    this.organizationCode = this.hospital != null ? this.hospital.getHospCode() : null;
    
    if(this.hospital != null){
      this.isDeleted = this.hospital.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}