//@formatter:off
package arrow.mems.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.mems.persistence.entity.PersonClass;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class PersonClassDto extends PersonClass {
  private java.lang.Integer classId;

  @Override
  public java.lang.Integer getClassId() {
    return this.classId;
  }

  public void setClassId(final java.lang.Integer classId) {
    this.classId = classId;
  }

  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  



  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}