/**
 *
 */
package arrow.mems.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.deltaspike.data.api.QueryResult;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MMIMessages;
import arrow.mems.messages.MMIXMessages;
import arrow.mems.persistence.entity.ApprovalConfig;
import arrow.mems.persistence.entity.ApprovalLevel;
import arrow.mems.persistence.entity.ApprovalLevelSupervisor;
import arrow.mems.persistence.entity.ApprovalSummary;
import arrow.mems.persistence.entity.ApprovalSummaryHistory;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.ApprovalConfigRepository;
import arrow.mems.persistence.repository.ApprovalLevelRepository;
import arrow.mems.persistence.repository.ApprovalLevelSupervisorRepository;
import arrow.mems.persistence.repository.ApprovalSummaryHistoryRepository;
import arrow.mems.persistence.repository.ApprovalSummaryRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.util.mail.EmailHelper;
import freemarker.template.TemplateException;

/**
 * @author tainguyen
 *
 */
public class ApprovalService extends AbstractService {
  @Inject
  ApprovalConfigRepository approvalRepository;
  @Inject
  ApprovalLevelSupervisorRepository supervisorRepository;
  @Inject
  ApprovalLevelRepository levelRepository;
  @Inject
  UsersRepository usersRepository;
  @Inject
  private ApprovalSummaryHistoryRepository summaryHistoryRepository;

  @Inject
  private ApprovalDataService approvalDataService;

  @Inject
  private ApprovalSummaryRepository approvalSummaryReposiroty;

  public List<ApprovalConfig> listAllApprovalConfig() {
    return this.approvalRepository.findAllOrderByDataTypeAsc();
  }

  public List<ApprovalLevel> listAllApprovalLevels(int configId) {
    return this.levelRepository.getListLevelFromConfigId(configId);
  }

  public ServiceResult<ApprovalLevel> addNewLevel(ApprovalConfig currentApproval, int levelIndex) {
    final List<Message> messages = new ArrayList<Message>();
    final ApprovalLevel level = new ApprovalLevel();
    level.setConfigId(currentApproval.getConfigId());
    level.setLevelIndex(levelIndex);
    this.levelRepository.saveAndFlush(level);
    messages.add(MMIMessages.MMI00024());

    return new ServiceResult<ApprovalLevel>(true, level, messages);
  }

  public ServiceResult<ApprovalLevelSupervisor> addNewSupervisor(int levelId, int userId) {
    final List<Message> messages = new ArrayList<>();
    final QueryResult<ApprovalLevelSupervisor> result = this.supervisorRepository.findSupervisorByLevelIdAndUserId(levelId, userId);

    if (result.getAnyResult() != null) {
      messages.add(MMIMessages.MMI00027());
      return new ServiceResult<ApprovalLevelSupervisor>(false, null, messages);
    }

    final ApprovalLevelSupervisor supervisor = new ApprovalLevelSupervisor();
    supervisor.setLevelId(levelId);
    supervisor.setSupervisorId(userId);
    this.supervisorRepository.saveAndFlush(supervisor);
    messages.add(MMIMessages.MMI00025());

    return new ServiceResult<ApprovalLevelSupervisor>(true, supervisor, messages);
  }

  public List<ApprovalLevelSupervisor> getAllSupervisorByLevelId(int levelId) {
    return this.supervisorRepository.getAllSupervisorByLevelId(levelId);
  }

  public Users findActiveUserById(int supervisorId) {
    return this.usersRepository.findActiveUserById(supervisorId);
  }

  public ServiceResult<ApprovalLevelSupervisor> deleteSupervisor(ApprovalLevelSupervisor supervisor) {
    final List<Message> messages = new ArrayList<>();
    final ApprovalLevelSupervisor oldSupervisor = this.supervisorRepository.findBy(supervisor.getPk());
    this.supervisorRepository.removeAndFlush(oldSupervisor);
    return new ServiceResult<ApprovalLevelSupervisor>(true, oldSupervisor, messages);
  }

  public void changeStatusOfApproval(ApprovalConfig pApproval) {
    final ApprovalConfig approval = this.approvalRepository.findBy(pApproval.getPk());
    approval.setDisableApprove(pApproval.getDisableApprove());
    this.approvalRepository.saveAndFlush(approval);
  }

  public ServiceResult<ApprovalLevel> deleteLevel(ApprovalLevel pLevel) {
    final ApprovalLevel level = this.levelRepository.findBy(pLevel.getLevelId());
    final List<ApprovalLevelSupervisor> allSupervisors = this.supervisorRepository.findByLevelId(level.getLevelId());
    if (CollectionUtils.isNotEmpty(allSupervisors)) {
      allSupervisors.stream().forEach(supervisor -> this.supervisorRepository.remove(supervisor));
    }
    this.levelRepository.removeAndFlush(level);
    return new ServiceResult<ApprovalLevel>(true, level);
  }

  /*
   * 2014-10-09
   * 
   * @author : vanlongdao
   */

  public ServiceResult<ApprovalSummary> requestApproval(String dataType, String itemCode, int requestor, String itemLabel) {
    final List<Message> message = new ArrayList<Message>();
    final Integer levelId = this.getFirstLevelIdByDataType(dataType);
    if (levelId == null) {
      message.add(MMIXMessages.MMIX00005());
      return new ServiceResult<ApprovalSummary>(false, null, message);
    }
    try {
      ApprovalSummary newApproval = new ApprovalSummary();
      newApproval.setItemCode(itemCode);
      newApproval.setLevelId(levelId);
      newApproval.setRequestor(requestor);
      newApproval.setCreatedBy(requestor);
      newApproval.setItemLabel(itemLabel);
      newApproval.setRequestAt(LocalDateTime.now());
      newApproval = this.approvalSummaryReposiroty.saveAndFlushAndRefresh(newApproval);
      final List<ApprovalLevelSupervisor> supervisors = this.getSupervisor(newApproval.getLevelId());
      if (supervisors != null) {
        for (final ApprovalLevelSupervisor supervisor : supervisors) {
          this.sendNotificationApproveToSupervior(newApproval, supervisor.getUser().getEmail());
        }
      }
      message.add(MMIXMessages.MMIX00004());
      return new ServiceResult<ApprovalSummary>(true, newApproval);
    } catch (final PersistenceException e) {
      super.logger.debug(e.getMessage(), e);
    }
    message.add(MMIXMessages.MMIX00005());
    return new ServiceResult<ApprovalSummary>(false, null, message);
  }

  public Integer getFirstLevelIdByDataType(String dataType) {
    final ApprovalLevel level = this.levelRepository.findApprovalLevelByDataTypeAndLevelIndexMin(dataType).getAnyResult();
    if (level != null)
      return level.getLevelId();
    return null;
  }

  public List<ApprovalSummary> getApprovalSummaryBySuppervisor(int currentUserId) {
    return this.approvalSummaryReposiroty.findApprovalSummaryBySuppervisor(currentUserId).getResultList();
  }

  public ServiceResult<ApprovalSummary> approveData(List<ApprovalSummary> listApprovalSummaries, int userIdApproval) {
    for (final ApprovalSummary approvalSummary : listApprovalSummaries) {
      if (CommonConstants.APPROVAL.equalsIgnoreCase(approvalSummary.getActionType())) {
        // Approval
        this.approve(approvalSummary, userIdApproval, LocalDateTime.now(), CommonConstants.APPROVAL);
      } else if (CommonConstants.REJECT.equalsIgnoreCase(approvalSummary.getActionType())) {
        // Reject
        this.reject(approvalSummary, userIdApproval, LocalDateTime.now(), CommonConstants.REJECT);
      } else if (CommonConstants.REVISE.equalsIgnoreCase(approvalSummary.getActionType())) {
        // Revise
        this.revise(approvalSummary, userIdApproval, LocalDateTime.now(), CommonConstants.REVISE);
      }
    }
    return new ServiceResult<ApprovalSummary>(true, null);
  }

  /*
   * Approval Feature
   */

  /**
   * Reject feature
   *
   * @param dataNeedApproval the data approval summary need approval
   * @param checkedBy the checked by
   * @param checkedAt the checked at
   * @param actionType the action type : Reject
   * @return the service result
   */
  public ServiceResult<ApprovalSummary> reject(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt, String actionType) {
    final ServiceResult<ApprovalSummaryHistory> saveHistoryResult =
        this.saveNewApprovalSummaryHistory(dataNeedApproval, dataNeedApproval.getComment(), actionType, checkedBy);
    if (saveHistoryResult.isSuccess()) {
      this.deleteApprovalSummary(dataNeedApproval);
    }
    final ServiceResult<ApprovalSummary> result = this.approvalDataService.rejectForEachEntity(dataNeedApproval, checkedBy, checkedAt);
    if (result.isSuccess()) {
      this.sendNotificationApproveToRequestor(dataNeedApproval, dataNeedApproval.getRequestorUser().getEmail());
      return new ServiceResult<ApprovalSummary>(true, dataNeedApproval, null);
    }
    return new ServiceResult<ApprovalSummary>(false, dataNeedApproval, null);
  }

  /**
   * Revise feature
   *
   * @param dataNeedApproval the data approval summary need approval
   * @param checkedBy the checked by
   * @param checkedAt the checked at
   * @param actionType the action type : Revise
   * @return the service result
   */
  public ServiceResult<ApprovalSummary> revise(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt, String actionType) {
    final ServiceResult<ApprovalSummaryHistory> saveHistoryResult =
        this.saveNewApprovalSummaryHistory(dataNeedApproval, dataNeedApproval.getComment(), actionType, checkedBy);
    ServiceResult<ApprovalSummary> result = null;
    if (saveHistoryResult.isSuccess()) {
      result = this.deleteApprovalSummary(dataNeedApproval);
    }
    if (result.isSuccess()) {
      this.sendNotificationApproveToRequestor(dataNeedApproval, dataNeedApproval.getRequestorUser().getEmail());
      return new ServiceResult<ApprovalSummary>(true, dataNeedApproval, null);
    }
    return new ServiceResult<ApprovalSummary>(true, dataNeedApproval, null);
  }

  /**
   * Approve feature
   *
   * @param dataNeedApproval the data approval summary need approval
   * @param checkedBy the checked by
   * @param checkedAt the checked at
   * @param actionType the action type
   * @return the service result
   */
  public ServiceResult<ApprovalSummary> approve(ApprovalSummary dataNeedApproval, int checkedBy, LocalDateTime checkedAt, String actionType) {
    // Check has next levelId
    final List<Message> message = new ArrayList<Message>();
    final ApprovalLevel nextApprovalLevel =
        this.getNextApprovalLevel(dataNeedApproval.getApprovalLevel().getLevelIndex(), dataNeedApproval.getApprovalLevel().getConfigId());

    final ServiceResult<ApprovalSummaryHistory> saveHistoryResult =
        this.saveNewApprovalSummaryHistory(dataNeedApproval, dataNeedApproval.getComment(), actionType, checkedBy);
    if (saveHistoryResult.isSuccess()) {
      this.deleteApprovalSummary(dataNeedApproval);
    }

    if (nextApprovalLevel != null) {
      final ServiceResult<ApprovalSummary> approvalResult = this.saveNewApprovalSummary(dataNeedApproval, nextApprovalLevel.getLevelId());
      if (approvalResult.isSuccess()) {
        final List<ApprovalLevelSupervisor> supervisors = this.getSupervisor(nextApprovalLevel.getLevelId());
        if (supervisors != null) {
          for (final ApprovalLevelSupervisor supervisor : supervisors) {
            this.sendNotificationApproveToSupervior(approvalResult.getData(), supervisor.getUser().getEmail());
          }
        }
        message.add(MMIXMessages.MMIX00001());
        return new ServiceResult<ApprovalSummary>(true, approvalResult.getData(), message);
      }
      message.add(MMIXMessages.MMIX00002());
      return new ServiceResult<ApprovalSummary>(false, null, message);
    }
    // Approve when has no next level index
    final ServiceResult<ApprovalSummary> result = this.approvalDataService.approveForEachEntity(dataNeedApproval, checkedBy, checkedAt);
    if (result.isSuccess()) {
      this.sendNotificationApproveToRequestor(result.getData(), dataNeedApproval.getRequestorUser().getEmail());;
      message.add(MMIXMessages.MMIX00001());
      return new ServiceResult<ApprovalSummary>(true, dataNeedApproval, message);
    }
    return new ServiceResult<ApprovalSummary>(false, dataNeedApproval, result.getErrors());
  }

  public ApprovalLevel getNextApprovalLevel(int currentLevelIndex, int configId) {
    return this.levelRepository.findNextApprovalLevel(currentLevelIndex + 1, configId).getAnyResult();
  }

  public ServiceResult<ApprovalSummaryHistory> saveNewApprovalSummaryHistory(ApprovalSummary dataNeedApproval, String comment, String actionType,
      int currentApprovalId) {
    ApprovalSummaryHistory newApprovalSummaryHistory = new ApprovalSummaryHistory();
    final ApprovalLevel approvalLevel = this.levelRepository.findBy(dataNeedApproval.getLevelId());
    try {
      newApprovalSummaryHistory.setAction(actionType);
      newApprovalSummaryHistory.setComment(comment);
      newApprovalSummaryHistory.setCreatedAt(LocalDateTime.now());
      newApprovalSummaryHistory.setCreatedBy(currentApprovalId);
      newApprovalSummaryHistory.setItemCode(dataNeedApproval.getItemCode());
      newApprovalSummaryHistory.setLevelId(dataNeedApproval.getLevelId());
      newApprovalSummaryHistory.setRequestAt(dataNeedApproval.getRequestAt());
      newApprovalSummaryHistory.setRequestor(dataNeedApproval.getRequestor());
      newApprovalSummaryHistory.setApprovalLevel(approvalLevel);
      newApprovalSummaryHistory.setItemLabel(dataNeedApproval.getItemLabel());
      newApprovalSummaryHistory = this.summaryHistoryRepository.saveAndFlush(newApprovalSummaryHistory);
      return new ServiceResult<ApprovalSummaryHistory>(true, newApprovalSummaryHistory);
    } catch (final PersistenceException e) {
      super.logger.debug(e.getMessage(), e);
    }
    return new ServiceResult<ApprovalSummaryHistory>(false, null);
  }

  public ServiceResult<ApprovalSummary> deleteApprovalSummary(ApprovalSummary dataApproval) {
    final ApprovalSummary deletedApprovalSummary = this.approvalSummaryReposiroty.findBy(dataApproval.getPk());
    try {
      if (deletedApprovalSummary != null) {
        this.approvalSummaryReposiroty.removeAndFlush(deletedApprovalSummary);
        return new ServiceResult<ApprovalSummary>(true, deletedApprovalSummary);
      }
    } catch (final PersistenceException e) {
      super.logger.debug(e.getMessage(), e);
    }
    return new ServiceResult<ApprovalSummary>(false, deletedApprovalSummary);
  }

  public ServiceResult<ApprovalSummary> saveNewApprovalSummary(ApprovalSummary oldDataApproval, int levelId) {
    ApprovalSummary newApprovalSummary = new ApprovalSummary();
    BeanCopier.copy(oldDataApproval, newApprovalSummary);
    newApprovalSummary.setLevelId(levelId);
    try {
      newApprovalSummary = this.approvalSummaryReposiroty.saveAndFlushAndRefresh(newApprovalSummary);
      return new ServiceResult<ApprovalSummary>(true, newApprovalSummary);
    } catch (final PersistenceException e) {
      super.logger.debug(e.getMessage(), e);
    }
    return new ServiceResult<ApprovalSummary>(false, newApprovalSummary);
  }

  public void sendNotificationApproveToSupervior(ApprovalSummary dataApprove, String toAddress) {
    try {
      EmailHelper.sendInfoApprovalForSupervisor(dataApprove, toAddress);
    } catch (IOException | TemplateException | MessagingException e) {
      super.logger.debug(e.getMessage(), e);
    }
  }

  public void sendNotificationApproveToRequestor(ApprovalSummary dataApprove, String toAddress) {
    try {
      EmailHelper.sendInfoApprovalForRequestor(dataApprove, toAddress);
    } catch (IOException | TemplateException | MessagingException e) {
      super.logger.debug(e.getMessage(), e);
    }
  }

  public List<ApprovalLevelSupervisor> getSupervisor(int levelId) {
    return this.supervisorRepository.findSupervisorByLevelId(levelId).getResultList();
  }

  public ApprovalSummary getApprovalSummaryByItemCodeAndDataType(String itemCode, String itemType) {
    return this.approvalSummaryReposiroty.findApprovalSummaryByItemCodeAndItemType(itemCode, itemType).getAnyResult();
  }

  public ApprovalLevelSupervisor getLevelSuppervisorBySupervisorIdAndLevelId(int levelId, int supervisorId) {
    return this.supervisorRepository.findSupervisorByLevelIdAndUserId(levelId, supervisorId).getAnyResult();
  }

  public ApprovalSummary checkSupervisorAndPendingItem(String itemCode, String dataType, int supervisorId) {
    final List<ApprovalSummary> listItems =
        this.approvalSummaryReposiroty.findApprovalSummaryByItemInfoAndSupervisor(itemCode, dataType, supervisorId);
    if (CollectionUtils.isNotEmpty(listItems))
      return listItems.get(0);
    return null;
  }
}
