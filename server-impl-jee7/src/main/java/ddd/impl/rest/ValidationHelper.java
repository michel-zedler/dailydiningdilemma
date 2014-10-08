package ddd.impl.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;

import ddd.api.model.ValidationFailure;
import ddd.api.response.ErrorResponse;

@ApplicationScoped
public class ValidationHelper {

	public <T> Response buildValidationFailureResponse(Set<ConstraintViolation<T>> violations) {
		List<ValidationFailure> errors = map(violations);
		ErrorResponse response = new ErrorResponse();
		response.setErrors(errors);
		return buildResponse(response);
	}

	protected Response buildResponse(ErrorResponse errorResponse) {
		return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
	}

	public <T> List<ValidationFailure> map(Set<ConstraintViolation<T>> violations) {
		ArrayList<ValidationFailure> result = new ArrayList<ValidationFailure>();

		for (ConstraintViolation<T> violation : violations) {
			result.add(map(violation));
		}

		return result;
	}

	public <T> ValidationFailure map(ConstraintViolation<T> violation) {
		ValidationFailure error = new ValidationFailure();
		
		if (violation.getPropertyPath() != null) {
			error.setProperty(violation.getPropertyPath().toString());
		}
		
		error.setInvalidValue(violation.getInvalidValue());
		error.setMessage(violation.getMessage());
		return error;
	}
}
