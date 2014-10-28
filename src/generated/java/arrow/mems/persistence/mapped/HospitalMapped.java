//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class HospitalMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Hospital> getEntityClass() {
    return arrow.mems.persistence.entity.Hospital.class;
  }
  
  //default constructor
  public HospitalMapped() {
  }
  
  @Column(name="HOSP_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_seq_gen")
  @SequenceGenerator(name = "hospital_seq_gen", sequenceName = "hospital_hosp_id_seq", allocationSize=1)
  protected int hospId;
  
  public int getHospId() {
    return this.hospId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.hospId;
  }

  //Normal columns
  @Column(name="CORP_CODE")
  protected java.lang.String corpCode;

  public java.lang.String getCorpCode() {
    return this.corpCode;
  }

  public void setCorpCode(java.lang.String corpCode) {
    this.corpCode = corpCode;
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
  @Column(name="NUM_BEDS")
  protected java.lang.Integer numBeds;

  public java.lang.Integer getNumBeds() {
    return this.numBeds;
  }

  public void setNumBeds(java.lang.Integer numBeds) {
    this.numBeds = numBeds;
  }
  @Column(name="OFFICE_CODE")
  protected java.lang.String officeCode;

  public java.lang.String getOfficeCode() {
    return this.officeCode;
  }

  public void setOfficeCode(java.lang.String officeCode) {
    this.officeCode = officeCode;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "CORP_CODE", referencedColumnName = "CORP_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Corporate corporate;

  public arrow.mems.persistence.entity.Corporate getCorporate() {
    return this.corporate;
  }

  public void setCorporate(final arrow.mems.persistence.entity.Corporate corporate) {
    this.corporate = corporate;
    this.corpCode = this.corporate != null ? this.corporate.getCorpCode() : null;
    
    if(this.corporate != null){
      this.isDeleted = this.corporate.getIsDeleted();
    }
    
  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "OFFICE_CODE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Office office;

  public arrow.mems.persistence.entity.Office getOffice() {
    return this.office;
  }

  public void setOffice(final arrow.mems.persistence.entity.Office office) {
    this.office = office;
    this.officeCode = this.office != null ? this.office.getOfficeCode() : null;
    
    if(this.office != null){
      this.isDeleted = this.office.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}