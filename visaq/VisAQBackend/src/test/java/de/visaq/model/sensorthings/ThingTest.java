package de.visaq.model.sensorthings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void getpropertyTest() {
        Thing thing = new Thing("id", "selfUrl", true, "description", "name", properties, null,
                null, null);
        assertEquals(-15, (int) thing.getPropertyByKey("integer"));
        assertEquals(testString, thing.getPropertyByKey("text"));
        assertEquals(null, thing.getPropertyByKey("missing"));
    }
}
