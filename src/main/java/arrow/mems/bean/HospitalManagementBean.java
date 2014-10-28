package arrow.mems.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.messages.MFMMessages;
import arrow.mems.persistence.dto.BudgetDto;
import arrow.mems.persistence.dto.HospitalDto;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.repository.BudgetRepository;
import arrow.mems.service.BudgetManagementService;
import arrow.mems.service.HospitalManagementService;
import arrow.mems.service.UserService;

@Named
@ViewScoped
public class HospitalManagementBean extends AbstractApprovalBean {
  private HospitalDto currentHospital;
  private Hospital selectedHospital;

  private List<Hospital> listHospital;
  private String comment;
  private String budget;
  private List<Budget> listBudgetAdd;
  private List<Budget> listBudgetDelete;

  @Inject
  HospitalManagementService hospitalService;
  @Inject
  UserService userService;
  @Inject
  UserCredential userCredential;
  @Inject
  BudgetManagementService budgetService;
  @Inject
  BudgetRepository budgetRep;

  public boolean isEnableEdit() {
    return this.selectedHospital != null;
  }

  public boolean isEnableDelete() {

    return (this.selectedHospital != null);
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  public String getBudget() {
    return this.budget;
  }

  public void setBudget(String pBudget) {
    this.budget = pBudget;
  }

  public Hospital getSelectedHospital() {
    return this.selectedHospital;
  }

  public void setSelectedHospital(Hospital pSelectedHospital) {
    this.selectedHospital = pSelectedHospital;
  }

  public HospitalDto getCurrentHospital() {
    return this.currentHospital;
  }

  public void setCurrentHospital(HospitalDto pCurrentHospital) {
    this.currentHospital = pCurrentHospital;
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

  @PostConstruct
  public void init() {}

  public void toggleSelection(final Hospital hospital) {
    if (hospital.isSelected()) {
      if (this.selectedHospital != null) {
        this.selectedHospital.setSelected(false);
      }
      this.selectedHospital = hospital;
    } else {
      this.selectedHospital = null;
    }
  }

  public List<Hospital> getListHospital() {
    if (this.userCredential.isLoggedIn() && (this.listHospital == null)) {
      this.listHospital = this.hospitalService.getListHospital();
    }
    return this.listHospital;
  }

  public void setListHospital(List<Hospital> listHospital) {
    this.listHospital = listHospital;
  }

  public void addNewHospital() {
    this.currentHospital = new HospitalDto();
  }

  public void editHospital() {
    final ServiceResult<HospitalDto> result = this.hospitalService.prepareEditHospital(this.selectedHospital);
    if (result.isSuccess()) {
      this.currentHospital = result.getData();
    } else {
      this.currentHospital = null;
    }
  }

  public void deleteHospital() {
    if (this.selectedHospital.isSelected()) {
      // Check if hospital is used in hospital dept. Stop delete and show message
      final List<HospitalDept> listHospDept = this.hospitalService.getListHosptialDeptByHospCode(this.selectedHospital.getHospCode());
      if (!listHospDept.isEmpty()) {
        MFMMessages.MFM00024().show();
        return;
      }
      this.hospitalService.deleteHospital(this.selectedHospital);
    }
    this.cleanStage();
  }

  public void saveHospital() {
    final ServiceResult<Hospital> result = this.hospitalService.saveHospital(this.currentHospital);

    if (result.isSuccess()) {
      // add budget
      if (this.listBudgetDelete != null) {
        for (final Budget budgetDelete : this.listBudgetDelete) {
          this.budgetService.deleteBudget(budgetDelete);
        }
      }
      if (this.listBudgetAdd != null) {
        final BudgetDto budgetDto = new BudgetDto();
        for (final Budget budgetAdd : this.listBudgetAdd) {
          if (budgetAdd.getBudgetId() == 0) {
            BeanCopier.copy(budgetAdd, budgetDto);
            budgetDto.setOrganizationCode(result.getData().getHospCode());
            this.budgetService.saveBudget(budgetDto);
          }
        }
      }
      // show message
      result.showMessages();
    }
    this.cleanStage();
  }

  public void closeUpdateHospital() {
    this.currentHospital = null;
  }

  public void requestApprove() {

  }

  public void revise() {

  }

  public void reject() {

  }

  public void editBudget() {

  }

  public void reset() {
    if (this.currentHospital == null) {
      this.currentHospital = new HospitalDto();
    } else {
      this.editHospital();
    }
  }

  public void cleanStage() {
    this.currentHospital = null;
    this.listHospital = null;
    if (this.selectedHospital != null) {
      this.selectedHospital = null;
    }
  }

  @Override
  public Object getEntityId() {
    return this.currentHospital.getHospCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.HOSPITAL;
  }

  @Override
  protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
    return true;
  }

  @Override
  public String getItemLabel() {
    return this.currentHospital.getName();
  }
}
