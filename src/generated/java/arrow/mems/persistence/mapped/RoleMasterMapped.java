//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class RoleMasterMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.RoleMaster> getEntityClass() {
    return arrow.mems.persistence.entity.RoleMaster.class;
  }
  
  //default constructor
  public RoleMasterMapped() {
  }
  
  @Column(name="ROLE_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_master_seq_gen")
  @SequenceGenerator(name = "role_master_seq_gen", sequenceName = "role_master_role_id_seq", allocationSize=1)
  protected int roleId;
  
  public int getRoleId() {
    return this.roleId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.roleId;
  }

  //Normal columns
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
  @Column(name="ROLE_CODE")
  protected java.lang.String roleCode;

  public java.lang.String getRoleCode() {
    return this.roleCode;
  }

  public void setRoleCode(java.lang.String roleCode) {
    this.roleCode = roleCode;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}