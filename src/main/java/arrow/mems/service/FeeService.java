/**
 *
 */
package arrow.mems.service;

import javax.inject.Inject;

import org.apache.deltaspike.data.api.QueryResult;

import arrow.framework.util.BeanCopier;
import arrow.mems.persistence.entity.ActionBill;
import arrow.mems.persistence.repository.ActionBillRepository;

/**
 * @author tainguyen
 *
 */
public class FeeService extends AbstractService {
  @Inject
  ActionBillRepository actionBillRepository;

  public void saveNewActionBill(ActionBill actionBill, String actionCode) {
    actionBill.setActionCode(actionCode);
    this.actionBillRepository.saveAndFlush(actionBill);
  }

  public void updateActionBill(ActionBill currentActionBill) {
    final String actionCode = currentActionBill.getActionCode();
    final QueryResult<ActionBill> result = this.actionBillRepository.getActiveActionBillByActionCode(actionCode);
    final ActionBill updateBill = new ActionBill();
    BeanCopier.copy(currentActionBill, updateBill);
    if (result.getAnyResult() != null) {
      final ActionBill deleteBill = result.getAnyResult();
      this.actionBillRepository.deleteItem(deleteBill);
    }
    this.actionBillRepository.saveAndFlush(updateBill);
  }
}
