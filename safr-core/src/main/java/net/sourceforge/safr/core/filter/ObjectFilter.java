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
package net.sourceforge.safr.core.filter;

import java.security.AccessControlException;

import net.sourceforge.safr.core.provider.AccessManager;

/**
 * @author Martin Krasser
 */
class ObjectFilter implements ResultFilter {

    private AccessManager accessManager;
    
    private boolean nullifyResult;
    
    public ObjectFilter(AccessManager accessManager) {
        this(accessManager, true);
    }

    public ObjectFilter(AccessManager accessManager, boolean nullifyResult) {
        this.accessManager = accessManager;
        this.nullifyResult = nullifyResult;
    }

    public AccessManager getAccessManager() {
        return accessManager;
    }
    
    public Object apply(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            getAccessManager().checkRead(obj);
            return obj;
        } catch (AccessControlException e) {
            if (nullifyResult) {
                return null;
            } else {
                throw e;
            }
        }
    }

}
