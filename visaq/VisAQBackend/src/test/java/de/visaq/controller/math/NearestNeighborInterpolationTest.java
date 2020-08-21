package de.visaq.controller.math;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import de.visaq.controller.ObservedPropertyController;
import de.visaq.controller.SensorthingsControllerTests;
import de.visaq.controller.math.Interpolation.InterpolationWrapper;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Tests {@link NearestNeighborInterpolation}.
 */
public class NearestNeighborInterpolationTest extends InterpolationTestSuite {

    @Test
    public void interpolateTest() {
        ObservedProperty op = new ObservedPropertyController()
                .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                        .getResourceAsStream("/alive_observedproperty.json"))));
        InterpolationWrapper w = new InterpolationWrapper(INTERPOLATIONSQUARE.getMinX(),
                INTERPOLATIONSQUARE.getMaxX(), INTERPOLATIONSQUARE.getMinY(),
                INTERPOLATIONSQUARE.getMaxY(), System.currentTimeMillis(), Duration.ofHours(1), op,
                0, 1000);
        assertTrue(0 < new NearestNeighborInterpolation().interpolate(w).length);
    }

}
