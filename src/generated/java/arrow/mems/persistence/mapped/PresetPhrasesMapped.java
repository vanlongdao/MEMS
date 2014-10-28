//@formatter:off
package arrow.mems.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/



import javax.persistence.*;
import arrow.mems.persistence.entity.AbstractApprovalEntity;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class PresetPhrasesMapped extends AbstractApprovalEntity {
  // method for ArrowEntity
  @Override
  public Class<? extends arrow.mems.persistence.entity.PresetPhrases> getEntityClass() {
    return arrow.mems.persistence.entity.PresetPhrases.class;
  }
  
  //default constructor
  public PresetPhrasesMapped() {
  }
  
  @Column(name="ID")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "preset_phrases_seq_gen")
  @SequenceGenerator(name = "preset_phrases_seq_gen", sequenceName = "preset_phrases_id_seq", allocationSize=1)
  protected int id;
  
  public int getId() {
    return this.id;
  }
  
  @Override
  public java.lang.Integer getPk() {
    return this.id;
  }

  //Normal columns
  @Column(name="CATEGORY_CODE")
  protected java.lang.String categoryCode;

  public java.lang.String getCategoryCode() {
    return this.categoryCode;
  }

  public void setCategoryCode(java.lang.String categoryCode) {
    this.categoryCode = categoryCode;
  }
  @Column(name="COUNTRY")
  protected java.lang.Integer country;

  public java.lang.Integer getCountry() {
    return this.country;
  }

  public void setCountry(java.lang.Integer country) {
    this.country = country;
  }
  @Column(name="INPUT_STR")
  protected java.lang.String inputStr;

  public java.lang.String getInputStr() {
    return this.inputStr;
  }

  public void setInputStr(java.lang.String inputStr) {
    this.inputStr = inputStr;
  }
  @Column(name="MEANING_CODE")
  protected java.lang.String meaningCode;

  public java.lang.String getMeaningCode() {
    return this.meaningCode;
  }

  public void setMeaningCode(java.lang.String meaningCode) {
    this.meaningCode = meaningCode;
  }
  @Column(name="SHOW_STR")
  protected java.lang.String showStr;

  public java.lang.String getShowStr() {
    return this.showStr;
  }

  public void setShowStr(java.lang.String showStr) {
    this.showStr = showStr;
  }
  
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off
}