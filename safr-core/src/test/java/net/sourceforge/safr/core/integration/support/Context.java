/*
 * Copyright 2006-2007 the original author or authors.
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
package net.sourceforge.safr.core.integration.support;

import net.sourceforge.safr.core.integration.sample.Service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Martin Krasser
 */
public class Context {

    private static Context instance = new Context(new ClassPathXmlApplicationContext("context.xml"));
    
    private ApplicationContext context;
    
    private Context(ApplicationContext context) {
        this.context = context;
    }
    
    public static Context getInstance() {
        return instance;
    }
    
    public Service getSecureService() {
        return (Service)getBean("secureService");
    }

    public ManagerImpl getAccessManager() {
        return (ManagerImpl)getBean("accessManager");
    }

    private Object getBean(String name) {
        return context.getBean(name);
    }
    
}
