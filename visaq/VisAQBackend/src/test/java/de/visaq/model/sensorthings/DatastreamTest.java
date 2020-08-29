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
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.controller.link.SingleOnlineLink;

/**
 * Tests {@link Datastream}.
 */
public class DatastreamTest {
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
        Datastream datastream = new Datastream("id", "selfUrl", true, "name", "description",
                properties, null, null, null, null, null, null);
        assertTrue(datastream.hasProperties("integer"));
        assertFalse(datastream.hasProperties("missing"));
        assertTrue(datastream.hasProperties("text"));

    }

    @Test
    public void getpropertyTest() {
        Datastream datastream = new Datastream("id", "selfUrl", true, "name", "description",
                properties, null, null, null, null, null, null);
        assertEquals(-15, (int) datastream.getPropertyByKey("integer"));
        assertEquals(testString, datastream.getPropertyByKey("text"));
        assertEquals(null, datastream.getPropertyByKey("missing"));
    }

    @Test
    public void initTest() {
        SingleNavigationLink<Sensor> sl = new SingleOnlineLink<Sensor>("url", false);
        SingleNavigationLink<Thing> tl = new SingleOnlineLink<Thing>("url", false);
        MultiNavigationLink<Observation> ol = new MultiOnlineLink<Observation>("url", false);
        SingleNavigationLink<ObservedProperty> opl =
                new SingleOnlineLink<ObservedProperty>("url", false);
        UnitOfMeasurement um = new UnitOfMeasurement("name", "symbol", "definition");
        Datastream ds = new Datastream("id", "selfUrl", false, "name", "description", properties,
                "observationTypeLink", sl, tl, ol, um, opl);

        assertEquals("id", ds.id);
        assertEquals("selfUrl", ds.selfLink.url);
        assertEquals("name", ds.name);
        assertEquals("description", ds.description);
        assertEquals("observationTypeLink", ds.observationTypeLink);
        assertEquals(sl, ds.sensorLink);
        assertEquals(tl, ds.thingLink);
        assertEquals(ol, ds.observationsLink);
        assertEquals(um, ds.unitOfMeasurement);
        assertEquals(opl, ds.observedPropertyLink);

        ds = new Datastream("id", "selfUrl", true, "name", "description", properties,
                "observationTypeLink", sl, tl, ol, um, opl);
        assertEquals(RestConstants.ENTRY_POINT + "selfUrl", ds.selfLink.url);

    }

}
