/**
 *
 */
package arrow.mems.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.dto.OfficeDto;
import arrow.mems.persistence.entity.Address;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.repository.AddressRepository;
import arrow.mems.persistence.repository.CorporateRepository;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.persistence.repository.OperationLogRepository;
import arrow.mems.test.DeploymentManager;

/**
 * @author tainguyen
 *
 */
public class OfficeServiceTest extends DeploymentManager {

  @Inject
  OfficeService officeService;
  @Inject
  OfficeRepository officeRepository;
  @Inject
  CorporateRepository corporateRepository;
  @Inject
  AddressRepository addressRepository;
  @Inject
  OperationLogRepository logRepository;

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetListOffices() throws Exception {
    final List<Office> listOffices = this.officeService.getListOffices("30051400003");
    Assert.assertNotNull(listOffices);
    Assert.assertEquals(listOffices.size(), 1);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveOfficeNonOfficeCode() throws Exception {
    final OfficeDto office = new OfficeDto();
    final String corpCode = "60041400002";
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(corpCode);
    office.setCorporate(corporate);
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    office.setAddress(address);
    office.setTaxCode("123456789");
    final ServiceResult<Office> result = this.officeService.saveOffice(office);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(result.getData().getIsDeleted(), CommonConstants.ACTIVE);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveOfficeWithOfficeCodeNotApprove() throws Exception {
    final String officeCode = "30041400004";
    final Office office = this.officeRepository.findActiveOfficeByOfficeCode(officeCode);
    office.setSelected(true);
    final OfficeDto currentOffice = this.officeService.editOffice(office).getData();
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    currentOffice.setAddress(address);
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(currentOffice.getCorpCode());
    currentOffice.setCorporate(corporate);
    final ServiceResult<Office> result = this.officeService.saveOffice(currentOffice);
    Assert.assertNotNull(result.getData());
    Assert.assertEquals(result.getData().getAddrCode(), addrCode);
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveOfficeWithOfficeCodeNotApproveButHaveCheckedAt() throws Exception {
    final String officeCode = "30041400004";
    final Office office = this.officeRepository.findActiveOfficeByOfficeCode(officeCode);
    office.setSelected(true);
    final OfficeDto currentOffice = this.officeService.editOffice(office).getData();
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    currentOffice.setAddress(address);
    currentOffice.setCheckedAt(LocalDateTime.now());
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(currentOffice.getCorpCode());
    currentOffice.setCorporate(corporate);
    final ServiceResult<Office> result = this.officeService.saveOffice(currentOffice);
    Assert.assertNotNull(result.getData());
    Assert.assertEquals(result.getData().getAddrCode(), addrCode);
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveOfficeWithOfficeCodeNotApproveButHaveCheckedBy() throws Exception {
    final String officeCode = "30041400004";
    final Office office = this.officeRepository.findActiveOfficeByOfficeCode(officeCode);
    office.setSelected(true);
    final OfficeDto currentOffice = this.officeService.editOffice(office).getData();
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    currentOffice.setAddress(address);
    currentOffice.setCheckedBy(1);
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(currentOffice.getCorpCode());
    currentOffice.setCorporate(corporate);
    final ServiceResult<Office> result = this.officeService.saveOffice(currentOffice);
    Assert.assertNotNull(result.getData());
    Assert.assertEquals(result.getData().getAddrCode(), addrCode);
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveOfficeWithOfficeCodeApproved() throws Exception {
    final String officeCode = "30041400004";
    final Office office = this.officeRepository.findActiveOfficeByOfficeCode(officeCode);
    office.setSelected(true);
    final OfficeDto currentOffice = this.officeService.editOffice(office).getData();
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    currentOffice.setAddress(address);
    final ServiceResult<Office> result = this.officeService.saveOffice(currentOffice);
    Assert.assertNotNull(result.getData());
    Assert.assertEquals(result.getData().getAddrCode(), addrCode);
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveOfficeWithPassApprovalProcess() throws Exception {
    final String officeCode = "30041400004";
    final Office office = this.officeRepository.findActiveOfficeByOfficeCode(officeCode);
    office.setSelected(true);
    final OfficeDto currentOffice = this.officeService.editOffice(office).getData();
    final String addrCode = "30011400002";
    final Address address = this.addressRepository.getAddressByAddressCode(addrCode);
    currentOffice.setAddress(address);
    currentOffice.setCheckedBy(3);
    currentOffice.setCheckedAt(LocalDateTime.now());
    final ServiceResult<Office> result = this.officeService.saveOffice(currentOffice);
    Assert.assertNotNull(result.getData());
    Assert.assertEquals(result.getData().getAddrCode(), addrCode);
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteOffice() throws Exception {
    final String officeCode = "30041400004";
    final Office office = this.officeRepository.findActiveOfficeByOfficeCode(officeCode);
    this.officeService.deleteOffice(office);
    final Office officeIsDeleted = this.officeRepository.findOfficeById(office.getOfficeId());
    Assert.assertEquals(officeIsDeleted.getIsDeleted(), CommonConstants.DELETE);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveNewOffice() throws Exception {
    final Office currentOffice = new Office();
    currentOffice.setOfficeCode("30041400004");
    currentOffice.setCorpCode("60041400002");
    currentOffice.setAddrCode("30011400002");
    currentOffice.setTaxCode("123456789");
    final ServiceResult<Office> result = this.officeService.saveNewOffice(currentOffice, null, null);

    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * result return is not null
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testEditOfficeCase1() throws Exception {
    final String officeCode = "30041400004";
    final Office office = this.officeRepository.findActiveOfficeByOfficeCode(officeCode);
    office.setSelected(true);
    final ServiceResult<OfficeDto> result = this.officeService.editOffice(office);

    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * result return is null
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testEditOfficeCase2() throws Exception {
    final String officeCode = "30041400004";
    final Office office = this.officeRepository.findActiveOfficeByOfficeCode(officeCode);
    final ServiceResult<OfficeDto> result = this.officeService.editOffice(office);
    Assert.assertNull(result);
  }

}
