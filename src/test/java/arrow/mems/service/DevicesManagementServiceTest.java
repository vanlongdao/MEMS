package arrow.mems.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.testng.annotations.Test;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.constant.CommonConstants;
import arrow.mems.messages.MessageCode;
import arrow.mems.persistence.dto.AlertByCountDto;
import arrow.mems.persistence.dto.MDeviceDto;
import arrow.mems.persistence.dto.MdevChecklistDto;
import arrow.mems.persistence.dto.ScheduleDto;
import arrow.mems.persistence.entity.AlertByCount;
import arrow.mems.persistence.entity.Document;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.MdevChecklistItem;
import arrow.mems.persistence.entity.MtCountry;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.PartsList;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.Schedule;
import arrow.mems.persistence.entity.Users;
import arrow.mems.persistence.repository.MDeviceRepository;
import arrow.mems.persistence.repository.ScheduleRepository;

@Test
@UsingDataSet("datasets/long_dataset.xls")
public class DevicesManagementServiceTest extends Arquillian {

  @Inject
  private DevicesManagementService testInstance;
  @Inject
  private MDeviceRepository mdeviceRepository;
  @Inject
  private ScheduleRepository scheduleRepository;

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testSearchDevicesSync() throws Exception {
    String manufactoryValue = "JP";
    final String genaralValue = null;
    final String nameValue = null;
    final String officeCode = "11400001";
    final List<MDevice> haveResult =
        this.testInstance.searchDevicesSync(manufactoryValue, genaralValue, nameValue, Collections.emptyList(), officeCode, null);
    Assertions.assertThat(haveResult.size()).isEqualTo(14);

    manufactoryValue = "noResultHahahah";
    final List<MDevice> noResult =
        this.testInstance.searchDevicesSync(manufactoryValue, genaralValue, nameValue, Collections.emptyList(), officeCode, null);
    Assertions.assertThat(noResult.size()).isEqualTo(0);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetPartlistInfo() throws Exception {
    final List<MDevice> result = this.testInstance.getPartlistInfo("14140016");
    Assertions.assertThat(result.size()).isEqualTo(1);

    final List<MDevice> noResult = this.testInstance.getPartlistInfo("9785978");
    Assertions.assertThat(noResult.size()).isEqualTo(0);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testDeleteMasterDevice() throws Exception {
    final String updatedMdevice = "14140014";
    final int isDeleted = CommonConstants.DELETE;
    final ServiceResult<Message> result = this.testInstance.deleteMasterDevice(updatedMdevice, isDeleted);
    Assertions.assertThat(result.getErrors().get(0).getMessageCode()).isEqualTo(MessageCode.MAU00019);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetPersonByOfficeCode() throws Exception {
    final String officeCode = "11400001";
    final int isDeleted = CommonConstants.ACTIVE;
    final List<Person> result = this.testInstance.getAllPersonByOwnedOfficeAndIsDeleted(officeCode, isDeleted);
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testSearchOfficeManufacturer() throws Exception {
    final List<Office> result = this.testInstance.searchOfficeManufacturer(null, null, null, CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(5);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testFindMDeviceByOwnedOfficeCodeAndIsDeleted() throws Exception {
    final List<MDevice> result = this.testInstance.findMDeviceByOwnedOfficeCodeAndIsDeleted("11400001", CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(21);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testAutoCompletePersonManufacturer() throws Exception {
    final List<Person> result = this.testInstance.autoCompletePersonManufacturer(CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testAutoCompleteOfficeManufacturer() throws Exception {
    final List<Office> result = this.testInstance.autoCompleteOfficeManufacturer(CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(5);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testSearchAvailableParts() throws Exception {
    final MDevice device = new MDevice();
    device.setIsDeleted(CommonConstants.ACTIVE);
    final List<MDevice> result = this.testInstance.searchAvailableParts(device, null);
    Assertions.assertThat(result.size()).isEqualTo(19);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetInfoPartlistByMdevcode() throws Exception {
    final List<MDevice> result = this.testInstance.getInfoPartlistByMdevcode("14140016", CommonConstants.ACTIVE, CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetNameUserByCreatedBy() throws Exception {
    final Users result = this.testInstance.getNameUserByCreatedBy(3);
    Assertions.assertThat(result.getName()).isEqualTo("Dao Van Long");
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetMdevChecklistItemByCklistCode() throws Exception {
    final List<MdevChecklistItem> result = this.testInstance.getMdevChecklistItemByCklistCode("15140002", CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetMdevChecklistByMdevcode() throws Exception {
    final List<MdevChecklist> result = this.testInstance.getMdevChecklistByMdevcode("54321321", CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(5);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetDeviceFromPK() throws Exception {
    final String mdevCode = "543221";
    final MDevice device = this.mdeviceRepository.findDevicesByMdevcodeAndIsDeleted(mdevCode, CommonConstants.ACTIVE).getAnyResult();
    final MDeviceDto dto = new MDeviceDto();
    BeanCopier.copy(device, dto);
    final MDevice result = this.testInstance.getDeviceFromPK(dto);
    Assertions.assertThat(result.getName()).isEqualTo(device.getName());
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetDeviceFromMdevCode() throws Exception {
    final String mdevCode = "543221";
    final MDevice result = this.testInstance.getDeviceFromMdevCode(mdevCode, CommonConstants.ACTIVE);
    Assertions.assertThat(result.getName()).isEqualTo("do tim");
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetCountry() throws Exception {
    final List<MtCountry> result = this.testInstance.getCountry();
    Assertions.assertThat(result.size()).isEqualTo(250);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetAllOfficeByOwnedOfficeAndIsDeleted() throws Exception {
    final List<Office> result = this.testInstance.getAllOfficeByOwnedOfficeAndIsDeleted("11400001", CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(5);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetAllPersonByOwnedOfficeAndIsDeleted() throws Exception {
    final List<Person> result = this.testInstance.getAllPersonByOwnedOfficeAndIsDeleted("11400001", CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testDeleteSchedule() throws Exception {
    final List<Schedule> deletedListSchedule = this.scheduleRepository.findAll();
    final int result = this.testInstance.deleteSchedule(deletedListSchedule);
    Assertions.assertThat(result).isEqualTo(5);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testDeletePartlist() throws Exception {
    final List<MDevice> deletedPartslist = this.mdeviceRepository.findDevicesByMdevcodeAndIsDeleted("94812", CommonConstants.ACTIVE).getResultList();
    final int result = this.testInstance.deletePartlist(deletedPartslist, "54321321");
    Assertions.assertThat(result).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testDeleteChecklist() throws Exception {
    final List<MdevChecklistDto> deletedChecklist = new ArrayList<MdevChecklistDto>();
    final MdevChecklistDto checklist = new MdevChecklistDto();
    checklist.setCklistCode("15140001");
    checklist.setCklistId(5);
    deletedChecklist.add(checklist);
    final int result = this.testInstance.deleteChecklist(deletedChecklist, "543221", 1);
    Assertions.assertThat(result).isEqualTo(3);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testSaveMasterDevice() throws Exception {
    final MDevice device = this.mdeviceRepository.findBy(4);
    final List<MDevice> deletedPartList =
        this.mdeviceRepository.findDevicesFromPartListByIsDeleted(device.getMdevCode(), CommonConstants.ACTIVE).getResultList();
    final List<MDevice> newPartsList = null;
    final List<MdevChecklistDto> listChecklist = null;
    final List<MdevChecklistDto> deletedChecklist = null;
    final List<Document> listDocumentAdd = null;
    final List<Document> listDocumentDelete = null;
    final List<ScheduleDto> listSchedule = null;
    final List<Schedule> deletedListSchedule = null;
    final int createdBy = 1;
    final List<Document> listModifiedDocuments = null;
    final List<AlertByCountDto> listScheduleAlert = null;
    final List<AlertByCount> deletedListScheduleAlert = null;

    final ServiceResult<MDevice> result =
        this.testInstance.saveMasterDevice(device, deletedPartList, newPartsList, listChecklist, deletedChecklist, listDocumentAdd,
            listDocumentDelete, listModifiedDocuments, listSchedule, deletedListSchedule, listScheduleAlert, deletedListScheduleAlert, createdBy);

    Assertions.assertThat(result.getData().getMdevCode()).isEqualTo("543221");
    Assertions.assertThat(result.getErrors().get(0).getMessageCode()).isEqualTo(MessageCode.MDM00003);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetMasterScheduleByCurrentDevice() throws Exception {
    final List<Schedule> result = this.testInstance.getMasterScheduleByCurrentDevice("54321321");
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetAllPersonByOwnedOfficeAndOfficeCodeAndIsDeleted() throws Exception {
    final List<Person> result = this.testInstance.getAllPersonByOwnedOfficeAndOfficeCodeAndIsDeleted("11400001", "11400003", CommonConstants.ACTIVE);
    Assertions.assertThat(result.size()).isEqualTo(1);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetAllPartsOfMasterDevice() throws Exception {
    final ServiceResult<List<MDevice>> result = this.testInstance.getAllPartsOfMasterDevice("54321321");
    Assertions.assertThat(result.getData().size()).isEqualTo(3);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetAlertByCountByMdevcode() throws Exception {
    final List<AlertByCount> result = this.testInstance.getAlertByCountByMdevcode("54321321");
    Assertions.assertThat(result.size()).isEqualTo(2);
  }

  @Test(enabled = true)
  @UsingDataSet("datasets/long_dataset.xls")
  public void testGetAllPartsListOfMasterDevice() throws Exception {
    final ServiceResult<List<PartsList>> result = this.testInstance.getAllPartsListOfMasterDevice("54321321");
    Assertions.assertThat(result.getData().size()).isEqualTo(3);
  }
}
