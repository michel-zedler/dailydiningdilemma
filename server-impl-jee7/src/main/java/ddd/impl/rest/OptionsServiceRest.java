package ddd.impl.rest;

import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ddd.api.request.CreateOptionRequest;
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
	public Response addOptionsToDecision(CreateOptionRequest createOptionRequest) {
		Set<ConstraintViolation<CreateOptionRequest>> violations = validator.validate(createOptionRequest);
		
		if (violations.isEmpty() == false) {
			return validationHelper.buildValidationFailureResponse(violations);
		}
		
		OptionModel optionModel = new OptionModel();
		optionModel.setName(createOptionRequest.getName());
		optionModel.setCoordinates(createOptionRequest.getCoordinates());
		optionModel.setDescription(createOptionRequest.getDescription());
		optionModel.setPhoneNumber(createOptionRequest.getPhoneNumber());
		optionModel.setUrl(createOptionRequest.getUrl());
		
		optionsService.saveOptionForDecision(createOptionRequest.getDecisionId(), optionModel);
		
		return Response.ok().build();
	}

}
