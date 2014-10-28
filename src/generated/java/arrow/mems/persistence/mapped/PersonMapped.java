//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class PersonMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Person> getEntityClass() {
    return arrow.mems.persistence.entity.Person.class;
  }
  
  //default constructor
  public PersonMapped() {
  }
  
  @Column(name="PSN_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq_gen")
  @SequenceGenerator(name = "person_seq_gen", sequenceName = "person_psn_id_seq", allocationSize=1)
  protected int psnId;
  
  public int getPsnId() {
    return this.psnId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.psnId;
  }

  //Normal columns
  @Column(name="ADDR_CODE")
  protected java.lang.String addrCode;

  public java.lang.String getAddrCode() {
    return this.addrCode;
  }

  public void setAddrCode(java.lang.String addrCode) {
    this.addrCode = addrCode;
  }
  @Column(name="CONTACT_METHOD")
  protected java.lang.String contactMethod;

  public java.lang.String getContactMethod() {
    return this.contactMethod;
  }

  public void setContactMethod(java.lang.String contactMethod) {
    this.contactMethod = contactMethod;
  }
  @Column(name="EMAIL")
  protected java.lang.String email;

  public java.lang.String getEmail() {
    return this.email;
  }

  public void setEmail(java.lang.String email) {
    this.email = email;
  }
  @Column(name="FAX")
  protected java.lang.String fax;

  public java.lang.String getFax() {
    return this.fax;
  }

  public void setFax(java.lang.String fax) {
    this.fax = fax;
  }
  @Column(name="NAME")
  protected java.lang.String name;

  public java.lang.String getName() {
    return this.name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }
  @Column(name="OFFICE_CODE")
  protected java.lang.String officeCode;

  public java.lang.String getOfficeCode() {
    return this.officeCode;
  }

  public void setOfficeCode(java.lang.String officeCode) {
    this.officeCode = officeCode;
  }
  @Column(name="PSN_CODE")
  protected java.lang.String psnCode;

  public java.lang.String getPsnCode() {
    return this.psnCode;
  }

  public void setPsnCode(java.lang.String psnCode) {
    this.psnCode = psnCode;
  }
  @Column(name="TEL")
  protected java.lang.String tel;

  public java.lang.String getTel() {
    return this.tel;
  }

  public void setTel(java.lang.String tel) {
    this.tel = tel;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "ADDR_CODE", referencedColumnName = "ADDR_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Address address;

  public arrow.mems.persistence.entity.Address getAddress() {
    return this.address;
  }

  public void setAddress(final arrow.mems.persistence.entity.Address address) {
    this.address = address;
    this.addrCode = this.address != null ? this.address.getAddrCode() : null;
    
    if(this.address != null){
      this.isDeleted = this.address.getIsDeleted();
    }
    
  }

  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "OFFICE_CODE", referencedColumnName = "OFFICE_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Office office;

  public arrow.mems.persistence.entity.Office getOffice() {
    return this.office;
  }

  public void setOffice(final arrow.mems.persistence.entity.Office office) {
    this.office = office;
    this.officeCode = this.office != null ? this.office.getOfficeCode() : null;
    
    if(this.office != null){
      this.isDeleted = this.office.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}