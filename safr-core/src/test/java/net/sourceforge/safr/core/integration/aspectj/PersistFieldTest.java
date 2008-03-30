package net.sourceforge.safr.core.integration.aspectj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sourceforge.safr.core.integration.sample.DomainObjectD;
import net.sourceforge.safr.core.integration.support.TestBase;

import org.junit.BeforeClass;
import org.junit.Test;

public class PersistFieldTest extends TestBase {

    private static JAXBContext JAXB;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        JAXB = JAXBContext.newInstance("net.sourceforge.safr.core.integration.sample");
    }

    @Test
    public void testSetPropertyNonNull() throws Exception {
        DomainObjectD domainObjectD = new DomainObjectD();
        domainObjectD.setS("blah");
        String xml = toXml(domainObjectD);
        // XML contains pseudo-encrypted (reverted) value
        assertTrue(xml.contains("<s>halb</s>"));
        domainObjectD = fromXml(xml);
        // getter returns decrypted value
        assertEquals("blah", domainObjectD.getS());
    }
 
    private static String toXml(DomainObjectD d) throws Exception {
        Marshaller marshaller = JAXB.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(d, writer);
        return writer.getBuffer().toString();
        
    }

    private static DomainObjectD fromXml(String xml) throws Exception {
        Unmarshaller unmarshaller = JAXB.createUnmarshaller();
        return (DomainObjectD)unmarshaller.unmarshal(new StringReader(xml));
    }

}
