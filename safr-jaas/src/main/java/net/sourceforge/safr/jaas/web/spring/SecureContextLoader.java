package net.sourceforge.safr.jaas.web.spring;

import javax.servlet.ServletContext;

import net.sourceforge.safr.jaas.login.AuthenticationService;
import net.sourceforge.safr.jaas.login.AuthenticationServiceHolder;

import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Christian Ohr
 */
public class SecureContextLoader extends ContextLoader {

    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext sc)
            throws IllegalStateException, BeansException {
        WebApplicationContext context = super.initWebApplicationContext(sc);

        // identify authentication service and put into holder class
        String authenticationServiceName = sc.getInitParameter("authenticationServiceName");
        AuthenticationService service = (AuthenticationService) context
                .getBean(authenticationServiceName);
        AuthenticationServiceHolder.getInstance().setAuthenticationService(service);
        return context;
    }

}
