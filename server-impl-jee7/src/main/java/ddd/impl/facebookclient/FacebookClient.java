package ddd.impl.facebookclient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class FacebookClient {

	public FacebookMeResponse getMe(String token) {
		Client client = ClientBuilder.newBuilder().build();

		WebTarget target = client.target("https://graph.facebook.com").path("me");

		target = target.queryParam("access_token", token);
		target = target.queryParam("", "");

		Response response = target.request(MediaType.APPLICATION_JSON).get();

		if (response.getStatus() == 200) {
			return response.readEntity(FacebookMeResponse.class);
		} else {
			throw buildException(response);
		}
	}

	private RuntimeException buildException(Response response) {
		int statusCode = response.getStatus();

		if (statusCode == 400) {
			throw new UnauthorizedException();
		} else {
			throw new RuntimeException();
		}
	}
}
