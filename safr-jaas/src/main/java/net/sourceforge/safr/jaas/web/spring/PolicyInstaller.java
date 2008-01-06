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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.sourceforge.safr.jaas.login.AuthenticationService;
import net.sourceforge.safr.jaas.login.AuthenticationServiceHolder;
import net.sourceforge.safr.jaas.policy.InstancePolicy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Martin Krasser
 */
@Component
public class PolicyInstaller {

    private static final Log log = LogFactory.getLog(PolicyInstaller.class);
    
    @Autowired
    private InstancePolicy instancePolicy;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @PostConstruct
    public void install() {
        installInstancePolicy();
        AuthenticationServiceHolder.getInstance().setAuthenticationService(authenticationService);
    }
    
    @PreDestroy
    public void uninstall() {
        AuthenticationServiceHolder.getInstance().setAuthenticationService(null);
        uninstallInstancePolicy();
    }
    
    private void installInstancePolicy() {
        Policy current = Policy.getPolicy();
        if (current instanceof InstancePolicy) {
            log.info("instance policy already installed");
        } else {
            instancePolicy.setDefaultPolicy(current);
            Policy.setPolicy(instancePolicy);
            log.info("instance policy installed");
        }
    }
    
    private void uninstallInstancePolicy() {
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
