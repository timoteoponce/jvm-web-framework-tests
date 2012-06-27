package org.timo.depotseam.controller.security;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

public class Restrictions {

	public @Secures @LoggedUser boolean checkLogged(Identity identity) {
		return identity.isLoggedIn();
	}
}
