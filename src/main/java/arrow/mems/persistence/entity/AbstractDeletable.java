//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.mems.persistence.mapped.AbstractDeletableMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public abstract class AbstractDeletable extends AbstractDeletableMapped {

  public AbstractDeletable() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  


  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  