package de.visaq.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.FeatureOfInterest;

/**
 * Tests {@link FeatureOfInterestController}.
 */
public class FeatureOfInterestControllerTest {
    private static final FeatureOfInterestController CONTROLLER = new FeatureOfInterestController();

    @Test
    public void testSingleFeatureOfInterestGet() {
        SingleOnlineLink<FeatureOfInterest> sol =
                new SingleOnlineLink<FeatureOfInterest>("/FeaturesOfInterests?$top=1", true);
        sol.get(CONTROLLER);
        sol.get(CONTROLLER);
    }

    @Test
    public void testSingleFeatureOfInterestGetById() {
        assertNull(CONTROLLER.get("undefined"));
        assertNotNull(CONTROLLER.get(SensorthingsControllerTests.ALIVEFEATUREOFINTEREST.id));
        assertNotNull(CONTROLLER
                .get(new IdWrapper(SensorthingsControllerTests.ALIVEFEATUREOFINTEREST.id)));
    }

    @Test
    public void testMultiFeatureOfInterestGet() {
        MultiOnlineLink<FeatureOfInterest> mol =
                new MultiOnlineLink<FeatureOfInterest>("/FeaturesOfInterests?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void singelBuildNullTest() {
        assertNull(CONTROLLER.singleBuild(null));
    }

    @Test
    public void getLocationPointTest() {
        assertNull(CONTROLLER.getLocationPoint(null));
    }

    @Test
    public void getAllTest() {
        assertTrue(0 < (CONTROLLER.getAll().size()));
    }

}
