package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

import de.visaq.RestConstants;
import de.visaq.controller.DatastreamController;
import de.visaq.controller.SensorthingController;
import de.visaq.controller.link.MultiNavigationLink.Builder;
import de.visaq.model.sensorthings.Datastream;

/**
 * Tests {@link MultiNavigationLink}.
 */
public class MultiNavigationLinkTest {
    public static final JSONObject ALIVETHINGEXPANDDATASTREANS;

    static {
        ALIVETHINGEXPANDDATASTREANS = new JSONObject(new JSONTokener(MultiNavigationLinkTest.class
                .getResourceAsStream("/alive_thing_expand_datastreams.json")));
    }

    @Test
    public void builderLocalLinkTest() {
        Builder<Datastream> builder = new Builder<Datastream>();
        MultiNavigationLink<Datastream> link = builder.build("Datastreams@iot.navigationLink",
                "Datastreams", new DatastreamController(), ALIVETHINGEXPANDDATASTREANS);
        assertNotNull(link);
        assertEquals(MultiLocalLink.class, link.getClass());
        assertEquals(ALIVETHINGEXPANDDATASTREANS.getString("Datastreams@iot.navigationLink"),
                link.url);
    }

    @Test
    public void builderOnlineLinkTest() {
        Builder<Datastream> builder = new Builder<Datastream>();
        MultiNavigationLink<Datastream> link = builder.build("Datastreams@iot.navigationLink",
                "no data key", new DatastreamController(), ALIVETHINGEXPANDDATASTREANS);
        assertNotNull(link);
        assertEquals(MultiOnlineLink.class, link.getClass());
        assertEquals(ALIVETHINGEXPANDDATASTREANS.getString("Datastreams@iot.navigationLink"),
                link.url);
    }

    @Test
    public void initTest() {
        TestMultiNavigationLink link = new TestMultiNavigationLink("url", false);
        assertEquals("url", link.url);
        link = new TestMultiNavigationLink("url", true);
        assertEquals(RestConstants.ENTRY_POINT + "url", link.url);
    }

    private class TestMultiNavigationLink extends MultiNavigationLink<TestSensorThing> {

        public TestMultiNavigationLink(String url, boolean relative) {
            super(url, relative);
        }

        @Override
        public ArrayList<TestSensorThing> get(SensorthingController<TestSensorThing> controller) {
            return null;
        }

    }
}
