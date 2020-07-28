package de.visaq.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.HistoricalLocation;

/**
 * Tests {@link HistoricalLocationController}.
 */
public class HistoricalLocationControllerTest {
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
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVEHISTORICALLOCATION.id));
        assertNotNull(CONTROLLER
                .get(new IdWrapper(SensorthingsControllerTests.ALIVEHISTORICALLOCATION.id)));
    }

    @Test
    public void testMultiHistoricalLocationGet() {
        MultiOnlineLink<HistoricalLocation> mol =
                new MultiOnlineLink<HistoricalLocation>("/HistoricalLocations?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void singelBuildNullTest() {
        assertNull(CONTROLLER.singleBuild(null));
    }
}
