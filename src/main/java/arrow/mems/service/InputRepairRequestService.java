/**
 *
 */
package arrow.mems.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.deltaspike.data.api.QueryResult;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.framework.validator.ArrowValidator;
import arrow.mems.constant.CommonConstants;
import arrow.mems.generator.ActionLogIdGenerator;
import arrow.mems.messages.MRRMessages;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.ActionTypeMaster;
import arrow.mems.persistence.entity.CountScheduleItem;
import arrow.mems.persistence.entity.Fault;
import arrow.mems.persistence.entity.HumanResource;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.ScheduleItem;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.ActionLogRepository;
import arrow.mems.persistence.repository.ActionTypeMasterRepository;
import arrow.mems.persistence.repository.ChecklistRepository;
import arrow.mems.persistence.repository.CountScheduleItemRepository;
import arrow.mems.persistence.repository.FaultRepository;
import arrow.mems.persistence.repository.HumanResourceRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.ReplPartRepository;
import arrow.mems.persistence.repository.ScheduleItemRepository;
import arrow.mems.persistence.repository.UsersRepository;


/**
 * @author tainguyen
 *
 */
public class InputRepairRequestService extends AbstractService {

  @Inject
  HumanResourceRepository humanRepository;
  @Inject
  ActionLogRepository actionRepository;
  @Inject
  FaultRepository faultRepository;
  @Inject
  ChecklistRepository checkListRepository;
  @Inject
  UsersRepository usersRepository;
  @Inject
  ReplPartRepository replPartRepository;
  @Inject
  ScheduleItemRepository scheduleItemRepository;
  @Inject
  CountScheduleItemRepository countScheduleItemRepository;
  @Inject
  ActionTypeMasterRepository typeMasterRepository;
  @Inject
  PersonRepository personRepository;

  public List<HumanResource> getAllHumanInOneHospital(String hospCode, String officeCode) {
    return this.humanRepository.findAllHumanInOneHospital(hospCode, officeCode).getResultList();
  }

  public ActionLog findActionByCode(String actionCode) {
    return this.actionRepository.findActionByCode(actionCode);
  }

  public ActionLog findActionLogByCodeForRest(String actionCode) {
    final ActionLog action = this.actionRepository.findActionByCode(actionCode);
    this.actionRepository.detach(action);
    return action;
  }

  public ServiceResult<ActionLog> editInputRepairRequest(ActionLog actionLog) {
    ServiceResult<ActionLog> result = null;
    if (actionLog.isSelected()) {
      final ActionLog editActionLog = this.actionRepository.findActionByCode(actionLog.getActionCode());
      final ActionLog currentAction = new ActionLog();
      BeanCopier.copy(editActionLog, currentAction);
      result = new ServiceResult<ActionLog>(true, currentAction);
    }
    return result;
  }

  public ServiceResult<Fault> findFaultByActionCode(String actionCode) {
    final QueryResult<Fault> queryResult = this.faultRepository.getActiveFaultByActionCode(actionCode);
    Fault fault = null;
    if (queryResult.getAnyResult() != null) {
      fault = queryResult.getAnyResult();
    } else {
      fault = new Fault();
    }
    return new ServiceResult<Fault>(true, fault);
  }

  public ServiceResult<List<ActionLog>> findAllActiveActionLog(String officeCode) {
    final QueryResult<ActionLog> list = this.actionRepository.findActiveActionLogOfOffice(officeCode);
    return new ServiceResult<List<ActionLog>>(true, list.getResultList(), null);
  }

  public void saveNewFault(Fault currentFault, String actionCode) {
    currentFault.setActionCode(actionCode);
    this.faultRepository.saveAndFlush(currentFault);
  }

  public void updateFault(Fault currentFault) {
    final String actionCode = currentFault.getActionCode();
    final QueryResult<Fault> result = this.faultRepository.getActiveFaultByActionCode(actionCode);
    final Fault updateFault = new Fault();
    BeanCopier.copy(currentFault, updateFault);
    if (result.getAnyResult() != null) {
      final Fault deleteFault = result.getAnyResult();
      deleteFault.setIsDeleted(CommonConstants.DELETE);
      this.faultRepository.saveAndFlush(deleteFault);
    }
    this.faultRepository.saveAndFlush(updateFault);
  }

  @Inject
  @RequestScoped
  private ArrowValidator validator;

  public ServiceResult<ActionLog> saveActionLog(ActionLog actionLog, String officeCode) {
    final List<Message> messages = new ArrayList<Message>();
    if ((actionLog.getInstallConfirmImg() != null) && (actionLog.getCheckedBy() == null)) {
      messages.add(MRRMessages.MRR00020());
    }

    messages.addAll(this.validator.validate(actionLog));

    if (!messages.isEmpty())
      return new ServiceResult<ActionLog>(false, actionLog, messages);

    if (!actionLog.isPersisted()) {
      final ActionLogIdGenerator generator = new ActionLogIdGenerator(officeCode, LocalDate.now().getYear());
      final String actionCode = generator.getNext();
      actionLog.setActionCode(actionCode);
      this.actionRepository.saveAndFlush(actionLog);
    } else {
      this.updateActionLog(actionLog);
    }

    final ActionLog newActionLog = this.actionRepository.getActiveActionLogByCode(actionLog.getActionCode()).getAnyResult();
    newActionLog.setFakeId(actionLog.getFakeId());
    messages.add(MRRMessages.MRR00007());
    return new ServiceResult<ActionLog>(true, newActionLog, messages);
  }

  public void updateActionLog(ActionLog currentActionLog) {
    final String actionCode = currentActionLog.getActionCode();
    final ActionLog action = this.actionRepository.findActionByCode(actionCode);
    this.actionRepository.deleteItem(action);
    this.actionRepository.saveAndFlush(currentActionLog);
  }

  public ActionLog loadActionLogByActionCode(String actionCode) {
    return this.actionRepository.findActionByCode(actionCode);
  }

  public List<Users> findActiveUserByPsnCodes(List<String> listPsnCode) {
    return this.usersRepository.findActiveUserByPsnCodes(listPsnCode).getResultList();
  }

  public void saveCountSchedule(String scheduleCode, ActionLog action) {
    final CountScheduleItem schedule = this.countScheduleItemRepository.getCountScheduleItemByCountSchedCode(scheduleCode).getAnyResult();
    schedule.setActionCode(action.getActionCode());
    schedule.setDevCode(action.getDevCode());
    this.countScheduleItemRepository.saveAndFlush(schedule);
  }

  public void saveScheduleItem(String scheduleCode, ActionLog action) {
    final ScheduleItem item = this.scheduleItemRepository.getScheduleItemBySchedCode(scheduleCode).getAnyResult();
    item.setActionCode(action.getActionCode());
    item.setDevCode(action.getDevCode());
    this.scheduleItemRepository.saveAndFlush(item);
  }

  public ActionTypeMaster getActionTypeMaster(String typeMaintenance) {
    return this.typeMasterRepository.getActiveTypeMasterByCode(typeMaintenance).getAnyResult();
  }

  public List<Person> getPersonsByOfficeCode(String officeCode, String userOffice) {
    return this.personRepository.findAllActivePersonByOffice(officeCode, userOffice).getResultList();
  }

  public List<Person> getPersonsNotInOffice(String officeCode, String userOffice) {
    return this.personRepository.findAllActivePersonNotInOffice(officeCode, userOffice).getResultList();
  }

  public HumanResource getHumanOfThisAction(String psnCode, String hospCode, String hospDeptCode) {
    return this.humanRepository.getActiveHumanOfThisActionLog(psnCode, hospCode, hospDeptCode).getAnyResult();
  }
}
