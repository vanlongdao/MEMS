//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.PersonClass;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class PersonClassRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<PersonClass, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT p FROM PersonClass p WHERE  p.classCode=?1")
  public abstract PersonClass findPersonClassByClassCode(String classCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}