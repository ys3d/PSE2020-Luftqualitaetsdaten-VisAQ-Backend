package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;

/**
 * Tests {@link SingleLocalLink}.
 */
public class SingleLocalLinkTest {

    @Test
    public void superInitTest() {
        SingleLocalLink<TestSensorThing> link =
                new SingleLocalLink<TestSensorThing>("url", false, null);
        assertEquals("url", link.url);
        link = new SingleLocalLink<TestSensorThing>("url", true, null);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    @Test
    public void cachingTest() {
        TestSensorThing st = new TestSensorThing("thingID", "url", false);
        SingleLocalLink<TestSensorThing> link =
                new SingleLocalLink<TestSensorThing>("url", false, st);
        assertEquals(st, link.cachedSensorthing);
    }
}
