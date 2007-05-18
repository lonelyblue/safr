/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
