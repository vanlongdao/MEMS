//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.MtCurrency;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class MtCurrencyRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<MtCurrency, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT p FROM MtCurrency p, Users u WHERE u.userId = p.createdBy AND u.officeCode = ?1 ORDER BY p.ccyName ASC")
  public abstract List<MtCurrency> findAllCurrencyInOneOffice(String officeCode);

  @Query("SELECT p FROM MtCurrency p WHERE p.ccyId =?")
  public abstract QueryResult<MtCurrency> findAllCurrencyInOneOffice(int ccyId);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}