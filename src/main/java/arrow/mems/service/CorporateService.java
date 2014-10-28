/**
 *
 */
package arrow.mems.service;

import java.time.LocalDate;
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
import arrow.mems.generator.CorporateIdGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.CorporateDto;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.repository.CorporateRepository;

/**
 * @author tainguyen
 *
 */
public class CorporateService extends AbstractService {
  @Inject
  CorporateRepository corporateRepository;
  @Inject
  UserCredential userCredential;
  @Inject
  UserService userService;
  @Inject
  CountryService countryService;
  @Inject
  OperationLogService logService;

  public ServiceResult<Corporate> deleteAddress(Corporate selectedCorporate) {
    final List<Message> messages = new ArrayList<>();
    final Corporate oldCorporate = this.corporateRepository.findBy(selectedCorporate.getCorpId());
    this.corporateRepository.deleteItem(oldCorporate);
    return new ServiceResult<Corporate>(true, null, messages);
  }

  public ServiceResult<CorporateDto> editCorporate(Corporate corporate) {
    ServiceResult<CorporateDto> result = null;
    Corporate editCorporate = new Corporate();
    if (corporate.isSelected()) {
      editCorporate = this.corporateRepository.findBy(corporate.getCorpId());
      final CorporateDto corporateDto = new CorporateDto();
      BeanCopier.copy(editCorporate, corporateDto);
      result = new ServiceResult<CorporateDto>(true, corporateDto);
    }
    return result;
  }

  public List<Corporate> getListCorporates(String officeCode) {
    final List<Integer> createdBy = this.userService.findUserInOneOffice(officeCode);

    final ArrowQuery<Corporate> query = new ArrowQuery<>(super.em);
    query.select("c").from("Corporate c");
    query.where("c.isDeleted = 0");
    query.where(" c.createdBy in (?)", createdBy);

    query.addFilterAndSorterForString("corporateCode", "c.corpCode");

    query.addFilterAndSorterForString("corporateName", "c.name");

    query.addFilterAndSorterForString("country", "c.mtCountry.name");

    query.sort("corporateCode", true);

    return query.getResultList();
  }

  public ServiceResult<Corporate> saveCorporate(CorporateDto currentCorporate) {
    ServiceResult<Corporate> result = null;
    Integer userIdChecked = null;
    LocalDateTime checkedAt = null;
    currentCorporate.setCountry(currentCorporate.getMtCountry().getCountryId());
    if (currentCorporate.getCorpCode() == null) {
      final CorporateIdGenerator corporateIdGenerator = new CorporateIdGenerator(currentCorporate.getCountry(), LocalDate.now().getYear());
      final String corpCode = corporateIdGenerator.getNext();
      currentCorporate.setCorpCode(corpCode);
      result = this.saveNewCorporate(currentCorporate, userIdChecked, checkedAt);
    } else {
      final Corporate oldCorporate = this.corporateRepository.findBy(currentCorporate.getCorpId());
      if (oldCorporate.isPassedApprovalProcess()) {
        userIdChecked = this.userCredential.getUserId();
        checkedAt = LocalDateTime.now();
      }
      this.corporateRepository.deleteItem(oldCorporate);
      result = this.saveNewCorporate(currentCorporate, userIdChecked, checkedAt);
      this.logService.addOperationLog(Address.class.getName(), oldCorporate.getCorpId(), result.getData().getCorpId(), "update approve");
    }
    return result;
  }

  public ServiceResult<Corporate> saveNewCorporate(Corporate currentCorporate, Integer checkedBy, LocalDateTime checkedAt) {
    final List<Message> messages = new ArrayList<>();

    final Corporate newCorporate = new Corporate();
    BeanCopier.copy(currentCorporate, newCorporate);

    newCorporate.setIsDeleted(CommonConstants.ACTIVE);
    newCorporate.setCheckedBy(checkedBy);
    newCorporate.setCheckedAt(checkedAt);
    this.corporateRepository.saveAndFlush(newCorporate);

    messages.add(MMIMessages.MMI00006());

    return new ServiceResult<Corporate>(true, newCorporate, messages);
  }
}
