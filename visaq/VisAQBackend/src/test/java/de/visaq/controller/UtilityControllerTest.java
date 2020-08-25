package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Point2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;

/**
 * Tests {@link UtilityController}.
 */
public class UtilityControllerTest extends ResourceTest {
    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<UtilityController> constructor =
                UtilityController.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void buildUnitOfMeasurementTest() {
        assertNotNull(ALIVEUNITOFMEASUREMENT);
        assertEquals(ALIVEUNITOFMEASUREMENTJSON.getString("name"), ALIVEUNITOFMEASUREMENT.name);
        assertEquals(ALIVEUNITOFMEASUREMENTJSON.getString("definition"),
                ALIVEUNITOFMEASUREMENT.definition);
        assertEquals(ALIVEUNITOFMEASUREMENTJSON.getString("symbol"), ALIVEUNITOFMEASUREMENT.symbol);
    }

    @Test
    public void buildTimeTest() {
        Instant instant = UtilityController.buildTime(ALIVEHISTORICALLOCATIONJSON, "time");
        assertNotNull(instant);
        assertEquals(Instant.parse(ALIVEHISTORICALLOCATIONJSON.getString("time").split("/")[0]),
                instant);
    }

    @Test
    public void buildPropertiesTest() {
        Map<String, Object> map = UtilityController.buildProperties(ALIVESENSORJSON);
        assertEquals(ALIVESENSORJSON.getJSONObject("properties").getString("shortname"),
                map.get("shortname"));
    }

    @Test
    public void buildLocationPointTest() {
        Point2D.Double p = UtilityController.buildLocationPoint(ALIVEFEATUREOFINTERESTJSON);
        assertEquals(10.897889d, p.x, 0.001);
        assertEquals(48.336028d, p.y, 0.001);

        p = UtilityController.buildLocationPoint(ALIVELOCATIONJSON);
        assertEquals(10.897889d, p.x, 0.001);
        assertEquals(48.336028d, p.y, 0.001);

        p = UtilityController.buildLocationPoint(ALIVEOBSERVATIONJSON);
        assertNull(p);
    }
}
