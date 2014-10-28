//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class MdevOperatorMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.MdevOperator> getEntityClass() {
    return arrow.mems.persistence.entity.MdevOperator.class;
  }
  
  //default constructor
  public MdevOperatorMapped() {
  }
  
  @Column(name="MDEV_SERVICE_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mdev_operator_seq_gen")
  @SequenceGenerator(name = "mdev_operator_seq_gen", sequenceName = "mdev_operator_mdev_service_id_seq", allocationSize=1)
  protected int mdevServiceId;
  
  public int getMdevServiceId() {
    return this.mdevServiceId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.mdevServiceId;
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
  @Column(name="ROLE_CODE")
  protected java.lang.String roleCode;

  public java.lang.String getRoleCode() {
    return this.roleCode;
  }

  public void setRoleCode(java.lang.String roleCode) {
    this.roleCode = roleCode;
  }
  @Column(name="SERVICE_OFFICE")
  protected java.lang.String serviceOffice;

  public java.lang.String getServiceOffice() {
    return this.serviceOffice;
  }

  public void setServiceOffice(java.lang.String serviceOffice) {
    this.serviceOffice = serviceOffice;
  }
  @Column(name="SERVICE_PSN")
  protected java.lang.String servicePsn;

  public java.lang.String getServicePsn() {
    return this.servicePsn;
  }

  public void setServicePsn(java.lang.String servicePsn) {
    this.servicePsn = servicePsn;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}