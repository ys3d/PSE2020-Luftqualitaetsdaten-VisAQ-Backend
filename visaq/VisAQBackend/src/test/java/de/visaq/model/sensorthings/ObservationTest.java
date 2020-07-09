package de.visaq.model.sensorthings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

import de.visaq.RestConstants;
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.controller.link.SingleOnlineLink;

/**
 * Tests {@link Observation}.
 */
public class ObservationTest {
    @Test
    public void isOlderTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        Instant i2 = Instant.parse("2008-12-03T10:15:30.00Z");
        Observation o1 = new Observation("id", "selfUrl", false, i1, 0.d, null, null, null);
        Observation o2 = new Observation("id", "selfUrl", false, i2, 0.d, null, null, null);

        assertTrue(o1.isOlder(o2));
        assertFalse(o2.isOlder(o1));
        assertFalse(o2.isOlder(o2));
    }

    @Test
    public void isNewerTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        Instant i2 = Instant.parse("2008-12-03T10:15:30.00Z");
        Observation o1 = new Observation("id", "selfUrl", false, i1, 0.d, null, null, null);
        Observation o2 = new Observation("id", "selfUrl", false, i2, 0.d, null, null, null);

        assertFalse(o1.isNewer(o2));
        assertTrue(o2.isNewer(o1));
        assertFalse(o2.isNewer(o2));
    }

    @Test
    public void isEqualOldTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        Instant i2 = Instant.parse("2008-12-03T10:15:30.00Z");
        Observation o1 = new Observation("id", "selfUrl", false, i1, 0.d, null, null, null);
        Observation o2 = new Observation("id", "selfUrl", false, i2, 0.d, null, null, null);

        assertFalse(o1.isEqualOld(o2));
        assertFalse(o2.isEqualOld(o1));
        assertTrue(o2.isEqualOld(o2));
    }

    @Test
    public void getTimeStampTest() {
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        Observation o1 = new Observation("id", "selfUrl", false, i1, 0.d, null, null, null);

        assertEquals(i1, o1.getTimeStamp());
    }

    @Test
    public void initTest() {
        SingleNavigationLink<Datastream> dl = new SingleOnlineLink<Datastream>("url", false);
        SingleNavigationLink<FeatureOfInterest> fl =
                new SingleOnlineLink<FeatureOfInterest>("url", false);
        Instant i1 = Instant.parse("2007-12-03T10:15:30.00Z");
        Instant i2 = Instant.parse("2008-12-03T10:15:30.00Z");
        Observation ob = new Observation("id", "selfUrl", false, i1, 0.2d, i2, dl, fl);

        assertEquals("id", ob.id);
        assertEquals("selfUrl", ob.selfLink.url);
        assertEquals(i1, ob.phenomenonTime);
        assertEquals(i2, ob.resultTime);
        assertEquals(dl, ob.datastreamLink);
        assertEquals(fl, ob.featureOfInterestLink);

        ob = new Observation("id", "selfUrl", true, i1, 0.2d, i2, dl, fl);
        assertEquals(RestConstants.ENTRY_POINT + "selfUrl", ob.selfLink.url);
    }
}
