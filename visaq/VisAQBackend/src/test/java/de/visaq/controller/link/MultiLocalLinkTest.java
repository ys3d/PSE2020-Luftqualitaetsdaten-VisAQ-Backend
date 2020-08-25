package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;

/**
 * Tests {@link MultiLocalLink}.
 */
public class MultiLocalLinkTest {

    @Test
    public void superInitTest() {
        MultiLocalLink<TestSensorThing> link =
                new MultiLocalLink<TestSensorThing>("url", false, null);
        assertEquals("url", link.url);
        link = new MultiLocalLink<TestSensorThing>("url", true, null);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    @Test
    public void cachingTest() {
        ArrayList<TestSensorThing> stA = new ArrayList<TestSensorThing>();
        stA.add(new TestSensorThing("thingID1", "url", false));
        stA.add(new TestSensorThing("thingID2", "url", false));
        MultiLocalLink<TestSensorThing> link =
                new MultiLocalLink<TestSensorThing>("url", false, stA);
        assertEquals(2, link.cachedSensorthing.size());
        assertEquals(new TestSensorThing("thingID1", "url", false), link.cachedSensorthing.get(0));
        assertEquals(new TestSensorThing("thingID2", "url", false), link.cachedSensorthing.get(1));
    }
}
