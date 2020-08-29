package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Location;

/**
 * Tests {@link LocationController}.
 */
public class LocationControllerTest extends ResourceTest {
    private static final LocationController CONTROLLER = new LocationController();

    @Test
    public void testSingleLocationGet() {
        SingleOnlineLink<Location> sol = new SingleOnlineLink<Location>("/Locations?$top=1", true);
        sol.get(CONTROLLER);
        sol.get(CONTROLLER);
    }

    @Test
    public void testSingleLocationGetById() {
        assertNull(CONTROLLER.get("undefined"));
        assertEquals(ALIVELOCATION, CONTROLLER.get(ALIVELOCATION.id));
        assertEquals(ALIVELOCATION, CONTROLLER.get(new IdWrapper(ALIVELOCATION.id)));
    }

    @Test
    public void testMultiLocationGet() {
        MultiOnlineLink<Location> mol = new MultiOnlineLink<Location>("/Locations?$top=2", true);
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
