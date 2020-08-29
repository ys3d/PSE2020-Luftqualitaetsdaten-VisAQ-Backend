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
 * Tests {@link Sensor}.
 */
public class SensorTest {
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
        Sensor sensor = new Sensor("id", "selfUrl", true, "description", "name", properties, null);
        assertTrue(sensor.hasProperties("integer"));
        assertFalse(sensor.hasProperties("missing"));
        assertTrue(sensor.hasProperties("text"));

    }

    @Test
    public void getpropertyTest() {
        Sensor sensor = new Sensor("id", "selfUrl", true, "description", "name", properties, null);
        assertEquals(-15, (int) sensor.getPropertyByKey("integer"));
        assertEquals(testString, sensor.getPropertyByKey("text"));
        assertEquals(null, sensor.getPropertyByKey("missing"));
    }

    @Test
    public void initTest() {
        MultiNavigationLink<Datastream> dl = new MultiOnlineLink<Datastream>("url", false);
        Sensor se = new Sensor("id", "selfUrl", false, "description", "name", properties, dl);

        assertEquals("id", se.id);
        assertEquals("selfUrl", se.selfLink.url);
        assertEquals("name", se.name);
        assertEquals("description", se.description);
        assertEquals(dl, se.datastreamsLink);

        se = new Sensor("id", "selfUrl", true, "description", "name", properties, dl);
        assertEquals(RestConstants.ENTRY_POINT + "selfUrl", se.selfLink.url);
    }
}
