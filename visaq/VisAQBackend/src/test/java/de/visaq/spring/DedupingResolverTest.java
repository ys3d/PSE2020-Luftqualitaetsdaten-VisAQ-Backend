package de.visaq.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

/**
 * Tests {@link DedumpingResolver}.
 */
public class DedupingResolverTest {
    @Test
    public void newForDeserializationTest() {
        ObjectIdResolver oidr = new DedupingResolver().newForDeserialization(null);
        assertTrue(oidr.getClass() == DedupingResolver.class);
    }

    @Test
    public void bindItemTest() {
        DedupingResolver dr = new DedupingResolver();
        IdKey idKey = new IdKey(DedupingResolver.class, null, "ob");
        assertNull(dr.resolveId(idKey));
        dr.bindItem(idKey, "ob");
        assertEquals("ob", dr.resolveId(idKey));
    }
}
