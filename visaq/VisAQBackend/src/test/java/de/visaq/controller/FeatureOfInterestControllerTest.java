package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.geom.Point2D;

import org.json.JSONObject;
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
        assertEquals(ALIVEFEATUREOFINTEREST, CONTROLLER.get(ALIVEFEATUREOFINTEREST.id));
        assertEquals(ALIVEFEATUREOFINTEREST,
                CONTROLLER.get(new IdWrapper(ALIVEFEATUREOFINTEREST.id)));
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
        Point2D.Double point = CONTROLLER.getLocationPoint(ALIVEFEATUREOFINTEREST);
        assertNotNull(point);
        assertEquals(UtilityController
                .buildLocationPoint(new JSONObject(ALIVEFEATUREOFINTEREST.features)), point);
    }

    @Test
    public void getAllTest() {
        assertFalse(CONTROLLER.getAll().isEmpty());
        assertNotNull(CONTROLLER.getAll());
    }

    @Test
    public void singleBuildEmptyTest() {
        assertNull(CONTROLLER.singleBuild(EMPTYARRAY));
        assertNull(CONTROLLER.singleBuild(ALIVEOBSERVATIONJSON));
    }

}
