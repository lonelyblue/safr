/*
 * Copyright 2007-2008 the original author or authors.
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

import java.security.Policy;

import javax.servlet.ServletContext;

import net.sourceforge.safr.jaas.login.AuthenticationService;
import net.sourceforge.safr.jaas.login.AuthenticationServiceHolder;
import net.sourceforge.safr.jaas.policy.InstancePolicy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Christian Ohr
 * @author Martin Krasser
 */
public class SecureContextLoader extends ContextLoader {

    private static final Log log = LogFactory.getLog(SecureContextLoader.class);
    
    @Override
    public WebApplicationContext initWebApplicationContext(ServletContext sc)
            throws IllegalStateException, BeansException {
        WebApplicationContext context = super.initWebApplicationContext(sc);

        // identify authentication service and put into holder class
        String authenticationServiceName = sc.getInitParameter("authenticationServiceName");
        AuthenticationService service = (AuthenticationService) context.getBean(authenticationServiceName);
        AuthenticationServiceHolder.getInstance().setAuthenticationService(service);
        
        // identify instance policy and set as global policy
        String instancePolicyName = sc.getInitParameter("instancePolicyName");
        InstancePolicy policy = (InstancePolicy) context.getBean(instancePolicyName);
        installInstancePolicy(sc, policy);
        
        return context;
    }

    @Override
    public void closeWebApplicationContext(ServletContext sc) {
        uninstallInstancePolicy(sc);
        super.closeWebApplicationContext(sc);
    }

    private static void installInstancePolicy(ServletContext sc, InstancePolicy ip) {
        Policy current = Policy.getPolicy();
        if (current instanceof InstancePolicy) {
            log.info("instance policy already installed");
        } else {
            ip.setDefaultPolicy(current);
            Policy.setPolicy(ip);
            log.info("instance policy installed");
        }
    }
    
    private static void uninstallInstancePolicy(ServletContext sc) {
        Policy current = Policy.getPolicy();
        if (current instanceof InstancePolicy) {
            InstancePolicy ip = (InstancePolicy)current;
            Policy.setPolicy(ip.getDefaultPolicy());
            log.info("instance policy uninstalled");
        } else {
            log.info("instance policy not installed");
        }
    }
    
}
