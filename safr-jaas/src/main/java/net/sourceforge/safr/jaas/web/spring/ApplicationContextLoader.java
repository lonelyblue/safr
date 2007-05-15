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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sourceforge.safr.jaas.login.AuthenticationService;
import net.sourceforge.safr.jaas.login.AuthenticationServiceHolder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Martin Krasser
 */
public class ApplicationContextLoader implements ServletContextListener {

    private static final String APPLICATION_CONTEXT_KEY = ApplicationContextLoader.class.getPackage().getName() + ".context";
    
    public static ApplicationContext getApplicationContext(ServletContext context) {
        return (ApplicationContext)context.getAttribute(APPLICATION_CONTEXT_KEY);
    }
    
    public void contextInitialized(ServletContextEvent event) {
        String contextLocation = event.getServletContext().getInitParameter("contextLocation");
        String authenticationServiceName = event.getServletContext().getInitParameter("authenticationServiceName");
    
        ApplicationContext context = new ClassPathXmlApplicationContext(contextLocation);
        AuthenticationService service = (AuthenticationService)context.getBean(authenticationServiceName);
        AuthenticationServiceHolder.getInstance().setAuthenticationService(service);
        event.getServletContext().setAttribute(APPLICATION_CONTEXT_KEY, context);
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

}
