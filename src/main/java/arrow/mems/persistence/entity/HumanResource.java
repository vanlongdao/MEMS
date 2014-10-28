//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.HumanResourceMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="HUMAN_RESOURCE")
public class HumanResource extends HumanResourceMapped {

  public HumanResource() {
  }


  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  public String getFullName() {
    if (this.personClass != null)
      return (this.personClass.getClassLocalPre() == null ? "" : (this.personClass.getClassLocalPre() + " ")) + this.person.getName() + (this.personClass
          .getClassLocalPost() == null ? "" : (" " + this.personClass.getClassLocalPost()));
    else if (this.person != null)
      return this.person.getName();
    return null;
  }

  public String getPersonClassName() {
    if (this.personClass != null)
      return (this.personClass.getClassLocalPre() == null ? "" : (this.personClass.getClassLocalPre() + " ")) + (this.personClass.getClassLocalPost() == null ? ""
          : (" " + this.personClass.getClassLocalPost()));
    else if (this.person != null)
      return this.person.getName();
    return null;
  }


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}