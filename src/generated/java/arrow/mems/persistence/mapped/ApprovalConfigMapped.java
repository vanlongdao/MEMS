//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ApprovalConfigMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ApprovalConfig> getEntityClass() {
    return arrow.mems.persistence.entity.ApprovalConfig.class;
  }
  
  //default constructor
  public ApprovalConfigMapped() {
  }
  
  @Column(name="CONFIG_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_config_seq_gen")
  @SequenceGenerator(name = "approval_config_seq_gen", sequenceName = "approval_config_config_id_seq", allocationSize=1)
  protected int configId;
  
  public int getConfigId() {
    return this.configId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.configId;
  }

  //Normal columns
  @Column(name="DATA_TYPE")
  protected java.lang.String dataType;

  public java.lang.String getDataType() {
    return this.dataType;
  }

  public void setDataType(java.lang.String dataType) {
    this.dataType = dataType;
  }
  @Column(name="DISABLE_APPROVE")
  protected int disableApprove;

  public int getDisableApprove() {
    return this.disableApprove;
  }

  public void setDisableApprove(int disableApprove) {
    this.disableApprove = disableApprove;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}