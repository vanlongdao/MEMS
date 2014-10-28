//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ActionLogMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.ActionLog> getEntityClass() {
    return arrow.mems.persistence.entity.ActionLog.class;
  }
  
  //default constructor
  public ActionLogMapped() {
  }
  
  @Column(name="ACTION_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_log_seq_gen")
  @SequenceGenerator(name = "action_log_seq_gen", sequenceName = "action_log_action_id_seq", allocationSize=1)
  protected int actionId;
  
  public int getActionId() {
    return this.actionId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.actionId;
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
  @Column(name="ACTION_DATE")
  protected java.time.LocalDate actionDate;

  public java.time.LocalDate getActionDate() {
    return this.actionDate;
  }

  public void setActionDate(java.time.LocalDate actionDate) {
    this.actionDate = actionDate;
  }
  @Column(name="ACTION_START_CONFIRM_IMG")
  protected byte[] actionStartConfirmImg;

  public byte[] getActionStartConfirmImg() {
    return this.actionStartConfirmImg;
  }

  public void setActionStartConfirmImg(byte[] actionStartConfirmImg) {
    this.actionStartConfirmImg = actionStartConfirmImg;
  }
  @Column(name="ACTION_TYPE")
  protected java.lang.String actionType;

  public java.lang.String getActionType() {
    return this.actionType;
  }

  public void setActionType(java.lang.String actionType) {
    this.actionType = actionType;
  }
  @Column(name="CHECK_START_CONFIRM_IMG")
  protected byte[] checkStartConfirmImg;

  public byte[] getCheckStartConfirmImg() {
    return this.checkStartConfirmImg;
  }

  public void setCheckStartConfirmImg(byte[] checkStartConfirmImg) {
    this.checkStartConfirmImg = checkStartConfirmImg;
  }
  @Column(name="CLIENT_SIDE_MGMT_CODE")
  protected java.lang.String clientSideMgmtCode;

  public java.lang.String getClientSideMgmtCode() {
    return this.clientSideMgmtCode;
  }

  public void setClientSideMgmtCode(java.lang.String clientSideMgmtCode) {
    this.clientSideMgmtCode = clientSideMgmtCode;
  }
  @Column(name="COMPLAINT")
  protected java.lang.String complaint;

  public java.lang.String getComplaint() {
    return this.complaint;
  }

  public void setComplaint(java.lang.String complaint) {
    this.complaint = complaint;
  }
  @Column(name="CONTACT_DATE")
  protected java.time.LocalDate contactDate;

  public java.time.LocalDate getContactDate() {
    return this.contactDate;
  }

  public void setContactDate(java.time.LocalDate contactDate) {
    this.contactDate = contactDate;
  }
  @Column(name="CONTACT_PSN")
  protected java.lang.String contactPsn;

  public java.lang.String getContactPsn() {
    return this.contactPsn;
  }

  public void setContactPsn(java.lang.String contactPsn) {
    this.contactPsn = contactPsn;
  }
  @Column(name="DELIVERY_METHOD")
  protected java.lang.String deliveryMethod;

  public java.lang.String getDeliveryMethod() {
    return this.deliveryMethod;
  }

  public void setDeliveryMethod(java.lang.String deliveryMethod) {
    this.deliveryMethod = deliveryMethod;
  }
  @Column(name="DEV_CODE")
  protected java.lang.String devCode;

  public java.lang.String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(java.lang.String devCode) {
    this.devCode = devCode;
  }
  @Column(name="FINISH_DATE")
  protected java.time.LocalDate finishDate;

  public java.time.LocalDate getFinishDate() {
    return this.finishDate;
  }

  public void setFinishDate(java.time.LocalDate finishDate) {
    this.finishDate = finishDate;
  }
  @Column(name="HOSP_CODE")
  protected java.lang.String hospCode;

  public java.lang.String getHospCode() {
    return this.hospCode;
  }

  public void setHospCode(java.lang.String hospCode) {
    this.hospCode = hospCode;
  }
  @Column(name="HOSP_CONTACT_PSN")
  protected java.lang.String hospContactPsn;

  public java.lang.String getHospContactPsn() {
    return this.hospContactPsn;
  }

  public void setHospContactPsn(java.lang.String hospContactPsn) {
    this.hospContactPsn = hospContactPsn;
  }
  @Column(name="HOSP_DEPT_CODE")
  protected java.lang.String hospDeptCode;

  public java.lang.String getHospDeptCode() {
    return this.hospDeptCode;
  }

  public void setHospDeptCode(java.lang.String hospDeptCode) {
    this.hospDeptCode = hospDeptCode;
  }
  @Column(name="INSTALL_CONFIRM_IMG")
  protected byte[] installConfirmImg;

  public byte[] getInstallConfirmImg() {
    return this.installConfirmImg;
  }

  public void setInstallConfirmImg(byte[] installConfirmImg) {
    this.installConfirmImg = installConfirmImg;
  }
  @Column(name="OCCUR_DATE")
  protected java.time.LocalDate occurDate;

  public java.time.LocalDate getOccurDate() {
    return this.occurDate;
  }

  public void setOccurDate(java.time.LocalDate occurDate) {
    this.occurDate = occurDate;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "HOSP_DEPT_CODE", referencedColumnName = "DEPT_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.HospitalDept hospitalDept;

  public arrow.mems.persistence.entity.HospitalDept getHospitalDept() {
    return this.hospitalDept;
  }

  public void setHospitalDept(final arrow.mems.persistence.entity.HospitalDept hospitalDept) {
    this.hospitalDept = hospitalDept;
    this.hospDeptCode = this.hospitalDept != null ? this.hospitalDept.getDeptCode() : null;
    
    if(this.hospitalDept != null){
      this.isDeleted = this.hospitalDept.getIsDeleted();
    }
    
  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "HOSP_CODE", referencedColumnName = "HOSP_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Hospital hospital;

  public arrow.mems.persistence.entity.Hospital getHospital() {
    return this.hospital;
  }

  public void setHospital(final arrow.mems.persistence.entity.Hospital hospital) {
    this.hospital = hospital;
    this.hospCode = this.hospital != null ? this.hospital.getHospCode() : null;
    
    if(this.hospital != null){
      this.isDeleted = this.hospital.getIsDeleted();
    }
    
  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "CONTACT_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Person person;

  public arrow.mems.persistence.entity.Person getPerson() {
    return this.person;
  }

  public void setPerson(final arrow.mems.persistence.entity.Person person) {
    this.person = person;
    this.contactPsn = this.person != null ? this.person.getPsnCode() : null;
    
    if(this.person != null){
      this.isDeleted = this.person.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @ManyToOne
  @JoinColumns({@JoinColumn(name = "HOSP_CONTACT_PSN", referencedColumnName = "PSN_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.Person hospitalContactPsn;

  public arrow.mems.persistence.entity.Person getHospitalContactPsn() {
    return this.hospitalContactPsn;
  }

  public void setHospitalContactPsn(final arrow.mems.persistence.entity.Person person) {
    this.hospitalContactPsn = person;
    this.hospContactPsn = this.hospitalContactPsn != null ? this.hospitalContactPsn.getPsnCode() : null;

    if (this.hospitalContactPsn != null) {
      this.isDeleted = this.hospitalContactPsn.getIsDeleted();
    }

  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}