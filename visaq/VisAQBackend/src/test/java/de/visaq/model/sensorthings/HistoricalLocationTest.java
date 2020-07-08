package de.visaq.model.sensorthings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

import de.visaq.RestConstants;
import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.controller.link.SingleOnlineLink;

/**
 * Tests {@link HistoricalLocation}.
 */
public class HistoricalLocationTest {
    @Test
    public void isOlderTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        Instant i2 = Instant.parse("2008-12-03T10:15:30.00Z");
        HistoricalLocation h1 = new HistoricalLocation("id", "selfUrl", false, i1, null, null);
        HistoricalLocation h2 = new HistoricalLocation("id", "selfUrl", false, i2, null, null);

        assertTrue(h1.isOlder(h2));
        assertFalse(h2.isOlder(h1));
        assertFalse(h2.isOlder(h2));
    }

    @Test
    public void isNewerTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        Instant i2 = Instant.parse("2008-12-03T10:15:30.00Z");
        HistoricalLocation h1 = new HistoricalLocation("id", "selfUrl", false, i1, null, null);
        HistoricalLocation h2 = new HistoricalLocation("id", "selfUrl", false, i2, null, null);

        assertFalse(h1.isNewer(h2));
        assertTrue(h2.isNewer(h1));
        assertFalse(h2.isNewer(h2));
    }

    @Test
    public void isEqualOldTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        Instant i2 = Instant.parse("2008-12-03T10:15:30.00Z");
        HistoricalLocation h1 = new HistoricalLocation("id", "selfUrl", false, i1, null, null);
        HistoricalLocation h2 = new HistoricalLocation("id", "selfUrl", false, i2, null, null);

        assertFalse(h1.isEqualOld(h2));
        assertFalse(h2.isEqualOld(h1));
        assertTrue(h2.isEqualOld(h2));
    }

    @Test
    public void getTimeStampTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        HistoricalLocation h1 = new HistoricalLocation("id", "selfUrl", false, i1, null, null);

        assertEquals(i1, h1.getTimeStamp());
    }

    @Test
    public void initTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        SingleNavigationLink<Thing> tl = new SingleOnlineLink<Thing>("url", false);
        MultiNavigationLink<Location> ll = new MultiOnlineLink<Location>("url", false);
        HistoricalLocation hl = new HistoricalLocation("id", "selfUrl", false, i1, tl, ll);

        assertEquals("id", hl.id);
        assertEquals("selfUrl", hl.selfLink.url);
        assertEquals(i1, hl.time);
        assertEquals(tl, hl.thingLink);
        assertEquals(ll, hl.locationsLink);

        hl = new HistoricalLocation("id", "selfUrl", true, i1, tl, ll);
        assertEquals(RestConstants.ENTRY_POINT + "selfUrl", hl.selfLink.url);
    }
}
