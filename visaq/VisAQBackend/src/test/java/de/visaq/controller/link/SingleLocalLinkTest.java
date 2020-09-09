package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;
import de.visaq.model.sensorthings.TestSensorthing;

/**
 * Tests {@link SingleLocalLink}.
 */
public class SingleLocalLinkTest {

    @Test
    public void superInitTest() {
        SingleLocalLink<TestSensorthing> link =
                new SingleLocalLink<TestSensorthing>("url", false, null);
        assertEquals("url", link.url);
        link = new SingleLocalLink<TestSensorthing>("url", true, null);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    @Test
    public void cachingTest() {
        TestSensorthing st = new TestSensorthing("thingID", "url", false);
        SingleLocalLink<TestSensorthing> link =
                new SingleLocalLink<TestSensorthing>("url", false, st);
        assertEquals(st, link.cachedSensorthing);
    }
}
