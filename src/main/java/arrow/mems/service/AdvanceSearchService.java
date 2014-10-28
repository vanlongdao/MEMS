package arrow.mems.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MASMessages;
import arrow.mems.persistence.entity.SearchCondition;
import arrow.mems.persistence.repository.SearchConditionRepository;

@Named
public class AdvanceSearchService extends AbstractService {

  @Inject
  private SearchConditionRepository conditionRepository;

  public ServiceResult<SearchCondition> saveAdvanceConditions(SearchCondition pSearchCondition) {
    final List<Message> messages = new ArrayList<Message>();
    try {
      pSearchCondition = this.conditionRepository.saveAndFlush(pSearchCondition);


      messages.add(MASMessages.MAS00001(0));
      return new ServiceResult<SearchCondition>(true, pSearchCondition, messages);
    } catch (final PersistenceException e) {
      super.logger.debug(e.getMessage(), e);
    }

    return new ServiceResult<SearchCondition>(false, null, messages);
  }

  public List<SearchCondition> searchConditionSync(UserCredential userCredential) {

    return this.conditionRepository.findCondition(userCredential.getUserId(), userCredential.getOfficeCode());
  }

  public void removeAdvanceConditions(SearchCondition pSearchCondition) {
    try {
      this.conditionRepository.updateIsDeleted(CommonConstants.DELETE, pSearchCondition.getPk());
    } catch (final PersistenceException e) {
      super.logger.debug(e.getMessage(), e);
    }
  }
}
