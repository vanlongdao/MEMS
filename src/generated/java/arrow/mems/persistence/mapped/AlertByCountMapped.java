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
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class AlertByCountMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.AlertByCount> getEntityClass() {
    return arrow.mems.persistence.entity.AlertByCount.class;
  }
  
  //default constructor
  public AlertByCountMapped() {
  }
  
  @Column(name="COUNTER_BASE_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_by_count_seq_gen")
  @SequenceGenerator(name = "alert_by_count_seq_gen", sequenceName = "alert_by_count_counter_base_id_seq", allocationSize=1)
  protected int counterBaseId;
  
  public int getCounterBaseId() {
    return this.counterBaseId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.counterBaseId;
  }

  //Normal columns
  @Column(name="CKLIST_CODE")
  protected java.lang.String cklistCode;

  public java.lang.String getCklistCode() {
    return this.cklistCode;
  }

  public void setCklistCode(java.lang.String cklistCode) {
    this.cklistCode = cklistCode;
  }
  @Column(name="COUNTER_BASE_CODE")
  protected java.lang.String counterBaseCode;

  public java.lang.String getCounterBaseCode() {
    return this.counterBaseCode;
  }

  public void setCounterBaseCode(java.lang.String counterBaseCode) {
    this.counterBaseCode = counterBaseCode;
  }
  @Column(name="DESCRIPTION")
  protected java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }
  @Column(name="INTERVAL_COUNT")
  protected java.lang.Integer intervalCount;

  public java.lang.Integer getIntervalCount() {
    return this.intervalCount;
  }

  public void setIntervalCount(java.lang.Integer intervalCount) {
    this.intervalCount = intervalCount;
  }
  @Column(name="LIMIT_COUNT")
  protected java.lang.Integer limitCount;

  public java.lang.Integer getLimitCount() {
    return this.limitCount;
  }

  public void setLimitCount(java.lang.Integer limitCount) {
    this.limitCount = limitCount;
  }
  @Column(name="MDEV_CODE")
  protected java.lang.String mdevCode;

  public java.lang.String getMdevCode() {
    return this.mdevCode;
  }

  public void setMdevCode(java.lang.String mdevCode) {
    this.mdevCode = mdevCode;
  }
  @Column(name="NAME")
  protected java.lang.String name;

  public java.lang.String getName() {
    return this.name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }
  @Column(name="UNIT")
  protected java.lang.String unit;

  public java.lang.String getUnit() {
    return this.unit;
  }

  public void setUnit(java.lang.String unit) {
    this.unit = unit;
  }
  //Foreign keys
  @OneToOne
  @JoinColumns({
    @JoinColumn(name = "CKLIST_CODE", referencedColumnName = "CKLIST_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.MdevChecklist mdevChecklist;

  public arrow.mems.persistence.entity.MdevChecklist getMdevChecklist() {
    return this.mdevChecklist;
  }

  public void setMdevChecklist(final arrow.mems.persistence.entity.MdevChecklist mdevChecklist) {
    this.mdevChecklist = mdevChecklist;
    this.cklistCode = this.mdevChecklist != null ? this.mdevChecklist.getCklistCode() : null;
    
    if(this.mdevChecklist != null){
      this.isDeleted = this.mdevChecklist.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}