package de.visaq.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Thing;

/**
 * Tests {@link ThingController}.
 */
public class ThingControllerTest {
    private static final ThingController CONTROLLER = new ThingController();

    @Test
    public void testSingleThingGet() {
        SingleOnlineLink<Thing> sol =
                new SingleOnlineLink<Thing>("/Things?$top=1&$expand=Datastreams", true);
        sol.get(CONTROLLER);
        sol.get(CONTROLLER);
    }

    @Test
    public void testSingleThingGetById() {
        assertNull(CONTROLLER.get("undefined"));
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVETHING.id));
    }

    @Test
    public void testMultiThingGet() {
        MultiOnlineLink<Thing> mol = new MultiOnlineLink<Thing>("/Things?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }
}
