package de.visaq.controller.math;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;

/**
 * Tests {@link DefaultInterpolation}.
 */
public class DefaultInterpolationTest extends ResourceTest {
    private static final DefaultInterpolation CONTROLLER = new DefaultInterpolation();

    @Test
    public void testDefaultInterpolationInterpolateCoordinates() {
        assertNotNull(
                CONTROLLER.interpolateCoordinates(INTERPOLATIONSQUARE, INTERPOLATIONCOORDINATES));
    }

}
