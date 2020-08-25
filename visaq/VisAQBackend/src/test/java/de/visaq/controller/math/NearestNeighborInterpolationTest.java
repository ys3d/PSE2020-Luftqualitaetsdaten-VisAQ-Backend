package de.visaq.controller.math;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.controller.math.Interpolation.InterpolationWrapper;

/**
 * Tests {@link NearestNeighborInterpolation}.
 */
public class NearestNeighborInterpolationTest extends ResourceTest {
    private static final NearestNeighborInterpolation CONTROLLER =
            new NearestNeighborInterpolation();

    @Test
    public void interpolateTest() {
        InterpolationWrapper w = new InterpolationWrapper(INTERPOLATIONSQUARE.getMinX(),
                INTERPOLATIONSQUARE.getMaxX(), INTERPOLATIONSQUARE.getMinY(),
                INTERPOLATIONSQUARE.getMaxY(), System.currentTimeMillis(), Duration.ofHours(1),
                ALIVEOBSERVEDPROPERTY, 0, 1000);
        assertTrue(0 < CONTROLLER.interpolate(w).length);
    }

}
