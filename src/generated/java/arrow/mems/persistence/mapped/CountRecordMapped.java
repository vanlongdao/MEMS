//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class CountRecordMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.CountRecord> getEntityClass() {
    return arrow.mems.persistence.entity.CountRecord.class;
  }
  
  //default constructor
  public CountRecordMapped() {
  }
  
  @Column(name="COUNT_RECORD_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "count_record_seq_gen")
  @SequenceGenerator(name = "count_record_seq_gen", sequenceName = "count_record_count_record_id_seq", allocationSize=1)
  protected int countRecordId;
  
  public int getCountRecordId() {
    return this.countRecordId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.countRecordId;
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
  @Column(name="COUNT_BASE_CODE")
  protected java.lang.String countBaseCode;

  public java.lang.String getCountBaseCode() {
    return this.countBaseCode;
  }

  public void setCountBaseCode(java.lang.String countBaseCode) {
    this.countBaseCode = countBaseCode;
  }
  @Column(name="COUNT_REC_CODE")
  protected java.lang.String countRecCode;

  public java.lang.String getCountRecCode() {
    return this.countRecCode;
  }

  public void setCountRecCode(java.lang.String countRecCode) {
    this.countRecCode = countRecCode;
  }
  @Column(name="DEV_CODE")
  protected java.lang.String devCode;

  public java.lang.String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(java.lang.String devCode) {
    this.devCode = devCode;
  }
  @Column(name="HOSP_DEPT_CODE")
  protected java.lang.String hospDeptCode;

  public java.lang.String getHospDeptCode() {
    return this.hospDeptCode;
  }

  public void setHospDeptCode(java.lang.String hospDeptCode) {
    this.hospDeptCode = hospDeptCode;
  }
  @Column(name="RAW_VALUE")
  protected java.lang.Double rawValue;

  public java.lang.Double getRawValue() {
    return this.rawValue;
  }

  public void setRawValue(java.lang.Double rawValue) {
    this.rawValue = rawValue;
  }
  @Column(name="RECORD_AT")
  protected java.time.LocalDateTime recordAt;

  public java.time.LocalDateTime getRecordAt() {
    return this.recordAt;
  }

  public void setRecordAt(java.time.LocalDateTime recordAt) {
    this.recordAt = recordAt;
  }
  @Column(name="UNIT")
  protected java.lang.String unit;

  public java.lang.String getUnit() {
    return this.unit;
  }

  public void setUnit(java.lang.String unit) {
    this.unit = unit;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}