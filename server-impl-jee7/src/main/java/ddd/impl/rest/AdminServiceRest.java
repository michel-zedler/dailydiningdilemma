package ddd.impl.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ddd.impl.annotation.RequiresValidUser;
import ddd.impl.service.DecisionService;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiresValidUser("admin")
public class AdminServiceRest {

	@Inject
	private DecisionService decisionService;
	
	@DELETE
	@Path("/decisions")
	public Response truncateDecisions() {
		decisionService.deleteAll();
		
		return Response.ok().build();
	}
}
