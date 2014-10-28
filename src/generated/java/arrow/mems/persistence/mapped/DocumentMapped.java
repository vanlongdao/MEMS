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
public class DocumentMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Document> getEntityClass() {
    return arrow.mems.persistence.entity.Document.class;
  }

  //default constructor
  public DocumentMapped() {
  }

  @Column(name="DOC_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_seq_gen")
  @SequenceGenerator(name = "document_seq_gen", sequenceName = "document_doc_id_seq", allocationSize=1)
  protected int docId;

  public int getDocId() {
    return this.docId;
  }

  @Override
  public java.lang.Integer getPk() {
    return this.docId;
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
  @Column(name="INST01")
  protected java.lang.String inst01;

  public java.lang.String getInst01() {
    return this.inst01;
  }

  public void setInst01(java.lang.String inst01) {
    this.inst01 = inst01;
  }
  @Column(name="INST_V")
  protected java.lang.String instV;

  public java.lang.String getInstV() {
    return this.instV;
  }

  public void setInstV(java.lang.String instV) {
    this.instV = instV;
  }
  @Column(name="MDEV_CODE")
  protected java.lang.String mdevCode;

  public java.lang.String getMdevCode() {
    return this.mdevCode;
  }

  public void setMdevCode(java.lang.String mdevCode) {
    this.mdevCode = mdevCode;
  }
  @Column(name="PERFORMANCE01")
  protected java.lang.String performance01;

  public java.lang.String getPerformance01() {
    return this.performance01;
  }

  public void setPerformance01(java.lang.String performance01) {
    this.performance01 = performance01;
  }
  @Column(name="PERFORMANCE_V")
  protected java.lang.String performanceV;

  public java.lang.String getPerformanceV() {
    return this.performanceV;
  }

  public void setPerformanceV(java.lang.String performanceV) {
    this.performanceV = performanceV;
  }
  @Column(name="REPLACE_PART_V")
  protected java.lang.String replacePartV;

  public java.lang.String getReplacePartV() {
    return this.replacePartV;
  }

  public void setReplacePartV(java.lang.String replacePartV) {
    this.replacePartV = replacePartV;
  }
  @Column(name="SAFETY01")
  protected java.lang.String safety01;

  public java.lang.String getSafety01() {
    return this.safety01;
  }

  public void setSafety01(java.lang.String safety01) {
    this.safety01 = safety01;
  }
  @Column(name="SAFETY_V")
  protected java.lang.String safetyV;

  public java.lang.String getSafetyV() {
    return this.safetyV;
  }

  public void setSafetyV(java.lang.String safetyV) {
    this.safetyV = safetyV;
  }
  @Column(name="SERVICE01")
  protected java.lang.String service01;

  public java.lang.String getService01() {
    return this.service01;
  }

  public void setService01(java.lang.String service01) {
    this.service01 = service01;
  }
  @Column(name="SERVICE02")
  protected java.lang.String service02;

  public java.lang.String getService02() {
    return this.service02;
  }

  public void setService02(java.lang.String service02) {
    this.service02 = service02;
  }
  @Column(name="SOFTWARE_REV")
  protected java.lang.String softwareRev;

  public java.lang.String getSoftwareRev() {
    return this.softwareRev;
  }

  public void setSoftwareRev(java.lang.String softwareRev) {
    this.softwareRev = softwareRev;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "MDEV_CODE", referencedColumnName = "MDEV_CODE", insertable = false, updatable = false),
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.MDevice mDevice;

  public arrow.mems.persistence.entity.MDevice getMDevice() {
    return this.mDevice;
  }

  public void setMDevice(final arrow.mems.persistence.entity.MDevice mDevice) {
    this.mDevice = mDevice;
    this.mdevCode = this.mDevice != null ? this.mDevice.getMdevCode() : null;

    if(this.mDevice != null){
      this.isDeleted = this.mDevice.getIsDeleted();
    }

  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}