//@formatter:off
package arrow.mems.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;
import javax.persistence.Table;

import arrow.mems.persistence.mapped.BudgetMapped;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
@Table(name="BUDGET")
public class Budget extends BudgetMapped {

  public Budget() {
  }


  //@formatter:on  
  /* =================== Start of manually added code after the marker line ================== */
  


  
  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}  