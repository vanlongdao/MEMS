package arrow.mems.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.mems.bean.data.UserCredential;
import arrow.mems.persistence.dto.CorporateDto;
import arrow.mems.persistence.entity.Corporate;
import arrow.mems.persistence.repository.CorporateRepository;
import arrow.mems.persistence.repository.MtCountryRepository;
import arrow.mems.test.DeploymentManager;

public class CorporateServiceTest extends DeploymentManager {
  @Inject
  CorporateRepository corporateRepository;
  @Inject
  MtCountryRepository countryRepository;
  @Inject
  CountryService countryService;

  @Inject
  UserCredential userCredential;
  @Inject
  UserService userService;
  @Inject
  CorporateService corporateService;

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetListCorporates() throws Exception {
    final List<Corporate> listCorporates = this.corporateService.getListCorporates("30051400003");
    Assert.assertNotNull(listCorporates);
    Assert.assertEquals(listCorporates.size(), 2);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveCorporateNoCorpCode() throws Exception {
    final CorporateDto corporate = new CorporateDto();
    corporate.setMtCountry(this.countryRepository.getMtCountryByMtCountryId(1));
    corporate.setName("Arrow Technologies");
    final ServiceResult<Corporate> result = this.corporateService.saveCorporate(corporate);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
    Assert.assertFalse(StringUtils.isEmpty(result.getData().getCorpCode()));
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveCorporateWithCorpCodeNoApprove() throws Exception {
    final String corpCode = "60041400002";
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(corpCode);
    corporate.setSelected(true);
    final CorporateDto currentCorporate = this.corporateService.editCorporate(corporate).getData();
    currentCorporate.setName("Arrow");
    final ServiceResult<Corporate> result = this.corporateService.saveCorporate(currentCorporate);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveCorporateWithCorpCodeNoApproveButHaveCheckedAt() throws Exception {
    final String corpCode = "60041400002";
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(corpCode);
    corporate.setSelected(true);
    final CorporateDto currentCorporate = this.corporateService.editCorporate(corporate).getData();
    currentCorporate.setName("Arrow");
    currentCorporate.setCheckedAt(LocalDateTime.now());
    final ServiceResult<Corporate> result = this.corporateService.saveCorporate(currentCorporate);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveCorporateWithCorpCodeNoApproveButHaveCheckedBy() throws Exception {
    final String corpCode = "60041400002";
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(corpCode);
    corporate.setSelected(true);
    final CorporateDto currentCorporate = this.corporateService.editCorporate(corporate).getData();
    currentCorporate.setName("Arrow");
    currentCorporate.setCheckedBy(1);
    final ServiceResult<Corporate> result = this.corporateService.saveCorporate(currentCorporate);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveCorporateWithCorpCodeApproved() throws Exception {
    final String corpCode = "60041400002";
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(corpCode);
    corporate.setSelected(true);
    final CorporateDto currentCorporate = this.corporateService.editCorporate(corporate).getData();
    currentCorporate.setName("Arrow");
    currentCorporate.setCheckedAt(LocalDateTime.now());
    currentCorporate.setCheckedBy(1);
    final ServiceResult<Corporate> result = this.corporateService.saveCorporate(currentCorporate);
    Assert.assertNotNull(result.getData());
    Assert.assertTrue(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testEditCorporate() throws Exception {
    final String corpCode = "60041400002";
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(corpCode);
    corporate.setSelected(true);
    final ServiceResult<CorporateDto> result = this.corporateService.editCorporate(corporate);
    Assert.assertTrue(result.isSuccess());
    Assert.assertNotNull(result.getData());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testEditCorporateNotSuccess() throws Exception {
    final String corpCode = "60041400002";
    final Corporate corporate = this.corporateRepository.getActiveCorporateByCorporateCode(corpCode);
    final ServiceResult<CorporateDto> result = this.corporateService.editCorporate(corporate);
    Assert.assertNull(result);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveNewCorporate() throws Exception {
    final Corporate corporate = new Corporate();
    corporate.setCorpCode("60041400002");
    corporate.setCreatedBy(3);
    corporate.setCreatedAt(LocalDateTime.now());
    corporate.setMtCountry(this.countryRepository.getMtCountryByMtCountryId(1));
    corporate.setCountry(corporate.getMtCountry().getCountryId());
    corporate.setName("Arrow-tech");
    corporate.setIsDeleted(0);
    final ServiceResult<Corporate> result = this.corporateService.saveNewCorporate(corporate, null, null);
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(result.getErrors().size(), 1);
    Assert.assertNotNull(result.getData());
  }
}
