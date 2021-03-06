package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.DatastreamController.SensorAndObservedPropertyWrapper;
import de.visaq.controller.DatastreamController.ThingAndObservedPropertyWrapper;
import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.HistoricalLocation;
import de.visaq.model.sensorthings.Location;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.Sensor;
import de.visaq.model.sensorthings.Thing;

/**
 * Tests {@link DatastreamController}.
 */
public class DatastreamControllerTest extends ResourceTest {
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
        assertEquals(ALIVEDATASTREAM, CONTROLLER.get(ALIVEDATASTREAM.id));
        assertEquals(ALIVEDATASTREAM, CONTROLLER.get(new IdWrapper(ALIVEDATASTREAM.id)));
    }

    @Test
    public void testSingleDatastreamGetByThingAndObservedProperty() {
        assertNotNull(CONTROLLER.get(ALIVETHING, ALIVEOBSERVEDPROPERTY));
        assertNotNull(CONTROLLER
                .get(new SensorAndObservedPropertyWrapper(ALIVESENSOR, ALIVEOBSERVEDPROPERTY)));
    }

    @Test
    public void testSingleDatastreamGetBySensorAndObservedProperty() {
        assertNotNull(CONTROLLER.get(ALIVESENSOR, ALIVEOBSERVEDPROPERTY));
        assertNotNull(CONTROLLER
                .get(new ThingAndObservedPropertyWrapper(ALIVETHING, ALIVEOBSERVEDPROPERTY)));
    }

    @Test
    public void testMultiDatastreamGetByThing() {
        assertFalse(CONTROLLER.getAll(ALIVETHING).isEmpty());
    }

    @Test
    public void testMultiDatastreamGetBySensor() {
        assertFalse(CONTROLLER.getAll(ALIVESENSOR).isEmpty());
    }

    @Test
    public void testMultiDatastreamGet() {
        MultiOnlineLink<Datastream> mol =
                new MultiOnlineLink<Datastream>("/Datastreams?$top=2", true);
        assertEquals(mol.get(CONTROLLER), mol.get(CONTROLLER));
    }

    @Test
    public void thingAndObservedPropertyWrapperTest() {
        DatastreamController.ThingAndObservedPropertyWrapper wrapper =
                new ThingAndObservedPropertyWrapper();
        assertNull(wrapper.thing);
        assertNull(wrapper.observedProperty);

        MultiNavigationLink<Datastream> dl = new MultiOnlineLink<Datastream>("url", false);
        MultiNavigationLink<HistoricalLocation> hl =
                new MultiOnlineLink<HistoricalLocation>("url", false);
        MultiNavigationLink<Location> ll = new MultiOnlineLink<Location>("url", false);
        Thing tg = new Thing("id", "selfUrl", false, "description", "name", null, dl, hl, ll);

        MultiNavigationLink<Datastream> dl2 = new MultiOnlineLink<Datastream>("url", false);
        ObservedProperty op = new ObservedProperty("id", "selfUrl", false, "description", "name",
                null, "definition", dl2);

        wrapper = new ThingAndObservedPropertyWrapper(tg, op);
        assertEquals(tg, wrapper.thing);
        assertEquals(op, wrapper.observedProperty);
    }

    @Test
    public void sensorAndObservedPropertyWrapperTest() {
        DatastreamController.SensorAndObservedPropertyWrapper wrapper =
                new SensorAndObservedPropertyWrapper();
        assertNull(wrapper.sensor);
        assertNull(wrapper.observedProperty);

        MultiNavigationLink<Datastream> dl = new MultiOnlineLink<Datastream>("url", false);
        Sensor se = new Sensor("id", "selfUrl", false, "description", "name", null, dl);

        MultiNavigationLink<Datastream> dl2 = new MultiOnlineLink<Datastream>("url", false);
        ObservedProperty op = new ObservedProperty("id", "selfUrl", false, "description", "name",
                null, "definition", dl2);

        wrapper = new SensorAndObservedPropertyWrapper(se, op);

        assertEquals(se, wrapper.sensor);
        assertEquals(op, wrapper.observedProperty);

    }

    @Test
    public void singleBuildEmptyTest() {
        assertNull(CONTROLLER.singleBuild(EMPTYARRAY));
    }

}
