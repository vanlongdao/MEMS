package arrow.mems.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;

import arrow.mems.persistence.dto.ActionLogDto;
import arrow.mems.persistence.dto.ActionTypeMasterDto;
import arrow.mems.persistence.entity.ActionLog;
import arrow.mems.persistence.entity.ActionTypeMaster;
import arrow.mems.persistence.repository.ActionLogRepository;
import arrow.mems.persistence.repository.ActionTypeMasterRepository;
import arrow.mems.persistence.repository.HospitalDeptRepository;
import arrow.mems.rest.util.TransformUtils;
import arrow.mems.service.MasterDashboardService;

@Path("/actions")
public class ActionLogService extends AbstractRestService {

  @Inject
  ActionLogRepository actionLogRepo;
  @Inject
  ActionTypeMasterRepository actionTypeRepo;
  @Inject
  MasterDashboardService service;

  @Inject
  HospitalDeptRepository hospitalDeptRepo;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllActions(@QueryParam("filter") String filter, @HeaderParam("officeCode") String officeCode) {
    final List<ActionLog> listActionLogOfOffice = this.service.getActionLogByOfficeCode(officeCode);

    final List<ActionLogDto> listDto = listActionLogOfOffice.stream().map(TransformUtils::transform).collect(Collectors.toList());

    final RestResult<List<ActionLogDto>> restResult = new RestResult<List<ActionLogDto>>(true, listDto);

    return Response.status(Status.OK).entity(restResult).build();
  }

  @GET
  @Path("/{actionCode}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getActionByCode(@PathParam("actionCode") String actionCode, @HeaderParam("officeCode") String officeCode) {
    final ActionLog actionLog = this.actionLogRepo.findActionLogByActionCodeAndOfficeCode(actionCode, officeCode).getAnyResult();
    RestResult<ActionLogDto> restResult = null;
    if (actionLog != null) {
      final ActionLogDto dto = TransformUtils.transform(actionLog);
      restResult = new RestResult<ActionLogDto>(true, dto);
    } else {
      restResult = new RestResult<ActionLogDto>(false, null);
    }
    return Response.status(Status.OK).entity(restResult).build();
  }

  @GET
  @Path("/actionTypes")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getActionTypes() {
    final List<ActionTypeMaster> listTypes = this.actionTypeRepo.findAll();
    RestResult<List<ActionTypeMasterDto>> restResult = null;
    if (CollectionUtils.isNotEmpty(listTypes)) {
      final List<ActionTypeMasterDto> listDtos = listTypes.stream().map(TransformUtils::transform).collect(Collectors.toList());
      restResult = new RestResult<List<ActionTypeMasterDto>>(true, listDtos);
    } else {
      restResult = new RestResult<List<ActionTypeMasterDto>>(false, null);
    }
    return Response.status(Status.OK).entity(restResult).build();

  }
}
