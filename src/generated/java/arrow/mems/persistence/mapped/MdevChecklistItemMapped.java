//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class MdevChecklistItemMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.MdevChecklistItem> getEntityClass() {
    return arrow.mems.persistence.entity.MdevChecklistItem.class;
  }
  
  //default constructor
  public MdevChecklistItemMapped() {
  }
  
  @Column(name="CKI_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mdev_checklist_item_seq_gen")
  @SequenceGenerator(name = "mdev_checklist_item_seq_gen", sequenceName = "mdev_checklist_item_cki_id_seq", allocationSize=1)
  protected int ckiId;
  
  public int getCkiId() {
    return this.ckiId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.ckiId;
  }

  //Normal columns
  @Column(name="CKI_CODE")
  protected java.lang.String ckiCode;

  public java.lang.String getCkiCode() {
    return this.ckiCode;
  }

  public void setCkiCode(java.lang.String ckiCode) {
    this.ckiCode = ckiCode;
  }
  @Column(name="CKLIST_CODE")
  protected java.lang.String cklistCode;

  public java.lang.String getCklistCode() {
    return this.cklistCode;
  }

  public void setCklistCode(java.lang.String cklistCode) {
    this.cklistCode = cklistCode;
  }
  @Column(name="COUNTRY")
  protected java.lang.Integer country;

  public java.lang.Integer getCountry() {
    return this.country;
  }

  public void setCountry(java.lang.Integer country) {
    this.country = country;
  }
  @Column(name="DESCRIPTION")
  protected java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}