package ddd.impl.rest;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ddd.impl.constants.Roles;
import ddd.impl.service.VotingService;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ADMIN)
public class AdminServiceRest {

	@Inject
	private VotingService votingService;
	
	@DELETE
	@Path("/votings")
	public Response truncateVotings() {
		votingService.deleteAll();
		return Response.ok().build();
	}
}
