package arrow.mems.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.framework.helper.ServiceResult;
import arrow.mems.constant.CommonConstants;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.repository.ChecklistItemRepository;
import arrow.mems.persistence.repository.ChecklistRepository;
import arrow.mems.persistence.repository.MdevChecklistItemRepository;
import arrow.mems.persistence.repository.MdevChecklistRepository;
import arrow.mems.test.DeploymentManager;

public class ChecklistResultServiceTest extends DeploymentManager {

  @Inject
  ChecklistResultService service;
  @Inject
  ChecklistRepository repository;
  @Inject
  ChecklistItemRepository itemRepository;
  @Inject
  MdevChecklistRepository mdevCkRepository;
  @Inject
  MdevChecklistItemRepository mdevCkiRepository;

  /*
   * Case 1: listChecklist is empty, mdevChecklist of new checklist is null
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testCreatedNewChecklistCase1() throws Exception {
    final String officeCode = "30051400003";
    final Checklist checklist = new Checklist();
    final List<Checklist> listChecklist = new ArrayList<Checklist>();
    final ServiceResult<Checklist> result = this.service.createdNewChecklist(checklist, listChecklist, officeCode);
    Assert.assertFalse(result.isSuccess());
  }

  /*
   * Case 2: listChecklist is not empty, CklistCode of new checklist is alreadt exist in list
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testCreatedNewChecklistCase2() throws Exception {
    final String officeCode = "30051400003";
    final Checklist checklist = new Checklist();
    final MdevChecklist mdevChecklist = new MdevChecklist();
    mdevChecklist.setCklistCode("1");
    checklist.setMdevChecklist(mdevChecklist);
    final List<Checklist> listChecklist = new ArrayList<Checklist>();
    final Checklist checklist1 = new Checklist();
    final MdevChecklist mdevChecklist1 = new MdevChecklist();
    mdevChecklist1.setCklistCode("1");
    checklist1.setMdevChecklist(mdevChecklist1);
    listChecklist.add(checklist1);
    final ServiceResult<Checklist> result = this.service.createdNewChecklist(checklist, listChecklist, officeCode);
    Assert.assertFalse(result.isSuccess());
  }

  /*
   * Case 3: create new checklist success
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testCreatedNewChecklistCase3() throws Exception {
    final String officeCode = "30051400003";
    final Checklist checklist = new Checklist();
    final MdevChecklist mdevChecklist = new MdevChecklist();
    mdevChecklist.setCklistCode("1");
    checklist.setMdevChecklist(mdevChecklist);
    final List<Checklist> listChecklist = new ArrayList<Checklist>();
    final Checklist checklist1 = new Checklist();
    final MdevChecklist mdevChecklist1 = new MdevChecklist();
    mdevChecklist1.setCklistCode("2");
    checklist1.setMdevChecklist(mdevChecklist1);
    listChecklist.add(checklist1);
    final ServiceResult<Checklist> result = this.service.createdNewChecklist(checklist, listChecklist, officeCode);
    Assert.assertTrue(result.isSuccess());
    Assert.assertNotNull(result.getData());
  }

  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetListChecklist() throws Exception {
    final String actionCode = "1";
    final String officeCode = "30051400003";
    final List<Checklist> listChecklist = this.service.getListChecklist(actionCode, officeCode);
    Assert.assertNotNull(listChecklist);
    Assert.assertEquals(listChecklist.size(), 2);
  }

  /*
   * return list cklistCode is null
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetListChecklistItemCase1() throws Exception {
    final String cklistCode = "99";
    final String officeCode = "2";
    final List<ChecklistItem> listChecklistItem = this.service.getListChecklistItem(cklistCode, officeCode);
    Assert.assertTrue(listChecklistItem.isEmpty());
  }

  /*
   * return list Checklist is not null
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testGetListChecklistItemCase2() throws Exception {
    final String cklistCode = "1";
    final String officeCode = "30051400003";
    final List<ChecklistItem> listChecklistItem = this.service.getListChecklistItem(cklistCode, officeCode);
    Assert.assertNotNull(listChecklistItem);
    Assert.assertEquals(listChecklistItem.size(), 1);
  }

  /*
   * Case 1: result return is empty
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testCreateLocalDataAfterCreatedNewChecklistCase1() throws Exception {
    final String cklistCode = "12";
    final String officeCode = "30051400003";
    final List<ChecklistItem> listItem = this.service.createLocalDataAfterCreatedNewChecklist(cklistCode, officeCode);
    Assert.assertTrue(listItem.isEmpty());
  }

  /*
   * Case 2: result return is not empty
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testCreateLocalDataAfterCreatedNewChecklistCase2() throws Exception {
    final String cklistCode = "1";
    final String officeCode = "30051400003";
    final List<ChecklistItem> listItem = this.service.createLocalDataAfterCreatedNewChecklist(cklistCode, officeCode);
    Assert.assertEquals(listItem.size(), 1);
  }

  /*
   * Case 1: measure 1 and 2 is null
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateChecklistCase1() throws Exception {
    final Checklist checklist = new Checklist();
    final ServiceResult<Checklist> result = this.service.validateChecklist(checklist);
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 2: measure 1 is not null and 2 is null
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateChecklistCase2() throws Exception {
    final Checklist checklist = new Checklist();
    checklist.setMeasureDev1("1");
    final ServiceResult<Checklist> result = this.service.validateChecklist(checklist);
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 3: measure 1 is null and 2 is not null
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateChecklistCase3() throws Exception {
    final Checklist checklist = new Checklist();
    checklist.setMeasureDev2("1");
    final ServiceResult<Checklist> result = this.service.validateChecklist(checklist);
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 4: measure 1 is not null and 2 is not null
   * measure 1 is equals measure 2
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateChecklistCase4() throws Exception {
    final Checklist checklist = new Checklist();
    checklist.setMeasureDev1("1");
    checklist.setMeasureDev2("1");
    final ServiceResult<Checklist> result = this.service.validateChecklist(checklist);
    Assert.assertFalse(result.isSuccess());
  }

  /*
   * Case 5: measure 1 is not null and 2 is not null
   * measure 1 is not equals measure 2
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testValidateChecklistCase5() throws Exception {
    final Checklist checklist = new Checklist();
    checklist.setMeasureDev1("2");
    checklist.setMeasureDev2("1");
    final ServiceResult<Checklist> result = this.service.validateChecklist(checklist);
    Assert.assertTrue(result.isSuccess());
  }

  /*
   * Case 1: map input is empty
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveNewChecklistAndChecklistItemCase1() throws Exception {
    final List<Checklist> listChecklist = new ArrayList<>();
    final Checklist checklist = new Checklist();
    final MdevChecklist mdev = this.mdevCkRepository.findMevChecklistByChecklistCode("1", 0).getAnyResult();
    checklist.setMdevChecklist(mdev);
    checklist.setCklistLogCode("111");
    listChecklist.add(checklist);
    final Map<String, List<ChecklistItem>> map = new HashMap<>();
    final String officeCode = "30051400003";
    this.service.saveNewChecklistAndChecklistItem(listChecklist, map, "12345", officeCode);
    final Checklist result = this.repository.getChecklistByCklistLogCode("111").getAnyResult();
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isPersisted());
  }

  /*
   * Case 2: map input is not empty
   */
  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testSaveNewChecklistAndChecklistItemCase2() throws Exception {
    final List<Checklist> listChecklist = new ArrayList<>();
    final Checklist checklist = new Checklist();
    final MdevChecklist mdev = this.mdevCkRepository.findMevChecklistByChecklistCode("1", 0).getAnyResult();
    checklist.setMdevChecklist(mdev);
    checklist.setCklistLogCode("111");
    listChecklist.add(checklist);
    final Map<String, List<ChecklistItem>> map = new HashMap<>();

    // create checklist item
    final ChecklistItem item = new ChecklistItem();
    final MdevChecklistItem mdevItem = this.mdevCkiRepository.findMdevChecklistItemByCklistCodeAndIsDeleted("1", 0).getAnyResult();
    item.setMdevChecklistItem(mdevItem);
    final List<ChecklistItem> listItem = new ArrayList<>();
    listItem.add(item);

    map.put(mdev.getCklistCode(), listItem);

    final String officeCode = "30051400003";
    this.service.saveNewChecklistAndChecklistItem(listChecklist, map, "12345", officeCode);
    final Checklist result = this.repository.getChecklistByCklistLogCode("111").getAnyResult();
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isPersisted());

    final List<ChecklistItem> items = this.itemRepository.findActiveChecklistItemByCklistLogCode("111").getResultList();
    Assert.assertEquals(items.size(), 1);
  }

  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteChecklist() throws Exception {
    final Checklist checklist = this.repository.getChecklistByCklistLogCode("8030051400003140000005").getAnyResult();
    final List<Checklist> listChecklistIsDelete = new ArrayList<>();
    listChecklistIsDelete.add(checklist);
    this.service.deleteChecklist(listChecklistIsDelete);
    final Checklist result = this.repository.findBy(checklist.getPk());
    Assert.assertTrue(result.isPersisted());
    Assert.assertEquals(result.getIsDeleted(), CommonConstants.DELETE);
  }

  @Test(enabled = false)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testDeleteChecklistItem() throws Exception {
    final ChecklistItem item = this.itemRepository.findActiveChecklistItemByCkiLogCode("7030051400003140000007").getAnyResult();
    final List<ChecklistItem> list = new ArrayList<>();
    list.add(item);
    this.service.deleteChecklistItem(list);
    final ChecklistItem result = this.itemRepository.findBy(item.getPk());
    Assert.assertTrue(result.isPersisted());
    Assert.assertEquals(result.getIsDeleted(), CommonConstants.DELETE);
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testUpdateChecklist() throws Exception {
    final Checklist checklist = this.repository.getChecklistByCklistLogCode("8030051400003140000005").getAnyResult();
    checklist.setServiceOffice("30041400004");
    final List<Checklist> listChecklistIsDelete = new ArrayList<>();
    listChecklistIsDelete.add(checklist);
    this.service.updateChecklist(listChecklistIsDelete);

    final Checklist resultDeleted = this.repository.findBy(checklist.getPk());
    Assert.assertTrue(resultDeleted.isPersisted());
    Assert.assertEquals(resultDeleted.getIsDeleted(), CommonConstants.DELETE);

    final Checklist newChecklist = this.repository.getChecklistByCklistLogCode(checklist.getCklistLogCode()).getAnyResult();
    Assert.assertTrue(newChecklist.isPersisted());
    Assert.assertEquals(newChecklist.getServiceOffice(), "30041400004");
  }

  @Test(enabled = true)
  @UsingDataSet({"datasets/base_data.xls", "datasets/data.xls"})
  public void testUpdateChecklistItem() throws Exception {
    final ChecklistItem item = this.itemRepository.findActiveChecklistItemByCkiLogCode("7030051400003140000007").getAnyResult();
    item.setIsOk(1);
    final List<ChecklistItem> list = new ArrayList<>();
    list.add(item);
    this.service.updateChecklistItem(list);

    final ChecklistItem resultDeleted = this.itemRepository.findBy(item.getPk());
    Assert.assertTrue(resultDeleted.isPersisted());
    Assert.assertEquals(resultDeleted.getIsDeleted(), CommonConstants.DELETE);

    final ChecklistItem newItem = this.itemRepository.findActiveChecklistItemByCkiLogCode(item.getCkiLogCode()).getAnyResult();
    Assert.assertTrue(newItem.isPersisted());
    Assert.assertEquals(item.getIsOk(), "1");
  }

}
