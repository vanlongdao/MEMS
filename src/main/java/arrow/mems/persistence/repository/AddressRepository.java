//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.time.LocalDateTime;
import java.util.List;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Address;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class AddressRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Address, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT MAX(addrCode) FROM Address as a WHERE a.addrCode like ?1")
  public abstract QueryResult<String> getMaxCodeByPrefix(String prefix);

  @Query("SELECT a FROM Address a WHERE a.addrCode =?1")
  public abstract Address getAddressByAddressCode(String addressCode);

  @Query("SELECT a FROM Address a, Users u WHERE u.userId = a.createdBy AND a.isDeleted = 0 AND u.officeCode = ?1")
  public abstract List<Address> findAllActiveAddressInOneOffice(String officeCode);

  @Modifying
  @Query("UPDATE Address a SET a.checkedBy = ?1, a.checkedAt = ?3 WHERE a.addrCode = ?2 and a.isDeleted = 0")
  public abstract int approvalAddress(int checkedBy, String addrCode, LocalDateTime checkedAt);

  @Modifying
  @Query("UPDATE Address a SET a.isDeleted = ?1 WHERE a.addrCode = ?2")
  public abstract int deletedAddress(int isDeleted, String addrCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}