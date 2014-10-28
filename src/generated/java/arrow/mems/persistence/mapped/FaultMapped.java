//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class FaultMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Fault> getEntityClass() {
    return arrow.mems.persistence.entity.Fault.class;
  }
  
  //default constructor
  public FaultMapped() {
  }
  
  @Column(name="FAULT_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fault_seq_gen")
  @SequenceGenerator(name = "fault_seq_gen", sequenceName = "fault_fault_id_seq", allocationSize=1)
  protected int faultId;
  
  public int getFaultId() {
    return this.faultId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.faultId;
  }

  //Normal columns
  @Column(name="ACTION_CODE")
  protected java.lang.String actionCode;

  public java.lang.String getActionCode() {
    return this.actionCode;
  }

  public void setActionCode(java.lang.String actionCode) {
    this.actionCode = actionCode;
  }
  @Column(name="CAUSE")
  protected java.lang.String cause;

  public java.lang.String getCause() {
    return this.cause;
  }

  public void setCause(java.lang.String cause) {
    this.cause = cause;
  }
  @Column(name="DESCRIPTION")
  protected java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }
  @Column(name="IMAGE_FILE")
  protected byte[] imageFile;

  public byte[] getImageFile() {
    return this.imageFile;
  }

  public void setImageFile(byte[] imageFile) {
    this.imageFile = imageFile;
  }
  @Column(name="PREVENTION")
  protected java.lang.String prevention;

  public java.lang.String getPrevention() {
    return this.prevention;
  }

  public void setPrevention(java.lang.String prevention) {
    this.prevention = prevention;
  }
  @Column(name="REPAIR")
  protected java.lang.String repair;

  public java.lang.String getRepair() {
    return this.repair;
  }

  public void setRepair(java.lang.String repair) {
    this.repair = repair;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}