package de.visaq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.HistoricalLocation;
import de.visaq.model.sensorthings.Location;
import de.visaq.model.sensorthings.Thing;

/**
 * Encapsulates the control over Thing objects.
 */
@RestController
public class ThingController extends SensorthingController<Thing> {
    public static final String MAPPING = "/api/thing";

    @Override
    public ArrayList<Thing> getAll() {
        MultiOnlineLink<Thing> multiLink = new MultiOnlineLink<Thing>("/Things?$top=3", true);
        return multiLink.get(this);
    }

    /**
     * Retrieves the Thing objects spatially located inside the specified square.
     * 
     * @param square Covers the area of all allowed locations
     * @return An array of Thing objects that were retrieved.
     */
    @PostMapping(value = MAPPING + "/all", params = { "square" })
    public ArrayList<Thing> getAll(Square square) {
        return new MultiOnlineLink<Thing>(MessageFormat
                .format("/Thing?$filter=st_within(location, geography''{{0}}'')", square), true)
                        .get(this);

    }

    @Override
    @PostMapping(value = MAPPING, params = { "id" })
    public Thing get(@RequestParam String id) {
        return (Thing) new SingleOnlineLink<Thing>(MessageFormat.format("/Things(''{0}'')", id),
                true).get(this);
    }

    @Override
    public Thing singleBuild(JSONObject json) {
        json = UtilityController.removeArrayWrapper(json);

        if (json == null) {
            return null;
        }

        MultiNavigationLink<Datastream> datastreams = new MultiNavigationLink.Builder<Datastream>()
                .build("Datastreams@iot.navigationLink", "Datastreams", new DatastreamController(),
                        json);
        MultiNavigationLink<HistoricalLocation> historicalLocations =
                new MultiNavigationLink.Builder<HistoricalLocation>().build(
                        "HistoricalLocations@iot.navigationLink", "HistoricalLocations",
                        new HistoricalLocationController(), json);
        MultiNavigationLink<Location> locations = new MultiNavigationLink.Builder<Location>()
                .build("Locations@iot.navigationLink", "Locations", new LocationController(), json);

        Thing thing = new Thing(json.getString("@iot.id"), json.getString("@iot.selfLink"), false,
                json.getString("description"), json.getString("name"),
                UtilityController.buildProperties(json), datastreams, historicalLocations,
                locations);
        return thing;
    }
}
