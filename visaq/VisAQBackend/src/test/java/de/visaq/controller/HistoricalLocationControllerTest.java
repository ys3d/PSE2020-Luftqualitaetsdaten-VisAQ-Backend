package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.HistoricalLocation;

/**
 * Tests {@link HistoricalLocationController}.
 */
public class HistoricalLocationControllerTest extends ResourceTest {
    private static final HistoricalLocationController CONTROLLER =
            new HistoricalLocationController();

    @Test
    public void testSingleHistoricalLocationGet() {
        SingleOnlineLink<HistoricalLocation> sol =
                new SingleOnlineLink<HistoricalLocation>("/HistoricalLocations?$top=1", true);
        sol.get(CONTROLLER);
        sol.get(CONTROLLER);
    }

    @Test
    public void testSingleHistoricalLocationGetById() {
        assertNull(CONTROLLER.get("undefined"));
        assertNotNull(CONTROLLER.get(ALIVEHISTORICALLOCATION.id));
        assertNotNull(CONTROLLER.get(new IdWrapper(ALIVEHISTORICALLOCATION.id)));
    }

    @Test
    public void testMultiHistoricalLocationGet() {
        MultiOnlineLink<HistoricalLocation> mol =
                new MultiOnlineLink<HistoricalLocation>("/HistoricalLocations?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void getAllTest() {
        assertFalse(CONTROLLER.getAll().isEmpty());
    }

    @Test
    public void singleBuildEmptyTest() {
        assertNull(CONTROLLER.singleBuild(EMPTYARRAY));
    }
}
