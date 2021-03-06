/*
 * Copyright 2006-2010 the original author or authors.
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
package net.sourceforge.safr.core.spring.advice;

import net.sourceforge.safr.core.interceptor.SecurityInterceptor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author Martin Krasser
 */
public class SecurityAttributeSourceAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = -1723776190576121393L;
    
    private SecurityAttributeSourcePointcut pointcut;
    private SecurityInterceptor interceptor;

    public SecurityAttributeSourceAdvisor() {
        pointcut = new SecurityAttributeSourcePointcut();
    }
    
    public void setSecurityInterceptor(SecurityInterceptor interceptor) {
        pointcut.setSecurityAttributeSource(interceptor.getSecurityAttributeSource());
        this.interceptor = interceptor;
    }
    
    public Pointcut getPointcut() {
        return pointcut;
    }

    public Advice getAdvice() {
        return interceptor;
    }

}
