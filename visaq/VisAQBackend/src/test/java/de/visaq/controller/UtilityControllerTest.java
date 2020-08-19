package de.visaq.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import de.visaq.model.sensorthings.UnitOfMeasurement;

/**
 * Tests {@link UtilityController}.
 */
public class UtilityControllerTest {
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
        JSONObject json = new JSONObject(new JSONTokener(
                SensorthingsControllerTests.class.getResourceAsStream("/alive_datastream.json")));
        UnitOfMeasurement um =
                UtilityController.buildUnitOfMeasurement(json.getJSONObject("unitOfMeasurement"));
        assertNotNull(um);
        assertEquals(json.getJSONObject("unitOfMeasurement").getString("name"), um.name);
        assertEquals(json.getJSONObject("unitOfMeasurement").getString("definition"),
                um.definition);
        assertEquals(json.getJSONObject("unitOfMeasurement").getString("symbol"), um.symbol);
    }

    @Test
    public void buildTimeTest() {
        JSONObject json = new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                .getResourceAsStream("/alive_historicallocation.json")));
        Instant instant = UtilityController.buildTime(json, "time");
        assertNotNull(instant);
        assertEquals(Instant.parse(json.getString("time").split("/")[0]), instant);
    }

    @Test
    public void buildPropertiesTest() {
        JSONObject json = new JSONObject(new JSONTokener(
                SensorthingsControllerTests.class.getResourceAsStream("/alive_sensor.json")));
        Map<String, Object> map = UtilityController.buildProperties(json);

        assertEquals("edm80neph", map.get("shortname"));
    }

    @Test
    public void buildLocationPointTest() {
        JSONObject json = new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                .getResourceAsStream("/alive_featureofinterest.json")));
        Point2D.Double p = UtilityController.buildLocationPoint(json);
        assertEquals(10.897889d, p.x, 0.001);
        assertEquals(48.336028d, p.y, 0.001);

        json = new JSONObject(new JSONTokener(
                SensorthingsControllerTests.class.getResourceAsStream("/alive_location.json")));
        p = UtilityController.buildLocationPoint(json);
        assertEquals(10.897889d, p.x, 0.001);
        assertEquals(48.336028d, p.y, 0.001);

        json = new JSONObject(new JSONTokener(
                SensorthingsControllerTests.class.getResourceAsStream("/alive_observation.json")));
        p = UtilityController.buildLocationPoint(json);
        assertNull(p);
    }
}
