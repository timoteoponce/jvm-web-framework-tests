package org.timo.depotseam.controller.security;

import org.jboss.seam.faces.view.config.AccessDeniedView;
import org.jboss.seam.faces.view.config.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

@ViewConfig
public interface Pages {

    static enum Pages1 {


        @ViewPattern("/index.xhtml")
        @LoggedUser
        FRONT,       
        @ViewPattern("/scaffold/*")
        @LoggedUser
        ITEMS,
        
        @ViewPattern("/*")
        @AccessDeniedView("/login.xhtml")
        @LoginView("/login.xhtml")
        ALL;        

    }

}