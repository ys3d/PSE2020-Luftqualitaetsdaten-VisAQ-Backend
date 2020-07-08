package de.visaq.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Observation;

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
}
