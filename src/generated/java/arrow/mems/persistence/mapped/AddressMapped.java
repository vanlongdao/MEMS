//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class AddressMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Address> getEntityClass() {
    return arrow.mems.persistence.entity.Address.class;
  }
  
  //default constructor
  public AddressMapped() {
  }
  
  @Column(name="ADDR_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq_gen")
  @SequenceGenerator(name = "address_seq_gen", sequenceName = "address_addr_id_seq", allocationSize=1)
  protected int addrId;
  
  public int getAddrId() {
    return this.addrId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.addrId;
  }

  //Normal columns
  @Column(name="ADDRESS1")
  protected java.lang.String address1;

  public java.lang.String getAddress1() {
    return this.address1;
  }

  public void setAddress1(java.lang.String address1) {
    this.address1 = address1;
  }
  @Column(name="ADDRESS2")
  protected java.lang.String address2;

  public java.lang.String getAddress2() {
    return this.address2;
  }

  public void setAddress2(java.lang.String address2) {
    this.address2 = address2;
  }
  @Column(name="ADDR_CODE")
  protected java.lang.String addrCode;

  public java.lang.String getAddrCode() {
    return this.addrCode;
  }

  public void setAddrCode(java.lang.String addrCode) {
    this.addrCode = addrCode;
  }
  @Column(name="CITY")
  protected java.lang.String city;

  public java.lang.String getCity() {
    return this.city;
  }

  public void setCity(java.lang.String city) {
    this.city = city;
  }
  @Column(name="CONTACT_METHOD")
  protected java.lang.String contactMethod;

  public java.lang.String getContactMethod() {
    return this.contactMethod;
  }

  public void setContactMethod(java.lang.String contactMethod) {
    this.contactMethod = contactMethod;
  }
  @Column(name="COUNTRY")
  protected java.lang.Integer country;

  public java.lang.Integer getCountry() {
    return this.country;
  }

  public void setCountry(java.lang.Integer country) {
    this.country = country;
  }
  @Column(name="PROVINCE")
  protected java.lang.String province;

  public java.lang.String getProvince() {
    return this.province;
  }

  public void setProvince(java.lang.String province) {
    this.province = province;
  }
  @Column(name="ZIP_CODE")
  protected java.lang.String zipCode;

  public java.lang.String getZipCode() {
    return this.zipCode;
  }

  public void setZipCode(java.lang.String zipCode) {
    this.zipCode = zipCode;
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