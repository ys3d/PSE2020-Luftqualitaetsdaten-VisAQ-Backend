package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.SensorthingController.IdWrapper;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.FeatureOfInterest;

/**
 * Tests {@link FeatureOfInterestController}.
 */
public class FeatureOfInterestControllerTest extends ResourceTest {
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
        assertNotNull(CONTROLLER.get(ALIVEFEATUREOFINTEREST.id));
        assertNotNull(CONTROLLER.get(new IdWrapper(ALIVEFEATUREOFINTEREST.id)));
    }

    @Test
    public void testMultiFeatureOfInterestGet() {
        MultiOnlineLink<FeatureOfInterest> mol =
                new MultiOnlineLink<FeatureOfInterest>("/FeaturesOfInterests?$top=2", true);
        mol.get(CONTROLLER);
        mol.get(CONTROLLER);
    }

    @Test
    public void getLocationPointTest() {
        assertNull(CONTROLLER.getLocationPoint(null));
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
