package ddd.impl.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ddd.api.model.VoteDto;
import ddd.api.request.CreateVoteRequest;
import ddd.api.response.VotesForUserByVotingIdResponse;
import ddd.impl.constants.Roles;
import ddd.impl.event.VotingChangedEvent;
import ddd.impl.security.DddPrincipal;
import ddd.impl.service.VoteService;

@Path("/votes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.USER)
public class VoteServiceRest {
	
	@Inject
	private VoteService voteService;
	
	@Context
	private SecurityContext securityContext;
	
	@Inject
	private Event<VotingChangedEvent> event;
	
	@POST
	public Response addVotesForUser(CreateVoteRequest createVoteRequest) {		
		voteService.addVotesForUser(getApiKey(), createVoteRequest.getVotingId(), createVoteRequest.getVotes());

		event.fire(new VotingChangedEvent(createVoteRequest.getVotingId()));
		
		return Response.ok().build();
	}
	
	@GET
	public Response getVotesForUserByVotingId(@QueryParam(value = "votingId") Long votingId ) {
		List<VoteDto> votes = voteService.getVotesForUserByVotingId(getApiKey(), votingId);
		VotesForUserByVotingIdResponse response = new VotesForUserByVotingIdResponse();
		response.setVotes(votes);
		response.setVotingId(votingId);
		
		return Response.ok(response).build();
	}
	
	@DELETE
	public Response cancelVotingForVoting(@QueryParam(value = "votingId") Long votingId) {
		voteService.removeVotesForVoting(getApiKey(), votingId);
		return Response.ok().build();		
	}

	private String getApiKey() {
		DddPrincipal principal = (DddPrincipal) securityContext.getUserPrincipal();
		String apiKey = principal.getApiKey();
		return apiKey;
	}

}
