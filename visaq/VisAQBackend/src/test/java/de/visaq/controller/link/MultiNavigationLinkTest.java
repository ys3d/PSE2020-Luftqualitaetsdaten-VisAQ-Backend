package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import de.visaq.controller.DatastreamController;
import de.visaq.controller.link.MultiNavigationLink.Builder;
import de.visaq.model.sensorthings.Datastream;

/**
 * Tests {@link MultiNavigationLink}.
 */
public class MultiNavigationLinkTest {
    @Test
    public void builderTest() {
        Builder<Datastream> builder = new Builder<Datastream>();
        assertNotNull(builder.build("Datastreams@iot.navigationLink", "Datastreams",
                new DatastreamController(),
                new JSONObject(new JSONTokener(MultiNavigationLinkTest.class
                        .getResourceAsStream("/alive_thing_expand_datastreams.json")))));
    }
}
