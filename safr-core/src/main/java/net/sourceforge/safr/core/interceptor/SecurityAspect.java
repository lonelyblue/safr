/*
 * Copyright 2006-2007 InterComponentWare AG.
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
package net.sourceforge.safr.core.interceptor;

import net.sourceforge.safr.core.attribute.FilterAttribute;
import net.sourceforge.safr.core.attribute.SecureAttribute;
import net.sourceforge.safr.core.invocation.AspectJProceedingInvocation;
import net.sourceforge.safr.core.invocation.ProceedingInvocation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author Martin Krasser
 */
@Aspect
public class SecurityAspect extends InterceptorSupport {

    @SuppressWarnings("unused")
    @Pointcut("@target(net.sourceforge.safr.core.annotation.SecureObject)")
    private void secureObjectAnnotatedClass() {}
    
    @SuppressWarnings("unused")
    @Pointcut("execution(!private * *(..)) && @annotation(net.sourceforge.safr.core.annotation.Filter)")
    private void filterAnnotatedMethod() {} 
    
    @SuppressWarnings("unused")
    @Pointcut("execution(!private * *(..)) && @annotation(net.sourceforge.safr.core.annotation.Secure)")
    private void secureAnnotatedMethod() {}
    
    @SuppressWarnings("unused")
    @Pointcut("execution(!private * *(..)) && @annotation(net.sourceforge.safr.core.annotation.Inherit)")
    private void inheritAnnotatedMethod() {}
    
    @SuppressWarnings("unused")
    @Pointcut("secureObjectAnnotatedClass() && filterAnnotatedMethod()")
    private void filterAnnotatedDomainObjectMethod() {}
    
    @SuppressWarnings("unused")
    @Pointcut("secureObjectAnnotatedClass() && secureAnnotatedMethod()")
    private void secureAnnotatedDomainObjectMethod() {}
    
    @SuppressWarnings("unused")
    @Pointcut("secureObjectAnnotatedClass() && inheritAnnotatedMethod()")
    private void inheritAnnotatedDomainObjectMethod() {}
    
    @SuppressWarnings("unused")
    @Pointcut("secureAnnotatedDomainObjectMethod() || filterAnnotatedDomainObjectMethod() || inheritAnnotatedDomainObjectMethod()")
    private void securityAnnotatedDomainObjectMethod() {}

    @SuppressWarnings("unused")
    @Around("securityAnnotatedDomainObjectMethod()")
    public Object invocation(ProceedingJoinPoint pjp) throws Throwable {
        if (!isConfigured()) {
            return pjp.proceed();
        }
        
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        SecureAttribute msa = getMethodSecureAttribute(signature);
        SecureAttribute[] psa = getParameterSecureAttributes(signature);

        ProceedingInvocation pi = new AspectJProceedingInvocation(pjp);
        
        // pre-processing
        beforeProceed(msa, pi);
        checkMethodAction(msa, pi);
        checkParameterActions(psa, pi.getArguments());
        
        // around processing
        Object result = aroundProceed(msa, pi);
        
        // post-processing
        FilterAttribute mfa = getMethodFilterAttribute(signature);
        result = filterResult(mfa, pi.getMethod(), result);
        return afterProceed(msa, pi, result);
        
    }
    
    private FilterAttribute getMethodFilterAttribute(MethodSignature signature) {
        return getSecurityAttributeSource().getMethodFilterAttribute(signature.getMethod(), signature.getDeclaringType());
    }
    
    private SecureAttribute getMethodSecureAttribute(MethodSignature signature) {
        return getSecurityAttributeSource().getMethodSecureAttribute(signature.getMethod(), signature.getDeclaringType());
    }
 
    private SecureAttribute[] getParameterSecureAttributes(MethodSignature signature) {
        return getSecurityAttributeSource().getParameterSecureAttributes(signature.getMethod(), signature.getDeclaringType());
    }
    
}
