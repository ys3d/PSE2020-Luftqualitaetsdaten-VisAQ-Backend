package de.visaq.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.Square;
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
        assertNotNull(CONTROLLER.get(new IdWrapper(SensorthingsControllerTests.ALIVETHING.id)));
    }

    @Test
    public void testMultiThingGet() {
        MultiOnlineLink<Thing> mol = new MultiOnlineLink<Thing>("/Things?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void testMultiThingGetBySquare() {
        Square square = new Square(10, 11, 48, 49);
        assertNotNull(CONTROLLER.getAll(square));
    }

    @Test
    public void singleBuildNullTest() {
        assertNull(CONTROLLER.singleBuild(null));
    }

    @Test
    public void getAllTest() {
        assertTrue(0 < (CONTROLLER.getAll().size()));
    }
}
