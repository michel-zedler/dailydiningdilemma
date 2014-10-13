package ddd.impl.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

@Provider
public class RestCorsResponseFilter implements ContainerResponseFilter {

	@Inject
	private Logger logger;

	@Override
	public void filter( ContainerRequestContext requestCtx, ContainerResponseContext responseCtx ) throws IOException {
        logger.info( "Executing REST response filter" );
		
		MultivaluedMap<String, Object> headers = responseCtx.getHeaders();

		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Credentials", "true");
		headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE, PUT");
		headers.add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, apikey");
	}
}
