package de.visaq.model.sensorthings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ObservedProperty}.
 */
public class ObservedPropertyTest {
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
        ObservedProperty observedProperty = new ObservedProperty("id", "selfUrl", true,
                "description", "name", properties, "definition", null);
        assertTrue(observedProperty.hasProperties("integer"));
        assertFalse(observedProperty.hasProperties("missing"));
        assertTrue(observedProperty.hasProperties("text"));

    }

    @Test
    public void getpropertyTest() {
        ObservedProperty observedProperty = new ObservedProperty("id", "selfUrl", true,
                "description", "name", properties, "definition", null);
        assertEquals(-15, (int) observedProperty.getPropertyByKey("integer"));
        assertEquals(testString, observedProperty.getPropertyByKey("text"));
        assertEquals(null, observedProperty.getPropertyByKey("missing"));
    }
}
