package ddd.api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValidationFailure {

	private String property;
	private String message;
	private Object invalidValue;

	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public Object getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
