package de.visaq.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

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
    }

    @Test
    public void testMultiFeatureOfInterestGet() {
        MultiOnlineLink<FeatureOfInterest> mol =
                new MultiOnlineLink<FeatureOfInterest>("/FeaturesOfInterests?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

}
