package ddd.impl.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.BooleanUtils;

import ddd.api.model.DecisionDto;
import ddd.api.request.CreateDecisionRequest;
import ddd.api.response.CreateDecisionResponse;
import ddd.impl.annotation.RequiresValidUser;
import ddd.impl.entity.DecisionCriteria;
import ddd.impl.model.DecisionModel;
import ddd.impl.service.DecisionService;

@Path("/decisions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DecisionServiceRest {

	@Inject
	private Validator validator;

	@Inject
	private DecisionService decisionService;

	@Inject
	private ValidationHelper validationHelper;

	@POST
	@Path("/create")
	@RequiresValidUser
	public Response createDecision(CreateDecisionRequest createDecisionRequest) {
		Set<ConstraintViolation<CreateDecisionRequest>> violations = validator.validate(createDecisionRequest);

		if (violations.isEmpty() == false) {
			return validationHelper.buildValidationFailureResponse(violations);
		}

		DecisionModel model = new DecisionModel();
		model.setTitle(createDecisionRequest.getTitle());
		model.setVotingOpenDate(createDecisionRequest.getVotingOpenDate());
		model.setVotingCloseDate(createDecisionRequest.getVotingCloseDate());

		try {
			decisionService.createNew(model);

			CreateDecisionResponse response = new CreateDecisionResponse();
			response.setId(model.getId());

			return Response.ok().entity(response).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}

	}

	@GET
	@Path("/find")
	@RequiresValidUser
	public Response find(@Context UriInfo uriInfo) {
		DecisionCriteria criteria = new DecisionCriteria();

		MultivaluedMap<String, String> parameters = uriInfo.getQueryParameters();
		if (parameters.containsKey("open")) {
			criteria.setOpen(BooleanUtils.toBoolean(parameters.getFirst("open")));
		}

		List<DecisionModel> list = decisionService.findByCriteria(criteria);

		List<DecisionDto> result = map(list);

		return Response.ok().entity(result).build();
	}

	private List<DecisionDto> map(List<DecisionModel> list) {
		List<DecisionDto> result = new ArrayList<DecisionDto>();

		for (DecisionModel m : list) {
			DecisionDto dto = new DecisionDto();
			dto.setId(m.getId());
			dto.setTitle(m.getTitle());
			dto.setVotingOpenDate(m.getVotingOpenDate());
			dto.setVotingCloseDate(m.getVotingCloseDate());

			result.add(dto);
		}

		return result;
	}

}