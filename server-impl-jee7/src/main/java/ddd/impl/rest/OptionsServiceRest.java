package ddd.impl.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ddd.api.request.CreateOptionRequest;
import ddd.api.request.CreateOptionsForVotingRequest;
import ddd.impl.constants.Roles;
import ddd.impl.model.OptionModel;
import ddd.impl.service.OptionsService;

@Path("/options")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.USER)
public class OptionsServiceRest {
	
	@Inject
	private OptionsService optionsService;
	
	@Inject
	private Validator validator;
	
	@Inject
	private ValidationHelper validationHelper;
	
	@POST
	public Response addOptionsToVoting(CreateOptionsForVotingRequest createOptionsForVotingRequest) {
		Set<ConstraintViolation<CreateOptionsForVotingRequest>> violations = validator.validate(createOptionsForVotingRequest);
		
		if (violations.isEmpty() == false) {
			return validationHelper.buildValidationFailureResponse(violations);
		}
		
		List<OptionModel> optionModels = new ArrayList<OptionModel>();
		
		for (CreateOptionRequest option : createOptionsForVotingRequest.getOptions()) {
			OptionModel optionModel = new OptionModel();
			optionModel.setName(option.getName());
			optionModel.setCoordinates(option.getCoordinates());
			optionModel.setDescription(option.getDescription());
			optionModel.setPhoneNumber(option.getPhoneNumber());
			optionModel.setUrl(option.getUrl());	
			optionModels.add(optionModel);
		}
		
		
		optionsService.saveOptionForVoting(createOptionsForVotingRequest.getVotingId(), optionModels);
		
		return Response.ok().build();
	}	
	
	@DELETE
	@Path("/{optionId}")
	public Response deleteOption(@PathParam(value="optionId") Long id) {
		optionsService.deleteOption(id);
		return Response.ok().build();
	}
	
	@GET
	public Response getOptionsForVoting(@QueryParam(value="votingId") Long votingId) {
		List<OptionModel> options = optionsService.getOptionsForVoting(votingId);
		return Response.ok(options).build();
	}
	

}
