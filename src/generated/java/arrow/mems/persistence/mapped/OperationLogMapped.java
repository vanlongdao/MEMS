//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class OperationLogMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.OperationLog> getEntityClass() {
    return arrow.mems.persistence.entity.OperationLog.class;
  }
  
  //default constructor
  public OperationLogMapped() {
  }
  
  @Column(name="OP_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_log_seq_gen")
  @SequenceGenerator(name = "operation_log_seq_gen", sequenceName = "operation_log_op_id_seq", allocationSize=1)
  protected long opId;
  
  public long getOpId() {
    return this.opId;
  }
  
  @Override
  public java.lang.Long getPk() {
    return this.opId;
  }

  //Normal columns
  @Column(name="APPROVED_BY")
  protected int approvedBy;

  public int getApprovedBy() {
    return this.approvedBy;
  }

  public void setApprovedBy(int approvedBy) {
    this.approvedBy = approvedBy;
  }
  @Column(name="DESCRIPTION")
  protected java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }
  @Column(name="NEW_ID")
  protected java.lang.Integer newId;

  public java.lang.Integer getNewId() {
    return this.newId;
  }

  public void setNewId(java.lang.Integer newId) {
    this.newId = newId;
  }
  @Column(name="OLD_ID")
  protected java.lang.Integer oldId;

  public java.lang.Integer getOldId() {
    return this.oldId;
  }

  public void setOldId(java.lang.Integer oldId) {
    this.oldId = oldId;
  }
  @Column(name="OPERATED_AT")
  protected java.time.LocalDateTime operatedAt;

  public java.time.LocalDateTime getOperatedAt() {
    return this.operatedAt;
  }

  public void setOperatedAt(java.time.LocalDateTime operatedAt) {
    this.operatedAt = operatedAt;
  }
  @Column(name="OPERATED_BY")
  protected int operatedBy;

  public int getOperatedBy() {
    return this.operatedBy;
  }

  public void setOperatedBy(int operatedBy) {
    this.operatedBy = operatedBy;
  }
  @Column(name="TARGET_TABLE")
  protected java.lang.String targetTable;

  public java.lang.String getTargetTable() {
    return this.targetTable;
  }

  public void setTargetTable(java.lang.String targetTable) {
    this.targetTable = targetTable;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}