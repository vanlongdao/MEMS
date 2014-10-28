package arrow.mems.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/repairrequest")
public class RepairRequestService {
  @POST
  @Path("repairrequest")
  @Consumes("application/json")
  public Response reciveRepairRequest() {
    final String output = "";
    return Response.status(200).entity(output).build();
  }

  @GET
  @Path("/{name}")
  public Response test(@PathParam("name") String name) {
    return Response.status(200).entity(name).build();
  }
}
