//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.PartOrderItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class PartOrderItemRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<PartOrderItem, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT poi FROM PartOrderItem poi WHERE poi.isDeleted = 0 AND poi.poCode = ?1 AND poi.createdBy IN ?2")
  public abstract List<PartOrderItem> findAllActivePartOrderItemInOneOffice(String poCode, List<Integer> listUsersInOneOffice);

  @Query("SELECT p FROM PartOrderItem p WHERE p.isDeleted = 0 AND p.poCode IN ?1 AND p.partCode NOT IN ?2")
  public abstract QueryResult<PartOrderItem> getAllActivePartCodeOfPartOrderItemNotUse(List<String> listPartOrderCode, List<String> listPartCodeInUse);

  @Query("SELECT poi FROM PartOrderItem poi, Users u WHERE u.userId = poi.createdBy AND poi.isDeleted = 0 AND poi.poCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<PartOrderItem> getAllActivePartOrderItemContainPartOrderCode(String pPoCode, String pOfficeCode);

  @Query("SELECT p FROM PartOrderItem p WHERE p.partOrder.actionLog.finishDate = NULL and p.checkedBy <> NULL and p.partOrder.checkedBy != 0 and p.isDeleted = ?1 and  p.createdBy IN (SELECT u.userId FROM Users u WHERE u.officeCode = ?2)")
  public abstract QueryResult<PartOrderItem> findPartOrderItemByOfficeCodeAndETA(int active, String officeCode);

  @Query("SELECT p FROM PartOrderItem p WHERE p.isDeleted = 0 AND p.partCode = ?1 AND p.poCode = ?2")
  public abstract QueryResult<PartOrderItem> getActiveOrderItemByPartCodeAndPoCode(String pPartCode, String pPoCode);

  @Query("SELECT p FROM PartOrderItem p WHERE p.isDeleted = 0 AND p.poCode = ?1")
  public abstract QueryResult<PartOrderItem> findActiveItemsByPoCode(String pPoCode);

  @Query("SELECT p FROM PartOrderItem p WHERE p.isDeleted = 0 AND p.poCode IN ?1")
  public abstract QueryResult<PartOrderItem> getAllActivePartOrderItemInListPoCode(List<String> pListPartOrderCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}