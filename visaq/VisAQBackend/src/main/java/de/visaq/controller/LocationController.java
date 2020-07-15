package de.visaq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.HistoricalLocation;
import de.visaq.model.sensorthings.Location;
import de.visaq.model.sensorthings.Thing;

/**
 * Encapsulates the control over Location objects.
 */
public class LocationController extends SensorthingController<Location> {
    public static final String MAPPING = "/api/location";

    @Override
    public ArrayList<Location> getAll() {
        return new MultiOnlineLink<Location>("/Locations", true).get(this);
    }

    @CrossOrigin
    @Override
    @PostMapping(value = MAPPING + "/id")
    public Location get(@RequestBody IdWrapper idWrapper) {
        return get(idWrapper.id);
    }

    @Override
    public Location get(String id) {
        return (Location) new SingleOnlineLink<Location>(
                MessageFormat.format("/Locations(''{0}'')", id), true).get(this);
    }

    @Override
    public Location singleBuild(JSONObject json) {
        json = UtilityController.removeArrayWrapper(json);

        if (json == null) {
            return null;
        }

        MultiNavigationLink<HistoricalLocation> historicalLocations =
                new MultiNavigationLink.Builder<HistoricalLocation>().build(
                        "HistoricalLocations@iot.navigationLink", "HistoricalLocations",
                        new HistoricalLocationController(), json);
        MultiNavigationLink<Thing> things = new MultiNavigationLink.Builder<Thing>()
                .build("Things@iot.navigationLink", "Things", new ThingController(), json);

        Location location = new Location(json.getString("@iot.id"), json.getString("@iot.selfLink"),
                false, json.getString("name"), json.getString("description"),
                UtilityController.buildLocationPoint(json), historicalLocations, things);
        return location;
    }

}
