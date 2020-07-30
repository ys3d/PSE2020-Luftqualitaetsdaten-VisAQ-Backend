package de.visaq.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.Location;

/**
 * Tests {@link LocationController}.
 */
public class LocationControllerTest {
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
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVELOCATION.id));
        assertNotNull(CONTROLLER.get(new IdWrapper(SensorthingsControllerTests.ALIVELOCATION.id)));
    }

    @Test
    public void testMultiLocationGet() {
        MultiOnlineLink<Location> mol = new MultiOnlineLink<Location>("/Locations?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void singleBuildEmptyTest() {
        assertNull(CONTROLLER.singleBuild(SensorthingsControllerTests.EMPTYARRAY));
    }

    @Test
    public void getAllTest() {
        assertFalse(CONTROLLER.getAll().isEmpty());
    }
}
