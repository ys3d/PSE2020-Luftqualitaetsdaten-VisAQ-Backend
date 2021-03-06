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
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Thing;

/**
 * Tests {@link ThingController}.
 */
public class ThingControllerTest extends ResourceTest {
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
        assertEquals(ALIVETHING, CONTROLLER.get(ALIVETHING.id));
        assertEquals(ALIVETHING, CONTROLLER.get(new IdWrapper(ALIVETHING.id)));
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
    public void singleBuildEmptyTest() {
        assertNull(CONTROLLER.singleBuild(EMPTYARRAY));
    }

    @Test
    public void getAllTest() {
        assertFalse(CONTROLLER.getAll().isEmpty());
    }
}
