/**
 *
 */
package arrow.mems.bean;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.bean.AbstractBean;
import arrow.mems.persistence.entity.ActionBill;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.service.FeeService;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class FeeBean extends AbstractBean {

  @Inject
  FeeService feeService;

  private ActionLog currentActionLog;
  private ActionBill currentActionBill;

  public void saveActionBill() {
    final String actionCode = this.currentActionLog.getActionCode();
    if (!this.currentActionLog.isPersisted()) {
      this.feeService.saveNewActionBill(this.currentActionBill, actionCode);
    } else {
      this.feeService.updateActionBill(this.currentActionBill);
    }
  }

  public void discardChanges() {
    this.currentActionBill = null;
    this.currentActionLog = null;
  }

  public ActionLog getCurrentActionLog() {
    return this.currentActionLog;
  }

  public void setCurrentActionLog(ActionLog pCurrentActionLog) {
    this.currentActionLog = pCurrentActionLog;
    if (this.currentActionLog.getActionBill() != null) {
      this.currentActionBill = this.currentActionLog.getActionBill();
    } else {
      this.currentActionBill = new ActionBill();
    }
  }

  public ActionBill getCurrentActionBill() {
    return this.currentActionBill;
  }

  public void setCurrentActionBill(ActionBill pCurrentActionBill) {
    this.currentActionBill = pCurrentActionBill;
  }
}
