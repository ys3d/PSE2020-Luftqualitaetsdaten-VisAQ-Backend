package de.visaq.model.sensorthings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}
