package arrow.mems.bean;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.base.AbstractApprovalBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.MemsDataType;
import arrow.mems.persistence.dto.PresetPhrasesDto;
import arrow.mems.persistence.entity.PresetPhrases;
import arrow.mems.service.PresetPhrasesManagementService;
import arrow.mems.service.UserService;

@Named
@ViewScoped
public class ManagePresetPhrasesBean extends AbstractApprovalBean {
  private PresetPhrasesDto currentPresetPhrases;
  private PresetPhrases selectedPresetPhrases;


  private List<PresetPhrases> listPresetPhrases;
  private String comment;
  @Inject
  PresetPhrasesManagementService presetPhrasesService;
  @Inject
  UserService userService;
  @Inject
  UserCredential userCredential;

  public boolean isEnableEdit() {
    return this.selectedPresetPhrases != null;
  }

  public boolean isEnableDelete() {

    return (this.selectedPresetPhrases != null);
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String pComment) {
    this.comment = pComment;
  }

  public PresetPhrases getSelectedPresetPhrases() {
    return this.selectedPresetPhrases;
  }

  public void setSelectedPresetPhrases(PresetPhrases pSelectedPresetPhrases) {
    this.selectedPresetPhrases = pSelectedPresetPhrases;
  }

  public PresetPhrasesDto getCurrentPresetPhrases() {
    return this.currentPresetPhrases;
  }

  public void setCurrentPresetPhrases(PresetPhrasesDto pCurrentPresetPhrases) {
    this.currentPresetPhrases = pCurrentPresetPhrases;
  }

  public void toggleSelection(final PresetPhrases presetPhrases) {
    if (presetPhrases.isSelected()) {
      if (this.selectedPresetPhrases != null) {
        this.selectedPresetPhrases.setSelected(false);
      }
      this.selectedPresetPhrases = presetPhrases;
    } else {
      this.selectedPresetPhrases = null;
    }
  }

  public List<PresetPhrases> getListPresetPhrases() {
    if (this.userCredential.isLoggedIn() && (this.listPresetPhrases == null)) {
      this.listPresetPhrases = this.presetPhrasesService.getListPresetPhrases();
    }
    return this.listPresetPhrases;
  }

  public void setListPresetPhrases(List<PresetPhrases> listPresetPhrases) {
    this.listPresetPhrases = listPresetPhrases;
  }

  public void addNewPresetPhrases() {
    this.currentPresetPhrases = new PresetPhrasesDto();
  }

  public void editPresetPhrases() {
    final ServiceResult<PresetPhrasesDto> result = this.presetPhrasesService.prepareEditPresetPhrases(this.selectedPresetPhrases);
    if (result.isSuccess()) {
      this.currentPresetPhrases = result.getData();
    } else {
      this.currentPresetPhrases = null;
    }
  }

  public void deletePresetPhrases() {
    if (this.selectedPresetPhrases.isSelected()) {
      this.presetPhrasesService.deletePresetPhrases(this.selectedPresetPhrases);
    }
    this.cleanStage();
  }

  public void savePresetPhrases() {
    final ServiceResult<PresetPhrases> result = this.presetPhrasesService.savePresetPhrases(this.currentPresetPhrases);
    if (result.isSuccess()) {
      result.showMessages();
    }
    this.cleanStage();
  }

  public void closeUpdatePresetPhrases(ActionEvent ae) {
    this.currentPresetPhrases = null;
    this.resetStage(ae);
  }

  public void approve() {

  }

  public void revise() {

  }

  public void reject() {

  }

  public void reset() {
    if (this.currentPresetPhrases == null) {
      this.currentPresetPhrases = new PresetPhrasesDto();
    } else {
      this.editPresetPhrases();
    }
  }

  public void cleanStage() {
    this.currentPresetPhrases = null;
    this.listPresetPhrases = null;
    if (this.selectedPresetPhrases != null) {
      this.selectedPresetPhrases = null;
    }
  }


  @Override
  public Object getEntityId() {
    return this.currentPresetPhrases.getMeaningCode();
  }

  @Override
  public String getDataType() {
    return MemsDataType.PRESET_PHRASES_MEANING;
  }

  @Override
  public String getItemLabel() {
    return this.currentPresetPhrases.getInputStr();
  }
}
