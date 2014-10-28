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
public class NoticeLogMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.NoticeLog> getEntityClass() {
    return arrow.mems.persistence.entity.NoticeLog.class;
  }
  
  //default constructor
  public NoticeLogMapped() {
  }
  
  @Column(name="NOTICE_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notice_log_seq_gen")
  @SequenceGenerator(name = "notice_log_seq_gen", sequenceName = "notice_log_notice_id_seq", allocationSize=1)
  protected int noticeId;
  
  public int getNoticeId() {
    return this.noticeId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.noticeId;
  }

  //Normal columns
  @Column(name="DELETED_AT")
  protected java.time.LocalDateTime deletedAt;

  public java.time.LocalDateTime getDeletedAt() {
    return this.deletedAt;
  }

  public void setDeletedAt(java.time.LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }
  @Column(name="DELETED_BY")
  protected java.lang.Integer deletedBy;

  public java.lang.Integer getDeletedBy() {
    return this.deletedBy;
  }

  public void setDeletedBy(java.lang.Integer deletedBy) {
    this.deletedBy = deletedBy;
  }
  @Column(name="DESCRIPTION")
  protected java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }
  @Column(name="TARGET_DEVICE")
  protected java.lang.String targetDevice;

  public java.lang.String getTargetDevice() {
    return this.targetDevice;
  }

  public void setTargetDevice(java.lang.String targetDevice) {
    this.targetDevice = targetDevice;
  }
  @Column(name="TARGET_OFFICE")
  protected java.lang.String targetOffice;

  public java.lang.String getTargetOffice() {
    return this.targetOffice;
  }

  public void setTargetOffice(java.lang.String targetOffice) {
    this.targetOffice = targetOffice;
  }
  @Column(name="TARGET_SCHED_ITEM")
  protected java.lang.String targetSchedItem;

  public java.lang.String getTargetSchedItem() {
    return this.targetSchedItem;
  }

  public void setTargetSchedItem(java.lang.String targetSchedItem) {
    this.targetSchedItem = targetSchedItem;
  }
  @Column(name="TITLE")
  protected java.lang.String title;

  public java.lang.String getTitle() {
    return this.title;
  }

  public void setTitle(java.lang.String title) {
    this.title = title;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "TARGET_SCHED_ITEM", referencedColumnName = "COUNT_SCHED_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.CountScheduleItem countScheduleItem;

  public arrow.mems.persistence.entity.CountScheduleItem getCountScheduleItem() {
    return this.countScheduleItem;
  }

  public void setCountScheduleItem(final arrow.mems.persistence.entity.CountScheduleItem countScheduleItem) {
    this.countScheduleItem = countScheduleItem;
    this.targetSchedItem = this.countScheduleItem != null ? this.countScheduleItem.getCountSchedCode() : null;
    
    if(this.countScheduleItem != null){
      this.isDeleted = this.countScheduleItem.getIsDeleted();
    }
    
  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "TARGET_DEVICE", referencedColumnName = "DEV_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Device device;

  public arrow.mems.persistence.entity.Device getDevice() {
    return this.device;
  }

  public void setDevice(final arrow.mems.persistence.entity.Device device) {
    this.device = device;
    this.targetDevice = this.device != null ? this.device.getDevCode() : null;
    
    if(this.device != null){
      this.isDeleted = this.device.getIsDeleted();
    }
    
  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "TARGET_OFFICE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Office office;

  public arrow.mems.persistence.entity.Office getOffice() {
    return this.office;
  }

  public void setOffice(final arrow.mems.persistence.entity.Office office) {
    this.office = office;
    this.targetOffice = this.office != null ? this.office.getOfficeCode() : null;
    
    if(this.office != null){
      this.isDeleted = this.office.getIsDeleted();
    }
    
  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "TARGET_SCHED_ITEM", referencedColumnName = "SCHED_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.ScheduleItem scheduleItem;

  public arrow.mems.persistence.entity.ScheduleItem getScheduleItem() {
    return this.scheduleItem;
  }

  public void setScheduleItem(final arrow.mems.persistence.entity.ScheduleItem scheduleItem) {
    this.scheduleItem = scheduleItem;
    this.targetSchedItem = this.scheduleItem != null ? this.scheduleItem.getSchedCode() : null;
    
    if(this.scheduleItem != null){
      this.isDeleted = this.scheduleItem.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}