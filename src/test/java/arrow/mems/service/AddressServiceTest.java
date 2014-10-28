package arrow.mems.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.AuthenticationBean;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.dto.AddressDto;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.repository.AddressRepository;
import arrow.mems.persistence.repository.MtCountryRepository;
import arrow.mems.persistence.repository.OperationLogRepository;
import arrow.mems.test.DeploymentManager;

public class AddressServiceTest extends DeploymentManager {
  @Inject
  UserCredential userCredential;
  @Inject
  AddressRepository addressRepository;
  @Inject
  AddressService addressService;
  @Inject
  CountryService countryService;
  @Inject
  AuthenticationBean authenticationBean;
  @Inject
  MtCountryRepository countryRepository;
  @Inject
  OperationLogRepository logRepository;

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetListAddresses() throws Exception {
    final List<Address> listAddresses = this.addressService.getListAddresses("30051400003");
    Assert.assertNotNull(listAddresses);
    Assert.assertEquals(listAddresses.size(), 6);
  }

  // Test with address code = null
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveAddressWithActionCreateNewAddress() throws Exception {
    final AddressDto currentAddress = new AddressDto();
    currentAddress.setAddress1("test1");
    currentAddress.setAddress2("test1");
    currentAddress.setMtCountry(this.countryRepository.getMtCountryByMtCountryId(1));
    currentAddress.setZipCode("12345");
    currentAddress.setProvince("test1");
    currentAddress.setCity("Ha Noi");
    final ServiceResult<Address> result = this.addressService.saveAddress(currentAddress);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveAddressWithActionEditAddressNonApprove() throws Exception {
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    address.setSelected(true);
    final AddressDto currentAddress = this.addressService.editAddress(address).getData();
    final ServiceResult<Address> result = this.addressService.saveAddress(currentAddress);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  // Test with address code not null and address approved(checked_by and checked_at not null)
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveAddressWithActionEditAddressApprove() throws Exception {
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    address.setSelected(true);
    final AddressDto currentAddress = this.addressService.editAddress(address).getData();
    currentAddress.setZipCode("12345");
    final ServiceResult<Address> result = this.addressService.saveAddress(currentAddress);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * pass is approval process
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveAddressWithActionPassApprovalProcess() throws Exception {
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    address.setSelected(true);
    final AddressDto currentAddress = this.addressService.editAddress(address).getData();
    currentAddress.setZipCode("12345");
    currentAddress.setCheckedBy(3);
    currentAddress.setCheckedAt(LocalDateTime.now());
    final ServiceResult<Address> result = this.addressService.saveAddress(currentAddress);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveNewAddress() throws Exception {
    final Address currentAddress = new Address();
    currentAddress.setAddrCode("30041400001");
    currentAddress.setAddress1("test1");
    currentAddress.setAddress2("test1");
    currentAddress.setCountry(1);
    currentAddress.setZipCode("12345");
    currentAddress.setProvince("test1");
    currentAddress.setCity("Ha Noi");
    final ServiceResult<Address> result = this.addressService.saveNewAddress(currentAddress, null, null);

    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 1: result return is not null
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testEditAddress() throws Exception {
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    address.setSelected(true);
    final ServiceResult<AddressDto> result = this.addressService.editAddress(address);

    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 2: result return is null
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testEditAddressCase2() throws Exception {
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    final ServiceResult<AddressDto> result = this.addressService.editAddress(address);

    Assert.assertNull(result);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteAddress() throws Exception {
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    address.setSelected(true);
    final ServiceResult<Address> result = this.addressService.deleteAddress(address);
    final Address addressTest = this.addressRepository.getAddressByAddressCode(addrCode);

    Assert.assertNull(result.getData());
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(addressTest.getIsDeleted(), CommonConstants.DELETE);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateWithPattermCase1() throws Exception {
    final String value = "";
    Assert.assertTrue(this.addressService.validateWithPattern(CommonConstants.REGEX_NUMBER, value));
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateWithPattermCase2() throws Exception {
    final String value = "abc";
    Assert.assertFalse(this.addressService.validateWithPattern(CommonConstants.REGEX_NUMBER, value));
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateWithPattermCase3() throws Exception {
    final String value = "123";
    Assert.assertTrue(this.addressService.validateWithPattern(CommonConstants.REGEX_NUMBER, value));
  }

  /*
   * Case 1: old address is not null
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteAddressCase1() throws Exception {
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    address.setSelected(true);
    final AddressDto currentAddress = this.addressService.editAddress(address).getData();
    final ServiceResult<Address> result = this.addressService.deleteAddress(currentAddress);
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 2: old address is null
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteAddressCase2() throws Exception {
    final AddressDto currentAddress = new AddressDto();
    final ServiceResult<Address> result = this.addressService.deleteAddress(currentAddress);
    Assert.assertFalse(result.isSuccess());
  }

}
