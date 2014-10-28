package arrow.mems.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.util.BeanCopier;
import arrow.framework.util.DateUtils;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.messages.MFMMessages;
import arrow.mems.persistence.dto.BudgetDto;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.service.BudgetManagementService;
import arrow.mems.service.UserService;

@Named
@ViewScoped
public class BudgetManagementBean extends AbstractApprovalBean {
  private BudgetDto currentBudget;
  private Budget selectedBudget;

  private Budget budgetTemp;
  private List<Budget> listBudgetAdd;
  private List<Budget> listBudgetTemp;
  private List<Budget> listBudgetDelete;
  private String comment;
  private String hospCode;

  @Inject
  BudgetManagementService budgetService;
  @Inject
  UserService userService;
  @Inject
  UserCredential userCredential;


  @PostConstruct
  public void init() {
    this.currentBudget = new BudgetDto();
    this.listBudgetTemp = new ArrayList<Budget>();
    this.listBudgetDelete = new ArrayList<Budget>();
  }

  public void toggleSelection(final Budget budget) {
    if (budget.isSelected()) {
      if (this.selectedBudget != null) {
        this.selectedBudget.setSelected(false);
      }
      this.selectedBudget = budget;
    } else {
      this.selectedBudget = null;
      this.currentBudget = new BudgetDto();
    }
  }

  public List<Budget> getListBudget() {
    List<Budget> listBudgetByHospCode = new ArrayList<Budget>();
    if (this.userCredential.isLoggedIn() && (this.listBudgetAdd == null)) {
      this.listBudgetAdd = new ArrayList<Budget>();
      if (this.hospCode != null) {
        listBudgetByHospCode = this.budgetService.getListBudget(this.hospCode);
        if ((listBudgetByHospCode != null) && (listBudgetByHospCode.size() > 0)) {
          for (final Budget budget : listBudgetByHospCode) {
            this.listBudgetAdd.add(budget);
          }
        }
      }
    }
    if ((this.listBudgetTemp != null) && (this.listBudgetTemp.size() > 0)) {
      for (final Budget budget : this.listBudgetTemp) {
        this.listBudgetAdd.add(budget);
      }
      this.listBudgetTemp.clear();
    }

    return this.listBudgetAdd;
  }

  public void setListBudget(List<Budget> listBudget) {
    this.listBudgetAdd = listBudget;
  }

  public void addNewBudget(ActionEvent ae) {
    this.resetStage(ae);
    this.cleanStage();
    this.currentBudget = new BudgetDto();
  }

  public void editBudget(ActionEvent ae) {
    this.resetStage(ae);
    this.currentBudget = new BudgetDto();
    BeanCopier.copy(this.selectedBudget, this.currentBudget);
  }

  public void deleteBudget() {
    if (this.selectedBudget.isSelected()) {
      this.listBudgetAdd.remove(this.selectedBudget);
      if (this.selectedBudget.getBudgetId() > 0) {
        this.listBudgetDelete.add(this.selectedBudget);
      }
    }
    this.cleanStage();
  }

  public void discardChange(ActionEvent ae) {
    this.resetStage(ae);
    this.listBudgetTemp.clear();
    this.listBudgetAdd = null;
    this.listBudgetDelete.clear();
    this.cleanStage();

  }

  public void reset() {
    if (this.currentBudget == null) {
      this.currentBudget = new BudgetDto();
    } else {
      this.editBudget(null);
    }

  }

  public void cleanStage() {
    this.budgetTemp = null;
    this.currentBudget.setBudget(null);
    this.currentBudget.setStartDate(null);
    this.currentBudget.setEndDate(null);
    if (this.selectedBudget != null) {
      this.selectedBudget.setSelected(false);
      this.selectedBudget = null;
    }
  }

  public void saveBudgetTemporary() {
    if (this.isValidStartdateEndateRang(this.currentBudget.getStartDate(), this.currentBudget.getEndDate())) {
      MFMMessages.MFM00012().show();
      return;
    }
    this.budgetTemp = new Budget();
    BeanCopier.copy(this.currentBudget, this.budgetTemp);

    if ((this.selectedBudget != null) && this.selectedBudget.isSelected()) {
      this.listBudgetAdd.remove(this.selectedBudget);
      if (this.selectedBudget.getBudgetId() > 0) {
        this.listBudgetDelete.add(this.selectedBudget);
      }
    }
    if (!this.isValidExistBudgetDateRange(this.listBudgetAdd, this.budgetTemp.getStartDate(), this.budgetTemp.getEndDate())) {
      MFMMessages.MFM00020().show();
      if ((this.selectedBudget != null) && this.selectedBudget.isSelected()) {
        this.listBudgetAdd.add(this.selectedBudget);
      }
      return;
    } else {
      // add temp
      this.listBudgetTemp.add(this.budgetTemp);
      if ((this.selectedBudget != null) && this.selectedBudget.isSelected()) {
      }
      this.cleanStage();
    }
  }

  public boolean isValidStartdateEndateRang(LocalDate startDate, LocalDate endDate) {
    boolean isValidDateRange = false;
    if (startDate.isBefore(endDate)) {
      isValidDateRange = false;
    } else {
      isValidDateRange = true;
    }
    return isValidDateRange;
  }

  public boolean isValidExistBudgetDateRange(List<Budget> newListBudget, LocalDate startDate, LocalDate endDate) {
    boolean isValidBudgetDateRange = true;
    if (newListBudget.size() > 0) {
      for (final Budget budget : newListBudget) {
        if (!budget.equals(this.currentBudget)) {
          if (DateUtils.isOverlapping(startDate, endDate, budget.getStartDate(), budget.getEndDate())) {
            isValidBudgetDateRange = false;
            break;
          }
        }
      }
    }

    return isValidBudgetDateRange;
  }

  public boolean isEnableEdit() {
    return this.selectedBudget != null;
  }

  public boolean isEnableDelete() {
    return (this.selectedBudget != null);
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  public Budget getSelectedBudget() {
    return this.selectedBudget;
  }

  public void setSelectedBudget(Budget pSelectedBudget) {
    this.selectedBudget = pSelectedBudget;
  }

  public BudgetDto getCurrentBudget() {
    return this.currentBudget;
  }

  public void setCurrentBudget(BudgetDto pCurrentBudget) {
    this.currentBudget = pCurrentBudget;
  }

  public String getHospCode() {
    return this.hospCode;
  }

  public void setHospCode(String pHospCode) {
    this.hospCode = pHospCode;
    this.listBudgetAdd = null;
  }

  public List<Budget> getListBudgetAdd() {
    return this.listBudgetAdd;
  }

  public void setListBudgetAdd(List<Budget> pListBudgetAdd) {
    this.listBudgetAdd = pListBudgetAdd;
  }

  public List<Budget> getListBudgetDelete() {
    return this.listBudgetDelete;
  }

  public void setListBudgetDelete(List<Budget> pListBudgetDelete) {
    this.listBudgetDelete = pListBudgetDelete;
  }

  public void closeUpdateBudget() {
    this.currentBudget = null;
    this.cleanStage();
  }

  public void requestApprove() {

  }

  public void revise() {

  }

  public void reject() {

  }

  public void applyChange() {
    this.cleanStage();
  }

  public void saveBudget() {

  }

  @Override
  public Object getEntityId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getDataType() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public String getItemLabel() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
    // TODO Auto-generated method stub
    return false;
  }


}
