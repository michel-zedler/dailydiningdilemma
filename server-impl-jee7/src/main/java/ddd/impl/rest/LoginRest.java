package ddd.impl.rest;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import ddd.api.model.LoginResult;
import ddd.api.rest.Login;
import ddd.impl.auth.FacebookAuthHandler;
import ddd.impl.entity.UserEntity;

@Path("/")
public class LoginRest implements Login {

	@Inject
	Logger logger;

	@Inject
	FacebookAuthHandler facebookAuthHandler;

	@Override
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Path("/login")
	public Response login(@FormParam("service") String service,
			@FormParam("token") String token) {
		if (token == null || token.equals("")) {
			return Response.serverError().build();
		}

		logger.info(token);

		LoginResult result = new LoginResult();
		try {
			UserEntity entity = facebookAuthHandler.login(token);
			result.setApiKey(entity.getApiKey());
			result.setDisplayName(entity.getDisplayName());
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMessage("Error. See Server logs.");
		}

		return Response.ok(result).type(MediaType.APPLICATION_JSON_TYPE)
				.build();

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
