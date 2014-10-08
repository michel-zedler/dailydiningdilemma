package ddd.api.response;

import javax.validation.constraints.NotNull;


public class LogoutRequest {

	@NotNull
	private String apiKey;
	
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
