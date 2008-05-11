/*
 * Copyright 2007-2008 the original author or authors.
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
package net.sourceforge.safr.sample.permission.service;

import static org.junit.Assert.assertEquals;
import net.sourceforge.safr.sample.notebook.domain.Entry;
import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.provider.SampleAccessManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
public class NotebookTest {

    private Entry entry;
    
    private Notebook notebook;
    
    @Autowired
    private SampleAccessManager accessManager;
    
    @Before
    public void setUp() throws Exception {
        accessManager.setEnabled(false);
        entry = new Entry(null);
        notebook = new Notebook(null);
        notebook.addEntry(entry);
    }

    @After
    public void tearDown() throws Exception {
        accessManager.setEnabled(true);
    }

    @Test
    public void testRemoveEntryEntry() {
        assertEquals(1, notebook.getEntries().size());
        notebook.removeEntry(new Entry("fake", ""));
        assertEquals(1, notebook.getEntries().size());
        notebook.removeEntry(new Entry(entry.getId(), ""));
        assertEquals(0, notebook.getEntries().size());
    }

    @Test
    public void testRemoveEntryString() {
        assertEquals(1, notebook.getEntries().size());
        notebook.removeEntry("fake");
        assertEquals(1, notebook.getEntries().size());
        notebook.removeEntry(entry.getId());
        assertEquals(0, notebook.getEntries().size());
    }

}
