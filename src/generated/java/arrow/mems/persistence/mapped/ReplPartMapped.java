//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ReplPartMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ReplPart> getEntityClass() {
    return arrow.mems.persistence.entity.ReplPart.class;
  }
  
  //default constructor
  public ReplPartMapped() {
  }
  
  @Column(name="REPL_PART_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repl_part_seq_gen")
  @SequenceGenerator(name = "repl_part_seq_gen", sequenceName = "repl_part_repl_part_id_seq", allocationSize=1)
  protected int replPartId;
  
  public int getReplPartId() {
    return this.replPartId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.replPartId;
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
  @Column(name="DELIVERY_METHOD")
  protected java.lang.String deliveryMethod;

  public java.lang.String getDeliveryMethod() {
    return this.deliveryMethod;
  }

  public void setDeliveryMethod(java.lang.String deliveryMethod) {
    this.deliveryMethod = deliveryMethod;
  }
  @Column(name="DEVICE_CODE")
  protected java.lang.String deviceCode;

  public java.lang.String getDeviceCode() {
    return this.deviceCode;
  }

  public void setDeviceCode(java.lang.String deviceCode) {
    this.deviceCode = deviceCode;
  }
  @Column(name="IMAGE_FILE")
  protected byte[] imageFile;

  public byte[] getImageFile() {
    return this.imageFile;
  }

  public void setImageFile(byte[] imageFile) {
    this.imageFile = imageFile;
  }
  @Column(name="MEMO_REMOVED")
  protected java.lang.String memoRemoved;

  public java.lang.String getMemoRemoved() {
    return this.memoRemoved;
  }

  public void setMemoRemoved(java.lang.String memoRemoved) {
    this.memoRemoved = memoRemoved;
  }
  @Column(name="REMOVED_DEV_CODE")
  protected java.lang.String removedDevCode;

  public java.lang.String getRemovedDevCode() {
    return this.removedDevCode;
  }

  public void setRemovedDevCode(java.lang.String removedDevCode) {
    this.removedDevCode = removedDevCode;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}