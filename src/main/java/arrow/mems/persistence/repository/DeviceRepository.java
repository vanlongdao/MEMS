//@formatter:off
package arrow.mems.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.mapped.AbstractDeletableMapped_;
import arrow.mems.persistence.mapped.AbstractEntityMapped_;
import arrow.mems.persistence.mapped.DeviceMapped_;
import arrow.mems.persistence.mapped.MDeviceMapped_;
import arrow.mems.persistence.mapped.UsersMapped_;
import arrow.mems.util.string.ArrowStrUtils;


/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/


@Repository
public abstract class DeviceRepository extends arrow.framework.persistence.AbstractApprovalEntityRepositoryWrapper<Device, java.lang.Integer> {
  //@formatter:on
  /* =================== Start of manually added code after the marker line ================== */

  @Query("SELECT d FROM Device d WHERE d.isDeleted = 0 AND d.devCode =?1")
  public abstract QueryResult<Device> findActiveDeviceByDeviceCode(String devCode);

  @Query("SELECT d FROM Device d, Users u WHERE u.userId = d.createdBy AND d.isDeleted = 0 AND d.targetDevCode =?1 AND u.officeCode = ?2 AND d.mDevice.mdevType = 3")
  public abstract List<Device> getAllListActiveDeviceInInputRepairRequest(String devCode, String officeCode);

  @Query("SELECT d.mdevCode FROM Device d, Users u WHERE u.userId = d.createdBy AND d.targetDevCode IS NULL AND d.isDeleted = 0 AND d.mDevice.mdevType = 3 AND u.officeCode = ?1")
  public abstract QueryResult<String> getAllMDevCodeOfActiveDeviceIsPart(String officeCode);

  @Query("SELECT d FROM Device d, Users u WHERE u.userId = d.createdBy AND d.isDeleted = 0 AND d.targetDevCode IS NULL AND d.mdevCode IN ?1 AND u.officeCode = ?2")
  public abstract QueryResult<Device> getAllActiveDeviceOfPartsList(List<String> listMDevCodeOfPartLists, String officeCode);

  @Query("SELECT d FROM Device d, Users u WHERE u.userId = d.createdBy AND d.isDeleted = 0  AND d.mDevice.mdevType = 2 AND u.officeCode = ?1 ")
  public abstract QueryResult<Device> getAllMeasurementInOneOffice(String pOfficeCode);

  @Query("SELECT d FROM Device d WHERE d.devCode =?1 AND d.isDeleted = 1 AND d.devId =?2")
  public abstract QueryResult<Device> findDeviceAfterUpdate(String devCode, int devId);

  @Query("SELECT d FROM Device d , MDevice m WHERE m.mdevCode = d.mdevCode AND m.mdevType = 1 AND d.isDeleted = 0 AND m.isDeleted = 0 AND d.createdBy IN ?1 AND d.targetDevCode is null")
  public abstract QueryResult<Device> findAllAvaiableDevice(List<Integer> listUsersInOneOffice);

  @Query("SELECT d FROM Device d , Users u WHERE u.userId = d.createdBy AND d.isDeleted = 0 AND u.officeCode = ?1")
  public abstract QueryResult<Device> findActiveDevices(String officeCode);

  @Query("SELECT d FROM Device d WHERE d.mdevCode =?1 AND d.isDeleted = 0")
  public abstract Device findActiveDeviceByMDeviceCode(String mdevCode);

  @Query("SELECT d FROM Device d, MDevice md WHERE md.mdevCode = d.mdevCode AND md.isDeleted = d.isDeleted AND d.isDeleted = 0 AND md.mdevType=3 AND d.targetDevCode = NULL")
  public abstract QueryResult<Device> getAllDeviceNotActive();

  @Query("SELECT d FROM Device d WHERE d.isDeleted = 0 AND d.targetDevCode = ?1 AND d.createdBy IN ?2")
  public abstract QueryResult<Device> getAllPartsByTargetDevice(String devCode, List<Integer> listUsersInOneOffice);

  @Query("SELECT d FROM Device d WHERE d.isDeleted = ?1  AND d.createdBy IN (SELECT u.userId FROM Users u WHERE u.isDeleted = 0 and u.officeCode = ?2)")
  public abstract QueryResult<Device> findDevicesByOfficeCode(int isDeleted, String officeCode);


  public List<Device> findDeviceByQueryAndOfficeCode(String officeCode, String query, String departmentCode) {
    final CriteriaBuilder cb = this.getCriteriaBuilder();
    final CriteriaQuery<Device> q = cb.createQuery(Device.class);
    final Root<Device> root = q.from(Device.class);
    final Join<Device, MDevice> mdev = root.join(DeviceMapped_.mDevice);

    final Path<Integer> createdBy = root.get(AbstractEntityMapped_.createdBy);

    final Subquery<Integer> subQuery = q.subquery(Integer.class);
    final Root<Users> u = subQuery.from(Users.class);
    subQuery.select(u.get(UsersMapped_.userId));
    Predicate subWhere = cb.equal(u.get(AbstractDeletableMapped_.isDeleted), CommonConstants.FLAG_FALSE);
    subWhere = cb.and(subWhere, cb.equal(u.get(UsersMapped_.officeCode), officeCode));
    subQuery.where(subWhere);

    Predicate where = cb.equal(root.get(AbstractDeletableMapped_.isDeleted), CommonConstants.FLAG_FALSE);
    where = cb.and(where, cb.in(createdBy).value(subQuery));

    if (StringUtils.isNotEmpty(departmentCode)) {
      where = cb.and(where, cb.equal(root.get(DeviceMapped_.hospDeptCode), departmentCode));
    }

    // where for query
    final String pattern = ArrowStrUtils.likePattern(query);
    Predicate filter = cb.like(root.get(DeviceMapped_.devCode), pattern);
    filter = cb.or(filter, cb.like(cb.upper(root.get(DeviceMapped_.serialNo)), pattern));
    filter = cb.or(filter, cb.like(cb.upper(root.get(DeviceMapped_.softwareRev)), pattern));
    filter = cb.or(filter, cb.like(cb.upper(mdev.get(MDeviceMapped_.modelNo)), pattern));
    filter = cb.or(filter, cb.like(cb.upper(mdev.get(MDeviceMapped_.name)), pattern));
    filter = cb.or(filter, cb.like(cb.upper(mdev.get(MDeviceMapped_.catName)), pattern));

    q.where(cb.and(where, filter));

    return this.entityManager().createQuery(q).getResultList();



  }

  public List<Device> getAllDevicesOfOffice(String pOfficeCode) {
    return this.findDeviceByQueryAndOfficeCode(pOfficeCode, StringUtils.EMPTY, StringUtils.EMPTY);
  }

  @Query("SELECT d FROM Device d WHERE d.isDeleted = ?1 and d.supplierOffice != NULL and d.mDevice.manufacturerOffice.corpCode IN (SELECT DISTINCT u.office.corpCode FROM Users u WHERE u.officeCode = ?2 and u.isDeleted = 0)")
  public abstract QueryResult<Device> findDevicesByCorpCodeAndOfficeCode(int isDeleted, String officeCode);

  @Query("SELECT d FROM Device d, Users u WHERE d.isDeleted = 0  AND d.createdBy = u.userId AND d.devCode=?1 AND u.officeCode = ?2)")
  public abstract QueryResult<Device> findDeviceByDevCodeAndOfficeCode(String devCode, String officeCode);


  /* =================== End of manually added code before the marker line =================== */
  //@formatter:off



}