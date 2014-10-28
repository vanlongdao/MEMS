//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class PartsListMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.PartsList> getEntityClass() {
    return arrow.mems.persistence.entity.PartsList.class;
  }
  
  //default constructor
  public PartsListMapped() {
  }
  
  @Column(name="PARTS_LIST_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parts_list_seq_gen")
  @SequenceGenerator(name = "parts_list_seq_gen", sequenceName = "parts_list_parts_list_id_seq", allocationSize=1)
  protected int partsListId;
  
  public int getPartsListId() {
    return this.partsListId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.partsListId;
  }

  //Normal columns
  @Column(name="MDEV_CODE")
  protected java.lang.String mdevCode;

  public java.lang.String getMdevCode() {
    return this.mdevCode;
  }

  public void setMdevCode(java.lang.String mdevCode) {
    this.mdevCode = mdevCode;
  }
  @Column(name="PART_CODE")
  protected java.lang.String partCode;

  public java.lang.String getPartCode() {
    return this.partCode;
  }

  public void setPartCode(java.lang.String partCode) {
    this.partCode = partCode;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}