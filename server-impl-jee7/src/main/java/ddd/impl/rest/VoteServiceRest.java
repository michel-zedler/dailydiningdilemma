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
import ddd.api.response.VotesForUserByVotingIdResponse;
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
		voteService.addVotesForUser(apiKey, createVoteRequest.getVotingId(), createVoteRequest.getVotes());
		return Response.ok().build();
	}
	
	@GET
	public Response getVotesForUserByVotingId(@QueryParam(value = "votingId") Long votingId ) {
		String apiKey = headers.getHeaderString("apikey");
		List<VoteDto> votes = voteService.getVotesForUserByVotingId(apiKey, votingId);
		VotesForUserByVotingIdResponse response = new VotesForUserByVotingIdResponse();
		response.setVotes(votes);
		response.setVotingId(votingId);
		
		return Response.ok(response).build();
	}
	
	@DELETE
	public Response cancelVotingForVoting(@QueryParam(value = "votingId") Long votingId) {
		String apiKey = headers.getHeaderString("apikey");	
		voteService.removeVotesForVoting(apiKey, votingId);
		return Response.ok().build();		
	}
	

}
