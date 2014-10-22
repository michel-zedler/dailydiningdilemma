package ddd.impl.filter;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import ddd.impl.security.DddPrincipal;

public class DddSecurityContext implements SecurityContext {

	private final DddPrincipal principle;

	public DddSecurityContext(DddPrincipal dddPrinciple) {
		this.principle = dddPrinciple;
	}

	@Override
	public Principal getUserPrincipal() {
		return principle;
	}

	@Override
	public boolean isUserInRole(String role) {
		return principle.getRoles().contains(role);
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public String getAuthenticationScheme() {
		return "apikey";
	}
	
}