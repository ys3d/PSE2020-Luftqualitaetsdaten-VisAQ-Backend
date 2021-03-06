package de.visaq.model.sensorthings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;
import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;

/**
 * Tests {@link Thing}.
 */
public class ThingTest {
    private Map<String, Object> properties;

    private final String testString = "Lorem ipsum dolor sit amet";
    private final int testInt = -15;

    @BeforeEach
    public void before() {
        properties = new HashMap<String, Object>();
        properties.put("integer", testInt);
        properties.put("text", testString);

    }

    @Test
    public void hasPropertyTest() {
        Thing thing = new Thing("id", "selfUrl", true, "description", "name", properties, null,
                null, null);
        assertTrue(thing.hasProperties("integer"));
        assertFalse(thing.hasProperties("missing"));
        assertTrue(thing.hasProperties("text"));

    }

    @Test
    public void getPropertyTest() {
        Thing thing = new Thing("id", "selfUrl", true, "description", "name", properties, null,
                null, null);
        assertEquals(-15, (int) thing.getPropertyByKey("integer"));
        assertEquals(testString, thing.getPropertyByKey("text"));
        assertEquals(null, thing.getPropertyByKey("missing"));
    }

    @Test
    public void initTest() {
        MultiNavigationLink<Datastream> dl = new MultiOnlineLink<Datastream>("url", false);
        MultiNavigationLink<HistoricalLocation> hl =
                new MultiOnlineLink<HistoricalLocation>("url", false);
        MultiNavigationLink<Location> ll = new MultiOnlineLink<Location>("url", false);
        Thing tg = new Thing("id", "selfUrl", false, "description", "name", properties, dl, hl, ll);

        assertEquals("id", tg.id);
        assertEquals("selfUrl", tg.selfLink.url);
        assertEquals("name", tg.name);
        assertEquals("description", tg.description);
        assertEquals(dl, tg.datastreamsLink);
        assertEquals(hl, tg.historicalLocationsLink);
        assertEquals(ll, tg.locationsLink);

        tg = new Thing("id", "selfUrl", true, "description", "name", properties, dl, hl, ll);
        assertEquals(RestConstants.ENTRY_POINT + "selfUrl", tg.selfLink.url);
    }
}
