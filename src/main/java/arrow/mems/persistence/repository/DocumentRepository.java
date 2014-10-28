//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Document;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class DocumentRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Document, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */
  
  @Query("SELECT d FROM Document d, MDevice m WHERE m.mdevCode=d.mdevCode and d.mdevCode = ?1 and d.isDeleted = ?2")
  public abstract QueryResult<Document> findDocumentByDeviceCodeAndIsDeleted(String mdevCode, int isDeleted);

  @Query("SELECT d FROM Document d WHERE d.mdevCode = ?1 AND d.isDeleted = 0 AND d.createdBy IN ?2")
  public abstract QueryResult<Document> findAllDocumentByMDevice(String mDeviceCode, List<Integer> listUsersInOneOffice);

  @Query("SELECT d FROM Document d WHERE d.mdevCode = ?1 and d.isDeleted = 0 AND d.softwareRev = ?2")
  public abstract QueryResult<Document> findDocumentByDeviceCodeAndIsDeleted(String mdevCode, String softwareRevision);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}