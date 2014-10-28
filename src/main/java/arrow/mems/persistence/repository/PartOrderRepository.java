//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.PartOrder;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class PartOrderRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<PartOrder, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT po.poCode FROM PartOrder po, Users u WHERE u.userId = po.createdBy AND po.isDeleted = 0 AND po.actionCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<String> getAllActivePartOrderCodeUseActionCode(String actionCode, String officeCode);

  @Query("SELECT p FROM PartOrder p, Users u WHERE u.userId = p.createdBy AND p.isDeleted=u.isDeleted AND p.isDeleted = 0 AND p.actionCode = ?1 AND u.officeCode = ?2 ORDER BY p.orderDate")
  public abstract QueryResult<PartOrder> getAllActivePartOrderUseActionCodeInOneOffice(String actionCode, String officeCode);

  @Query("SELECT p FROM PartOrder p WHERE p.isDeleted = 0 AND p.poCode = ?1")
  public abstract QueryResult<PartOrder> getActiveOrderByPoCode(String poCode);

  @Query("SELECT p FROM PartOrder p WHERE p.isDeleted = 0 AND p.actionCode = ?1")
  public abstract QueryResult<PartOrder> getActiveOrderByActionCode(String actionCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}