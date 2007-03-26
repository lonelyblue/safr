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
package net.sourceforge.safr.core.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.integration.sample.DomainObjectA;
import net.sourceforge.safr.core.integration.sample.DomainObjectC;
import net.sourceforge.safr.core.integration.sample.Service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Martin Krasser
 */
public abstract class TestBase {

    protected static ManagerImpl manager;
    protected static CheckHistory history;
    protected static DomainObjectA domainObject;
    protected static Service service;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Context context = Context.getInstance();
        service = context.getSecureService();
        manager = context.getAccessManager();
        history = manager.getCheckHistory();
        domainObject = new DomainObjectC();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // nothing to tear down after class
    }

    @Before
    public void setUp() throws Exception {
        // nothing to set up
    }

    @After
    public void tearDown() throws Exception {
        history.clear();
    }

    protected static void checkReadHistory(Object... elements) {
        assertEquals("wrong check history size", elements.length, history.size());
        for (Object element : elements) {
            assertTrue("wrong check history content", history.contains(element, SecureAction.READ));
        }
    }
    
}
