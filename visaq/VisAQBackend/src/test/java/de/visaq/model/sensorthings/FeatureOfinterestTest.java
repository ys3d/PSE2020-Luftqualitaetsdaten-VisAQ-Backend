package de.visaq.model.sensorthings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;
import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;

/**
 * Tests {@link FeatureOfInterest}.
 */
public class FeatureOfinterestTest {
    @Test
    public void initTest() {
        HashMap<String, Object> features = new HashMap<String, Object>();
        features.put("integer", 4);
        features.put("text", "testString");

        MultiNavigationLink<Observation> ol = new MultiOnlineLink<Observation>("url", false);
        FeatureOfInterest fi =
                new FeatureOfInterest("id", "selfUrl", false, "description", "name", ol, features);

        assertEquals("id", fi.id);
        assertEquals("selfUrl", fi.selfLink.url);
        assertEquals("name", fi.name);
        assertEquals("description", fi.description);
        assertEquals(ol, fi.observationsLink);
        assertEquals(features, fi.features);

        fi = new FeatureOfInterest("id", "selfUrl", true, "description", "name", ol, features);
        assertEquals(RestConstants.ENTRY_POINT + "selfUrl", fi.selfLink.url);
    }
}
