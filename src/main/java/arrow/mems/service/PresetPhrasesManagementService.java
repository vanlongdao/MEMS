package arrow.mems.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.generator.PresetPhrasesCodeGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.PresetPhrasesDto;
import arrow.mems.persistence.entity.PresetPhrases;
import arrow.mems.persistence.repository.OperationLogRepository;
import arrow.mems.persistence.repository.PresetPhrasesRepository;
import arrow.mems.persistence.repository.UsersRepository;

public class PresetPhrasesManagementService extends AbstractService {
  @Inject
  PresetPhrasesRepository presetPhrasesRepo;
  @Inject
  UserCredential userCredential;
  @Inject
  UsersRepository usersRepo;
  @Inject
  UserService userService;
  @Inject
  OperationLogRepository logRepo;
  @Inject
  OperationLogService logService;

  public ServiceResult<PresetPhrases> createPresetPhrases(PresetPhrasesDto currentPresetPhrases, Integer checkedBy, LocalDateTime checkedDate) {
    final List<Message> message = new ArrayList<Message>();
    ServiceResult<PresetPhrases> result = null;
    final PresetPhrases newPresetPhrases = new PresetPhrases();
    BeanCopier.copy(currentPresetPhrases, newPresetPhrases);
    newPresetPhrases.setCountry(currentPresetPhrases.getMtCountry().getCountryId());
    newPresetPhrases.setIsDeleted(CommonConstants.ACTIVE);
    newPresetPhrases.setCheckedAt(checkedDate);
    newPresetPhrases.setCheckedBy(checkedBy);
    newPresetPhrases.setCreatedAt(LocalDateTime.now());
    newPresetPhrases.setCreatedBy(this.userCredential.getUserId());
    this.presetPhrasesRepo.saveAndFlush(newPresetPhrases);
    message.add(MMIMessages.MMI00006());
    result = new ServiceResult<>(true, newPresetPhrases, message);
    return result;
  }



  public ServiceResult<PresetPhrases> deletePresetPhrases(PresetPhrases selectedPresetPhrases) {
    ServiceResult<PresetPhrases> result = null;
    final PresetPhrases deletePresetPhrases = this.presetPhrasesRepo.findBy(selectedPresetPhrases.getId());
    deletePresetPhrases.setIsDeleted(CommonConstants.DELETE);
    this.presetPhrasesRepo.saveAndFlush(deletePresetPhrases);
    result = new ServiceResult<>(true, null, null);
    return result;
  }

  public List<PresetPhrases> getListPresetPhrases() {

    final ArrowQuery<PresetPhrases> query = new ArrowQuery<>(super.em);
    query.select("e").from("PresetPhrases e ");
    query.where(" e.isDeleted = 0");
    query.addFilterAndSorterForString("inputString", "e.inputStr");
    query.addFilterAndSorterForString("showString", "e.showStr");

    query.orderBy("inputStr");

    return query.getResultList();
  }

  public ServiceResult<PresetPhrasesDto> prepareEditPresetPhrases(PresetPhrases presetPhrases) {
    ServiceResult<PresetPhrasesDto> result = null;
    PresetPhrases getPresetPhrases = new PresetPhrases();
    if (presetPhrases.isSelected()) {
      getPresetPhrases = this.presetPhrasesRepo.findBy(presetPhrases.getId());
      final PresetPhrasesDto presetPhrasesDTO = new PresetPhrasesDto();
      BeanCopier.copy(getPresetPhrases, presetPhrasesDTO);
      result = new ServiceResult<PresetPhrasesDto>(true, presetPhrasesDTO);
    }
    return result;
  }


  public ServiceResult<PresetPhrases> savePresetPhrases(PresetPhrasesDto currentPresetPhrases) {
    ServiceResult<PresetPhrases> result = null;
    if (currentPresetPhrases.getId() == 0) {
      final PresetPhrasesCodeGenerator generator = new PresetPhrasesCodeGenerator(currentPresetPhrases.getMtCountry().getCountryId());
      final String nextCode = generator.getNext();
      currentPresetPhrases.setMeaningCode(nextCode);
      result = this.createPresetPhrases(currentPresetPhrases, null, null);
    } else {
      final PresetPhrases editPresetPhrases = this.presetPhrasesRepo.findBy(currentPresetPhrases.getId());
      if (editPresetPhrases.isPassedApprovalProcess()) {
        editPresetPhrases.setIsDeleted(CommonConstants.DELETE);
        this.presetPhrasesRepo.saveAndFlush(editPresetPhrases);
        result = this.createPresetPhrases(currentPresetPhrases, this.userCredential.getUserId(), LocalDateTime.now());

      } else {
        editPresetPhrases.setIsDeleted(CommonConstants.DELETE);
        this.presetPhrasesRepo.saveAndFlush(editPresetPhrases);
        result = this.createPresetPhrases(currentPresetPhrases, null, null);
      }
      // add operation log after edit PresetPhrases
      this.logService.addOperationLog(PresetPhrases.class.getName(), currentPresetPhrases.getId(), result.getData().getId(), "update approve");
    }
    return result;
  }
}
