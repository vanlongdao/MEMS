package arrow.mems.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.collections.CollectionUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.mems.constant.CommonConstants;
import arrow.mems.constant.MemsDataType;
import arrow.mems.messages.MMIXMessages;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.AbstractDeletable;
import arrow.mems.persistence.entity.AlertByCount;
import arrow.mems.persistence.entity.ApprovalSummary;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.entity.PartsList;
import arrow.mems.persistence.entity.Schedule;
import arrow.mems.persistence.mapped.AbstractApprovalEntityMapped_;
import arrow.mems.persistence.mapped.AbstractDeletableMapped_;
import arrow.mems.persistence.mapped.AlertByCountMapped_;
import arrow.mems.persistence.mapped.BudgetMapped_;
import arrow.mems.persistence.mapped.DocumentMapped_;
import arrow.mems.persistence.mapped.HospitalDeptMapped_;
import arrow.mems.persistence.mapped.HospitalMapped_;
import arrow.mems.persistence.mapped.HumanResourceMapped_;
import arrow.mems.persistence.mapped.MDeviceMapped_;
import arrow.mems.persistence.mapped.MdevChecklistItemMapped_;
import arrow.mems.persistence.mapped.MdevChecklistMapped_;
import arrow.mems.persistence.mapped.PartsListMapped_;
import arrow.mems.persistence.mapped.ScheduleMapped_;

@Named
@SuppressWarnings({"rawtypes", "unchecked"})
public class ApprovalDataService extends AbstractService {

  private Map<String, Class<? extends AbstractApprovalEntity>> mapTypeAndEntityClass = new HashMap<String, Class<? extends AbstractApprovalEntity>>(
      MemsDataType.buildMapTypeAndEntity());

  private Map<String, SingularAttribute> mapTypeAndCodeField = new HashMap<String, SingularAttribute>(MemsDataType.buildMapTypeAndCodeField());



  public ServiceResult<ApprovalSummary> rejectForEachEntity(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt) {
    final List<Message> message = new ArrayList<Message>();
    final String dataType = dataNeedApproval.getApprovalLevel().getApprovalConfig().getDataType();

    // Reject in case : Mdevice
    if (MemsDataType.MDEVICE.equalsIgnoreCase(dataType))
      return this.rejectForMdevice(dataNeedApproval);
    if (MemsDataType.HUMAN_RESOURCE.equalsIgnoreCase(dataType))
      return this.rejectForHumanResource(dataNeedApproval);

    if (this.mapTypeAndEntityClass.containsKey(dataType)) {
      this.deleteEntity(this.mapTypeAndEntityClass.get(dataType), this.mapTypeAndCodeField.get(dataType), dataNeedApproval.getItemCode());
    }

    if (CollectionUtils.isEmpty(message))
      return new ServiceResult<ApprovalSummary>(true, dataNeedApproval, null);
    message.add(MMIXMessages.MMIX00002());
    return new ServiceResult<ApprovalSummary>(false, dataNeedApproval, message);
  }

  public ServiceResult<ApprovalSummary> approveForEachEntity(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt) {
    final List<Message> message = new ArrayList<Message>();
    final String dataType = dataNeedApproval.getApprovalLevel().getApprovalConfig().getDataType();
    // Check in case exception : Mdevice
    if (MemsDataType.MDEVICE.equalsIgnoreCase(dataType))
      return this.approveForMdevice(dataNeedApproval, checkedBy, checkedAt);
    // Check in case exception : Human resource
    if (MemsDataType.HUMAN_RESOURCE.equals(dataType))
      return this.approveForHumanResource(dataNeedApproval, checkedBy, checkedAt);
    // Hospital
    if (MemsDataType.HOSPITAL.equals(dataType))
      return this.approveForHospital(dataNeedApproval, checkedBy, checkedAt);
    if (MemsDataType.HOSPITAL_DEPARTMENT.equals(dataType))
      return this.approveForHospitalDept(dataNeedApproval, checkedBy, checkedAt);

    if (this.mapTypeAndEntityClass.containsKey(dataType)) {
      this.updateCheckedField(this.mapTypeAndEntityClass.get(dataType), this.mapTypeAndCodeField.get(dataType), checkedAt, checkedBy,
          dataNeedApproval.getItemCode());
    }

    if (CollectionUtils.isEmpty(message))
      return new ServiceResult<ApprovalSummary>(true, dataNeedApproval, null);
    message.add(MMIXMessages.MMIX00002());
    return new ServiceResult<ApprovalSummary>(false, dataNeedApproval, message);
  }

  public ServiceResult<ApprovalSummary> rejectForMdevice(ApprovalSummary dataNeedApproval) {
    this.deleteEntity(MDevice.class, MDeviceMapped_.mdevCode, dataNeedApproval.getItemCode());
    this.deleteEntity(Document.class, DocumentMapped_.mdevCode, dataNeedApproval.getItemCode());
    this.deleteEntity(Schedule.class, ScheduleMapped_.mdevCode, dataNeedApproval.getItemCode());
    this.deleteEntity(AlertByCount.class, AlertByCountMapped_.mdevCode, dataNeedApproval.getItemCode());
    this.deleteEntity(PartsList.class, PartsListMapped_.mdevCode, dataNeedApproval.getItemCode());
    final List<MdevChecklist> listDeleteMdevChecklist =
        this.findAllEntity(MdevChecklist.class, MdevChecklistMapped_.mdevCode, dataNeedApproval.getItemCode());
    if (CollectionUtils.isNotEmpty(listDeleteMdevChecklist)) {
      for (final MdevChecklist mdevCklist : listDeleteMdevChecklist) {
        this.deleteEntity(MdevChecklistItem.class, MdevChecklistItemMapped_.cklistCode, mdevCklist.getCklistCode());
      }
    }
    this.deleteEntity(MdevChecklist.class, MdevChecklistMapped_.mdevCode, dataNeedApproval.getItemCode());
    return new ServiceResult<ApprovalSummary>(true, dataNeedApproval);
  }

  public ServiceResult<ApprovalSummary> rejectForHumanResource(ApprovalSummary dataNeedApproval) {
    final String humanCodeKey = dataNeedApproval.getItemCode();
    final String[] splitKeys = humanCodeKey.split(";");
    if (splitKeys == null)
      return new ServiceResult<ApprovalSummary>(false, dataNeedApproval);
    if (splitKeys.length != 3)
      return new ServiceResult<ApprovalSummary>(false, dataNeedApproval);
    this.deleteEntityByThreeAttr(HumanResource.class, HumanResourceMapped_.hospCode, HumanResourceMapped_.hospDeptCode, HumanResourceMapped_.psnCode,
        splitKeys[0], splitKeys[1], splitKeys[2]);
    return new ServiceResult<ApprovalSummary>(true, dataNeedApproval);
  }

  /**
   * this is exception that related Mdevice and other entity related to it
   * */
  public ServiceResult<ApprovalSummary> approveForMdevice(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt) {
    this.updateCheckedField(MDevice.class, MDeviceMapped_.mdevCode, checkedAt, checkedBy, dataNeedApproval.getItemCode());
    this.updateCheckedField(Document.class, DocumentMapped_.mdevCode, checkedAt, checkedBy, dataNeedApproval.getItemCode());
    this.updateCheckedField(Schedule.class, ScheduleMapped_.mdevCode, checkedAt, checkedBy, dataNeedApproval.getItemCode());
    this.updateCheckedField(AlertByCount.class, AlertByCountMapped_.mdevCode, checkedAt, checkedBy, dataNeedApproval.getItemCode());
    this.updateCheckedField(PartsList.class, PartsListMapped_.mdevCode, checkedAt, checkedBy, dataNeedApproval.getItemCode());
    this.updateCheckedField(MdevChecklist.class, MdevChecklistMapped_.mdevCode, checkedAt, checkedBy, dataNeedApproval.getItemCode());
    final List<MdevChecklist> listUpdateMdevChecklist =
        this.findAllEntity(MdevChecklist.class, MdevChecklistMapped_.mdevCode, dataNeedApproval.getItemCode());
    if (CollectionUtils.isNotEmpty(listUpdateMdevChecklist)) {
      for (final MdevChecklist mdevCklist : listUpdateMdevChecklist) {
        this.updateCheckedField(MdevChecklistItem.class, MdevChecklistItemMapped_.cklistCode, checkedAt, checkedBy, mdevCklist.getCklistCode());
      }
    }
    return new ServiceResult<ApprovalSummary>(true, dataNeedApproval);
  }

  /**
   * this is exception that related Human resource
   * */
  public ServiceResult<ApprovalSummary> approveForHumanResource(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt) {
    final String humanCodeKey = dataNeedApproval.getItemCode();
    final String[] splitKeys = humanCodeKey.split(";");
    if (splitKeys == null)
      return new ServiceResult<ApprovalSummary>(false, dataNeedApproval);
    if (splitKeys.length != 3)
      return new ServiceResult<ApprovalSummary>(false, dataNeedApproval);
    this.updateCheckedFieldByThreeAttr(HumanResource.class, HumanResourceMapped_.hospCode, HumanResourceMapped_.hospDeptCode,
        HumanResourceMapped_.psnCode, checkedAt, checkedBy, splitKeys[0], splitKeys[1], splitKeys[2]);
    return new ServiceResult<ApprovalSummary>(true, dataNeedApproval);
  }

  /**
   * this is exception that related Hospital and other entity related to it
   * */
  public ServiceResult<ApprovalSummary> approveForHospital(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt) {
    this.updateCheckedField(Hospital.class, HospitalMapped_.hospCode, checkedAt, checkedBy, dataNeedApproval.getItemCode());
    final List<Budget> listUpdateBudget = this.findAllEntity(Budget.class, BudgetMapped_.organizationCode, dataNeedApproval.getItemCode());
    if (CollectionUtils.isNotEmpty(listUpdateBudget)) {
      for (final Budget budget : listUpdateBudget) {
        this.updateCheckedField(Budget.class, BudgetMapped_.organizationCode, checkedAt, checkedBy, budget.getOrganizationCode());
      }
    }
    return new ServiceResult<ApprovalSummary>(true, dataNeedApproval);
  }

  /**
   * this is exception that related Hospital Dept and other entity related to it
   * */
  public ServiceResult<ApprovalSummary> approveForHospitalDept(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt) {
    this.updateCheckedField(HospitalDept.class, HospitalDeptMapped_.deptCode, checkedAt, checkedBy, dataNeedApproval.getItemCode());
    final List<Budget> listUpdateBudget = this.findAllEntity(Budget.class, BudgetMapped_.organizationCode, dataNeedApproval.getItemCode());
    if (CollectionUtils.isNotEmpty(listUpdateBudget)) {
      for (final Budget budget : listUpdateBudget) {
        this.updateCheckedField(Budget.class, BudgetMapped_.organizationCode, checkedAt, checkedBy, budget.getOrganizationCode());
      }
    }
    return new ServiceResult<ApprovalSummary>(true, dataNeedApproval);
  }

  public <T extends AbstractDeletable> void deleteEntity(T entity, SingularAttribute idField) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();

    final CriteriaUpdate<T> query = (CriteriaUpdate<T>) cb.createCriteriaUpdate(entity.getClass());

    final Root<T> root = query.from((Class<T>) entity.getClass());
    query.set(root.get(AbstractDeletableMapped_.isDeleted), 1);
    query.where(cb.equal(root.get(idField), entity.getPk()));
    final javax.persistence.Query test = this.em.createQuery(query);
    test.executeUpdate();
  }

  public <T extends AbstractDeletable> void deleteEntity(Class<T> clazz, SingularAttribute codeField, String codeValue) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();
    final CriteriaUpdate<T> query = cb.createCriteriaUpdate(clazz);

    final Root<T> root = query.from(clazz);
    query.set(root.get(AbstractDeletableMapped_.isDeleted), 1);

    query.where(cb.and(cb.equal(root.get(codeField), codeValue), cb.equal(root.get(AbstractDeletableMapped_.isDeleted), 0)));
    final javax.persistence.Query run = this.em.createQuery(query);
    run.executeUpdate();
  }

  public <T extends AbstractApprovalEntity> void updateCheckedField(Class<T> clazz, SingularAttribute codeField, LocalDateTime checkedAt,
      int checkedBy, String itemCode) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();
    final CriteriaUpdate<T> query = cb.createCriteriaUpdate(clazz);
    final Root<T> root = query.from(clazz);
    query.set(root.get(AbstractApprovalEntityMapped_.checkedAt), checkedAt);
    query.set(root.get(AbstractApprovalEntityMapped_.checkedBy), checkedBy);
    query.where(cb.and(cb.equal(root.get(codeField), itemCode)), cb.equal(root.get(AbstractDeletableMapped_.isDeleted), 0));
    final Query run = this.em.createQuery(query);
    run.executeUpdate();
  }

  public <T extends AbstractApprovalEntity> void updateCheckedFieldByThreeAttr(Class<T> clazz, SingularAttribute codeField1,
      SingularAttribute codeField2, SingularAttribute codeField3, LocalDateTime checkedAt, int checkedBy, String itemCode1, String itemCode2,
      String itemCode3) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();
    final CriteriaUpdate<T> query = cb.createCriteriaUpdate(clazz);
    final Root<T> root = query.from(clazz);
    query.set(root.get(AbstractApprovalEntityMapped_.checkedAt), checkedAt);
    query.set(root.get(AbstractApprovalEntityMapped_.checkedBy), checkedBy);
    query.where(
        cb.and(cb.equal(root.get(codeField1), itemCode1), cb.equal(root.get(codeField2), itemCode2), cb.equal(root.get(codeField3), itemCode3)),
        cb.equal(root.get(AbstractDeletableMapped_.isDeleted), 0));
    final Query run = this.em.createQuery(query);
    run.executeUpdate();
  }

  public <T extends AbstractDeletable> void deleteEntityByThreeAttr(Class<T> clazz, SingularAttribute codeField1, SingularAttribute codeField2,
      SingularAttribute codeField3, String itemCode1, String itemCode2, String itemCode3) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();
    final CriteriaUpdate<T> query = cb.createCriteriaUpdate(clazz);
    final Root<T> root = query.from(clazz);
    query.set(root.get(AbstractDeletableMapped_.isDeleted), CommonConstants.DELETE);
    query.where(
        cb.and(cb.equal(root.get(codeField1), itemCode1), cb.equal(root.get(codeField2), itemCode2), cb.equal(root.get(codeField3), itemCode3)),
        cb.equal(root.get(AbstractDeletableMapped_.isDeleted), 0));
    final Query run = this.em.createQuery(query);
    run.executeUpdate();
  }

  public <T extends AbstractApprovalEntity> List<T> findAllEntity(Class<T> clazz, SingularAttribute codeField, String codeValue) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();
    final CriteriaQuery<T> query = cb.createQuery(clazz);

    final Root<T> root = query.from(clazz);
    query.where(cb.and(cb.equal(root.get(codeField), codeValue), cb.equal(root.get(AbstractDeletableMapped_.isDeleted), 0)));
    final javax.persistence.Query run = this.em.createQuery(query);
    return run.getResultList();
  }

  public <T extends AbstractApprovalEntity> T findEntityByThreeAttr(Class<T> clazz, SingularAttribute codeField1, SingularAttribute codeField2,
      SingularAttribute codeField3, String itemCode1, String itemCode2, String itemCode3) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();
    final CriteriaQuery<T> query = cb.createQuery(clazz);

    final Root<T> root = query.from(clazz);
    query.where(
        cb.and(cb.equal(root.get(codeField1), itemCode1), cb.equal(root.get(codeField2), itemCode2), cb.equal(root.get(codeField3), itemCode3)),
        cb.equal(root.get(AbstractDeletableMapped_.isDeleted), 0));
    final javax.persistence.Query run = this.em.createQuery(query);
    final List<T> result = run.getResultList();
    if (CollectionUtils.isEmpty(result))
      return null;
    return result.get(0);
  }

  public <T extends AbstractApprovalEntity> T findEntity(Class<T> clazz, SingularAttribute codeField, String codeValue) {
    final CriteriaBuilder cb = this.em.getCriteriaBuilder();
    final CriteriaQuery<T> query = cb.createQuery(clazz);

    final Root<T> root = query.from(clazz);
    query.where(cb.and(cb.equal(root.get(codeField), codeValue), cb.equal(root.get(AbstractDeletableMapped_.isDeleted), 0)));
    final javax.persistence.Query run = this.em.createQuery(query);
    final List<T> result = run.getResultList();
    if (CollectionUtils.isEmpty(result))
      return null;
    return result.get(0);
  }

}
