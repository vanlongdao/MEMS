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
import arrow.mems.generator.PartEstimateIdGenerator;
import arrow.mems.messages.MRRMessages;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.repository.PartEstimateItemRepository;
import arrow.mems.persistence.repository.PartEstimateRepository;

/**
 * @author tainguyen
 *
 */
public class PartEstimateService extends AbstractService {

  @Inject
  PartEstimateItemRepository estimateItemRepository;
  @Inject
  PartEstimateRepository estimateRepository;

  @Inject
  @RequestScoped
  private ArrowValidator validator;

  public List<PartEstimateItem> getListPartEstimateItem(String peCode, String officeCode) {
    return this.estimateItemRepository.findAllActivePartEstimateItemContainPartEstimateCode(peCode, officeCode).getResultList();
  }

  public List<PartEstimate> getListPartEstimateShow(String actionCode, String officeCode) {
    return this.estimateRepository.getAllActivePartEstimateUseActionCodeInOneOffice(actionCode, officeCode).getResultList();
  }

  /*
   * save new part estimate and items of it
   */
  public void saveNewPartEstimateAndNewPartEstimateItemWithIt(List<PartEstimate> listPartEstimateIsNew,
      Map<String, List<PartEstimateItem>> mapListNewPartEstimateItem, String actionCode, String officeCode) {
    for (final PartEstimate estimate : listPartEstimateIsNew) {
      estimate.setActionCode(actionCode);
      final PartEstimateIdGenerator generator = new PartEstimateIdGenerator(officeCode, LocalDate.now().getYear());
      final String peCode = generator.getNext();
      estimate.setPeCode(peCode);
      this.estimateRepository.saveAndFlush(estimate);
      if (mapListNewPartEstimateItem.get(estimate.getFakeId()) != null) {
        for (final PartEstimateItem item : mapListNewPartEstimateItem.get(estimate.getFakeId())) {
          item.setPeCode(peCode);
          this.estimateItemRepository.saveAndFlush(item);
        }
      }
    }
  }

  public void saveNewPartEstimateItemWhenPartEstimateIsExisted(List<PartEstimate> estimates,
      Map<String, List<PartEstimateItem>> mapListNewPartEstimateItem) {
    for (final PartEstimate estimate : estimates) {
      if (estimate.isPersisted()) {
        if (mapListNewPartEstimateItem.get(estimate.getFakeId()) != null) {
          for (final PartEstimateItem item : mapListNewPartEstimateItem.get(estimate.getFakeId())) {
            if (!item.isPersisted()) {
              item.setPeCode(estimate.getPeCode());
              this.estimateItemRepository.saveAndFlush(item);
            }
          }
        }
      }
    }
  }

  public void updatePartEstimate(List<PartEstimate> listPartEstimateIsModify) {
    for (final PartEstimate estimate : listPartEstimateIsModify) {
      this.getPartEstimateAndDelete(estimate);
      this.estimateRepository.saveAndFlush(estimate);
    }
  }

  public void updatePartEstimateItem(List<PartEstimateItem> listPartEstimateItemIsModify, List<PartEstimateItem> listPartEstimateHelpModify) {
    for (int index = 0; index < listPartEstimateItemIsModify.size(); index++) {
      this.getPartEstimateItemAndDelete(listPartEstimateHelpModify.get(index));
      this.estimateItemRepository.saveAndFlush(listPartEstimateItemIsModify.get(index));
    }
  }

  public void deletePartEstimate(List<PartEstimate> listPartEstimateIsDelete) {
    for (final PartEstimate estimate : listPartEstimateIsDelete) {
      this.getPartEstimateAndDelete(estimate);
    }
  }

  public void deletePartEstimateItem(List<PartEstimateItem> listPartEstimateItemIsDelete) {
    for (final PartEstimateItem item : listPartEstimateItemIsDelete) {
      this.getPartEstimateItemAndDelete(item);
    }
  }

  public void getPartEstimateAndDelete(PartEstimate estimate) {
    final QueryResult<PartEstimate> result = this.estimateRepository.getActiveEstimateByPeCode(estimate.getPeCode());
    if (result.getAnyResult() != null) {
      final PartEstimate deleteEstimate = result.getAnyResult();
      this.estimateRepository.deleteItem(deleteEstimate);
    }
  }

  public void getPartEstimateItemAndDelete(PartEstimateItem item) {
    final QueryResult<PartEstimateItem> result =
        this.estimateItemRepository.getActiveEstimateItemByPartCodeAndPeCode(item.getPartCode(), item.getPeCode());
    if (result.getAnyResult() != null) {
      final PartEstimateItem deleteItem = result.getAnyResult();
      this.estimateItemRepository.deleteItem(deleteItem);
    }
  }

  public PartEstimate loadPartEstimateByPeCode(String pPeCode) {
    return this.estimateRepository.getActiveEstimateByPeCode(pPeCode).getAnyResult();
  }

  public ServiceResult<PartEstimate> addNewEstimate(PartEstimate selectedPartEstimate, String actionCode, List<PartEstimateItem> listItems) {
    final List<Message> messages = new ArrayList<Message>();
    // check required request date
    messages.addAll(this.validator.validate(selectedPartEstimate));
    if (listItems.isEmpty()) {
      messages.add(MRRMessages.MRR00023());
    }
    if (!messages.isEmpty())
      return new ServiceResult<PartEstimate>(false, null, messages);

    selectedPartEstimate.setActionCode(actionCode);
    messages.add(MRRMessages.MRR00012());
    return new ServiceResult<PartEstimate>(true, selectedPartEstimate, messages);
  }

  public ServiceResult<PartEstimateItem> validateEstimateItem(PartEstimateItem selectedPartEstimateItem, PartEstimateItem currentPartEstimate,
      List<PartEstimateItem> listPartEstimateItemShow) {
    final List<Message> messages = new ArrayList<>();
    if (StringUtils.isEmpty(selectedPartEstimateItem.getPartName())) {
      messages.add(MRRMessages.MRR00014());
      return new ServiceResult<PartEstimateItem>(false, null, messages);
    }

    for (final PartEstimateItem item : listPartEstimateItemShow) {
      if (!item.equals(currentPartEstimate))
        if ((item.getPartCode() != null) && item.getPartCode().equals(selectedPartEstimateItem.getPartCode())) {
          messages.add(MRRMessages.MRR00015());
          return new ServiceResult<PartEstimateItem>(false, null, messages);
        }
    }
    return new ServiceResult<PartEstimateItem>(true, null);
  }
}
