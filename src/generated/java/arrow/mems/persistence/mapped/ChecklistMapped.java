//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class ChecklistMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Checklist> getEntityClass() {
    return arrow.mems.persistence.entity.Checklist.class;
  }
  
  //default constructor
  public ChecklistMapped() {
  }
  
  @Column(name="CKLIST_LOG_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checklist_seq_gen")
  @SequenceGenerator(name = "checklist_seq_gen", sequenceName = "checklist_cklist_log_id_seq", allocationSize=1)
  protected int cklistLogId;
  
  public int getCklistLogId() {
    return this.cklistLogId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.cklistLogId;
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
  @Column(name="CHECK_DATE")
  protected java.time.LocalDate checkDate;

  public java.time.LocalDate getCheckDate() {
    return this.checkDate;
  }

  public void setCheckDate(java.time.LocalDate checkDate) {
    this.checkDate = checkDate;
  }
  @Column(name="CKLIST_LOG_CODE")
  protected java.lang.String cklistLogCode;

  public java.lang.String getCklistLogCode() {
    return this.cklistLogCode;
  }

  public void setCklistLogCode(java.lang.String cklistLogCode) {
    this.cklistLogCode = cklistLogCode;
  }
  @Column(name="HOSP_PSN")
  protected java.lang.String hospPsn;

  public java.lang.String getHospPsn() {
    return this.hospPsn;
  }

  public void setHospPsn(java.lang.String hospPsn) {
    this.hospPsn = hospPsn;
  }
  @Column(name="MEASURE_DEV1")
  protected java.lang.String measureDev1;

  public java.lang.String getMeasureDev1() {
    return this.measureDev1;
  }

  public void setMeasureDev1(java.lang.String measureDev1) {
    this.measureDev1 = measureDev1;
  }
  @Column(name="MEASURE_DEV2")
  protected java.lang.String measureDev2;

  public java.lang.String getMeasureDev2() {
    return this.measureDev2;
  }

  public void setMeasureDev2(java.lang.String measureDev2) {
    this.measureDev2 = measureDev2;
  }
  @Column(name="REFER_CKLIST_CODE")
  protected java.lang.String referCklistCode;

  public java.lang.String getReferCklistCode() {
    return this.referCklistCode;
  }

  public void setReferCklistCode(java.lang.String referCklistCode) {
    this.referCklistCode = referCklistCode;
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