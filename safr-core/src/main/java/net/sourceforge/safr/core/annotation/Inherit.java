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
package net.sourceforge.safr.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is a hint to the AspectJ compiler that security annotations
 * shall be inherited from superclasses and interfaces. This annotation is only
 * necessary for classes processed by the AspectJ compiler. For beans managed by
 * a Spring application context, annotation inheritance is the default
 * behaviour. This annotation has no effect if other (method-level) security
 * annotations are defined on the same method. This annotation can also be used
 * to work around a current shortcoming of the AspectJ pointcut language
 * (version 1.5). This language currently doesn't support pointcut expressions
 * that address parameter-level annotations. Add this annotation to methods that
 * define security annotations on parameter-level only, otherwise, the security
 * annotations won't be recognized. As before, this is only necessary for
 * classes processed by the AspectJ compiler, not for Spring managed beans.
 * 
 * @author Martin Krasser
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD)
public @interface Inherit {

}
