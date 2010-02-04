/*
 * Copyright 2006-2008 the original author or authors.
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
package net.sourceforge.safr.core.integration.aspectj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import net.sourceforge.safr.core.annotation.SecureAction;
import net.sourceforge.safr.core.integration.support.TestBase;

import org.junit.Test;


/**
 * @author Martin Krasser
 */
public class SecureMethodTest extends TestBase {

    // -------------------------------------------------------------------
    //  method annotation (@Secure)
    // -------------------------------------------------------------------

    @Test
    public void testM10a() {
        domainObject.m10a();
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains(domainObject, SecureAction.EXECUTE));
    }
    
    @Test
    public void testM10b() {
        domainObject.m10b();
        assertEquals("wrong check history size", 0, checkHistory.size());
    }
    
    @Test
    public void testM10c() {
        domainObject.m10c();
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains(domainObject, SecureAction.EXECUTE));
    }
    
    @Test
    public void testM10d() {
        domainObject.m10d();
        assertEquals("wrong check history size", 1, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains(domainObject, SecureAction.CREATE));
    }
    
    @Test
    public void testM10e() {
        domainObject.m10e();
        assertEquals("wrong check history size", 2, checkHistory.size());
        assertTrue("wrong check history content", checkHistory.contains(domainObject, SecureAction.EXECUTE));
        assertTrue("wrong check history content", checkHistory.contains(domainObject, SecureAction.CREATE));
    }
    
}
