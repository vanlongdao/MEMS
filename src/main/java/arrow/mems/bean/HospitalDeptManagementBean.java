package arrow.mems.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.persistence.dto.BudgetDto;
import arrow.mems.persistence.dto.HospitalDeptDto;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.Budget;
import arrow.mems.persistence.entity.Hospital;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.repository.BudgetRepository;
import arrow.mems.persistence.repository.HospitalRepository;
import arrow.mems.service.BudgetManagementService;
import arrow.mems.service.HospitalDeptManagementService;
import arrow.mems.service.UserService;

@Named
@ViewScoped
public class HospitalDeptManagementBean extends AbstractApprovalBean {
  private HospitalDeptDto currentHospitalDept;
  private HospitalDept selectedHospitalDept;

  private List<HospitalDept> listHospitalDept;
  private List<Budget> listBudgetAdd;
  private List<Budget> listBudgetDelete;
  private String comment;

  @Inject
  HospitalDeptManagementService hospitalDeptService;
  @Inject
  UserService userService;
  @Inject
  UserCredential userCredential;
  @Inject
  BudgetManagementService budgetService;
  @Inject
  BudgetRepository budgetRep;
  @Inject
  HospitalRepository hospRepo;

  // Set&Get HospCode from Hospital manage screen
  private String hospCode;

  public String getHospCode() {
    return this.hospCode;
  }

  public void setHospCode(String pHospCode) {
    this.hospCode = pHospCode;
    if (StringUtils.isNotEmpty(this.hospCode)) {
      this.listHospitalDept = this.hospitalDeptService.getListHospitalDept(this.hospCode);
    }
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

  public void addNewHospitalDept() {
    this.currentHospitalDept = new HospitalDeptDto();
    if (StringUtils.isNotEmpty(this.hospCode)) {
      final Hospital hospital = this.hospRepo.findByHospitalCode(this.hospCode);
      this.currentHospitalDept.setHospital(hospital);
    }
  }

  public void cleanStage() {
    this.currentHospitalDept = null;
    this.listHospitalDept = null;
    if (this.selectedHospitalDept != null) {
      this.selectedHospitalDept = null;
    }
  }

  public void closeUpdateHospitalDept() {
    this.currentHospitalDept = null;
  }

  public void deleteHospitalDept() {
    if (this.selectedHospitalDept.isSelected()) {
      this.hospitalDeptService.deleteHospitalDept(this.selectedHospitalDept);
    }
    this.cleanStage();
  }

  public void editBudget() {

  }

  public void editHospitalDept() {
    final ServiceResult<HospitalDeptDto> result = this.hospitalDeptService.prepareEditHospitalDept(this.selectedHospitalDept);
    if (result.isSuccess()) {
      this.currentHospitalDept = result.getData();
    } else {
      this.currentHospitalDept = null;
    }
  }

  public String getComment() {
    return this.comment;
  }

  public HospitalDeptDto getCurrentHospitalDept() {
    return this.currentHospitalDept;
  }

  public List<HospitalDept> getListHospitalDept() {
    if (this.userCredential.isLoggedIn() && (this.listHospitalDept == null)) {
      if (StringUtils.isEmpty(this.hospCode)) {
        this.listHospitalDept = this.hospitalDeptService.getListHospitalDept(null);
      } else {
        this.listHospitalDept = this.hospitalDeptService.getListHospitalDept(this.hospCode);
      }

    }
    return this.listHospitalDept;
  }



  public HospitalDept getSelectedHospitalDept() {
    return this.selectedHospitalDept;
  }

  @PostConstruct
  public void init() {}

  public boolean isEnableDelete() {

    return (this.selectedHospitalDept != null);
  }

  public boolean isEnableEdit() {
    return this.selectedHospitalDept != null;
  }

  public void reject() {

  }

  public void requestApprove() {

  }

  public void reset() {
    if (this.currentHospitalDept == null) {
      this.currentHospitalDept = new HospitalDeptDto();
    } else {
      this.editHospitalDept();
    }
  }

  public void revise() {

  }

  public void saveHospitalDept() {
    final ServiceResult<HospitalDept> result = this.hospitalDeptService.saveHospitalDept(this.currentHospitalDept);
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
            budgetDto.setOrganizationCode(result.getData().getDeptCode());
            this.budgetService.saveBudget(budgetDto);
          }
        }
      }
      // show message
      result.showMessages();
    }
    this.cleanStage();
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  public void setCurrentHospitalDept(HospitalDeptDto pCurrentHospitalDept) {
    this.currentHospitalDept = pCurrentHospitalDept;
  }

  public void setListHospitalDept(List<HospitalDept> listHospitalDept) {
    this.listHospitalDept = listHospitalDept;
  }

  public void setSelectedHospitalDept(HospitalDept pSelectedHospitalDept) {
    this.selectedHospitalDept = pSelectedHospitalDept;
  }

  public void toggleSelection(final HospitalDept hospital) {
    if (hospital.isSelected()) {
      if (this.selectedHospitalDept != null) {
        this.selectedHospitalDept.setSelected(false);
      }
      this.selectedHospitalDept = hospital;
    } else {
      this.selectedHospitalDept = null;
    }
  }

  @Override
  public Object getEntityId() {
    return this.currentHospitalDept.getHospCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.HOSPITAL_DEPARTMENT;
  }

  @Override
  protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
    return true;
  }

  @Override
  public String getItemLabel() {
    return this.currentHospitalDept.getName();
  }

}
