//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class CorporateMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.Corporate> getEntityClass() {
    return arrow.mems.persistence.entity.Corporate.class;
  }
  
  //default constructor
  public CorporateMapped() {
  }
  
  @Column(name="CORP_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "corporate_seq_gen")
  @SequenceGenerator(name = "corporate_seq_gen", sequenceName = "corporate_corp_id_seq", allocationSize=1)
  protected int corpId;
  
  public int getCorpId() {
    return this.corpId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.corpId;
  }

  //Normal columns
  @Column(name="CORP_CODE")
  protected java.lang.String corpCode;

  public java.lang.String getCorpCode() {
    return this.corpCode;
  }

  public void setCorpCode(java.lang.String corpCode) {
    this.corpCode = corpCode;
  }
  @Column(name="COUNTRY")
  protected int country;

  public int getCountry() {
    return this.country;
  }

  public void setCountry(int country) {
    this.country = country;
  }
  @Column(name="NAME")
  protected java.lang.String name;

  public java.lang.String getName() {
    return this.name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
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