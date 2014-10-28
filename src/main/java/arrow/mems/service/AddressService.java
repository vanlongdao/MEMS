/**
 *
 */
package arrow.mems.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.BeanCopier;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.generator.AddressIdGenerator;
import arrow.mems.messages.MMIMessages;
import arrow.mems.persistence.dto.AddressDto;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.repository.AddressRepository;

/**
 * @author tainguyen
 *
 */
public class AddressService extends AbstractService {
  @Inject
  AddressRepository addressRepository;
  @Inject
  UserCredential userCredential;
  @Inject
  CountryService countryService;
  @Inject
  UserService userService;
  @Inject
  OperationLogService logService;

  public ServiceResult<Address> deleteAddress(Address selectedAddress) {
    final List<Message> messages = new ArrayList<>();
    final Address oldAddress = this.addressRepository.findBy(selectedAddress.getAddrId());
    this.addressRepository.deleteItem(oldAddress);
    return new ServiceResult<Address>(true, null, messages);
  }

  public ServiceResult<Address> deleteAddress(AddressDto selectedAddress) {
    final List<Message> messages = new ArrayList<>();
    final Address oldAddress = this.addressRepository.findBy(selectedAddress.getAddrId());
    if (oldAddress != null) {
      this.addressRepository.deleteItem(oldAddress);
    } else {
      messages.add(MMIMessages.MMI00021());
    }
    return new ServiceResult<Address>(messages.isEmpty(), oldAddress, messages);
  }

  public ServiceResult<AddressDto> editAddress(Address address) {
    ServiceResult<AddressDto> result = null;
    Address editAddress = new Address();
    if (address.isSelected()) {
      editAddress = this.addressRepository.findBy(address.getAddrId());
      final AddressDto addressDto = new AddressDto();
      BeanCopier.copy(editAddress, addressDto);
      result = new ServiceResult<AddressDto>(true, addressDto);
    }
    return result;
  }

  public List<Address> getListAddresses(String officeCode) {
    final List<Integer> createdBy = this.userService.findUserInOneOffice(officeCode);
    final ArrowQuery<Address> query = new ArrowQuery<>(super.em);
    query.select("a").from("Address a");
    query.where("a.isDeleted = 0");
    query.where(" a.createdBy in (?)", createdBy);
    // add filter and sorter
    query.addFilterAndSorterForString("addressCode", "a.addrCode");

    query.addFilterAndSorterForString("country", "a.mtCountry.name");

    query.addFilterAndSorterForString("zipCode", "a.zipCode");

    query.addFilterAndSorterForString("province", "a.province");

    query.addFilterAndSorterForString("city", "a.city");

    query.addFilterAndSorterForString("address1", "a.address1");

    query.addFilterAndSorterForString("address2", "a.address2");

    query.sort("addressCode", true);

    return query.getResultList();
  }

  public ServiceResult<Address> saveAddress(AddressDto currentAddress) {
    ServiceResult<Address> result = null;
    Integer userIdChecked = null;
    LocalDateTime checkedAt = null;

    currentAddress.setCountry(currentAddress.getMtCountry().getCountryId());
    if (currentAddress.getAddrCode() == null) {
      // Generate new address code
      final AddressIdGenerator generator = new AddressIdGenerator(currentAddress.getCountry(), LocalDate.now().getYear());
      final String addrCode = generator.getNext();
      currentAddress.setAddrCode(addrCode);
      result = this.saveNewAddress(currentAddress, userIdChecked, checkedAt);
    } else {
      final Address oldAddress = this.addressRepository.findBy(currentAddress.getAddrId());
      if (oldAddress.isPassedApprovalProcess()) {
        // if (this.userCredential.getUserInfo().getIsSupervisor() == 1) {
        userIdChecked = this.userCredential.getUserId();
        checkedAt = LocalDateTime.now();
        // } else {
        // // open screen login because user don't login
        // this.screenMonitor.openScreen(ScreenCodes.LOGIN);
        // }
      }
      this.addressRepository.deleteItem(oldAddress);
      result = this.saveNewAddress(currentAddress, userIdChecked, checkedAt);
      this.logService.addOperationLog(Address.class.getName(), oldAddress.getAddrId(), result.getData().getAddrId(), "update approve");
    }
    return result;
  }

  // Create new address
  public ServiceResult<Address> saveNewAddress(Address currentAddress, Integer checkedBy, LocalDateTime checkedAt) {
    final List<Message> messages = new ArrayList<>();
    // Create new Address and copy data from current address to it
    final Address newAddress = new Address();
    BeanCopier.copy(currentAddress, newAddress);

    newAddress.setIsDeleted(CommonConstants.ACTIVE);
    newAddress.setCheckedBy(checkedBy);
    newAddress.setCheckedAt(checkedAt);
    this.addressRepository.saveAndFlush(newAddress);

    messages.add(MMIMessages.MMI00006());
    return new ServiceResult<Address>(true, newAddress, messages);

  }

  public boolean validateWithPattern(String pattern, String value) {
    if (StringUtils.isEmpty(value))
      return true;
    final Pattern ptn = Pattern.compile(pattern);
    final Matcher matcher = ptn.matcher(value);
    return matcher.matches();
  }

}
