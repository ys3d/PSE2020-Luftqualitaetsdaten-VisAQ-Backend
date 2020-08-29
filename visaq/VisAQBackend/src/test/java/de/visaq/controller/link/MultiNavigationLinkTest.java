package de.visaq.controller.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.visaq.ResourceTest;
import de.visaq.RestConstants;
import de.visaq.controller.DatastreamController;
import de.visaq.controller.SensorthingController;
import de.visaq.controller.link.MultiNavigationLink.Builder;
import de.visaq.model.sensorthings.Datastream;

/**
 * Tests {@link MultiNavigationLink}.
 */
public class MultiNavigationLinkTest extends ResourceTest {

    @Test
    public void builderLocalLinkTest() {
        Builder<Datastream> builder = new Builder<Datastream>();
        MultiNavigationLink<Datastream> link = builder.build("Datastreams@iot.navigationLink",
                "Datastreams", new DatastreamController(), ALIVETHINGEXPANDDATASTREANSJSON);
        assertNotNull(link);
        assertEquals(MultiLocalLink.class, link.getClass());
        assertEquals(ALIVETHINGEXPANDDATASTREANSJSON.getString("Datastreams@iot.navigationLink"),
                link.url);
    }

    @Test
    public void builderOnlineLinkTest() {
        Builder<Datastream> builder = new Builder<Datastream>();
        MultiNavigationLink<Datastream> link = builder.build("Datastreams@iot.navigationLink",
                "no data key", new DatastreamController(), ALIVETHINGEXPANDDATASTREANSJSON);
        assertNotNull(link);
        assertEquals(MultiOnlineLink.class, link.getClass());
        assertEquals(ALIVETHINGEXPANDDATASTREANSJSON.getString("Datastreams@iot.navigationLink"),
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
