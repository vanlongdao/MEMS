//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.SearchCondition;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class SearchConditionRepository extends arrow.framework.persistence.AbstractDeletableRepositoryWrapper<SearchCondition, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT s FROM SearchCondition s WHERE (s.createdBy = ?1 OR s.officeCode = ?2) AND s.isDeleted = 0")
  public abstract List<SearchCondition> findCondition(int createdBy, String officeCode);

  @Modifying
  @Query("UPDATE SearchCondition s SET s.isDeleted = ?1 WHERE s.id = ?2")
  public abstract int updateIsDeleted(int isDeleted, int id);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}