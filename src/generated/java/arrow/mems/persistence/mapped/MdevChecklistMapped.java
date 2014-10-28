//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class MdevChecklistMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.MdevChecklist> getEntityClass() {
    return arrow.mems.persistence.entity.MdevChecklist.class;
  }
  
  //default constructor
  public MdevChecklistMapped() {
  }
  
  @Column(name="CKLIST_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mdev_checklist_seq_gen")
  @SequenceGenerator(name = "mdev_checklist_seq_gen", sequenceName = "mdev_checklist_cklist_id_seq", allocationSize=1)
  protected int cklistId;
  
  public int getCklistId() {
    return this.cklistId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.cklistId;
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
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "MDEV_CODE", referencedColumnName = "MDEV_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.MDevice mDevice;

  public arrow.mems.persistence.entity.MDevice getMDevice() {
    return this.mDevice;
  }

  public void setMDevice(final arrow.mems.persistence.entity.MDevice mDevice) {
    this.mDevice = mDevice;
    this.mdevCode = this.mDevice != null ? this.mDevice.getMdevCode() : null;
    
    if(this.mDevice != null){
      this.isDeleted = this.mDevice.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}