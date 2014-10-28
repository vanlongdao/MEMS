//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ApprovalLevelMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ApprovalLevel> getEntityClass() {
    return arrow.mems.persistence.entity.ApprovalLevel.class;
  }
  
  //default constructor
  public ApprovalLevelMapped() {
  }
  
  @Column(name="LEVEL_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_level_seq_gen")
  @SequenceGenerator(name = "approval_level_seq_gen", sequenceName = "approval_level_level_id_seq", allocationSize=1)
  protected int levelId;
  
  public int getLevelId() {
    return this.levelId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.levelId;
  }

  //Normal columns
  @Column(name="CONFIG_ID")
  protected int configId;

  public int getConfigId() {
    return this.configId;
  }

  public void setConfigId(int configId) {
    this.configId = configId;
  }
  @Column(name="LEVEL_INDEX")
  protected int levelIndex;

  public int getLevelIndex() {
    return this.levelIndex;
  }

  public void setLevelIndex(int levelIndex) {
    this.levelIndex = levelIndex;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "CONFIG_ID", referencedColumnName = "CONFIG_ID", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.ApprovalConfig approvalConfig;

  public arrow.mems.persistence.entity.ApprovalConfig getApprovalConfig() {
    return this.approvalConfig;
  }

  public void setApprovalConfig(final arrow.mems.persistence.entity.ApprovalConfig approvalConfig) {
    this.approvalConfig = approvalConfig;
    if(this.approvalConfig != null){
      this.configId = this.approvalConfig.getConfigId();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}