/**
 *
 */
package arrow.mems.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MRRMessages;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.PartOrderItemRepository;
import arrow.mems.persistence.repository.PartOrderRepository;
import arrow.mems.persistence.repository.PartsListRepository;
import arrow.mems.persistence.repository.ReplPartRepository;
import arrow.mems.persistence.repository.UsersRepository;

/**
 * @author tainguyen
 *
 */

public class ReplacedPartService extends AbstractService {

  @Inject
  UserCredential userCredential;
  @Inject
  PartOrderRepository partOrderRepository;
  @Inject
  PartOrderItemRepository partOrderItemRepository;
  @Inject
  UsersRepository usersRepository;
  @Inject
  ReplPartRepository replacedPartRepository;
  @Inject
  PartsListRepository partsListRepository;
  @Inject
  DeviceRepository deviceRepository;

  public List<ReplPart> getListReplacedParts(String actionCode, String officeCode) {
    return this.replacedPartRepository.findAllActiveReplacedPartsInOneOffice(actionCode, officeCode).getResultList();
  }

  public ReplPart deletePart(ReplPart pSelectedReplacedPart) {
    pSelectedReplacedPart.setIsDeleted(CommonConstants.DELETE);
    return pSelectedReplacedPart;
  }

  public ServiceResult<List<PartOrderItem>> getAllPartUseToFill(String actionCode, List<String> mDevCodeOfListReplacedParts, String officeCode) {

    final List<Message> messages = new ArrayList<>();
    final List<String> listPartOrderCode = this.partOrderRepository.getAllActivePartOrderCodeUseActionCode(actionCode, officeCode).getResultList();

    if (listPartOrderCode.isEmpty()) {
      messages.add(MRRMessages.MRR00021());
      return new ServiceResult<List<PartOrderItem>>(false, null, messages);
    }

    List<PartOrderItem> list = new ArrayList<>();
    if (!mDevCodeOfListReplacedParts.isEmpty()) {
      list = this.partOrderItemRepository.getAllActivePartCodeOfPartOrderItemNotUse(listPartOrderCode, mDevCodeOfListReplacedParts).getResultList();
    } else {
      list = this.partOrderItemRepository.getAllActivePartOrderItemInListPoCode(listPartOrderCode).getResultList();
    }

    if (list.isEmpty()) {
      messages.add(MRRMessages.MRR00021());
      return new ServiceResult<List<PartOrderItem>>(false, null, messages);
    }

    messages.add(MRRMessages.MRR00036());
    return new ServiceResult<List<PartOrderItem>>(true, list, messages);
  }

  public ServiceResult<ReplPart> validateData(ReplPart newReplacedPart, List<ReplPart> listReplacedParts) {
    final List<Message> messages = new ArrayList<Message>();
    // check: user wasn't seleted new device
    if (StringUtils.isEmpty(newReplacedPart.getDeviceCode())) {
      messages.add(MRRMessages.MRR00001());
      return new ServiceResult<ReplPart>(false, newReplacedPart, messages);
    }
    // check: new device same is old device
    if (newReplacedPart.getDeviceCode().equals(newReplacedPart.getRemovedDevCode())) {
      messages.add(MRRMessages.MRR00002(newReplacedPart.getDeviceCode()));
      return new ServiceResult<ReplPart>(false, newReplacedPart, messages);
    }
    // check: this current replace part is already existed on database.
    for (final ReplPart replPart : listReplacedParts) {
      if (newReplacedPart.getDeviceCode().equals(replPart.getDeviceCode())) {
        messages.add(MRRMessages.MRR00003());
        return new ServiceResult<ReplPart>(false, newReplacedPart, messages);
      }
    }
    return new ServiceResult<ReplPart>(true, newReplacedPart, messages);
  }

  public ServiceResult<ReplPart> validateDataBeforeSave(List<ReplPart> listReplacedParts) {
    final List<Message> messages = new ArrayList<>();
    for (final ReplPart replPart : listReplacedParts) {
      if (replPart.getDeviceCode() == null) {
        messages.add(MRRMessages.MRR00001());
        return new ServiceResult<ReplPart>(false, null, messages);
      }
    }
    return new ServiceResult<ReplPart>(true, null, messages);
  }

  public void deleteReplacedParts(List<ReplPart> listReplacedPartIsDeleted) {
    for (final ReplPart replPart : listReplacedPartIsDeleted) {
      final ReplPart replPartIsDelete = this.replacedPartRepository.findBy(replPart.getReplPartId());
      this.replacedPartRepository.deleteItem(replPartIsDelete);
    }
  }

  public void saveNewReplacedParts(String actionCode, List<ReplPart> listNewReplacedPart) {
    for (final ReplPart replPart : listNewReplacedPart) {
      replPart.setActionCode(actionCode);
      this.replacedPartRepository.saveAndFlush(replPart);
    }
  }

  public void updateReplaceParts(List<ReplPart> listPartIsModify, List<ReplPart> listPartHelpModify) {
    for (int index = 0; index < listPartIsModify.size(); index++) {
      this.getReplacePartAndDelete(listPartHelpModify.get(index));
      this.replacedPartRepository.saveAndFlush(listPartIsModify.get(index));
    }
  }

  private void getReplacePartAndDelete(ReplPart replPart) {
    final String actionCode = replPart.getActionCode();
    final String devCode = replPart.getDeviceCode();
    final String removeDevCode = replPart.getRemovedDevCode();
    final ReplPart deleteItem =
        this.replacedPartRepository.findActivePartByActionCodeAndNewDeviceAndOldDevice(actionCode, devCode, removeDevCode).getAnyResult();
    this.replacedPartRepository.deleteItem(deleteItem);
  }

  public ReplPart getActiveReplPartByActionCodeDevCodeRemoveDevCode(String pString, String pString2, String pString3) {
    return this.replacedPartRepository.findActivePartByActionCodeAndNewDeviceAndOldDevice(pString, pString2, pString3).getAnyResult();
  }

  public List<String> getPartCodesByMDevCode(String mdevCode, String officeCode) {
    return this.partsListRepository.getAllPartCodeByMdevCode(mdevCode, officeCode).getResultList();
  }

  public List<Device> findNewPartReplace(List<String> pPartCodes, String pOfficeCode) {
    return this.deviceRepository.getAllActiveDeviceOfPartsList(pPartCodes, pOfficeCode).getResultList();
  }
}
