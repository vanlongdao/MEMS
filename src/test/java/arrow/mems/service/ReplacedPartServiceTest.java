package arrow.mems.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.repository.ReplPartRepository;
import arrow.mems.test.DeploymentManager;

public class ReplacedPartServiceTest extends DeploymentManager {

  @Inject
  ReplacedPartService service;
  @Inject
  ReplPartRepository repository;

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetListReplacedParts() throws Exception {
    final String actionCode = "1";
    final String officeCode = "30051400003";
    final List<ReplPart> replParts = this.service.getListReplacedParts(actionCode, officeCode);
    Assert.assertNotNull(replParts);
    Assert.assertEquals(replParts.size(), 1);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeletePart() throws Exception {
    ReplPart repl = new ReplPart();
    repl = this.service.deletePart(repl);
    Assert.assertEquals(repl.getIsDeleted(), CommonConstants.DELETE);
  }

  // @Test(enabled = true)
  // @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  // public void testGetAllPartUseToFillCaseTrue() throws Exception {
  // // case true
  // final String actionCode = "1";
  // final String officeCode = "30051400003";
  // final List<String> devCodes = new ArrayList<String>();
  // devCodes.add("1");
  // final List<PartOrderItem> partItems = this.service.getAllPartUseToFill(actionCode, devCodes,
  // officeCode);
  // Assert.assertNotNull(partItems);
  // Assert.assertEquals(partItems.size(), 2);
  // // case false
  // final String officeCodeCase2 = "123456";
  // final List<PartOrderItem> partItemsCase2 = this.service.getAllPartUseToFill(actionCode,
  // devCodes, officeCodeCase2);
  // Assert.assertNull(partItemsCase2);
  // }
  //
  // @Test(enabled = true)
  // @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  // public void testGetAllPartUseToFillCaseFalse() throws Exception {
  // final String actionCode = "1";
  // final String officeCode = "123456";
  // final List<String> devCodes = new ArrayList<String>();
  // devCodes.add("1");
  // final List<PartOrderItem> partItems = this.service.getAllPartUseToFill(actionCode, devCodes,
  // officeCode);
  // Assert.assertNull(partItems);
  // }


  /*
   * Case 1: new device is missing
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateDataCase1() throws Exception {
    final ReplPart part = new ReplPart();
    final List<ReplPart> parts = new ArrayList<>();
    final ServiceResult<ReplPart> result = this.service.validateData(part, parts);
    Assert.assertFalse(result.isSuccess());
  }

  /*
   * Case 2: new device same is old device
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateDataCase2() throws Exception {
    final ReplPart part = new ReplPart();
    part.setDeviceCode("1");
    part.setRemovedDevCode("1");
    final List<ReplPart> parts = new ArrayList<>();
    final ServiceResult<ReplPart> result = this.service.validateData(part, parts);
    Assert.assertFalse(result.isSuccess());
  }

  /*
   * Case 3: current replace part already existed on database
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateDataCase3() throws Exception {
    final ReplPart part = new ReplPart();
    part.setDeviceCode("1");
    part.setRemovedDevCode("2");
    final List<ReplPart> parts = new ArrayList<>();
    final ReplPart part1 = new ReplPart();
    part1.setDeviceCode("1");
    parts.add(part1);
    final ServiceResult<ReplPart> result = this.service.validateData(part, parts);
    Assert.assertFalse(result.isSuccess());
  }

  /*
   * Case 4: pass validate
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateDataCase4() throws Exception {
    final ReplPart part = new ReplPart();
    part.setDeviceCode("1");
    part.setRemovedDevCode("2");
    final List<ReplPart> parts = new ArrayList<>();
    final ReplPart part1 = new ReplPart();
    part1.setDeviceCode("3");
    parts.add(part1);
    final ServiceResult<ReplPart> result = this.service.validateData(part, parts);
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 1: pass validate
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateDataBeforeSaveCase1() throws Exception {
    final List<ReplPart> parts = new ArrayList<>();
    final ReplPart part1 = new ReplPart();
    part1.setDeviceCode("1");
    parts.add(part1);
    final ReplPart part2 = new ReplPart();
    part2.setDeviceCode("2");
    parts.add(part2);

    final ServiceResult<ReplPart> result = this.service.validateDataBeforeSave(parts);
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 2: otherwise
   */
  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateDataBeforeSaveCase2() throws Exception {
    final List<ReplPart> parts = new ArrayList<>();
    final ReplPart part1 = new ReplPart();
    parts.add(part1);
    final ReplPart part2 = new ReplPart();
    parts.add(part2);

    final ServiceResult<ReplPart> result = this.service.validateDataBeforeSave(parts);
    Assert.assertFalse(result.isSuccess());
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteReplacedParts() throws Exception {
    final String actionCode = "1";
    final String officeCode = "30051400003";
    final List<ReplPart> parts = this.service.getListReplacedParts(actionCode, officeCode);
    this.service.deleteReplacedParts(parts);
    for (final ReplPart part : parts) {
      final ReplPart result = this.repository.findReplPartById(part.getReplPartId()).getAnyResult();
      Assert.assertEquals(result.getIsDeleted(), CommonConstants.DELETE);
    }
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveNewReplacedParts() throws Exception {
    final ReplPart part = new ReplPart();
    part.setDeviceCode("3");
    final List<ReplPart> parts = new ArrayList<>();
    parts.add(part);
    final String actionCode = "1";
    this.service.saveNewReplacedParts(actionCode, parts);
    final ReplPart newPart = this.repository.findReplPartByActionCodeAndDeviceCode(actionCode, "3");
    Assert.assertNotNull(newPart);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testUpdateReplaceParts() throws Exception {
    final String actionCode = "1";
    final String officeCode = "30051400003";
    final List<ReplPart> parts = this.service.getListReplacedParts(actionCode, officeCode);
    final List<ReplPart> partsModify = this.service.getListReplacedParts(actionCode, officeCode);
    for (final ReplPart part : partsModify) {
      part.setRemovedDevCode("3");
    }
    this.service.updateReplaceParts(partsModify, parts);
    for (final ReplPart part : parts) {
      final ReplPart result = this.repository.findReplPartById(part.getReplPartId()).getAnyResult();
      Assert.assertEquals(result.getIsDeleted(), CommonConstants.DELETE);
    }
    final List<ReplPart> listResult = this.service.getListReplacedParts(actionCode, officeCode);
    for (final ReplPart part : listResult) {
      Assert.assertEquals(part.getRemovedDevCode(), "3");
    }
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteReplPart() throws Exception {
    final String actionCode = "1";
    final String officeCode = "30051400003";
    final List<ReplPart> parts = this.service.getListReplacedParts(actionCode, officeCode);
    this.service.deleteReplacedParts(parts);
    for (final ReplPart part : parts) {
      final ReplPart result = this.repository.findReplPartById(part.getReplPartId()).getAnyResult();
      Assert.assertEquals(result.getIsDeleted(), CommonConstants.DELETE);
    }
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetActiveReplPartByActionCodeDevCodeRemoveDevCode() throws Exception {
    final String actionCode = "1";
    final String devCode = "1";
    final String removeDevCode = "2";
    final ReplPart part = this.service.getActiveReplPartByActionCodeDevCodeRemoveDevCode(actionCode, devCode, removeDevCode);
    Assert.assertNotNull(part);
    Assert.assertEquals(part.getIsDeleted(), CommonConstants.ACTIVE);
  }

}
