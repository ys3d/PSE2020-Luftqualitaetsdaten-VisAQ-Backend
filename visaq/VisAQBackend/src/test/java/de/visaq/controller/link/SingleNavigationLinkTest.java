package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;
import de.visaq.controller.SensorthingController;
import de.visaq.controller.ThingController;
import de.visaq.controller.link.SingleNavigationLink.Builder;
import de.visaq.model.sensorthings.Sensorthing;
import de.visaq.model.sensorthings.Thing;

/**
 * Tests {@link SingleNavigationLink}.
 */
public class SingleNavigationLinkTest {
    private static final JSONObject ALIVEDATASTREAMEXPANDTHING;

    static {
        ALIVEDATASTREAMEXPANDTHING = new JSONObject(new JSONTokener(SingleNavigationLinkTest.class
                .getResourceAsStream("/alive_datastream_expand_thing.json")));
    }

    @Test
    public void builderLocalLinkTest() {
        Builder<Thing> builder = new Builder<Thing>();
        SingleNavigationLink<Thing> link = builder.build("Thing@iot.navigationLink", "Thing",
                new ThingController(), ALIVEDATASTREAMEXPANDTHING);
        assertNotNull(link);
        assertEquals(SingleLocalLink.class, link.getClass());
        assertEquals(ALIVEDATASTREAMEXPANDTHING.getString("Thing@iot.navigationLink"), link.url);
    }

    @Test
    public void builderOnlineLinkTest() {
        Builder<Thing> builder = new Builder<Thing>();
        SingleNavigationLink<Thing> link = builder.build("Thing@iot.navigationLink", "No data key",
                new ThingController(), ALIVEDATASTREAMEXPANDTHING);
        assertNotNull(link);
        assertEquals(SingleOnlineLink.class, link.getClass());
        assertEquals(ALIVEDATASTREAMEXPANDTHING.getString("Thing@iot.navigationLink"), link.url);
    }

    @Test
    public void initTest() {
        SingleNavigationLink<TestSensorThing> link = new TestSingleNavigationLink("url", false);
        assertEquals("url", link.url);
        link = new TestSingleNavigationLink("url", true);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    private class TestSingleNavigationLink extends SingleNavigationLink<TestSensorThing> {

        public TestSingleNavigationLink(String url, boolean relative) {
            super(url, relative);
        }

        @Override
        public Sensorthing<TestSensorThing> get(SensorthingController<TestSensorThing> controller) {
            return null;
        }

    }

}
