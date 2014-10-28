package arrow.mems.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import arrow.mems.persistence.dto.PersonDto;
import arrow.mems.persistence.entity.Person;
import arrow.mems.persistence.repository.PersonRepository;
import arrow.mems.rest.util.TransformUtils;

@Path("/")
public class MemsServices extends AbstractRestService {

  @Inject
  PersonRepository personRepo;

  @GET
  @Path("/person/{psnCode}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPerson(@PathParam("psnCode") String psnCode, @HeaderParam("officeCode") String officeCode) {
    super.registerCustomSerializers();

    RestResult<PersonDto> result = null;
    final Person person = this.personRepo.findActivePersonInOffice(psnCode, officeCode).getAnyResult();
    if (person != null) {
      result = new RestResult<PersonDto>(true, TransformUtils.transform(person));
    } else {
      result = new RestResult<PersonDto>(false, null);
    }

    return Response.status(Status.OK).entity(result).build();
  }
}
