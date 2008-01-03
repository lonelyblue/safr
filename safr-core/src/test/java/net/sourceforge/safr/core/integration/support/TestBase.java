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
package net.sourceforge.safr.core.integration.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.integration.sample.DomainObjectA;
import net.sourceforge.safr.core.integration.sample.DomainObjectC;
import net.sourceforge.safr.core.integration.sample.Service;
import net.sourceforge.safr.core.provider.AccessManager;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @author Martin Krasser
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public abstract class TestBase {

    @Autowired
    protected AccessManager manager;
    
    @Autowired
    protected Service service;
    
    protected CheckHistory history;

    protected DomainObjectA domainObject;
    
    public ManagerImpl getManagerImpl() {
        return (ManagerImpl)manager;
    }

    @Before
    public void setUp() throws Exception {
        history = getManagerImpl().getCheckHistory();
        domainObject = new DomainObjectC();
    }

    @After
    public void tearDown() throws Exception {
        history.clear();
    }

    protected void checkReadHistory(Object... elements) {
        assertEquals("wrong check history size", elements.length, history.size());
        for (Object element : elements) {
            assertTrue("wrong check history content", history.contains(element, SecureAction.READ));
        }
    }
    
}
