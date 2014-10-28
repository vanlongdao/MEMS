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
import arrow.mems.generator.PartOrderIdGenerator;
import arrow.mems.messages.MRRMessages;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.repository.PartOrderItemRepository;
import arrow.mems.persistence.repository.PartOrderRepository;

/**
 * @author tainguyen
 *
 */
public class PartsOrderService extends AbstractService {

  @Inject
  PartOrderItemRepository partOrderItemRepository;
  @Inject
  PartOrderRepository partOrderRepository;
  @Inject
  UserCredential userCredential;

  public List<PartOrderItem> getListPartOrderItem(String poCode, String officeCode) {
    return this.partOrderItemRepository.getAllActivePartOrderItemContainPartOrderCode(poCode, officeCode).getResultList();
  }

  public List<PartOrder> getListPartsOrderShow(String actionCode, String officeCode) {
    return this.partOrderRepository.getAllActivePartOrderUseActionCodeInOneOffice(actionCode, officeCode).getResultList();
  }

  public PartOrder loadPartOrderByPoCode(String poCode) {
    return this.partOrderRepository.getActiveOrderByPoCode(poCode).getAnyResult();
  }

  public ServiceResult<PartOrder> copyDataFromEstimateToOrder(PartEstimate estimate, List<PartOrder> listPartOrderShow) {
    final List<Message> messages = new ArrayList<Message>();
    if (this.validateCopy(estimate, listPartOrderShow)) {
      final PartOrder order = new PartOrder();
      order.setPartEstimate(estimate);
      order.setDistributorOffice(estimate.getDistributorOffice());
      order.setDistributorPerson(estimate.getDistributorPerson());
      order.setEntityServiceOffice(estimate.getSupplierOffice());
      order.setServicePerson(estimate.getSupplierPerson());
      order.setEntityRequestOffice(estimate.getClientOffice());
      order.setRequestPerson(estimate.getClientPerson());
      order.setTax(estimate.getTax());
      order.setPCcy(estimate.getPCcy());
      order.setTotalPrice(estimate.getTotalPrice());
      order.setImageFile(estimate.getImageFile());
      messages.add(MRRMessages.MRR00009());
      return new ServiceResult<PartOrder>(true, order, messages);
    }
    messages.add(MRRMessages.MRR00010());
    return new ServiceResult<PartOrder>(false, null, messages);
  }

  @Inject
  @RequestScoped
  private ArrowValidator validator;

  public ServiceResult<PartOrder> validateOrder(PartOrder order, List<PartOrderItem> listPartOrderItem) {
    final List<Message> messages = new ArrayList<>();
    messages.addAll(this.validator.validate(order));
    if (listPartOrderItem.isEmpty()) {
      messages.add(MRRMessages.MRR00022());
    }
    if (!messages.isEmpty())
      return new ServiceResult<PartOrder>(false, null, messages);
    return new ServiceResult<PartOrder>(true, null);
  }

  public ServiceResult<PartOrder> checkDuplicateEstimate(PartOrder order, List<PartOrder> listPartOrder) {
    final List<Message> messages = new ArrayList<>();
    for (final PartOrder partOrder : listPartOrder) {
      if (order.getEstimateCode().equals(partOrder.getEstimateCode())) {
        messages.add(MRRMessages.MRR00010());
        return new ServiceResult<PartOrder>(false, null, messages);
      }
    }
    return new ServiceResult<PartOrder>(true, null);
  }

  public boolean validateCopy(PartEstimate estimate, List<PartOrder> listPartOrderShow) {
    for (final PartOrder order : listPartOrderShow) {
      if (estimate.getPeCode().equals(order.getEstimateCode()))
        return false;
    }
    return true;
  }

  public ServiceResult<PartOrderItem> copyDataFromEstimateItemToOrderItem(PartEstimateItem item) {
    final PartOrderItem orderItem = new PartOrderItem();
    orderItem.setMDevice(item.getMDevice());
    orderItem.setItemPrice(item.getItemPrice());
    orderItem.setNumItems(item.getNumItems());
    orderItem.setPartModelNo(item.getPartModelNo());
    orderItem.setPartName(item.getPartName());
    orderItem.setTax(item.getTax());
    orderItem.setPriceWithTax(item.getPriceWithTax());
    orderItem.setTotPrice(item.getTotPrice());
    orderItem.setTaxRate(item.getTaxRate());
    return new ServiceResult<PartOrderItem>(true, orderItem);
  }

  public void saveNewPartOrderAndNewPartOrderItem(List<PartOrder> listPartOrderIsNew, Map<String, List<PartOrderItem>> mapListNewPartOrderItem,
      String actionCode, String officeCode) {
    for (final PartOrder order : listPartOrderIsNew) {
      order.setActionCode(actionCode);
      final PartOrderIdGenerator generator = new PartOrderIdGenerator(officeCode, LocalDate.now().getYear());
      final String poCode = generator.getNext();
      order.setPoCode(poCode);
      this.partOrderRepository.saveAndFlush(order);
      if (mapListNewPartOrderItem.get(order.getFakeId()) != null) {
        for (final PartOrderItem item : mapListNewPartOrderItem.get(order.getFakeId())) {
          item.setPoCode(poCode);
          this.partOrderItemRepository.saveAndFlush(item);
        }
      }
    }
  }

  public void updatePartOrder(List<PartOrder> listPartOrderIsModify) {
    for (final PartOrder order : listPartOrderIsModify) {
      this.getPartOrderAndDelete(order);
      this.partOrderRepository.saveAndFlush(order);
    }
  }

  public void updatePartOrderItem(List<PartOrderItem> listPartOrderItemIsModify, List<PartOrderItem> listPartOrderItemsHelpModify) {
    for (int index = 0; index < listPartOrderItemIsModify.size(); index++) {
      this.getPartOrderItemAndDelete(listPartOrderItemsHelpModify.get(index));
      this.partOrderItemRepository.saveAndFlush(listPartOrderItemIsModify.get(index));
    }
  }

  public void deletePartOrder(List<PartOrder> listPartOrderIsDelete) {
    for (final PartOrder order : listPartOrderIsDelete) {
      this.getPartOrderAndDelete(order);
    }
  }

  public void deletePartOrderItem(List<PartOrderItem> listPartOrderItemIsDelete) {
    for (final PartOrderItem item : listPartOrderItemIsDelete) {
      this.getPartOrderItemAndDelete(item);
    }
  }

  public void getPartOrderAndDelete(PartOrder order) {
    final QueryResult<PartOrder> result = this.partOrderRepository.getActiveOrderByPoCode(order.getPoCode());
    if (result.getAnyResult() != null) {
      final PartOrder deleteOrder = result.getAnyResult();
      this.partOrderRepository.deleteItem(deleteOrder);
    }
  }

  public void getPartOrderItemAndDelete(PartOrderItem item) {
    final QueryResult<PartOrderItem> result =
        this.partOrderItemRepository.getActiveOrderItemByPartCodeAndPoCode(item.getPartCode(), item.getPoCode());
    if (result.getAnyResult() != null) {
      final PartOrderItem deleteItem = result.getAnyResult();
      this.partOrderItemRepository.deleteItem(deleteItem);
    }
  }

  public ServiceResult<PartOrderItem> validateOrderItem(PartOrderItem selectedPartOrderItem, PartOrderItem currentPartOrderItem,
      List<PartOrderItem> listPartOrderItemShow) {
    final List<Message> messages = new ArrayList<>();
    if (StringUtils.isEmpty(selectedPartOrderItem.getPartName())) {
      messages.add(MRRMessages.MRR00037());
      return new ServiceResult<PartOrderItem>(false, null, messages);
    }

    for (final PartOrderItem item : listPartOrderItemShow) {
      if (!item.equals(currentPartOrderItem))
        if ((item.getPartCode() != null) && item.getPartCode().equals(selectedPartOrderItem.getPartCode())) {
          messages.add(MRRMessages.MRR00015());
          return new ServiceResult<PartOrderItem>(false, null, messages);
        }
    }
    return new ServiceResult<PartOrderItem>(true, null);
  }

}
