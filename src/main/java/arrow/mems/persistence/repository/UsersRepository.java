//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.persistence.entity.Users;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class UsersRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Users, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT u FROM Users u WHERE u.loginName = ?1 and u.isDeleted = ?2")
  public abstract QueryResult<Users> findUserByLoginName(String userName, int isDeleted);

  @Query("SELECT u FROM Users u WHERE u.loginName = ?1")
  public abstract QueryResult<Users> findAllUserByLoginName(String userName);

  @Query("SELECT u FROM Users u WHERE u.loginName = ?1")
  public abstract Users findUserByNameLogin(String userName);

  @Query("SELECT u FROM Users u WHERE u.userId = ?1")
  public abstract QueryResult<Users> findUserById(int id);

  @Query("SELECT u FROM Users u WHERE u.isDeleted = ?1 ORDER BY u.createdAt DESC")
  public abstract QueryResult<Users> findAllUserByIsDeleted(int isDeleted);

  @Query("SELECT u FROM Users u WHERE u.isDeleted = ?1 and u.userId <> ?2 ORDER BY u.createdAt DESC")
  public abstract QueryResult<Users> findAllUserByIsDeletedExceptUserLogging(int isDeleted, int userId);

  @Query("SELECT u FROM Users u WHERE u.email = ?1 and u.loginName = ?2 and u.isDeleted = ?3")
  public abstract QueryResult<Users> findUserByLoginNameAndEmail(String email, String loginName, int isDeleted);

  @Query("SELECT u FROM Users as u WHERE u.sessionid = ?1")
  public abstract QueryResult<Users> findUserBySessionId(String sessionId);

  @Modifying
  @Query("UPDATE Users as u SET u.theme = ?1 WHERE u.userId=?2")
  public abstract void addThemeToUser(String theme, int userId);

  @Modifying
  @Query("UPDATE Users as u SET u.sessionid = ?1 WHERE u.email = ?2 and u.loginName = ?3")
  public abstract int updateSessionId(String sessionId, String email, String loginName);

  @Modifying
  @Query("UPDATE Users as u SET u.password = ?1, u.sessionid = ?2  WHERE u.userId=?3")
  public abstract int updatePasswordAndSessionById(String newPassword, String sessionId, int userId);

  @Modifying
  @Query("UPDATE Users as u SET u.password = ?1  WHERE u.userId=?2")
  public abstract int updatePasswordById(String newPassword, int userId);

  @Modifying
  @Query("UPDATE Users as u SET u.isDeleted = ?1 WHERE u.userId = ?2")
  public abstract int updateIsDeleted(int isDeleted, int userId);

  @Modifying
  @Query("UPDATE Users as u SET u.name = ?1, u.email = ?2 WHERE u.userId = ?3")
  public abstract int updateCurrentUser(String userName, String email, int userId);

  @Modifying
  @Query("UPDATE Users as u SET u.name = ?1, u.email = ?2, u.accountType = ?3, u.loginName = ?4, u.isSupervisor = ?5, u.password = ?6 WHERE u.userId = ?7")
  public abstract int updateCurrentUser(String userName, String email, int accountType, String loginName, int suppervisor, String password, int userId);

  @Query("SELECT u.userId FROM Users u WHERE u.isDeleted = 0 AND u.officeCode =?1")
  public abstract List<Integer> findUserInOneOffice(String officeCode);

  @Query("SELECT u FROM Users u WHERE u.userId =?1 AND u.isDeleted = 0")
  public abstract Users findActiveUserById(int userId);

  @Query("SELECT u FROM Users u WHERE u.isDeleted = 0 AND u.isSupervisor = 1 AND u.userId IN ?1")
  public abstract List<Users> findActiveUsersInOneOffice(List<Integer> userIds);

  @Query("SELECT u FROM Users u WHERE u.isDeleted = 0 AND u.psnCode IN ?1")
  public abstract QueryResult<Users> findActiveUserByPsnCodes(List<String> pListPsnCode);

  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off

}