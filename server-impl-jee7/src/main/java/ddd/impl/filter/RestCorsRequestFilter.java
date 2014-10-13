package ddd.impl.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

@Provider
@PreMatching
public class RestCorsRequestFilter implements ContainerRequestFilter {

	@Inject
	private Logger logger;

	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException {
		logger.info("Executing REST request filter");

		// When HttpMethod comes as OPTIONS, just acknowledge that it accepts...
		if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
			logger.info("HTTP Method (OPTIONS) - Detected!");

			// Just send a OK signal back to the browser
			requestCtx.abortWith(Response.status(Response.Status.OK).build());
		}
	}
}
