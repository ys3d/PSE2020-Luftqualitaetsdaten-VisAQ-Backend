package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.visaq.VisAQ;

/**
 * Tests {@link WebUtilityController}.
 */
public class WebUtilityControllerTest {

    @Test
    public void testServerVersion() {
        assertEquals("Currently running on Version " + VisAQ.VERSION,
                new WebUtilityController().versionPage());
    }
}
