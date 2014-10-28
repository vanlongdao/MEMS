//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class AcquisitionMasterMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.AcquisitionMaster> getEntityClass() {
    return arrow.mems.persistence.entity.AcquisitionMaster.class;
  }
  
  //default constructor
  public AcquisitionMasterMapped() {
  }
  
  @Column(name="ACQ_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acquisition_master_seq_gen")
  @SequenceGenerator(name = "acquisition_master_seq_gen", sequenceName = "acquisition_master_acq_id_seq", allocationSize=1)
  protected int acqId;
  
  public int getAcqId() {
    return this.acqId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.acqId;
  }

  //Normal columns
  @Column(name="ACQ_CODE")
  protected java.lang.String acqCode;

  public java.lang.String getAcqCode() {
    return this.acqCode;
  }

  public void setAcqCode(java.lang.String acqCode) {
    this.acqCode = acqCode;
  }
  @Column(name="COUNTRY")
  protected java.lang.Integer country;

  public java.lang.Integer getCountry() {
    return this.country;
  }

  public void setCountry(java.lang.Integer country) {
    this.country = country;
  }
  @Column(name="IS_NEED_PRICE")
  protected java.lang.Integer isNeedPrice;

  public java.lang.Integer getIsNeedPrice() {
    return this.isNeedPrice;
  }

  public void setIsNeedPrice(java.lang.Integer isNeedPrice) {
    this.isNeedPrice = isNeedPrice;
  }
  @Column(name="MSG_LOCAL")
  protected java.lang.String msgLocal;

  public java.lang.String getMsgLocal() {
    return this.msgLocal;
  }

  public void setMsgLocal(java.lang.String msgLocal) {
    this.msgLocal = msgLocal;
  }
  @Column(name="MSG_PRICE_ENTRY")
  protected java.lang.String msgPriceEntry;

  public java.lang.String getMsgPriceEntry() {
    return this.msgPriceEntry;
  }

  public void setMsgPriceEntry(java.lang.String msgPriceEntry) {
    this.msgPriceEntry = msgPriceEntry;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}