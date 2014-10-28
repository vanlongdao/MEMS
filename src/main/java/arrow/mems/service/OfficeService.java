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
import arrow.mems.generator.OfficeIdGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.OfficeDto;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.repository.OfficeRepository;

/**
 * @author tainguyen
 *
 */
public class OfficeService extends AbstractService {
  @Inject
  UserService userService;
  @Inject
  OfficeRepository officeRepository;
  @Inject
  UserCredential userCredential;
  @Inject
  OperationLogService logService;

  public ServiceResult<Office> deleteOffice(Office selectedOffice) {
    final List<Message> messages = new ArrayList<>();
    final Office oldOffice = this.officeRepository.findBy(selectedOffice.getOfficeId());
    this.officeRepository.deleteItem(oldOffice);
    return new ServiceResult<Office>(true, null, messages);
  }

  public ServiceResult<OfficeDto> editOffice(Office office) {
    ServiceResult<OfficeDto> result = null;
    Office editOffice = new Office();
    if (office.isSelected()) {
      editOffice = this.officeRepository.findBy(office.getOfficeId());
      final OfficeDto officeDto = new OfficeDto();
      BeanCopier.copy(editOffice, officeDto);
      result = new ServiceResult<OfficeDto>(true, officeDto);
    }
    return result;
  }

  public List<Office> getListOffices(String officeCode) {
    final List<Integer> createdBy = this.userService.findUserInOneOffice(officeCode);
    final ArrowQuery<Office> query = new ArrowQuery<>(super.em);
    query.select("o").from("Office o").leftJoin("o.managerPerson mp").leftJoin("o.accountantPerson ap").leftJoin("o.technicianPerson tp")
    .leftJoin("o.equipmentPerson ep");
    query.where("o.isDeleted = 0");
    query.where(" o.createdBy in (?)", createdBy);
    // add filter and sorter
    query.addFilterAndSorterForString("officeCode", "o.officeCode");

    query.addFilterAndSorterForString("officeName", "o.name");

    query.addFilterAndSorterForString("corporate", "o.corporate.name");

    query.addFilterAndSorterForString("address", "o.address.address1");

    query.addFilterAndSorterForString("tel", "o.tel");

    query.addFilterAndSorterForString("fax", "o.fax");

    query.addFilterAndSorterForString("email", "o.address.email");

    query.addFilterAndSorterForString("manager", "mp.name");

    query.addFilterAndSorterForString("accountant", "ap.name");

    query.addFilterAndSorterForString("technician", "tp.name");

    query.addFilterAndSorterForString("equipmentManager", "ep.name");

    query.addFilterAndSorterForString("taxCode", "o.taxCode");

    query.sort("officeCode", true);

    return query.getResultList();
  }

  public ServiceResult<Office> saveNewOffice(Office currentOffice, Integer checkedBy, LocalDateTime checkedAt) {
    final List<Message> messages = new ArrayList<>();
    final Office newOffice = new Office();
    BeanCopier.copy(currentOffice, newOffice);

    newOffice.setIsDeleted(CommonConstants.ACTIVE);
    newOffice.setCheckedBy(checkedBy);
    newOffice.setCheckedAt(checkedAt);
    this.officeRepository.saveAndFlush(newOffice);

    messages.add(MMIMessages.MMI00006());
    return new ServiceResult<Office>(true, newOffice, messages);
  }

  public ServiceResult<Office> saveOffice(OfficeDto currentOffice) {
    ServiceResult<Office> result = null;
    Integer userId = null;
    LocalDateTime checkedAt = null;
    currentOffice.setCorpCode(currentOffice.getCorporate().getCorpCode());
    currentOffice.setAddrCode(currentOffice.getAddress().getAddrCode());
    if (currentOffice.getOfficeCode() == null) {
      final OfficeIdGenerator generator = new OfficeIdGenerator(currentOffice.getAddress().getCountry(), LocalDate.now().getYear());
      final String officeCode = generator.getNext();
      currentOffice.setOfficeCode(officeCode);
      result = this.saveNewOffice(currentOffice, userId, checkedAt);
    } else {
      final Office oldOffice = this.officeRepository.findBy(currentOffice.getOfficeId());
      if (oldOffice.isPassedApprovalProcess()) {
        userId = this.userCredential.getUserId();
        checkedAt = LocalDateTime.now();
      }
      this.officeRepository.deleteItem(oldOffice);
      result = this.saveNewOffice(currentOffice, userId, checkedAt);
      this.logService.addOperationLog(Address.class.getName(), oldOffice.getOfficeId(), result.getData().getOfficeId(), "update approve");
    }
    return result;
  }
}
