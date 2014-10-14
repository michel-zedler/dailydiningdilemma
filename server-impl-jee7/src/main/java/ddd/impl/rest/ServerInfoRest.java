package ddd.impl.rest;

import java.util.ResourceBundle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ddd.api.response.ServerInfoResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ServerInfoRest {

	@Path("/server-info")
	@GET
	public ServerInfoResponse getServerInfo() {
		ResourceBundle bundle = ResourceBundle.getBundle("/versions");
		
		ServerInfoResponse response = new ServerInfoResponse();
		response.setVersion(bundle.getString("mvn.version"));
		response.setGitHash(bundle.getString("git.hash"));
		response.setBuildDateTime(bundle.getString("buildDateTime"));
		response.setBuildTimestamp(bundle.getString("buildTimestamp"));
		
		return response;
	}
}
