//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class OfficeMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Office> getEntityClass() {
    return arrow.mems.persistence.entity.Office.class;
  }
  
  //default constructor
  public OfficeMapped() {
  }
  
  @Column(name="OFFICE_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "office_seq_gen")
  @SequenceGenerator(name = "office_seq_gen", sequenceName = "office_office_id_seq", allocationSize=1)
  protected int officeId;
  
  public int getOfficeId() {
    return this.officeId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.officeId;
  }

  //Normal columns
  @Column(name="ACCOUNTANT_PSN")
  protected java.lang.String accountantPsn;

  public java.lang.String getAccountantPsn() {
    return this.accountantPsn;
  }

  public void setAccountantPsn(java.lang.String accountantPsn) {
    this.accountantPsn = accountantPsn;
  }
  @Column(name="ADDR_CODE")
  protected java.lang.String addrCode;

  public java.lang.String getAddrCode() {
    return this.addrCode;
  }

  public void setAddrCode(java.lang.String addrCode) {
    this.addrCode = addrCode;
  }
  @Column(name="CORP_CODE")
  protected java.lang.String corpCode;

  public java.lang.String getCorpCode() {
    return this.corpCode;
  }

  public void setCorpCode(java.lang.String corpCode) {
    this.corpCode = corpCode;
  }
  @Column(name="EQUIPMENT_MGR_PSN")
  protected java.lang.String equipmentMgrPsn;

  public java.lang.String getEquipmentMgrPsn() {
    return this.equipmentMgrPsn;
  }

  public void setEquipmentMgrPsn(java.lang.String equipmentMgrPsn) {
    this.equipmentMgrPsn = equipmentMgrPsn;
  }
  @Column(name="FAX")
  protected java.lang.String fax;

  public java.lang.String getFax() {
    return this.fax;
  }

  public void setFax(java.lang.String fax) {
    this.fax = fax;
  }
  @Column(name="MANAGER_PSN")
  protected java.lang.String managerPsn;

  public java.lang.String getManagerPsn() {
    return this.managerPsn;
  }

  public void setManagerPsn(java.lang.String managerPsn) {
    this.managerPsn = managerPsn;
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
  @Column(name="TAX_CODE")
  protected java.lang.String taxCode;

  public java.lang.String getTaxCode() {
    return this.taxCode;
  }

  public void setTaxCode(java.lang.String taxCode) {
    this.taxCode = taxCode;
  }
  @Column(name="TECHNICIAN_PSN")
  protected java.lang.String technicianPsn;

  public java.lang.String getTechnicianPsn() {
    return this.technicianPsn;
  }

  public void setTechnicianPsn(java.lang.String technicianPsn) {
    this.technicianPsn = technicianPsn;
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
    @JoinColumn(name = "CORP_CODE", referencedColumnName = "CORP_CODE", insertable = false, updatable = false), 
    @JoinColumn(name = "IS_DELETED", referencedColumnName = "IS_DELETED", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.Corporate corporate;

  public arrow.mems.persistence.entity.Corporate getCorporate() {
    return this.corporate;
  }

  public void setCorporate(final arrow.mems.persistence.entity.Corporate corporate) {
    this.corporate = corporate;
    this.corpCode = this.corporate != null ? this.corporate.getCorpCode() : null;
    
    if(this.corporate != null){
      this.isDeleted = this.corporate.getIsDeleted();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}