package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import de.visaq.controller.ThingController;
import de.visaq.controller.link.SingleNavigationLink.Builder;
import de.visaq.model.sensorthings.Thing;

/**
 * Tests {@link SingleNavigationLink}.
 */
public class SingleNavigationLinkTest {

    @Test
    public void builderTest() {
        Builder<Thing> builder = new Builder<Thing>();
        assertNotNull(builder.build("Thing@iot.navigationLink", "Thing", new ThingController(),
                new JSONObject(new JSONTokener(SingleNavigationLinkTest.class
                        .getResourceAsStream("/alive_datastream_expand_thing.json")))));
    }
}
