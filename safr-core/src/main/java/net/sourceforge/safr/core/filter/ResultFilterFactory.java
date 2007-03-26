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
package net.sourceforge.safr.core.filter;

import java.lang.reflect.Method;
import java.util.Collection;

import net.sourceforge.safr.core.provider.AccessManager;

/**
 * @author Martin Krasser
 */
public abstract class ResultFilterFactory {

    private ResultFilter objectFilter;
    private ResultFilter arrayFilter;
    
    private AccessManager accessManager;
    
    public ResultFilterFactory(AccessManager accessManager) {
        this.accessManager = accessManager;
        this.objectFilter = new ObjectFilter(accessManager);
        this.arrayFilter = new ArrayFilter(accessManager);
    }

    public AccessManager getAccessManager() {
        return accessManager;
    }

    public ResultFilter getResultFilter(Method method, Class<? extends Collection> clazz) {
        if (method.getReturnType().isArray()) {
            return arrayFilter;
        } else if (Collection.class.isAssignableFrom(method.getReturnType())) {
            return doGetResultFilter(method, clazz);
        } else {
            return objectFilter;
        }
    }
    
    protected abstract ResultFilter doGetResultFilter(Method method, Class<? extends Collection> clazz);
    
}
