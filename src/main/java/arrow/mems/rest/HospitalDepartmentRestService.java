package arrow.mems.rest;

import java.util.ArrayList;
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
import org.apache.commons.lang3.StringUtils;

import arrow.framework.helper.ServiceResult;
import arrow.framework.util.BeanCopier;
import arrow.mems.persistence.dto.HospitalDeptDto;
import arrow.mems.persistence.dto.PersonDto;
import arrow.mems.persistence.entity.HospitalDept;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.rest.util.TransformUtils;
import arrow.mems.service.HospitalDeptManagementService;

@Path("/departments")
public class HospitalDepartmentRestService extends AbstractRestService {

  @Inject
  HospitalDeptManagementService service;
  @Inject
  PersonRepository personRepo;


  public static HospitalDeptDto transform(HospitalDept item) {
    final HospitalDeptDto dto = new HospitalDeptDto();
    BeanCopier.flatCopy(item, dto);
    return dto;
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllDepartments(@QueryParam("filter") String query, @HeaderParam("officeCode") String officeCode) {

    ServiceResult<List<HospitalDept>> result = null;
    if (StringUtils.isEmpty(query)) {
      result = this.service.getAllDepartmentsByOfficeForRestful(officeCode);
    } else {
      result = this.service.findDepartmentInOffice(officeCode, query);
    }

    RestResult<List<HospitalDeptDto>> restResult = null;

    if (result.isSuccess()) {
      // Convert to DTO
      List<HospitalDeptDto> dtoList = new ArrayList<HospitalDeptDto>();
      dtoList = result.getData().stream().map(HospitalDepartmentRestService::transform).collect(Collectors.toList());

      restResult = new RestResult<List<HospitalDeptDto>>(true, dtoList);
    } else {
      restResult = new RestResult<List<HospitalDeptDto>>(false, null, result.getErrors());
    }

    return Response.status(Status.OK).entity(restResult).build();
  }

  @GET
  @Path("/{departmentCode}/persons")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPersonsOfDeparment(@PathParam("departmentCode") String departmentCode, @QueryParam("filter") String query,
      @HeaderParam("officeCode") String officeCode) {
    final List<Person> persons = this.personRepo.findAllPersonsOfDepartment(departmentCode, officeCode).getResultList();
    RestResult<List<PersonDto>> restResult = null;
    if (CollectionUtils.isNotEmpty(persons)) {
      final List<PersonDto> listDto = persons.stream().filter(person -> {
        return (this.isMatchPerson(person, query));
      }).map(TransformUtils::transform).collect(Collectors.toList());
      restResult = new RestResult<List<PersonDto>>(true, listDto);
    } else {
      restResult = new RestResult<List<PersonDto>>(false, null);
    }

    return Response.status(Status.OK).entity(restResult).build();
  }

  private boolean isMatchPerson(Person person, String query) {
    if (StringUtils.isEmpty(query))
      return true;
    return StringUtils.containsIgnoreCase(person.getName(), query) || StringUtils.containsIgnoreCase(person.getPsnCode(), query) || StringUtils
        .containsIgnoreCase(person.getTel(), query) || StringUtils.containsIgnoreCase(person.getFax(), query) || StringUtils.containsIgnoreCase(
            person.getEmail(), query);
  }
}
