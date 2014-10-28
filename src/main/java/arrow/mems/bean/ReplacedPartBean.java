/**
 *
 */
package arrow.mems.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.framework.util.CollectionUtils;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.entity.AbstractApprovalEntity;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.service.ReplacedPartService;
import arrow.mems.util.file.FileUtils;

/**
 * @author tainguyen
 *
 */
@Named
@ViewScoped
public class ReplacedPartBean extends AbstractApprovalBean {
  @Inject
  InputRepairRequestBean inputRepairRequestBean;
  @Inject
  ReplacedPartService replacedPartService;
  @Inject
  ImageStreamBean imageStreamBean;
  @Inject
  UserCredential userCredential;

  private ActionLog currentActionLog;

  // listReplacedParts contains all replaced part(new part and old part)
  private List<ReplPart> listReplacedPartShow;

  private List<ReplPart> listPartIsDeleted;
  private List<ReplPart> listPartIsModify;
  private List<ReplPart> listNewReplacedPart;
  private List<ReplPart> listPartHelpModify;
  private ReplPart currentReplacedPart;
  private ReplPart selectedReplacedPart;
  private boolean isAddNew;

  private List<Device> listNewPart;

  private UploadedFile image;
  private String currentKey;

  @PostConstruct
  public void init() {
    this.listPartIsDeleted = new ArrayList<>();
    this.listNewReplacedPart = new ArrayList<>();
    this.listPartIsModify = new ArrayList<>();
    this.listPartHelpModify = new ArrayList<>();
  }

  public void toggleSelectionReplacedPart(ReplPart replacedPart) {
    if (replacedPart.isSelected()) {
      if (this.currentReplacedPart != null) {
        this.currentReplacedPart.setSelected(false);
      }
      this.setCurrentReplacedPart(replacedPart);
      if (this.currentReplacedPart.isPersisted()) {
        final String actionCode = this.currentReplacedPart.getActionCode();
        final String devCode = this.currentReplacedPart.getDeviceCode();
        final String removeDevCode = this.currentReplacedPart.getRemovedDevCode();
        this.currentKey = actionCode + "," + devCode + "," + removeDevCode;
      } else {
        this.currentKey = this.currentReplacedPart.getFakeId();
      }
    } else {
      this.currentReplacedPart = null;
      this.selectedReplacedPart = null;
      this.currentKey = null;
      this.isAddNew = false;
    }
  }

  // check item, if it is persisted, set isDeleted = 1. Otherwise, remove it.
  public void deleteItem() {
    if (this.currentReplacedPart != null) {
      if (this.currentReplacedPart.isPersisted()) {
        this.listPartIsDeleted.add(this.currentReplacedPart);
      } else {
        this.listNewReplacedPart.remove(this.currentReplacedPart);
      }
      this.listReplacedPartShow.remove(this.currentReplacedPart);
      this.currentReplacedPart = null;
      this.selectedReplacedPart = null;
    }
  }

  public void addNewReplacedPart() {
    this.selectedReplacedPart = new ReplPart();
    if (this.currentReplacedPart != null) {
      this.currentReplacedPart.setSelected(false);
    }
    this.currentReplacedPart = null;
    this.currentKey = this.selectedReplacedPart.getFakeId();
    this.isAddNew = true;
  }

  public void addNewReplacedPartToLists(ReplPart newReplacedPart) {
    this.listNewReplacedPart.add(newReplacedPart);
    this.listReplacedPartShow.add(newReplacedPart);
  }

  public void fillPartOrderInfo() {
    if (this.inputRepairRequestBean.getCurrentActionLog().getActionCode() == null)
      return;

    final List<String> mDevCodeOfReplacedParts = new ArrayList<>();
    for (final ReplPart replacedPart : this.listReplacedPartShow) {
      mDevCodeOfReplacedParts.add(replacedPart.getDevice().getMdevCode());
    }
    final String actionCode = this.inputRepairRequestBean.getCurrentActionLog().getActionCode();
    final String officeCode = this.userCredential.getOfficeCode();
    final ServiceResult<List<PartOrderItem>> result = this.replacedPartService.getAllPartUseToFill(actionCode, mDevCodeOfReplacedParts, officeCode);
    result.showMessages();
    if (!result.isSuccess())
      return;
    for (final PartOrderItem item : result.getData()) {
      final ReplPart newReplacedPart = new ReplPart();
      newReplacedPart.setModelNo(item.getPartModelNo());
      // partCode <=> mdevCode
      newReplacedPart.setPartCode(item.getPartCode());
      this.addNewReplacedPartToLists(newReplacedPart);
    }
    this.isAddNew = false;
    this.selectedReplacedPart = null;
    this.currentReplacedPart = null;
    this.currentKey = null;

  }

  public void applyThisReplacedPart() {
    final ServiceResult<ReplPart> result = this.replacedPartService.validateData(this.selectedReplacedPart, this.listReplacedPartShow);
    result.showMessages();
    if (!result.isSuccess())
      return;
    if (this.image != null) {
      this.selectedReplacedPart.setImageFile(this.imageStreamBean.getRequestImage(this.currentKey));
    }
    if (this.isAddNew) {
      this.listReplacedPartShow.add(this.selectedReplacedPart);
      this.listNewReplacedPart.add(this.selectedReplacedPart);
    } else {
      if (this.selectedReplacedPart.isPersisted()) {
        this.listPartIsModify.add(this.selectedReplacedPart);
        this.listPartHelpModify.add(this.currentReplacedPart);
      } else {
        Collections.replaceAll(this.listNewReplacedPart, this.currentReplacedPart, this.selectedReplacedPart);
      }
      Collections.replaceAll(this.listReplacedPartShow, this.currentReplacedPart, this.selectedReplacedPart);
    }
    this.isAddNew = false;
    this.selectedReplacedPart = null;
    this.currentReplacedPart = null;
    this.currentKey = null;

  }

  public void saveReplacedParts() {
    final ServiceResult<ReplPart> result = this.replacedPartService.validateDataBeforeSave(this.listReplacedPartShow);
    result.showMessages();
    if (!result.isSuccess())
      return;
    if (!this.listNewReplacedPart.isEmpty()) {
      final String actionCode = this.inputRepairRequestBean.getCurrentActionLog().getActionCode();
      this.replacedPartService.saveNewReplacedParts(actionCode, this.listNewReplacedPart);
    }

    if (!this.listPartIsModify.isEmpty()) {
      this.replacedPartService.updateReplaceParts(this.listPartIsModify, this.listPartHelpModify);
    }

    if (this.listPartIsDeleted.size() != 0) {
      this.replacedPartService.deleteReplacedParts(this.listPartIsDeleted);
    }

    this.discardChanges();

  }

  public void discardChanges() {
    if (this.currentReplacedPart != null) {
      this.currentReplacedPart.setSelected(false);
    }
    this.currentReplacedPart = null;
    this.selectedReplacedPart = null;
    this.listPartHelpModify = new ArrayList<>();
    this.listPartIsModify = new ArrayList<>();
    this.listNewReplacedPart = new ArrayList<>();
    this.listPartIsDeleted = new ArrayList<>();
    this.listReplacedPartShow = null;
    this.currentKey = null;
  }

  // if current mode is edit, load all replaced part of current action log
  public List<ReplPart> getListReplacedPartShow() {
    if ((this.listReplacedPartShow == null) || this.listReplacedPartShow.isEmpty()) {
      this.listReplacedPartShow = new ArrayList<>();
      final String actionCode = this.getCurrentActionLog().getActionCode();
      if (!StringUtils.isEmpty(actionCode)) {
        final String officeCode = this.userCredential.getOfficeCode();
        this.listReplacedPartShow = this.replacedPartService.getListReplacedParts(actionCode, officeCode);
      }
    }
    return this.listReplacedPartShow;
  }

  public void setListReplacedPartShow(List<ReplPart> pListReplacedParts) {
    this.listReplacedPartShow = pListReplacedParts;
  }

  public ReplPart getCurrentReplacedPart() {
    return this.currentReplacedPart;
  }

  public void setCurrentReplacedPart(ReplPart pSelectedReplacedPart) {
    this.currentReplacedPart = pSelectedReplacedPart;
    if (this.currentReplacedPart != null) {
      this.selectedReplacedPart = new ReplPart();
      BeanCopier.copy(this.currentReplacedPart, this.selectedReplacedPart);
    }
  }

  public boolean isEnableDelete() {
    return this.currentReplacedPart != null;
  }

  public ReplPart getSelectedReplacedPart() {
    return this.selectedReplacedPart;
  }

  public void setSelectedReplacedPart(ReplPart pNewReplacedPart) {
    this.selectedReplacedPart = pNewReplacedPart;
  }

  public List<ReplPart> getListNewReplacedPart() {
    return this.listNewReplacedPart;
  }

  public void setListNewReplacedPart(List<ReplPart> pListNewReplacedPart) {
    this.listNewReplacedPart = pListNewReplacedPart;
  }

  public List<ReplPart> getListPartIsDeleted() {
    return this.listPartIsDeleted;
  }

  public void setListPartIsDeleted(List<ReplPart> pListPartsIsDeleted) {
    this.listPartIsDeleted = pListPartsIsDeleted;
  }

  public UploadedFile getImage() {
    return this.image;
  }

  public void setImage(UploadedFile pImage) {
    this.image = pImage;
  }

  public String getCurrentKey() {
    return this.currentKey;
  }

  public void setCurrentKey(String pCurrentKey) {
    this.currentKey = pCurrentKey;
  }

  public void fileImageUploadListener(FileUploadEvent e) throws IOException {
    this.setImage(e.getFile());
    final byte[] content = FileUtils.getContentFile(this.getImage().getInputstream());
    this.imageStreamBean.uploadEstimateImageTemporary(this.currentKey, content);
  }

  @Override
  public Object getEntityId() {
    return null;
  }

  @Override
  public String getDataType() {
    return null;
  }

  @Override
  protected boolean canProcess(AbstractApprovalEntity pTargetEntity) {
    return false;
  }

  @Override
  public String getItemLabel() {
    return null;
  }

  public List<ReplPart> getListPartIsModify() {
    return this.listPartIsModify;
  }

  public void setListPartIsModify(List<ReplPart> pListPartIsModify) {
    this.listPartIsModify = pListPartIsModify;
  }

  public List<ReplPart> getListPartHelpModify() {
    return this.listPartHelpModify;
  }

  public void setListPartHelpModify(List<ReplPart> pListPartHelpModify) {
    this.listPartHelpModify = pListPartHelpModify;
  }

  public boolean isAddNew() {
    return this.isAddNew;
  }

  public void setAddNew(boolean pIsAddNew) {
    this.isAddNew = pIsAddNew;
  }

  public ActionLog getCurrentActionLog() {
    return this.currentActionLog;
  }

  public void setCurrentActionLog(ActionLog pCurrentActionLog) {
    this.currentActionLog = pCurrentActionLog;
  }

  public List<Device> getListNewPart() {
    if (this.listNewPart == null) {

      this.listNewPart = new ArrayList<>();
      if (this.currentActionLog.getDevice().getMDevice() == null)
        return this.listNewPart;

      final String officeCode = this.userCredential.getOfficeCode();
      final String mdevCode = this.currentActionLog.getDevice().getMdevCode();
      final List<String> partCodes = this.replacedPartService.getPartCodesByMDevCode(mdevCode, officeCode);

      if ((partCodes == null) || partCodes.isEmpty())
        return this.listNewPart;

      this.listNewPart = this.replacedPartService.findNewPartReplace(partCodes, officeCode);
    }
    return this.listNewPart;
  }

  public void setListNewPart(List<Device> pListNewPart) {
    this.listNewPart = pListNewPart;
  }

  public List<Device> filterNewPart(String query) {
    if (StringUtils.isEmpty(query))
      return this.getListNewPart();
    return CollectionUtils.filter(this.getListNewPart(), object -> {
      final Device item = (Device) object;
      return StringUtils.containsIgnoreCase(item.getMDevice().getName(), query) || item.getDevCode().contains(query);
    });
  }

  public boolean validateData() {
    if (this.listReplacedPartShow == null)
      return true;

    final ServiceResult<ReplPart> result = this.replacedPartService.validateDataBeforeSave(this.listReplacedPartShow);
    result.showMessages();
    return result.isSuccess();
  }

}
