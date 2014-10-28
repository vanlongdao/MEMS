//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.PersonClassMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="PERSON_CLASS")
public class PersonClass extends PersonClassMapped {

  public PersonClass() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */



  public String getPersonTitle() {
    return (this.getClassLocalPre() == null ? "" : (this.getClassLocalPre() + " ")) + (this.getClassLocalPost() == null ? "" : ("" + this
        .getClassLocalPost()));
  }
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}