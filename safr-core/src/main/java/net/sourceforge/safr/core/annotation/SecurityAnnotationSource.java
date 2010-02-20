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
package net.sourceforge.safr.core.annotation;

import net.sourceforge.safr.core.annotation.field.FieldAnnotationCollector;
import net.sourceforge.safr.core.annotation.method.MethodAnnotationCollector;
import net.sourceforge.safr.core.attribute.SecurityAttributeSourceSupport;
import net.sourceforge.safr.core.attribute.field.FieldAttributeCollector;
import net.sourceforge.safr.core.attribute.method.MethodAttributeCollector;

/**
 * @author Martin Krasser
 */
public class SecurityAnnotationSource extends SecurityAttributeSourceSupport {

    @Override
    protected FieldAttributeCollector createFieldAttributeCollector() {
        return new FieldAnnotationCollector();
    }

    @Override
    protected MethodAttributeCollector createMethodAttributeCollector() {
        return new MethodAnnotationCollector();
    }

}
