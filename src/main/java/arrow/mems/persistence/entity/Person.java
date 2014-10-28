//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import arrow.mems.persistence.mapped.PersonMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="PERSON")
public class Person extends PersonMapped {

  public Person() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  public String getFullName() {
    final StringBuffer fullName = new StringBuffer();
    fullName.append(this.getName());
    if (StringUtils.isNotEmpty(this.getTel()) && (this.getTel() != null)) {
      fullName.append(", " + this.getTel());
    }
    if (StringUtils.isNotEmpty(this.getFax()) && (this.getFax() != null)) {
      fullName.append(", " + this.getFax());
    }
    if (StringUtils.isNotEmpty(this.getEmail()) && (this.getEmail() != null)) {
      fullName.append(", " + this.getEmail());
    }
    return fullName.toString();
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}