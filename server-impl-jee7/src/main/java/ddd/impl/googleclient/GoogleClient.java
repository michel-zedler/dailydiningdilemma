package ddd.impl.googleclient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ddd.impl.facebookclient.UnauthorizedException;

@ApplicationScoped
public class GoogleClient {

	public GoogleMeResponse getMe(String token) {
		Client client = ClientBuilder.newBuilder().build();
		
		WebTarget target = client.target("https://www.googleapis.com").path(
				"userinfo/v2/me");

		Response response = target.request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).get();

		if (response.getStatus() == 200) {
			return response.readEntity(GoogleMeResponse.class);
		} else {
			throw buildException(response);
		}
	}

	private RuntimeException buildException(Response response) {
		return new UnauthorizedException();
	}
}
