//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class MDeviceMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.MDevice> getEntityClass() {
    return arrow.mems.persistence.entity.MDevice.class;
  }
  
  //default constructor
  public MDeviceMapped() {
  }
  
  @Column(name="MDEV_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_device_seq_gen")
  @SequenceGenerator(name = "m_device_seq_gen", sequenceName = "m_device_mdev_id_seq", allocationSize=1)
  protected int mdevId;
  
  public int getMdevId() {
    return this.mdevId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.mdevId;
  }

  //Normal columns
  @Column(name="CATALOG")
  protected java.lang.String catalog;

  public java.lang.String getCatalog() {
    return this.catalog;
  }

  public void setCatalog(java.lang.String catalog) {
    this.catalog = catalog;
  }
  @Column(name="CAT_NAME")
  protected java.lang.String catName;

  public java.lang.String getCatName() {
    return this.catName;
  }

  public void setCatName(java.lang.String catName) {
    this.catName = catName;
  }
  @Column(name="COUNTRY")
  protected java.lang.Integer country;

  public java.lang.Integer getCountry() {
    return this.country;
  }

  public void setCountry(java.lang.Integer country) {
    this.country = country;
  }
  @Column(name="MANUFACTURER_CODE")
  protected java.lang.String manufacturerCode;

  public java.lang.String getManufacturerCode() {
    return this.manufacturerCode;
  }

  public void setManufacturerCode(java.lang.String manufacturerCode) {
    this.manufacturerCode = manufacturerCode;
  }
  @Column(name="MANUFACTURER_PSN")
  protected java.lang.String manufacturerPsn;

  public java.lang.String getManufacturerPsn() {
    return this.manufacturerPsn;
  }

  public void setManufacturerPsn(java.lang.String manufacturerPsn) {
    this.manufacturerPsn = manufacturerPsn;
  }
  @Column(name="MDEV_CODE")
  protected java.lang.String mdevCode;

  public java.lang.String getMdevCode() {
    return this.mdevCode;
  }

  public void setMdevCode(java.lang.String mdevCode) {
    this.mdevCode = mdevCode;
  }
  @Column(name="MDEV_TYPE")
  protected java.lang.Integer mdevType;

  public java.lang.Integer getMdevType() {
    return this.mdevType;
  }

  public void setMdevType(java.lang.Integer mdevType) {
    this.mdevType = mdevType;
  }
  @Column(name="MODEL_NO")
  protected java.lang.String modelNo;

  public java.lang.String getModelNo() {
    return this.modelNo;
  }

  public void setModelNo(java.lang.String modelNo) {
    this.modelNo = modelNo;
  }
  @Column(name="NAME")
  protected java.lang.String name;

  public java.lang.String getName() {
    return this.name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }
  @Column(name="NOTICE")
  protected java.lang.String notice;

  public java.lang.String getNotice() {
    return this.notice;
  }

  public void setNotice(java.lang.String notice) {
    this.notice = notice;
  }
  @Column(name="OWNER_CORP_CODE")
  protected java.lang.String ownerCorpCode;

  public java.lang.String getOwnerCorpCode() {
    return this.ownerCorpCode;
  }

  public void setOwnerCorpCode(java.lang.String ownerCorpCode) {
    this.ownerCorpCode = ownerCorpCode;
  }
  @Column(name="PICTURE")
  protected byte[] picture;

  public byte[] getPicture() {
    return this.picture;
  }

  public void setPicture(byte[] picture) {
    this.picture = picture;
  }
  @Column(name="SPECIFICATION")
  protected java.lang.String specification;

  public java.lang.String getSpecification() {
    return this.specification;
  }

  public void setSpecification(java.lang.String specification) {
    this.specification = specification;
  }
  @Column(name="SUPPLIER_CODE")
  protected java.lang.String supplierCode;

  public java.lang.String getSupplierCode() {
    return this.supplierCode;
  }

  public void setSupplierCode(java.lang.String supplierCode) {
    this.supplierCode = supplierCode;
  }
  @Column(name="SUPPLIER_PSN")
  protected java.lang.String supplierPsn;

  public java.lang.String getSupplierPsn() {
    return this.supplierPsn;
  }

  public void setSupplierPsn(java.lang.String supplierPsn) {
    this.supplierPsn = supplierPsn;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "COUNTRY", referencedColumnName = "COUNTRY_ID", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.MtCountry mtCountry;

  public arrow.mems.persistence.entity.MtCountry getMtCountry() {
    return this.mtCountry;
  }

  public void setMtCountry(final arrow.mems.persistence.entity.MtCountry mtCountry) {
    this.mtCountry = mtCountry;
    if(this.mtCountry != null){
      this.country = this.mtCountry.getCountryId();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}