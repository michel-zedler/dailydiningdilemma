package ddd.impl.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
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

import ddd.api.model.DecisionDto;
import ddd.api.request.CreateDecisionRequest;
import ddd.api.response.CreateDecisionResponse;
import ddd.impl.constants.Roles;
import ddd.impl.criteria.DecisionCriteria;
import ddd.impl.model.DecisionModel;
import ddd.impl.service.DecisionService;

@Path("/decisions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.USER)
public class DecisionServiceRest {

	@Inject
	private Validator validator;

	@Inject
	private DecisionService decisionService;

	@Inject
	private ValidationHelper validationHelper;

	@POST
	@Path("")
	public Response createDecision(CreateDecisionRequest createDecisionRequest) {
		Set<ConstraintViolation<CreateDecisionRequest>> violations = validator.validate(createDecisionRequest);

		if (violations.isEmpty() == false) {
			return validationHelper.buildValidationFailureResponse(violations);
		}

		DecisionModel model = new DecisionModel();
		model.setTitle(createDecisionRequest.getTitle());
		model.setDescription(createDecisionRequest.getDescription());
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
	@Path("")
	public Response find(@QueryParam("open") Boolean open) {
		DecisionCriteria criteria = new DecisionCriteria();

		criteria.setOpen(open);

		List<DecisionModel> list = decisionService.findByCriteria(criteria);

		List<DecisionDto> result = map(list);

		return Response.ok().entity(result).build();
	}
	
	@GET
	@Path("/{decisionId}")
	public Response findDecisionById(@PathParam("decisionId") Long decisionId) {
		DecisionModel decisionModel = decisionService.findById(decisionId);

		return Response.ok(decisionModel).build();
	}
	
	private List<DecisionDto> map(List<DecisionModel> list) {
		List<DecisionDto> result = new ArrayList<DecisionDto>();

		for (DecisionModel m : list) {
			DecisionDto dto = new DecisionDto();
			dto.setId(m.getId());
			dto.setTitle(m.getTitle());
			dto.setDescription(m.getDescription());
			dto.setVotingCloseDate(m.getVotingCloseDate());
			dto.setActualCloseDate(m.getActualCloseDate());
			
			dto.setIsOpen(determineIsOpen(m));
			
			result.add(dto);
		}

		return result;
	}
	
	private boolean determineIsOpen(DecisionModel model) {
		if (model.getActualCloseDate() == null) {
			return true;
		}
		
		return false;
	}

}
