package de.visaq.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Datastream;

/**
 * Tests {@link DatastreamController}.
 */
public class DatastreamControllerTest {
    private static final DatastreamController CONTROLLER = new DatastreamController();

    @Test
    public void testSingleDatastreamGet() {
        SingleOnlineLink<Datastream> sol =
                new SingleOnlineLink<Datastream>("/Datastreams?$top=1", true);
        assertEquals(sol.get(CONTROLLER), sol.get(CONTROLLER));
    }

    @Test
    public void testSingleDatastreamGetById() {
        assertNull(CONTROLLER.get("undefined"));
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVEDATASTREAM.id));
    }

    @Test
    public void testSingleDatastreamGetByThingAndObservedProperty() {
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVETHING,
                SensorthingsControllerTests.ALIVEOBSERVEDPROPERTY));
    }

    @Test
    public void testSingleDatastreamGetBySensorAndObservedProperty() {
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVESENSOR,
                SensorthingsControllerTests.ALIVEOBSERVEDPROPERTY));
    }

    @Test
    public void testMultiDatastreamGetByThing() {
        assertFalse(CONTROLLER.getAll(SensorthingsControllerTests.ALIVETHING).isEmpty());
    }

    @Test
    public void testMultiDatastreamGetBySensor() {
        assertFalse(CONTROLLER.getAll(SensorthingsControllerTests.ALIVESENSOR).isEmpty());
    }

    @Test
    public void testMultiDatastreamGet() {
        MultiOnlineLink<Datastream> mol =
                new MultiOnlineLink<Datastream>("/Datastreams?$top=2", true);
        assertEquals(mol.get(CONTROLLER), mol.get(CONTROLLER));
    }
}
