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
package net.sourceforge.safr.core.spring.config;

import net.sourceforge.safr.core.annotation.SecurityAnnotationSource;
import net.sourceforge.safr.core.filter.CopyFilterFactory;
import net.sourceforge.safr.core.filter.RemoveFilterFactory;
import net.sourceforge.safr.core.interceptor.SecurityAspect;
import net.sourceforge.safr.core.interceptor.SecurityInterceptor;
import net.sourceforge.safr.core.spring.advice.SecurityAttributeSourceAdvisor;

import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author Martin Krasser
 */
public class SecurityAnnotationDrivenBeanDefinitionParser extends AbstractBeanDefinitionParser {

    private static final String ACCESS_MANAGER_ATTRIBUTE = "access-manager";
    private static final String PROXY_TARGET_CLASS_ATTRIBUTE = "proxy-target-class";
    private static final String INTERCEPTOR_ORDER_ATTRIBUTE = "interceptor-order";
    private static final String SUPPORT_ASPECTJ_ATTRIBUTE = "support-aspectj"; 
    
    private static final String SECURITY_ATTRIBUTE_SOURCE_PROPERTY = "securityAttributeSource";
    private static final String ACCESS_MANAGER_PROPERTY = "accessManager";
    private static final String SECURITY_INTERCEPTOR_PROPERTY = "securityInterceptor";
    private static final String REMOVE_FILTER_FACTORY_PROPERTY = "removeFilterFactory";
    private static final String COPY_FILTER_FACTORY_PROPERTY = "copyFilterFactory";
    private static final String ORDER_PROPERTY = "order";
    
    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);

        String accessManagerName = element.getAttribute(ACCESS_MANAGER_ATTRIBUTE);
        String proxyTargetClass = element.getAttribute(PROXY_TARGET_CLASS_ATTRIBUTE);
        String interceptorOrder = element.getAttribute(INTERCEPTOR_ORDER_ATTRIBUTE);
        String useAspectJ = element.getAttribute(SUPPORT_ASPECTJ_ATTRIBUTE);
        
        if (Boolean.parseBoolean(proxyTargetClass)) {
            AopNamespaceUtils.forceAutoProxyCreatorToUseClassProxying(parserContext.getRegistry());
        }
        
        RootBeanDefinition removeFilterFactoryDefinition = new RootBeanDefinition(RemoveFilterFactory.class);
        removeFilterFactoryDefinition.setSource(parserContext.extractSource(element));
        removeFilterFactoryDefinition.getConstructorArgumentValues().addGenericArgumentValue(
                new RuntimeBeanReference(accessManagerName));

        RootBeanDefinition copyFilterFactoryDefinition = new RootBeanDefinition(CopyFilterFactory.class);
        copyFilterFactoryDefinition.setSource(parserContext.extractSource(element));
        copyFilterFactoryDefinition.getConstructorArgumentValues().addGenericArgumentValue(
                new RuntimeBeanReference(accessManagerName));
        
        RootBeanDefinition interceptorDefinition = new RootBeanDefinition(SecurityInterceptor.class);
        interceptorDefinition.setSource(parserContext.extractSource(element));
        interceptorDefinition.getPropertyValues().addPropertyValue(REMOVE_FILTER_FACTORY_PROPERTY, removeFilterFactoryDefinition);
        interceptorDefinition.getPropertyValues().addPropertyValue(COPY_FILTER_FACTORY_PROPERTY, copyFilterFactoryDefinition);
        interceptorDefinition.getPropertyValues().addPropertyValue(SECURITY_ATTRIBUTE_SOURCE_PROPERTY, 
                new RootBeanDefinition(SecurityAnnotationSource.class));
        interceptorDefinition.getPropertyValues().addPropertyValue(ACCESS_MANAGER_PROPERTY, 
                new RuntimeBeanReference(accessManagerName));
        
        RootBeanDefinition advisorDefinition = new RootBeanDefinition(SecurityAttributeSourceAdvisor.class);
        advisorDefinition.setSource(parserContext.extractSource(element));
        advisorDefinition.getPropertyValues().addPropertyValue(SECURITY_INTERCEPTOR_PROPERTY, interceptorDefinition);
        advisorDefinition.getPropertyValues().addPropertyValue(ORDER_PROPERTY, interceptorOrder);
        
        if (Boolean.parseBoolean(useAspectJ)) {
            RootBeanDefinition aspectDefinition = new RootBeanDefinition(SecurityAspect.class);
            aspectDefinition.setSource(parserContext.extractSource(element));
            aspectDefinition.setFactoryMethodName("aspectOf");
            aspectDefinition.getPropertyValues().addPropertyValue(REMOVE_FILTER_FACTORY_PROPERTY, removeFilterFactoryDefinition);
            aspectDefinition.getPropertyValues().addPropertyValue(COPY_FILTER_FACTORY_PROPERTY, copyFilterFactoryDefinition);
            aspectDefinition.getPropertyValues().addPropertyValue(SECURITY_ATTRIBUTE_SOURCE_PROPERTY, 
                    new RootBeanDefinition(SecurityAnnotationSource.class));
            aspectDefinition.getPropertyValues().addPropertyValue(ACCESS_MANAGER_PROPERTY, 
                    new RuntimeBeanReference(accessManagerName));
            parserContext.getRegistry().registerBeanDefinition(SecurityAspect.class.getName(), aspectDefinition);
        }
        
        return advisorDefinition;
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

}
