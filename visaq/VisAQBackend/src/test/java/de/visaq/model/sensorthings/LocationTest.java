package de.visaq.model.sensorthings;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Test;

import de.visaq.RestConstants;
import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;

/**
 * Tests {@link Location}.
 */
public class LocationTest {
    @Test
    public void initTest() {
        Point p = new Point(3, 4);
        MultiNavigationLink<HistoricalLocation> hl =
                new MultiOnlineLink<HistoricalLocation>("url", false);
        MultiNavigationLink<Thing> tl = new MultiOnlineLink<Thing>("url", false);
        Location lo = new Location("id", "selfUrl", false, "name", "description", p, hl, tl);

        assertEquals("id", lo.id);
        assertEquals("selfUrl", lo.selfLink.url);
        assertEquals("name", lo.name);
        assertEquals("description", lo.description);
        assertEquals(p, lo.location);
        assertEquals(hl, lo.historicalLocationsLink);
        assertEquals(tl, lo.thingsLink);

        lo = new Location("id", "selfUrl", true, "name", "description", p, hl, tl);
        assertEquals(RestConstants.ENTRY_POINT + "selfUrl", lo.selfLink.url);
    }
}
