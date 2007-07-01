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

/**
 * @author Martin Krasser
 */
class SecurityAttributeHierarchy {

    private Method leaf;
    private String name;
    private Class<?>[] types;
    
    private boolean done;
    
    public SecurityAttributeHierarchy(Method leaf) {
        this.leaf = leaf;
        this.name = leaf.getName();
        this.types = leaf.getParameterTypes();
        this.done = false;
    }
    
    public void accept(SecurityAttributeCollector collector) {
        accept(leaf.getDeclaringClass(), collector);
    }

    private void accept(Class<?> clazz, SecurityAttributeCollector collector) {
        if (done || clazz == null) {
            return;
        }
        try {
            done = collector.visit(clazz.getDeclaredMethod(name, types));
        } catch (NoSuchMethodException e) {
            // this class or interface doesn't declare the method                 
        }
        if (!clazz.isInterface()) {
            accept(clazz.getSuperclass(), collector);
        }
        for (Class<?> elem : clazz.getInterfaces()) {
            accept(elem, collector);
        }
        
    }
}
