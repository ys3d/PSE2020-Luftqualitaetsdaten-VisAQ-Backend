package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;
import de.visaq.controller.ObservedPropertyController;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.TestSensorthing;

/**
 * Tests {@link MultiOnlineLink}.
 */
public class MultiOnlineLinkTest {

    @Test
    public void superInitTest() {
        MultiOnlineLink<TestSensorthing> link = new MultiOnlineLink<TestSensorthing>("url", false);
        assertEquals("url", link.url);
        link = new MultiOnlineLink<TestSensorthing>("url", true);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    @Test
    public void getTest() {
        MultiOnlineLink<ObservedProperty> link =
                new MultiOnlineLink<ObservedProperty>("/ObservedProperties", true);
        ArrayList<ObservedProperty> result = link.get(new ObservedPropertyController());
        assertNotNull(result);
        assertTrue(result.size() > 0);

        // Illegal URL
        link = new MultiOnlineLink<ObservedProperty>("/illegal", true);
        result = link.get(new ObservedPropertyController());
        assertNull(result);

    }
}
