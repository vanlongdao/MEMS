/**
 *
 */
package arrow.mems.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.data.api.QueryResult;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.validator.ArrowValidator;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.generator.ChecklistIdGenerator;
import arrow.mems.generator.ChecklistItemIdGenerator;
import arrow.mems.messages.MRRMessages;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.repository.ChecklistItemRepository;
import arrow.mems.persistence.repository.ChecklistRepository;
import arrow.mems.persistence.repository.MdevChecklistItemRepository;
import arrow.mems.persistence.repository.MdevChecklistRepository;
import arrow.mems.persistence.repository.UsersRepository;

/**
 * @author tainguyen
 *
 */
public class ChecklistResultService extends AbstractService {

  @Inject
  UserCredential userCredential;
  @Inject
  UsersRepository usersRepository;
  @Inject
  ChecklistRepository checklistRepository;
  @Inject
  ChecklistItemRepository checklistItemRepository;
  @Inject
  MdevChecklistItemRepository mdevchecklistItemRepository;
  @Inject
  MdevChecklistRepository mdevChecklistRepository;

  @Inject
  @RequestScoped
  private ArrowValidator validator;

  public ServiceResult<Checklist> createdNewChecklist(Checklist newChecklist, List<Checklist> listChecklist, String officeCode) {
    final List<Message> messages = new ArrayList<Message>();

    messages.addAll(this.validator.validate(newChecklist));
    if (!messages.isEmpty())
      return new ServiceResult<Checklist>(false, null, messages);

    if (!listChecklist.isEmpty()) {
      for (final Checklist checklist : listChecklist) {
        if ((newChecklist.getMdevChecklist() != null) && (checklist.getMdevChecklist() != null))
          if (newChecklist.getMdevChecklist().getCklistCode().equals(checklist.getMdevChecklist().getCklistCode())) {
            messages.add(MRRMessages.MRR00005());
            return new ServiceResult<Checklist>(false, null, messages);
          }
      }
    }

    if (newChecklist.getMdevChecklist() != null) {
      final ChecklistIdGenerator generator = new ChecklistIdGenerator(officeCode, LocalDate.now().getYear());
      final String cklistLogCode = generator.getNext();
      newChecklist.setCklistLogCode(cklistLogCode);
      newChecklist.setServiceOffice(officeCode);
      messages.add(MRRMessages.MRR00004());
      return new ServiceResult<Checklist>(true, newChecklist, messages);
    }

    messages.add(MRRMessages.MRR00005());
    return new ServiceResult<Checklist>(false, null);
  }

  public List<Checklist> getListChecklist(String actionCode, String officeCode) {
    return this.checklistRepository.findAllActiveChecklistByActionCodeInOwnedOffice(actionCode, officeCode).getResultList();
  }

  public List<ChecklistItem> getListChecklistItem(String cklistCode, String officeCode) {
    final QueryResult<String> resultListCkiCode =
        this.mdevchecklistItemRepository.getAllActiveMdevCkiCodeContainChecklistCode(cklistCode, officeCode);

    if (resultListCkiCode.getResultList().isEmpty())
      return new ArrayList<ChecklistItem>();

    final QueryResult<ChecklistItem> result = this.checklistItemRepository.getAllActiveChecklistItemContainCkiCode(resultListCkiCode.getResultList());
    return result.getResultList();
  }

  // created list checklist item from ckiCode get from setupChecklist
  // set temporarily code for each item.
  public List<ChecklistItem> createLocalDataAfterCreatedNewChecklist(String cklistCode, String officeCode) {
    final QueryResult<MdevChecklistItem> resultListCkiCode =
        this.mdevchecklistItemRepository.getAllActiveMdevChecklistItemContainChecklistCode(cklistCode, officeCode);
    final List<ChecklistItem> newChecklistItems = new ArrayList<>();
    if (resultListCkiCode.getAnyResult() != null) {
      for (final MdevChecklistItem mdevCki : resultListCkiCode.getResultList()) {
        final ChecklistItem checklistItem = new ChecklistItem();
        checklistItem.setMdevChecklistItem(mdevCki);
        checklistItem.setCkiLogCode(checklistItem.getFakeId());
        newChecklistItems.add(checklistItem);
      }
    }
    return newChecklistItems;
  }

  public ServiceResult<Checklist> validateChecklist(Checklist selectedChecklist) {
    final List<Message> messages = new ArrayList<>();
    if ((selectedChecklist.getMeasureDev1() != null) && (selectedChecklist.getMeasureDev2() != null)) {
      if (selectedChecklist.getMeasureDev1().equals(selectedChecklist.getMeasureDev2())) {
        messages.add(MRRMessages.MRR00008());
        return new ServiceResult<Checklist>(false, null, messages);
      }
    }
    messages.add(MRRMessages.MRR00029());
    return new ServiceResult<Checklist>(true, null, messages);
  }

  /*
   * this method only add new checklist and new checklist item
   */
  public void saveNewChecklistAndChecklistItem(List<Checklist> listChecklistIsNew, Map<String, List<ChecklistItem>> mapListChecklistItemIsNew,
      String actionCode, String officeCode) {
    for (final Checklist checklist : listChecklistIsNew) {
      checklist.setActionCode(actionCode);
      this.checklistRepository.saveAndFlush(checklist);
      if (mapListChecklistItemIsNew.get(checklist.getMdevChecklist().getCklistCode()) != null) {
        for (final ChecklistItem checklistItem : mapListChecklistItemIsNew.get(checklist.getMdevChecklist().getCklistCode())) {
          final ChecklistItemIdGenerator generator = new ChecklistItemIdGenerator(officeCode, LocalDate.now().getYear());
          final String ckiLogCode = generator.getNext();
          checklistItem.setCkiLogCode(ckiLogCode);
          checklistItem.setCklistLogCode(checklist.getCklistLogCode());
          this.checklistItemRepository.saveAndFlush(checklistItem);
        }
      }
    }
  }

  public void deleteChecklist(List<Checklist> listChecklistIsDelete) {
    for (final Checklist checklist : listChecklistIsDelete) {
      this.getChecklistAndDelete(checklist);
    }
  }

  public void deleteChecklistItem(List<ChecklistItem> listChecklistItemIsDelete) {
    for (final ChecklistItem checklistItem : listChecklistItemIsDelete) {
      this.getChecklistItemAndDelete(checklistItem);
    }
  }

  public void updateChecklist(List<Checklist> listChecklistIsModify) {
    for (final Checklist checklist : listChecklistIsModify) {
      this.getChecklistAndDelete(checklist);
      this.checklistRepository.saveAndFlush(checklist);
    }
  }

  public void updateChecklistItem(List<ChecklistItem> listChecklistItemIsModify) {
    for (final ChecklistItem checklistItem : listChecklistItemIsModify) {
      this.getChecklistItemAndDelete(checklistItem);
      this.checklistItemRepository.saveAndFlush(checklistItem);
    }
  }

  private void getChecklistAndDelete(Checklist currentChecklist) {
    final QueryResult<Checklist> result = this.checklistRepository.getChecklistByCklistLogCode(currentChecklist.getCklistLogCode());
    if (result.getAnyResult() != null) {
      final Checklist deleteChecklist = result.getAnyResult();
      this.checklistRepository.deleteItem(deleteChecklist);
    }
  }

  private void getChecklistItemAndDelete(ChecklistItem currentChecklistItem) {
    final QueryResult<ChecklistItem> result = this.checklistItemRepository.findActiveChecklistItemByCkiLogCode(currentChecklistItem.getCkiLogCode());
    if (result.getAnyResult() != null) {
      final ChecklistItem deleteChecklistItem = result.getAnyResult();
      this.checklistItemRepository.deleteItem(deleteChecklistItem);
    }
  }

  public List<MdevChecklist> getAllMdevChecklist(String mdevCode, String officeCode) {
    return this.mdevChecklistRepository.findAllActiveMdevChecklistInOneOffice(mdevCode, officeCode).getResultList();
  }

  public ServiceResult<ChecklistItem> validateChecklistItem(ChecklistItem checklistItem) {
    final List<Message> messages = new ArrayList<>();

    if (StringUtils.isEmpty(checklistItem.getResultValue())) {
      messages.add(MRRMessages.MRR00030());
    }

    if (checklistItem.getIsOk() == null) {
      messages.add(MRRMessages.MRR00031());
    }

    if (!messages.isEmpty())
      return new ServiceResult<ChecklistItem>(false, null, messages);

    messages.add(MRRMessages.MRR00032());
    return new ServiceResult<ChecklistItem>(true, checklistItem, messages);
  }
}
