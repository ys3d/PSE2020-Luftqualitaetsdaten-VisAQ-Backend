package de.visaq.controller.math;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Tests {@link DefaultInterpolation}.
 */
public class DefaultInterpolationTest extends InterpolationTestSuite {
    private static final DefaultInterpolation CONTROLLER = new DefaultInterpolation();

    @Test
    public void testDefaultInterpolationInterpolateCoordinates() {
        assertNotNull(
                CONTROLLER.interpolateCoordinates(INTERPOLATIONSQUARE, INTERPOLATIONCOORDINATES));
    }

}
