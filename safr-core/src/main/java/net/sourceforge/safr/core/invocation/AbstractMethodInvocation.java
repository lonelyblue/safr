/*
 * Copyright 2006-2008 InterComponentWare AG.
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
package net.sourceforge.safr.core.invocation;

import java.lang.reflect.Method;

/**
 * @author Martin Krasser
 */
public abstract class AbstractMethodInvocation implements MethodInvocation {

    private Method method;
    private Object target;
    private Object[] arguments;
    
    public AbstractMethodInvocation(Method method, Object target, Object[] arguments) {
        this.method = method;
        this.target = target;
        this.arguments = arguments;
    }

    public Method getMethod() {
        return method;
    }

    public Object getTarget() {
        return target;
    }

    public Object[] getArguments() {
        return arguments;
    }

}
