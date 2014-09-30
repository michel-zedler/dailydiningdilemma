package ddd.api.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public interface Login {

	@POST
	@Path("/login")
	Response login(String service, String oAuthToken);
	
	@POST
	@Path("/logout")
	void logout();

	@POST
	@Path("/validate")
	boolean validate();
}
