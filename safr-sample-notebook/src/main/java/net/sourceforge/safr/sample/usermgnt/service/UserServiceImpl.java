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
package net.sourceforge.safr.sample.usermgnt.service;

import java.security.AccessController;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;

import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.sample.usermgnt.domain.User;

/**
 * @author Martin Krasser
 */
public class UserServiceImpl implements UserService {

    private Map<String, User> users;
    
    public UserServiceImpl() {
        this.users = new HashMap<String, User>();
        createUser("root");
        createUser("user1");
        createUser("user2");
    }
    
    public User currentUser() {
        return findUser(currentUserPrincipal().getName());
    }
    
    public User findUser(String id) {
        return users.get(id);
    }

    public Collection<User> findUsers() {
        return Collections.unmodifiableCollection(users.values());
    }

    private void createUser(String id) {
        users.put(id, new User(id));
    }
    
    private static Principal currentUserPrincipal() {
        Subject s = Subject.getSubject(AccessController.getContext());
        return s.getPrincipals(UserPrincipal.class).iterator().next();
    }
    
}
