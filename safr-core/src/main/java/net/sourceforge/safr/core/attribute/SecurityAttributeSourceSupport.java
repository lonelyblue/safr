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
package net.sourceforge.safr.core.attribute;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Martin Krasser
 */
public abstract class SecurityAttributeSourceSupport implements SecurityAttributeSource {

    private SecurityAttributeContainerCache cache;

    public SecurityAttributeSourceSupport() {
        this.cache = new SecurityAttributeContainerCache(); 
    }
    
    public FilterAttribute getMethodFilterAttribute(Method method, Class<?> targetClass) {
        return getSecurityAttributeContainer(method, targetClass).getMethodFilterAttribute();
    }

    public SecureAttribute getMethodSecureAttribute(Method method, Class<?> targetClass) {
        return getSecurityAttributeContainer(method, targetClass).getMethodSecureAttribute();
    }

    public SecureAttribute[] getParameterSecureAttributes(Method method, Class<?> targetClass) {
        return getSecurityAttributeContainer(method, targetClass).getParameterSecureAttributes();
    }
    
    public boolean isAnySecurityAttributeDefined(Method method, Class<?> targetClass) {
        return getSecurityAttributeContainer(method, targetClass).isAnySecurityAttributeDefined();
    }

    private SecurityAttributeContainer getSecurityAttributeContainer(Method method, Class<?> targetClass) {
        String key = generateKey(method, targetClass);
        SecurityAttributeContainer container = cache.get(key);
        if (container != null) {
            return container;
        }
        container = computeSecurityAttributes(method, targetClass);
        // Only calls to annotated methods are intercepted. Therefore, 
        // containers without attributes need not be added to the cache.
        if (container.isAnySecurityAttributeDefined()) {
            cache.put(key, container);
        }
        return container;
    }

    private SecurityAttributeContainer computeSecurityAttributes(Method method, Class<?> targetClass) {
        SecurityAttributeHierarchy hierarchy = new SecurityAttributeHierarchy(getMostSpecificMethod(method, targetClass));
        SecurityAttributeCollector collector = createSecurityAttributeCollector();
        hierarchy.accept(collector);
        return collector.getSecurityAttributeContainer();
    }
    
    protected abstract SecurityAttributeCollector createSecurityAttributeCollector();
    
    private static String generateKey(Method method, Class<?> targetClass) {
        return targetClass + "." + method;
    }

    private static Method getMostSpecificMethod(Method method, Class<?> targetClass) {
        if (method == null || targetClass == null) {
            return method;
        }
        try {
            String name = method.getName();
            Class<?>[] types = method.getParameterTypes();
            method = targetClass.getMethod(name, types);
        } catch (NoSuchMethodException ex) {
            // use original method
        }
        return method;
    }
    
    @SuppressWarnings("serial")
    private static final class SecurityAttributeContainerCache extends HashMap<String, SecurityAttributeContainer> {

        private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock r = rwl.readLock();
        private final Lock w = rwl.writeLock();
        
        @Override
        public SecurityAttributeContainer get(Object key) {
            r.lock();
            try {
                return super.get(key);
            } finally {
                r.unlock();
            }
        }

        @Override
        public SecurityAttributeContainer put(String key, SecurityAttributeContainer value) {
            w.lock();
            try {
                return super.put(key, value);
            } finally {
                w.unlock();
            }
        }
        
    }
    
}
