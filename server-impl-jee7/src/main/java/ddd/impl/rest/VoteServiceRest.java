package ddd.impl.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ddd.api.model.VoteDto;
import ddd.api.request.CreateVoteRequest;
import ddd.api.response.VotesForUserByDecisionIdResponse;
import ddd.impl.constants.Roles;
import ddd.impl.service.VoteService;

@Path("/votes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.USER)
public class VoteServiceRest {
	
	@Inject
	private VoteService voteService;
	
	@Context
	private HttpHeaders headers;
		
	@POST
	public Response addVotesForUser(CreateVoteRequest createVoteRequest) {		
		String apiKey = headers.getHeaderString("apikey");
		voteService.addVotesForUser(apiKey,createVoteRequest.getDecisionId(),createVoteRequest.getVotes());
		return Response.ok().build();
	}
	
	@GET
	public Response getVotesForUserByDecisionId(@QueryParam(value = "decisionId") Long decisionId ) {
		String apiKey = headers.getHeaderString("apikey");
		List<VoteDto> votes = voteService.getVotesForUserByDecisionId(apiKey, decisionId);
		VotesForUserByDecisionIdResponse response = new VotesForUserByDecisionIdResponse();
		response.setVotes(votes);
		response.setDecisionId(decisionId);
		
		return Response.ok(response).build();
	}
	
	@DELETE
	public Response cancelVotingForDecision(@QueryParam(value = "decisionId") Long decisionId) {
		String apiKey = headers.getHeaderString("apikey");	
		voteService.removeVotesForDecision(apiKey, decisionId);
		return Response.ok().build();		
	}
	

}
