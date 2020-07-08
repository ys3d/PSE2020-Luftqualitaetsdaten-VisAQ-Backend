package de.visaq.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Sensor;

/**
 * Tests {@link SensorController}.
 */
public class SensorControllerTest {
    private static final SensorController CONTROLLER = new SensorController();

    @Test
    public void testSingleSensorGet() {
        SingleOnlineLink<Sensor> sol = new SingleOnlineLink<Sensor>("/Sensors?$top=1", true);
        sol.get(CONTROLLER);
        sol.get(CONTROLLER);
    }

    @Test
    public void testSingleSensorGetById() {
        assertNull(CONTROLLER.get("undefined"));
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVESENSOR.id));
    }

    @Test
    public void testSingleSensorGetByDatastream() {
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVEDATASTREAM));
    }

    @Test
    public void testMultiSensorGetByThing() {
        assertFalse(CONTROLLER.getAll(SensorthingsControllerTests.ALIVETHING).isEmpty());
    }

    @Test
    public void testMultiSensorGet() {
        MultiOnlineLink<Sensor> mol = new MultiOnlineLink<Sensor>("/Sensors?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

}
