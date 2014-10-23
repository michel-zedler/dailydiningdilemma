package ddd.impl.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ddd.api.model.VotingDto;
import ddd.api.request.CreateVotingRequest;
import ddd.api.response.CreateVotingResponse;
import ddd.api.response.VotingStatusResponse;
import ddd.impl.constants.Roles;
import ddd.impl.criteria.VotingCriteria;
import ddd.impl.model.VotingModel;
import ddd.impl.service.VotingService;

@Singleton
@Path("/votings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.USER)
public class VotingServiceRest {

	@Inject
	private Validator validator;

	@Inject
	private VotingService votingService;

	@Inject
	private ValidationHelper validationHelper;
	
	@POST
	@Path("")
	public Response createVoting(CreateVotingRequest createVotingRequest) {
		Set<ConstraintViolation<CreateVotingRequest>> violations = validator.validate(createVotingRequest);

		if (violations.isEmpty() == false) {
			return validationHelper.buildValidationFailureResponse(violations);
		}

		VotingModel model = new VotingModel();
		model.setTitle(createVotingRequest.getTitle());
		model.setDescription(createVotingRequest.getDescription());
		model.setVotingCloseDate(createVotingRequest.getVotingCloseDate());
		
		try {
			votingService.createNew(model);

			CreateVotingResponse response = new CreateVotingResponse();
			response.setId(model.getId());

			return Response.ok().entity(response).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("")
	public Response find(@QueryParam("open") Boolean open) {
		VotingCriteria criteria = new VotingCriteria();

		criteria.setOpen(open);

		List<VotingModel> list = votingService.findByCriteria(criteria);

		List<VotingDto> result = map(list);

		return Response.ok().entity(result).build();
	}

	@GET
	@Path("/{votingId}")
	public Response getVotingStatusById(@PathParam("votingId") Long votingId) {
		VotingModel votingModel = votingService.findById(votingId);
		
		if (votingModel == null) {
			return Response.status(404).build();
		}
		
		VotingStatusResponse response = new VotingStatusResponse();
		response.setDetails(map(votingModel));
		response.setCurrentVoteDistribution(votingService.getVotingDistribution(votingId));
		response.setNumberOfParticipants(votingService.getNumberOfParticipants(votingId));
		
		return Response.ok(response).build();
	}

	private List<VotingDto> map(List<VotingModel> list) {
		List<VotingDto> result = new ArrayList<VotingDto>();

		for (VotingModel m : list) {
			result.add(map(m));
		}

		return result;
	}
	
	private VotingDto map(VotingModel m) {
		VotingDto dto = new VotingDto();
		dto.setId(m.getId());
		dto.setTitle(m.getTitle());
		dto.setDescription(m.getDescription());
		dto.setVotingCloseDate(m.getVotingCloseDate());
		dto.setActualCloseDate(m.getActualCloseDate());

		dto.setIsOpen(determineIsOpen(m));

		return dto;
	}

	private boolean determineIsOpen(VotingModel model) {
		if (model.getActualCloseDate() == null) {
			return true;
		}

		return false;
	}

}
