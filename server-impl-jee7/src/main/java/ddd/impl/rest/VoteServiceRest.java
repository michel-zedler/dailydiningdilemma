package ddd.impl.rest;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ddd.api.request.CreateVoteRequest;
import ddd.impl.constants.Roles;
import ddd.impl.service.VoteService;

@Path("/votes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.USER)
public class VoteServiceRest {
	
	@Inject
	private VoteService voteService;
	
	@POST
	public Response addVotesForUser(CreateVoteRequest createVoteRequest) {		
		voteService.addVotesForUser(createVoteRequest.getUserId(),createVoteRequest.getDecisionId(),createVoteRequest.getVotes());
		return Response.ok().build();
	}
	

}
