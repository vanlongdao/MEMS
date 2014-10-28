package arrow.mems.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import arrow.mems.persistence.dto.ActionLogDto;
import arrow.mems.persistence.dto.DeviceDto;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.Checklist;
import arrow.mems.persistence.entity.ChecklistItem;
import arrow.mems.persistence.entity.Device;
import arrow.mems.persistence.entity.MDevice;
import arrow.mems.persistence.entity.MdevChecklist;
import arrow.mems.persistence.entity.Office;
import arrow.mems.persistence.entity.PartEstimate;
import arrow.mems.persistence.entity.PartEstimateItem;
import arrow.mems.persistence.entity.PartOrder;
import arrow.mems.persistence.entity.PartOrderItem;
import arrow.mems.persistence.entity.PartsList;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.entity.ReplPart;
import arrow.mems.persistence.repository.ActionLogRepository;
import arrow.mems.persistence.repository.ChecklistItemRepository;
import arrow.mems.persistence.repository.ChecklistRepository;
import arrow.mems.persistence.repository.DeviceRepository;
import arrow.mems.persistence.repository.MDeviceRepository;
import arrow.mems.persistence.repository.MdevChecklistItemRepository;
import arrow.mems.persistence.repository.MdevChecklistRepository;
import arrow.mems.persistence.repository.OfficeRepository;
import arrow.mems.persistence.repository.PartEstimateItemRepository;
import arrow.mems.persistence.repository.PartEstimateRepository;
import arrow.mems.persistence.repository.PartOrderItemRepository;
import arrow.mems.persistence.repository.PartOrderRepository;
import arrow.mems.persistence.repository.PartsListRepository;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.persistence.repository.ReplPartRepository;
import arrow.mems.persistence.repository.UsersRepository;
import arrow.mems.rest.entities.ChecklistRest;
import arrow.mems.rest.entities.ReplacePartInfo;
import arrow.mems.rest.entities.ResponseChecklist;
import arrow.mems.rest.util.TransformUtils;
import arrow.mems.service.DevicesManagementService;
import arrow.mems.service.InputRepairRequestService;
import arrow.mems.service.ReplacedPartService;

@Path("/device")
public class DeviceService extends AbstractRestService {
  @Inject
  InputRepairRequestService inputRepairService;
  @Inject
  ReplacedPartService replacePartService;
  @Inject
  UsersRepository usersRepository;
  @Inject
  ReplPartRepository replacePartRepository;
  @Inject
  DevicesManagementService devicesManagementService;
  @Inject
  DeviceRepository deviceRepository;
  @Inject
  MDeviceRepository mDeviceRepository;
  @Inject
  PartsListRepository partslistRepository;
  @Inject
  PartOrderRepository partOrderRepository;
  @Inject
  PartOrderItemRepository partOrderItemRepository;
  @Inject
  ChecklistRepository checklistRepository;
  @Inject
  MdevChecklistRepository mdevChecklistRepository;
  @Inject
  MdevChecklistItemRepository mdevChecklistItemRepository;
  @Inject
  ChecklistItemRepository checklistItemRepository;
  @Inject
  PersonRepository personRepository;
  @Inject
  PartEstimateRepository partEstimateRepository;

  @Inject
  ActionLogRepository actionLogRepo;
  @Inject
  OfficeRepository officeRepository;
  @Inject
  PartEstimateItemRepository partEstimateItemRepository;

  @POST
  @Path("/getactionlog")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResult<ActionLog> getActionLog(String actionCode) {
    final ActionLog actionLog = this.inputRepairService.findActionLogByCodeForRest(actionCode);
    return new RestResult<ActionLog>(true, actionLog);
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findDevice(@QueryParam("filter") String query, @HeaderParam("officeCode") String officeCode) {
    final List<Device> list = this.deviceRepository.findDeviceByQueryAndOfficeCode(officeCode, query, StringUtils.EMPTY);
    final List<DeviceDto> outputList = new ArrayList<DeviceDto>();
    for (final Device item : list) {
      outputList.add(TransformUtils.transform(item));
    }
    final RestResult<List<DeviceDto>> result = new RestResult<List<DeviceDto>>(true, outputList);
    return Response.status(Status.OK).entity(result).build();
  }

  @GET
  @Path("/{devCode}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDevice(@PathParam("devCode") String devCode, @HeaderParam("officeCode") String officeCode) {
    final Device device = this.deviceRepository.findDeviceByDevCodeAndOfficeCode(devCode, officeCode).getAnyResult();
    RestResult<DeviceDto> restResult = null;
    if (device != null) {
      final DeviceDto dto = TransformUtils.transform(device);
      restResult = new RestResult<DeviceDto>(true, dto);
    } else {
      restResult = new RestResult<DeviceDto>(false, null);
    }
    return Response.status(Status.OK).entity(restResult).build();
  }


  @GET
  @Path("/department/{department}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response findDeviceOfDepartment(@PathParam("department") String departmentCode, @QueryParam("filter") String query,
      @HeaderParam("officeCode") String officeCode) {
    final List<Device> list = this.deviceRepository.findDeviceByQueryAndOfficeCode(officeCode, query, departmentCode);
    final List<DeviceDto> outputList = new ArrayList<DeviceDto>();
    for (final Device item : list) {
      outputList.add(TransformUtils.transform(item));
    }
    final RestResult<List<DeviceDto>> result = new RestResult<List<DeviceDto>>(true, outputList);
    return Response.status(Status.OK).entity(result).build();
  }

  @GET
  @Path("/{devCode}/actions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getActions(@PathParam("devCode") String devCode, @QueryParam("filter") String filter, @HeaderParam("officeCode") String officeCode) {
    final List<ActionLog> list = this.actionLogRepo.getAllActionLogsOfDevice(officeCode, devCode, filter);
    final List<ActionLogDto> outputList = new ArrayList<ActionLogDto>();
    for (final ActionLog item : list) {
      outputList.add(TransformUtils.transform(item));
    }
    final RestResult<List<ActionLogDto>> result = new RestResult<List<ActionLogDto>>(true, outputList);
    return Response.status(Status.OK).entity(result).build();
  }



  @POST
  @Path("/getDeviceInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResult<Device> getDeviceInfo(String actionCode) {

    final ActionLog actionLog = this.inputRepairService.findActionByCode(actionCode);
    return new RestResult<Device>(true, actionLog.getDevice());
  }

  @POST
  @Path("/getReplaceParts")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResult<List<ReplPart>> getReplacedParts(String actionCode, @Context HttpHeaders header) {

    final List<ReplPart> list =
        this.replacePartRepository.findAllActiveReplacedPartsInOneOffice(actionCode, header.getRequestHeader("officeCode").get(0)).getResultList();
    for (int i = 0; i < list.size(); i++) {
      list.get(i).setModelNo(list.get(i).getDevice().getMDevice().getModelNo());
    }
    return new RestResult<List<ReplPart>>(true, list);

  }

  @POST
  @Path("/addNewPart")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResult<ReplacePartInfo> addNewPart(String actionCode, @Context HttpHeaders header) {

    final ReplacePartInfo listPart = new ReplacePartInfo();
    final List<Integer> listUsersInOneOffice = this.usersRepository.findUserInOneOffice(header.getRequestHeader("officeCode").get(0));
    final String devCode = header.getRequestHeader("devCode").get(0);
    try {
      final List<Device> listDevice = this.deviceRepository.getAllPartsByTargetDevice(devCode, listUsersInOneOffice).getResultList();

      listPart.setListOldPart(listDevice);

      final List<Device> listDeviceNotActive = this.deviceRepository.getAllDeviceNotActive().getResultList();
      List<PartsList> listPartsList = new ArrayList<PartsList>();

      for (int i = 0; i < listDeviceNotActive.size(); i++) {
        listPartsList = this.partslistRepository.findPartlistByMdevcodeAndIsDeleted(listDeviceNotActive.get(i).getMdevCode(), 0).getResultList();
        for (int pi = 0; pi < listPartsList.size(); pi++) {
          for (int li = 0; li < listDeviceNotActive.size(); li++) {
            if (listDeviceNotActive.get(li).getMdevCode().equalsIgnoreCase(listPartsList.get(pi).getPartCode())) {
              listDeviceNotActive.get(i).setMDevice(this.mDeviceRepository.findActiveMdeviceBymdevCode(listDeviceNotActive.get(i).getMdevCode()));
              listPart.getListNewPart().add(listDeviceNotActive.get(i));
            }
          }
        }
      }
      return new RestResult<ReplacePartInfo>(true, listPart);
    } catch (final NullPointerException e) {
      return new RestResult<ReplacePartInfo>(true, null);
    }

  }

  @POST
  @Path("/getReplacePartInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getReplacePartInfo(String actionCode, @Context HttpHeaders header) {

    final ReplacePartInfo listPart = new ReplacePartInfo();
    final List<Integer> listUsersInOneOffice = this.usersRepository.findUserInOneOffice(header.getRequestHeader("officeCode").get(0));
    final String devCode = header.getRequestHeader("devCode").get(0);
    try {
      final List<Device> listDevice = this.deviceRepository.getAllPartsByTargetDevice(devCode, listUsersInOneOffice).getResultList();
      for (int i = 0; i < listDevice.size(); i++) {
        listDevice.get(i).setMDevice(this.mDeviceRepository.findActiveMdeviceBymdevCode(listDevice.get(i).getMdevCode()));
        listPart.getListOldPart().add(listDevice.get(i));
      }

      final List<String> listPartList =
          this.partslistRepository.getAllPartCodeByMdevCode(header.getRequestHeader("mdevCode").get(0), header.getRequestHeader("officeCode").get(0))
              .getResultList();

      final List<Device> listDeviceNotActive =
          this.deviceRepository.getAllActiveDeviceOfPartsList(listPartList, header.getRequestHeader("officeCode").get(0)).getResultList();

      listPart.setListNewPart(listDeviceNotActive);
      return Response.status(200).entity(listPart).build();
    } catch (final NullPointerException e) {
      return Response.status(403).entity("").build();
    }

  }

  @POST
  @Path("/getPartOrderItem")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPartOrderItem(String actionCode, @Context HttpHeaders header) {
    final List<String> listPoCode =
        this.partOrderRepository.getAllActivePartOrderCodeUseActionCode(actionCode, header.getRequestHeader("officeCode").get(0)).getResultList();

    final List<PartOrderItem> listPartOrderItem = this.partOrderItemRepository.getAllActivePartOrderItemInListPoCode(listPoCode).getResultList();
    final List<ReplPart> listReplacedParts = new ArrayList<ReplPart>();
    for (final PartOrderItem item : listPartOrderItem) {
      final ReplPart replPart = new ReplPart();
      final Device device = new Device();
      device.setMDevice(item.getMDevice());
      replPart.setModelNo(item.getPartModelNo());
      replPart.setPartCode(item.getPartCode());
      replPart.setDevice(device);
      listReplacedParts.add(replPart);
    }
    return Response.status(200).entity(new RestResult<List<ReplPart>>(true, listReplacedParts)).build();
  }

  @POST
  @Path("/getChecklist")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getChecklist(String actionCode, @Context HttpHeaders header) {

    final List<Checklist> listChecklist = this.checklistRepository.findActiveChecklistByActionCode(actionCode).getResultList();
    final List<ChecklistRest> listResponseChecklist = new ArrayList<ChecklistRest>();
    if (listChecklist.size() > 0) {
      for (int i = 0; i < listChecklist.size(); i++) {
        final ChecklistRest checklist = new ChecklistRest();
        checklist.setChecklistDate(listChecklist.get(i).getCheckDate());
        checklist.setChecklistName(listChecklist.get(i).getMdevChecklist().getName());
        checklist.setCklistLogCode(listChecklist.get(i).getCklistLogCode());
        checklist.setHospPsn(listChecklist.get(i).getHospPsn());
        checklist.setMeasureDev1(listChecklist.get(i).getMeasureDev1());
        checklist.setMeasureDev2(listChecklist.get(i).getMeasureDev2());
        checklist.setReferCklistCode(listChecklist.get(i).getReferCklistCode());
        checklist.setIsDelete(listChecklist.get(i).getIsDeleted());
        listResponseChecklist.add(checklist);
      }
    }
    return Response.status(200).entity(listResponseChecklist).build();

  }

  @POST
  @Path("/getMDevChecklist")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getMDevChecklist(String officeCode, @HeaderParam("devCode") String devCode) {
    final MDevice mDevice = this.mDeviceRepository.findDevicesFromPartListByIsDeleted(devCode, 0).getAnyResult();

    final List<MdevChecklist> listMdevChecklist =
        this.mdevChecklistRepository.findAllActiveMdevChecklistInOneOffice(mDevice.getMdevCode(), officeCode).getResultList();
    if (listMdevChecklist.size() > 0)
      return Response.status(200).entity(new RestResult<List<MdevChecklist>>(true, listMdevChecklist)).build();
    return Response.status(200).entity(null).build();

  }

  @POST
  @Path("/getMDevChecklistItem")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getMDevChecklistItem(String officeCode, @Context HttpHeaders header) {

    final List<String> listMdevChecklistItem =
        this.mdevChecklistItemRepository.getAllActiveMdevCkiCodeContainChecklistCode(header.getRequestHeader("cklistCode").get(0), officeCode)
        .getResultList();
    return Response.status(200).entity(listMdevChecklistItem).build();

  }

  // get all checklist info when user chose a item in list checklist
  // if a checklist no have measurement1 and measurement2, then set 2 value is null and it is a new
  // checklist
  @POST
  @Path("/getChecklistInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getChecklistInfo(String officeCode, @Context HttpHeaders header) {
    final List<ChecklistItem> listChecklistItem = new ArrayList<ChecklistItem>();

    final List<String> listCkiCode =
        this.mdevChecklistItemRepository.getAllActiveMdevCkiCodeContainChecklistCode(header.getRequestHeader("cklistCode").get(0), officeCode)
            .getResultList();

    final List<ChecklistItem> listCklistItem = this.checklistItemRepository.getAllActiveChecklistItemContainCkiCode(listCkiCode).getResultList();

    listChecklistItem.addAll(listCklistItem);

    List<Device> listMeasureDev1 = new ArrayList<Device>();
    List<Device> listMeasureDev2 = new ArrayList<Device>();
    Person person = new Person();
    try {
      listMeasureDev1 = this.deviceRepository.findActiveDeviceByDeviceCode(header.getRequestHeader("measure1").get(0)).getResultList();

      listMeasureDev2 = this.deviceRepository.findActiveDeviceByDeviceCode(header.getRequestHeader("measure2").get(0)).getResultList();

      person = this.personRepository.findByPersonCode(header.getRequestHeader("hospPsn").get(0));
    } catch (final IndexOutOfBoundsException e) {
      listMeasureDev1 = null;
      listMeasureDev2 = null;
      person = null;
    }


    final ResponseChecklist responseChecklist = new ResponseChecklist();
    responseChecklist.setListChecklistInfo(listChecklistItem);
    responseChecklist.setListMeasure1(listMeasureDev1);
    responseChecklist.setListMeasure2(listMeasureDev2);
    if (person != null) {
      responseChecklist.setPersonName(person.getName());
    } else {
      responseChecklist.setPersonName(null);
    }
    return Response.status(200).entity(new RestResult<ResponseChecklist>(true, responseChecklist)).build();

  }

  @POST
  @Path("/getPartsEstimate")
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResult<List<PartEstimate>> getPartsEstimate(String actionCode, @Context HttpHeaders header) {

    final List<PartEstimate> listPartEstimate = this.partEstimateRepository.findAllByActionLog(actionCode).getResultList();
    for (int i = 0; i < listPartEstimate.size(); i++) {
      final Office distOffice = this.officeRepository.findOfficeByOfficeCode(listPartEstimate.get(i).getDistOffice());
      listPartEstimate.get(i).setDistributorOffice(distOffice);

      final Office serviceOffice = this.officeRepository.findOfficeByOfficeCode(listPartEstimate.get(i).getServiceOffice());
      listPartEstimate.get(i).setSupplierOffice(serviceOffice);

      final Office requestOffice = this.officeRepository.findOfficeByOfficeCode(listPartEstimate.get(i).getRequesterOffice());
      listPartEstimate.get(i).setClientOffice(requestOffice);

      final Office requestDestOffice = this.officeRepository.findOfficeByOfficeCode(listPartEstimate.get(i).getReqDestOffice());
      listPartEstimate.get(i).setDeliveryToOffice(requestDestOffice);

      final Person distPerson = this.personRepository.findByPersonCode(listPartEstimate.get(i).getDistPsn());
      listPartEstimate.get(i).setDistributorPerson(distPerson);

      final Person servicePerson = this.personRepository.findByPersonCode(listPartEstimate.get(i).getServicePsn());
      listPartEstimate.get(i).setSupplierPerson(servicePerson);

      final Person requestPerson = this.personRepository.findByPersonCode(listPartEstimate.get(i).getRequesterPsn());
      listPartEstimate.get(i).setClientPerson(requestPerson);
    }
    return new RestResult<List<PartEstimate>>(true, listPartEstimate);

  }

  @POST
  @Path("/getPartEstimateItems")
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResult<List<PartEstimateItem>> getPartEstimateItems(String peCode, @Context HttpHeaders header) {
    final List<PartEstimateItem> listPartEstimateItem = this.partEstimateItemRepository.findActiveItemByPeCode(peCode).getResultList();
    return new RestResult<List<PartEstimateItem>>(true, listPartEstimateItem);
  }

  @POST
  @Path("/getPartItemsEstimate")
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResult<List<MDevice>> getPartItemsEstimate(String deviceCode) {
    final List<Device> listDevice = this.deviceRepository.findActiveDeviceByDeviceCode(deviceCode).getResultList();
    final List<PartsList> listAllPartsList = new ArrayList<PartsList>();
    for (int i = 0; i < listDevice.size(); i++) {
      final List<PartsList> listPartsList =
          this.partslistRepository.findPartlistByMdevcodeAndIsDeleted(listDevice.get(i).getMdevCode(), 0).getResultList();
      listAllPartsList.addAll(listPartsList);
    }

    final List<MDevice> listAllMDevice = new ArrayList<MDevice>();
    for (int i = 0; i < listAllPartsList.size(); i++) {
      final List<MDevice> listMDevice =
          this.mDeviceRepository.findDevicesByMdevcodeAndIsDeleted(listAllPartsList.get(i).getPartCode(), 0).getResultList();
      listAllMDevice.addAll(listMDevice);
    }

    return new RestResult<List<MDevice>>(true, listAllMDevice);
  }

  @POST
  @Path("/getPartsOrder")
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResult<List<PartOrder>> getPartsOrder(String actionCode, @HeaderParam("officeCode") String officeCode) {
    final List<PartOrder> listPartOrder =
        this.partOrderRepository.getAllActivePartOrderUseActionCodeInOneOffice(actionCode, officeCode).getResultList();
    for (int i = 0; i < listPartOrder.size(); i++) {
      final Office distOffice = this.officeRepository.findOfficeByOfficeCode(listPartOrder.get(i).getDistOffice());
      listPartOrder.get(i).setDistributorOffice(distOffice);

      final Office serviceOffice = this.officeRepository.findOfficeByOfficeCode(listPartOrder.get(i).getServiceOffice());
      listPartOrder.get(i).setEntityServiceOffice(serviceOffice);

      final Office requestOffice = this.officeRepository.findOfficeByOfficeCode(listPartOrder.get(i).getRequesterOffice());
      listPartOrder.get(i).setEntityRequestOffice(requestOffice);

      final Office destOffice = this.officeRepository.findOfficeByOfficeCode(listPartOrder.get(i).getDestOffice());
      listPartOrder.get(i).setDestinationOffice(destOffice);

      final Person distPerson = this.personRepository.findByPersonCode(listPartOrder.get(i).getDistPsn());
      listPartOrder.get(i).setDistributorPerson(distPerson);

      final Person servicePerson = this.personRepository.findByPersonCode(listPartOrder.get(i).getServicePsn());
      listPartOrder.get(i).setServicePerson(servicePerson);

      final Person requestPerson = this.personRepository.findByPersonCode(listPartOrder.get(i).getRequesterPsn());
      listPartOrder.get(i).setRequestPerson(requestPerson);
    }
    return new RestResult<List<PartOrder>>(true, listPartOrder);
  }

  @POST
  @Path("/getPartOrderItems")
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResult<List<PartOrderItem>> getPartOrderItems(String poCode, @HeaderParam("officeCode") String officeCode) {
    final List<PartOrderItem> listPartOrderItem =
        this.partOrderItemRepository.getAllActivePartOrderItemContainPartOrderCode(poCode, officeCode).getResultList();
    return new RestResult<List<PartOrderItem>>(true, listPartOrderItem);
  }

  @POST
  @Path("/getPartItemsOrder")
  @Consumes(MediaType.APPLICATION_JSON)
  public RestResult<List<MDevice>> getPartItemsOrder(String deviceCode) {
    final List<Device> listDevice = this.deviceRepository.findActiveDeviceByDeviceCode(deviceCode).getResultList();
    final List<PartsList> listAllPartsList = new ArrayList<PartsList>();
    for (int i = 0; i < listDevice.size(); i++) {
      final List<PartsList> listPartsList =
          this.partslistRepository.findPartlistByMdevcodeAndIsDeleted(listDevice.get(i).getMdevCode(), 0).getResultList();
      listAllPartsList.addAll(listPartsList);
    }

    final List<MDevice> listAllMDevice = new ArrayList<MDevice>();
    for (int i = 0; i < listAllPartsList.size(); i++) {
      final List<MDevice> listMDevice =
          this.mDeviceRepository.findDevicesByMdevcodeAndIsDeleted(listAllPartsList.get(i).getPartCode(), 0).getResultList();
      listAllMDevice.addAll(listMDevice);
    }

    return new RestResult<List<MDevice>>(true, listAllMDevice);
  }

  @POST
  @Path("/saveReplacedParts")
  @Consumes(MediaType.APPLICATION_JSON)
  public void saveReplacedParts(List<ReplPart> listPart) {
    final List<ReplPart> list = listPart;
  }
}
