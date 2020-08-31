package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Sensor;

/**
 * Tests {@link SensorController}.
 */
public class SensorControllerTest extends ResourceTest {
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
        assertEquals(ALIVESENSOR, CONTROLLER.get(ALIVESENSOR.id));
        assertEquals(ALIVESENSOR, CONTROLLER.get(new IdWrapper(ALIVESENSOR.id)));
    }

    @Test
    public void testSingleSensorGetByDatastream() {
        assertNotNull(CONTROLLER.get(ALIVEDATASTREAM));
    }

    @Test
    public void testMultiSensorGetByThing() {
        assertFalse(CONTROLLER.getAll(ALIVETHING).isEmpty());
    }

    @Test
    public void testMultiSensorGet() {
        MultiOnlineLink<Sensor> mol = new MultiOnlineLink<Sensor>("/Sensors?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void singleBuildEmptyTest() {
        assertNull(CONTROLLER.singleBuild(EMPTYARRAY));
    }

    @Test
    public void getAllTest() {
        assertFalse(CONTROLLER.getAll().isEmpty());
    }

}
