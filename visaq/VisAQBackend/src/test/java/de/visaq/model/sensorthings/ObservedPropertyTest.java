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

    @Test
    public void initTest() {
        MultiNavigationLink<Datastream> dl = new MultiOnlineLink<Datastream>("url", false);
        ObservedProperty op = new ObservedProperty("id", "selfUrl", false, "description", "name",
                properties, "definition", dl);

        assertEquals("id", op.id);
        assertEquals("selfUrl", op.selfLink.url);
        assertEquals("name", op.name);
        assertEquals("description", op.description);
        assertEquals("definition", op.definition);
        assertEquals(dl, op.datastreamsLink);

        op = new ObservedProperty("id", "selfUrl", true, "description", "name", properties,
                "definition", dl);
        assertEquals(RestConstants.ENTRY_POINT + "selfUrl", op.selfLink.url);
    }
}
