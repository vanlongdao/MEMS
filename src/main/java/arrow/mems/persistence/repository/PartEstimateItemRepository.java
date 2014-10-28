//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.PartEstimateItem;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class PartEstimateItemRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<PartEstimateItem, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT pei FROM PartEstimateItem pei, Users u WHERE u.userId = pei.createdBy AND pei.isDeleted = 0 AND pei.peCode = ?1 AND u.officeCode = ?2")
  public abstract QueryResult<PartEstimateItem> findAllActivePartEstimateItemContainPartEstimateCode(String pPeCode, String pOfficeCode);

  @Query("SELECT p FROM PartEstimateItem p WHERE p.isDeleted = 0 AND p.partCode = ?1 AND p.peCode = ?2")
  public abstract QueryResult<PartEstimateItem> getActiveEstimateItemByPartCodeAndPeCode(String pPartCode, String pPeCode);

  @Query("SELECT p FROM PartEstimateItem p WHERE p.isDeleted = 0 AND p.peCode = ?1")
  public abstract QueryResult<PartEstimateItem> findActiveItemByPeCode(String peCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}