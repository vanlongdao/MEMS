/**
 *
 */
package arrow.mems.bean;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.persistence.dto.CorporateDto;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.repository.CorporateRepository;
import arrow.mems.service.CorporateService;
import arrow.mems.service.CountryService;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class CorporateManagementBean extends AbstractApprovalBean {

  @Inject
  CorporateService corporateService;

  @Inject
  CorporateRepository corporateRepository;

  @Inject
  UserCredential userCredential;

  @Inject
  CountryService countryService;

  private CorporateDto currentCorporate;
  private Corporate selectedCorporate;

  private List<Corporate> listCorporates;

  private String comment;

  public boolean isEnableEdit() {
    return this.getSelectedCorporate() != null;
  }

  public void toggleSelection(Corporate corporate) {
    if (corporate.isSelected()) {
      if (this.getSelectedCorporate() != null) {
        this.getSelectedCorporate().setSelected(false);
      }
      this.setSelectedCorporate(corporate);
    } else {
      this.setSelectedCorporate(null);
    }
  }

  public void addCorporate() {
    this.currentCorporate = new CorporateDto();
  }

  public void deleteCorporate() {
    final ServiceResult<Corporate> result = this.corporateService.deleteAddress(this.selectedCorporate);
    result.showMessages();
    if (result.isSuccess()) {
      this.cleanStage();
    }
  }

  public void saveCorporate() {
    final ServiceResult<Corporate> result = this.corporateService.saveCorporate(this.currentCorporate);
    result.showMessages();
    if (result.isSuccess()) {
      this.cleanStage();
    }
  }

  public void editCorporate() {
    final ServiceResult<CorporateDto> result = this.corporateService.editCorporate(this.selectedCorporate);
    if (result.isSuccess()) {
      this.currentCorporate = result.getData();
    }
  }

  public void closeCorporate(ActionEvent ae) {
    this.currentCorporate = null;
    this.resetStage(ae);
  }

  private void cleanStage() {
    this.currentCorporate = null;
    this.listCorporates = null;
    if (this.selectedCorporate != null) {
      this.selectedCorporate.setSelected(false);
      this.selectedCorporate = null;
    }
  }

  public void requestApprove() {}

  public Corporate getSelectedCorporate() {
    return this.selectedCorporate;
  }

  public void setSelectedCorporate(Corporate pSelectedCorporate) {
    this.selectedCorporate = pSelectedCorporate;
  }

  public CorporateDto getCurrentCorporate() {
    return this.currentCorporate;
  }

  public void setCurrentCorporate(CorporateDto pCurrentCorporate) {
    this.currentCorporate = pCurrentCorporate;
  }

  public List<Corporate> getListCorporates() {
    if (this.userCredential.isLoggedIn() && (this.listCorporates == null)) {
      this.listCorporates = this.corporateService.getListCorporates(this.userCredential.getUserInfo().getOfficeCode());
    }
    return this.listCorporates;
  }

  public void setListCorporates(List<Corporate> pListCorporates) {
    this.listCorporates = pListCorporates;
  }

  public void approve() {}

  public void revise() {}

  public void reject() {}

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  @Override
  public Object getEntityId() {
    return this.currentCorporate.getCorpCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.CORPORATE;
  }

  @Override
  public String getItemLabel() {
    return this.currentCorporate.getName();
  }

}
