package ddd.api.request;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class LoginRequest {

	@NotNull
	private String token;
	
	@NotNull
	private String service;
	
	public String getService() {
		return service;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}
