package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.RestConstants;
import de.visaq.controller.DatastreamController;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.TestSensorthing;

/**
 * Tests {@link SingleOnlineLink}.
 */
public class SingleOnlineLinkTest {

    @Test
    public void superInitTest() {
        SingleOnlineLink<TestSensorthing> link =
                new SingleOnlineLink<TestSensorthing>("url", false);
        assertEquals("url", link.url);
        link = new SingleOnlineLink<TestSensorthing>("url", true);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    @Test
    public void getTest() {
        SingleOnlineLink<Datastream> link =
                new SingleOnlineLink<Datastream>(ResourceTest.ALIVEDATASTREAM.selfUrl, false);
        assertEquals(ResourceTest.ALIVEDATASTREAM, link.get(new DatastreamController()));

        // Illegal URL
        link = new SingleOnlineLink<Datastream>("/illegal", true);
        assertNull(link.get(new DatastreamController()));

    }
}
