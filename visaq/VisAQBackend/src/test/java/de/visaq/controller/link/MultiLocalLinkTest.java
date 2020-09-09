package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;
import de.visaq.model.sensorthings.TestSensorthing;

/**
 * Tests {@link MultiLocalLink}.
 */
public class MultiLocalLinkTest {

    @Test
    public void superInitTest() {
        MultiLocalLink<TestSensorthing> link =
                new MultiLocalLink<TestSensorthing>("url", false, null);
        assertEquals("url", link.url);
        link = new MultiLocalLink<TestSensorthing>("url", true, null);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    @Test
    public void cachingTest() {
        ArrayList<TestSensorthing> stA = new ArrayList<TestSensorthing>();
        stA.add(new TestSensorthing("thingID1", "url", false));
        stA.add(new TestSensorthing("thingID2", "url", false));
        MultiLocalLink<TestSensorthing> link =
                new MultiLocalLink<TestSensorthing>("url", false, stA);
        assertEquals(2, link.cachedSensorthing.size());
        assertEquals(new TestSensorthing("thingID1", "url", false), link.cachedSensorthing.get(0));
        assertEquals(new TestSensorthing("thingID2", "url", false), link.cachedSensorthing.get(1));
    }
}
