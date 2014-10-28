//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.MtCountry;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class MtCountryRepository extends arrow.framework.persistence.AbstractEntityRepositoryWrapper<MtCountry, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT m FROM MtCountry m WHERE m.countryId =?1")
  public abstract MtCountry getMtCountryByMtCountryId(int countryId);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}