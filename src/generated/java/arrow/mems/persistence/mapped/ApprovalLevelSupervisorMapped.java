//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ApprovalLevelSupervisorMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ApprovalLevelSupervisor> getEntityClass() {
    return arrow.mems.persistence.entity.ApprovalLevelSupervisor.class;
  }
  
  //default constructor
  public ApprovalLevelSupervisorMapped() {
  }
  
  @Column(name="LEVEL_SUPERVISOR_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_level_supervisor_seq_gen")
  @SequenceGenerator(name = "approval_level_supervisor_seq_gen", sequenceName = "approval_level_supervisor_level_supervisor_id_seq", allocationSize=1)
  protected int levelSupervisorId;
  
  public int getLevelSupervisorId() {
    return this.levelSupervisorId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.levelSupervisorId;
  }

  //Normal columns
  @Column(name="LEVEL_ID")
  protected int levelId;

  public int getLevelId() {
    return this.levelId;
  }

  public void setLevelId(int levelId) {
    this.levelId = levelId;
  }
  @Column(name="SUPERVISOR_ID")
  protected int supervisorId;

  public int getSupervisorId() {
    return this.supervisorId;
  }

  public void setSupervisorId(int supervisorId) {
    this.supervisorId = supervisorId;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "LEVEL_ID", referencedColumnName = "LEVEL_ID", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.ApprovalLevel approvalLevel;

  public arrow.mems.persistence.entity.ApprovalLevel getApprovalLevel() {
    return this.approvalLevel;
  }

  public void setApprovalLevel(final arrow.mems.persistence.entity.ApprovalLevel approvalLevel) {
    this.approvalLevel = approvalLevel;
    if(this.approvalLevel != null){
      this.levelId = this.approvalLevel.getLevelId();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}