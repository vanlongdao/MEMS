//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class MtCountryMapped extends AbstractEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.MtCountry> getEntityClass() {
    return arrow.mems.persistence.entity.MtCountry.class;
  }
  
  //default constructor
  public MtCountryMapped() {
  }
  
  @Column(name="COUNTRY_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mt_country_seq_gen")
  @SequenceGenerator(name = "mt_country_seq_gen", sequenceName = "mt_country_country_id_seq", allocationSize=1)
  protected int countryId;
  
  public int getCountryId() {
    return this.countryId;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.countryId;
  }

  //Normal columns
  @Column(name="NAME")
  protected java.lang.String name;

  public java.lang.String getName() {
    return this.name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }
  @Column(name="NAME_EN")
  protected java.lang.String nameEn;

  public java.lang.String getNameEn() {
    return this.nameEn;
  }

  public void setNameEn(java.lang.String nameEn) {
    this.nameEn = nameEn;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}