package org.timo.depotseam.controller;

import javax.inject.Inject;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.model.SimpleUser;

public class LocalAuthenticator extends BaseAuthenticator implements
		Authenticator {

	@Inject
	private Credentials credentials;

	@Override
	public void authenticate() {
		String username = credentials.getUsername();
		if (username != null && !username.isEmpty()) {
			setStatus(AuthenticationStatus.SUCCESS);
			setUser(new SimpleUser(username));
		}
	}

}
