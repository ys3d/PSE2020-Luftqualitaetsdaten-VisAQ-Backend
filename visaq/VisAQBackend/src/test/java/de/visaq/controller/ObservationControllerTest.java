package de.visaq.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.junit.Test;

import de.visaq.controller.ObservationController.AreaWrapper;
import de.visaq.controller.ObservationController.TimeframedThingWrapper;
import de.visaq.controller.ObservationController.TopWrapper;
import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Observation;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.Thing;

/**
 * Tests {@link ObservationController}.
 */
public class ObservationControllerTest {
    private static final ObservationController CONTROLLER = new ObservationController();

    @Test
    public void testSingleObservationGet() {
        SingleOnlineLink<Observation> sol =
                new SingleOnlineLink<Observation>("/Observations?$top=1&$skip=1", true);
        sol.get(CONTROLLER);
        sol.get(CONTROLLER);
    }

    @Test
    public void testSingleObservationGetById() {
        assertNull(CONTROLLER.get("undefined"));
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVEOBSERVATION.id));
        assertNotNull(
                CONTROLLER.get(new IdWrapper(SensorthingsControllerTests.ALIVEOBSERVATION.id)));
    }

    @Test
    public void testMultiObservationGet() {
        MultiOnlineLink<Observation> mol =
                new MultiOnlineLink<Observation>("/Observations?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void testMultiObservationGetByDatastream() {
        assertFalse(CONTROLLER.getAll(SensorthingsControllerTests.ALIVEDATASTREAM).isEmpty());
    }

    @Test
    public void testMultiObservationGetByArea() {
        Square square = new Square(10, 11, 48, 49);
        Instant time = Instant.now();
        Duration range = Duration.ofMinutes(5);

        assertNotNull(CONTROLLER.getAll(square, time, range,
                SensorthingsControllerTests.ALIVEOBSERVEDPROPERTY));
        assertNotNull(CONTROLLER.getAll(new AreaWrapper(square, 10000, range,
                SensorthingsControllerTests.ALIVEOBSERVEDPROPERTY)));
    }

    @Test
    public void testMultiObservationGetByTimeframedThings() {
        ArrayList<Thing> things = new ArrayList<Thing>();
        things.add(SensorthingsControllerTests.ALIVETHING);
        Instant time = Instant.now();
        Duration range = Duration.ofHours(12);

        assertNotNull(CONTROLLER.getAll(things, time, range,
                SensorthingsControllerTests.ALIVEOBSERVEDPROPERTY));
        assertNotNull(CONTROLLER.getAll(new TimeframedThingWrapper(things, 10000, range,
                SensorthingsControllerTests.ALIVEOBSERVEDPROPERTY)));
    }

    @Test
    public void testMultiObservationsGetNewest() {
        assertNotNull(CONTROLLER.getAll(10, "saqn:ds:5aa3aa2"));
        assertEquals(10, CONTROLLER.getAll(10, "saqn:ds:5aa3aa2").size());
        assertNotNull(CONTROLLER.getAll(new TopWrapper(10, "saqn:ds:5aa3aa2")));
    }

    @Test
    public void singelBuildNullTest() {
        assertNull(CONTROLLER.singleBuild(null));
    }

    @Test
    public void areaWrapperTest() {
        AreaWrapper wrapper = new AreaWrapper();
        assertNull(wrapper.square);
        assertNull(wrapper.range);
        assertNull(wrapper.observedProperty);
        assertEquals(0, wrapper.millis);

        Square sq = new Square(0, 0, 1, 1);
        long millis = 100;
        Duration range = Duration.ZERO;
        ObservedProperty observedProperty = new ObservedProperty("id", "selfUrl", true,
                "description", "name", null, "definition", null);

        wrapper = new AreaWrapper(sq, millis, range, observedProperty);
        assertEquals(sq, wrapper.square);
        assertEquals(millis, wrapper.millis);
        assertEquals(range, wrapper.range);
        assertEquals(observedProperty, wrapper.observedProperty);
    }

    @Test
    public void timeframedThingWrapperTest() {
        TimeframedThingWrapper wrapper = new TimeframedThingWrapper();
        assertNull(wrapper.things);
        assertNull(wrapper.range);
        assertNull(wrapper.observedProperty);
        assertEquals(0, wrapper.millis);

        ArrayList<Thing> things = new ArrayList<Thing>();
        long millis = 100;
        Duration range = Duration.ZERO;
        ObservedProperty observedProperty = new ObservedProperty("id", "selfUrl", true,
                "description", "name", null, "definition", null);

        wrapper = new TimeframedThingWrapper(things, millis, range, observedProperty);
        assertEquals(things, wrapper.things);
        assertEquals(millis, wrapper.millis);
        assertEquals(range, wrapper.range);
        assertEquals(observedProperty, wrapper.observedProperty);
    }

    @Test
    public void topWrapperTest() {
        TopWrapper wrapper = new TopWrapper();
        assertEquals(0, wrapper.topNumber);
        assertNull(wrapper.datastreamId);

        int topNumber = 0;
        String datastreamId = "id";

        wrapper = new TopWrapper(topNumber, datastreamId);
        assertEquals(topNumber, wrapper.topNumber);
        assertEquals(datastreamId, wrapper.datastreamId);
    }
}
