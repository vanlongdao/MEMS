//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.PresetPhrasesMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="PRESET_PHRASES")
public class PresetPhrases extends PresetPhrasesMapped {

  public PresetPhrases() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  
  @ManyToOne
  @JoinColumns({@JoinColumn(name = "COUNTRY", referencedColumnName = "COUNTRY_ID", insertable = false, updatable = false)})
  protected arrow.mems.persistence.entity.MtCountry mtCountry;

  public arrow.mems.persistence.entity.MtCountry getMtCountry() {
    return this.mtCountry;
  }

  public void setMtCountry(final arrow.mems.persistence.entity.MtCountry mtCountry) {
    this.mtCountry = mtCountry;
    if (this.mtCountry != null) {
      this.country = this.mtCountry.getCountryId();
    }

  }

  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  