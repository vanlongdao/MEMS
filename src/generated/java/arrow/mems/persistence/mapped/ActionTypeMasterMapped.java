//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ActionTypeMasterMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ActionTypeMaster> getEntityClass() {
    return arrow.mems.persistence.entity.ActionTypeMaster.class;
  }
  
  //default constructor
  public ActionTypeMasterMapped() {
  }
  
  @Column(name="ACTION_TYPE_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_type_master_seq_gen")
  @SequenceGenerator(name = "action_type_master_seq_gen", sequenceName = "action_type_master_action_type_id_seq", allocationSize=1)
  protected int actionTypeId;
  
  public int getActionTypeId() {
    return this.actionTypeId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.actionTypeId;
  }

  //Normal columns
  @Column(name="ACTION_TYPE_CODE")
  protected java.lang.String actionTypeCode;

  public java.lang.String getActionTypeCode() {
    return this.actionTypeCode;
  }

  public void setActionTypeCode(java.lang.String actionTypeCode) {
    this.actionTypeCode = actionTypeCode;
  }
  @Column(name="COUNTRY")
  protected java.lang.Integer country;

  public java.lang.Integer getCountry() {
    return this.country;
  }

  public void setCountry(java.lang.Integer country) {
    this.country = country;
  }
  @Column(name="LABEL")
  protected java.lang.String label;

  public java.lang.String getLabel() {
    return this.label;
  }

  public void setLabel(java.lang.String label) {
    this.label = label;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}