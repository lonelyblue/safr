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
package net.sourceforge.safr.sample.hellosaf;

import java.util.List;

import net.sourceforge.safr.core.annotation.Filter;
import net.sourceforge.safr.core.annotation.Secure;
import net.sourceforge.safr.core.annotation.SecureAction;

/**
 * @author Martin Krasser
 */
public interface Service {

    @Filter DomainObject findDomainObject(long id);
    
    @Filter List<DomainObject> findDomainObjects(long... ids);

    void deleteDomainObject(@Secure(SecureAction.DELETE)DomainObject obj);

}
