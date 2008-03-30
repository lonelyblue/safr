package net.sourceforge.safr.core.integration.aspectj;

import static net.sourceforge.safr.core.integration.support.Crypto.Operation.DECRYPT;
import static net.sourceforge.safr.core.integration.support.Crypto.Operation.ENCRYPT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sourceforge.safr.core.integration.sample.DomainObjectD;
import net.sourceforge.safr.core.integration.sample.DomainObjectE;
import net.sourceforge.safr.core.integration.support.Crypto;
import net.sourceforge.safr.core.integration.support.TestBase;

import org.junit.Test;

public class EncryptFieldTest extends TestBase {
    
    @Test
    public void testSetPropertyNonNull() {
        DomainObjectD d = new DomainObjectD();
        DomainObjectE e = new DomainObjectE();
        d.setS("blah");
        assertEquals(1, cryptoHistory.size());
        assertTrue(cryptoHistory.contains(new Crypto(ENCRYPT, "blah", d)));
        d.setS("blub");
        assertEquals(2, cryptoHistory.size());
        assertTrue(cryptoHistory.contains(new Crypto(ENCRYPT, "blub", d)));
        e.setT("oink");
        assertTrue(cryptoHistory.contains(new Crypto(ENCRYPT, "oink", e)));
    }
    
    @Test
    public void testGetPropertyNonNull() {
        DomainObjectD obj = new DomainObjectD("blah");
        assertEquals(1, cryptoHistory.size());
        assertTrue(cryptoHistory.contains(new Crypto(ENCRYPT, "blah", obj)));
        assertEquals("blah", obj.getS());
        assertEquals(2, cryptoHistory.size());
        assertTrue(cryptoHistory.contains(new Crypto(DECRYPT, "halb", obj)));
    }
    
    @Test
    public void testAccessField() throws Exception {
        DomainObjectD obj = new DomainObjectD("blah");
        assertEquals(1, cryptoHistory.size());
        assertEquals("halb", obj.getSReflective());
        assertEquals(1, cryptoHistory.size());
        obj.setSReflective("blub");
        assertEquals(1, cryptoHistory.size());
        assertEquals("blub", obj.getSReflective());
        assertEquals(1, cryptoHistory.size());
    }
    
}
