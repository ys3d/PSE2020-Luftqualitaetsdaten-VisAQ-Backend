package de.visaq.controller.math;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.visaq.controller.SensorthingsControllerTests;

/**
 * Tests {@link DefaultInterpolation}.
 */
public class DefaultInterpolationTest {
    private static final DefaultInterpolation CONTROLLER = new DefaultInterpolation();

    @Test
    public void testDefaultInterpolationInterpolateCoordinates() {
        assertNotNull(
                CONTROLLER.interpolateCoordinates(SensorthingsControllerTests.INTERPOLATIONSQUARE,
                        SensorthingsControllerTests.INTERPOLATIONCOORDINATES));
    }

}
