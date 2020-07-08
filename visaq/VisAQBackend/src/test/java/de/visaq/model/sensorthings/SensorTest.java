package de.visaq.model.sensorthings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
