package ddd.api.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import ddd.api.model.ValidationFailure;

@XmlRootElement
public class ErrorResponse {

	public List<ValidationFailure> errors = new ArrayList<ValidationFailure>();
	
	public List<ValidationFailure> getErrors() {
		return errors;
	}
	
	public void setErrors(List<ValidationFailure> errors) {
		this.errors = errors;
	}
}
