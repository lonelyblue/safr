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
package net.sourceforge.safr.jaas.policy;

import java.security.Principal;
import java.util.Collection;

import net.sourceforge.safr.jaas.permission.InstancePermission;

/**
 * @author Martin Krasser
 */
public interface PermissionManager {

    public Collection<InstancePermission> getPermissions(Principal principal);
    
    public void grantPermission(Principal principal, InstancePermission permission);

    public void revokePermission(Principal principal, InstancePermission permission);

    public PermissionManagementLog newPermissionManagementLog();

}
