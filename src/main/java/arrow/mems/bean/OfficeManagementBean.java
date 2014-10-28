/**
 *
 */
package arrow.mems.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.persistence.dto.OfficeDto;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.service.OfficeService;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class OfficeManagementBean extends AbstractApprovalBean {
  @Inject
  OfficeService officeService;
  @Inject
  OfficeRepository officeRepository;
  @Inject
  UserCredential userCredential;

  private OfficeDto currentOffice;
  private Office selectedOffice;
  private List<Office> listOffices;
  private String comment;

  @PostConstruct
  public void init() {}

  public void toggleSelection(Office office) {
    if (office.isSelected()) {
      if (this.selectedOffice != null) {
        this.selectedOffice.setSelected(false);
      }
      this.selectedOffice = office;
    } else {
      this.selectedOffice = null;
    }
  }

  public List<Office> getListOffices() {
    if (this.userCredential.isLoggedIn() && (this.listOffices == null)) {
      this.listOffices = this.officeService.getListOffices(this.userCredential.getUserInfo().getOfficeCode());
    }
    return this.listOffices;
  }

  public void addOffice() {
    this.currentOffice = new OfficeDto();
  }

  public void editOffice() {
    final ServiceResult<OfficeDto> result = this.officeService.editOffice(this.selectedOffice);
    if (result.isSuccess()) {
      this.currentOffice = result.getData();
    }
  }

  public void saveOffice() {
    final ServiceResult<Office> result = this.officeService.saveOffice(this.currentOffice);
    result.showMessages();
    if (result.isSuccess()) {
      this.cleanStage();
    }
  }

  public void cleanStage() {
    this.currentOffice = null;
    this.listOffices = null;
    if (this.selectedOffice != null) {
      this.selectedOffice.setSelected(false);
      this.selectedOffice = null;
    }
  }

  public void requestApprove() {}

  public void closeOffice(ActionEvent ae) {
    this.currentOffice = null;
    this.resetStage(ae);
  }

  public void deleteOffice() {
    final ServiceResult<Office> result = this.officeService.deleteOffice(this.selectedOffice);
    result.showMessages();
    if (result.isSuccess()) {
      this.cleanStage();
    }
  }

  public boolean isEnableEdit() {
    return this.selectedOffice != null;
  }

  public OfficeDto getCurrentOffice() {
    return this.currentOffice;
  }

  public void setCurrentOffice(OfficeDto pCurrentOffice) {
    this.currentOffice = pCurrentOffice;
  }

  public Office getSelectedOffice() {
    return this.selectedOffice;
  }

  public void setSelectedOffice(Office pSelectedOffice) {
    this.selectedOffice = pSelectedOffice;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  @Override
  public Object getEntityId() {
    return this.currentOffice.getOfficeCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.OFFICE;
  }

  @Override
  public String getItemLabel() {
    return this.currentOffice.getName();
  }
}
