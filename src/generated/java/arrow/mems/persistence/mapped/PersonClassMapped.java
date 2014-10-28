//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class PersonClassMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.PersonClass> getEntityClass() {
    return arrow.mems.persistence.entity.PersonClass.class;
  }
  
  //default constructor
  public PersonClassMapped() {
  }
  
  @Column(name="CLASS_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_class_seq_gen")
  @SequenceGenerator(name = "person_class_seq_gen", sequenceName = "person_class_class_id_seq", allocationSize=1)
  protected java.lang.Integer classId;
  
  public java.lang.Integer getClassId() {
    return this.classId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.classId;
  }

  //Normal columns
  @Column(name="CLASS_CODE")
  protected java.lang.String classCode;

  public java.lang.String getClassCode() {
    return this.classCode;
  }

  public void setClassCode(java.lang.String classCode) {
    this.classCode = classCode;
  }
  @Column(name="CLASS_LOCAL_POST")
  protected java.lang.String classLocalPost;

  public java.lang.String getClassLocalPost() {
    return this.classLocalPost;
  }

  public void setClassLocalPost(java.lang.String classLocalPost) {
    this.classLocalPost = classLocalPost;
  }
  @Column(name="CLASS_LOCAL_PRE")
  protected java.lang.String classLocalPre;

  public java.lang.String getClassLocalPre() {
    return this.classLocalPre;
  }

  public void setClassLocalPre(java.lang.String classLocalPre) {
    this.classLocalPre = classLocalPre;
  }
  @Column(name="COUNTRY_ID")
  protected java.lang.Integer countryId;

  public java.lang.Integer getCountryId() {
    return this.countryId;
  }

  public void setCountryId(java.lang.Integer countryId) {
    this.countryId = countryId;
  }
  //Foreign keys
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID", insertable = false, updatable = false)
  })
  protected arrow.mems.persistence.entity.MtCountry mtCountry;

  public arrow.mems.persistence.entity.MtCountry getMtCountry() {
    return this.mtCountry;
  }

  public void setMtCountry(final arrow.mems.persistence.entity.MtCountry mtCountry) {
    this.mtCountry = mtCountry;
    if(this.mtCountry != null){
      this.countryId = this.mtCountry.getCountryId();
    }
    
  }

  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}