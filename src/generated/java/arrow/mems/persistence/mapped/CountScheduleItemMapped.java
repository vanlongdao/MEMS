//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class CountScheduleItemMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.CountScheduleItem> getEntityClass() {
    return arrow.mems.persistence.entity.CountScheduleItem.class;
  }
  
  //default constructor
  public CountScheduleItemMapped() {
  }
  
  @Column(name="COUNT_SCHED_ITEM_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "count_schedule_item_seq_gen")
  @SequenceGenerator(name = "count_schedule_item_seq_gen", sequenceName = "count_schedule_item_count_sched_item_id_seq", allocationSize=1)
  protected int countSchedItemId;
  
  public int getCountSchedItemId() {
    return this.countSchedItemId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.countSchedItemId;
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
  @Column(name="ASSIGNED_PSN")
  protected java.lang.String assignedPsn;

  public java.lang.String getAssignedPsn() {
    return this.assignedPsn;
  }

  public void setAssignedPsn(java.lang.String assignedPsn) {
    this.assignedPsn = assignedPsn;
  }
  @Column(name="CKLIST_CODE")
  protected java.lang.String cklistCode;

  public java.lang.String getCklistCode() {
    return this.cklistCode;
  }

  public void setCklistCode(java.lang.String cklistCode) {
    this.cklistCode = cklistCode;
  }
  @Column(name="COUNTER_BASE_CODE")
  protected java.lang.String counterBaseCode;

  public java.lang.String getCounterBaseCode() {
    return this.counterBaseCode;
  }

  public void setCounterBaseCode(java.lang.String counterBaseCode) {
    this.counterBaseCode = counterBaseCode;
  }
  @Column(name="COUNT_SCHED_CODE")
  protected java.lang.String countSchedCode;

  public java.lang.String getCountSchedCode() {
    return this.countSchedCode;
  }

  public void setCountSchedCode(java.lang.String countSchedCode) {
    this.countSchedCode = countSchedCode;
  }
  @Column(name="DESCRIPTION")
  protected java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }
  @Column(name="DEV_CODE")
  protected java.lang.String devCode;

  public java.lang.String getDevCode() {
    return this.devCode;
  }

  public void setDevCode(java.lang.String devCode) {
    this.devCode = devCode;
  }
  @Column(name="DISP_OFF_AT")
  protected java.time.LocalDateTime dispOffAt;

  public java.time.LocalDateTime getDispOffAt() {
    return this.dispOffAt;
  }

  public void setDispOffAt(java.time.LocalDateTime dispOffAt) {
    this.dispOffAt = dispOffAt;
  }
  @Column(name="DISP_OFF_BY")
  protected java.lang.String dispOffBy;

  public java.lang.String getDispOffBy() {
    return this.dispOffBy;
  }

  public void setDispOffBy(java.lang.String dispOffBy) {
    this.dispOffBy = dispOffBy;
  }
  @Column(name="END_DATE")
  protected java.time.LocalDate endDate;

  public java.time.LocalDate getEndDate() {
    return this.endDate;
  }

  public void setEndDate(java.time.LocalDate endDate) {
    this.endDate = endDate;
  }
  @Column(name="HOSP_DEPT_CODE")
  protected java.lang.String hospDeptCode;

  public java.lang.String getHospDeptCode() {
    return this.hospDeptCode;
  }

  public void setHospDeptCode(java.lang.String hospDeptCode) {
    this.hospDeptCode = hospDeptCode;
  }
  @Column(name="IS_DISP_OFF")
  protected java.lang.Integer isDispOff;

  public java.lang.Integer getIsDispOff() {
    return this.isDispOff;
  }

  public void setIsDispOff(java.lang.Integer isDispOff) {
    this.isDispOff = isDispOff;
  }
  @Column(name="SCHED_BASE_CODE")
  protected java.lang.String schedBaseCode;

  public java.lang.String getSchedBaseCode() {
    return this.schedBaseCode;
  }

  public void setSchedBaseCode(java.lang.String schedBaseCode) {
    this.schedBaseCode = schedBaseCode;
  }
  @Column(name="SCHED_TITLE")
  protected java.lang.String schedTitle;

  public java.lang.String getSchedTitle() {
    return this.schedTitle;
  }

  public void setSchedTitle(java.lang.String schedTitle) {
    this.schedTitle = schedTitle;
  }
  @Column(name="START_DATE")
  protected java.time.LocalDate startDate;

  public java.time.LocalDate getStartDate() {
    return this.startDate;
  }

  public void setStartDate(java.time.LocalDate startDate) {
    this.startDate = startDate;
  }
  @Column(name="TARGET_COUNT")
  protected java.lang.Double targetCount;

  public java.lang.Double getTargetCount() {
    return this.targetCount;
  }

  public void setTargetCount(java.lang.Double targetCount) {
    this.targetCount = targetCount;
  }
  @Column(name="TARGET_DATE")
  protected java.time.LocalDate targetDate;

  public java.time.LocalDate getTargetDate() {
    return this.targetDate;
  }

  public void setTargetDate(java.time.LocalDate targetDate) {
    this.targetDate = targetDate;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}