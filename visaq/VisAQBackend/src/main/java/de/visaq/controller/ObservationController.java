package de.visaq.controller;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.FeatureOfInterest;
import de.visaq.model.sensorthings.Observation;
import de.visaq.model.sensorthings.ObservedProperty;

/**
 * Encapsulates the control over Observation objects.
 */
@RestController
public class ObservationController extends SensorthingController<Observation> {
    public static final String MAPPING = "/api/observation";

    static class AreaWrapper {
        public Square square;
        public Instant time;
        public TemporalAmount range;
        public ObservedProperty observedProperty;

        public AreaWrapper() {
        }

        public AreaWrapper(Square square, Instant time, TemporalAmount range,
                ObservedProperty observedProperty) {
            this.square = square;
            this.time = time;
            this.range = range;
            this.observedProperty = observedProperty;
        }
    }

    /**
     * Wrapps data for the top request.
     */
    static class TopWrapper {
        public int topNumber;
        public String datastreamId;

        public TopWrapper() {
        }

        public TopWrapper(int topNumber, String datastreamId) {
            this.topNumber = topNumber;
            this.datastreamId = datastreamId;
        }

    }

    @Override
    public ArrayList<Observation> getAll() {
        return new MultiOnlineLink<Observation>("/Observations", true).get(this);
    }

    /**
     * Retrieves the Observation entities associated with the given Datastream entity.
     * 
     * @param datastream The Datastream entity
     * @return An ArrayList containing the associated Observation entities
     */
    @CrossOrigin
    @PostMapping(value = MAPPING + "/all/datastream")
    public ArrayList<Observation> getAll(@RequestBody Datastream datastream) {
        return datastream.observationsLink.get(this);
    }

    /**
     * Retrieves the Observation entities of an ObservedProperty entity within a specified square
     * and time range.
     * 
     * @param areaWrapper Encapsulates a Square, Instant, Range and ObservedProperty
     * @return An ArrayList of Observation entities
     */
    @CrossOrigin
    @PostMapping(value = MAPPING + "/all/area")
    public ArrayList<Observation> getAll(@RequestBody AreaWrapper areaWrapper) {
        return getAll(areaWrapper.square, areaWrapper.time, areaWrapper.range,
                areaWrapper.observedProperty);
    }

    /**
     * Returns the newest Observatiosn for the given Datastream.
     * 
     * @param topWrapper Encapsulates the number of elements and teh datastreams id
     * @return A number of newest elements
     */
    @CrossOrigin
    @PostMapping(value = MAPPING + "/all/newest")
    public ArrayList<Observation> getNewest(@RequestBody TopWrapper topWrapper) {
        return new MultiOnlineLink<Observation>(MessageFormat.format(
                "/Observations?$orderby=phenomenonTime desc&$top={0,number,integer}&"
                        + "$filter=Datastream/id eq ''{1}''",
                topWrapper.topNumber, topWrapper.datastreamId), true).get(this);
    }

    /**
     * Retrieves the Observation entities of an ObservedProperty entity within a specified square
     * and time range.
     * 
     * @param square           Covers the area of all allowed locations
     * @param time             A point in time
     * @param range            The Observation must have been recorded in [time - range, time +
     *                         range]
     * @param observedProperty The ObservedProperty that was observed
     * @return An ArrayList of Observation entities
     */
    public ArrayList<Observation> getAll(Square square, Instant time, TemporalAmount range,
            ObservedProperty observedProperty) {
        return new MultiOnlineLink<Observation>(MessageFormat.format(
                "/Observations?$filter=phenomenonTime gt ''{0}'' and "
                        + "phenomenonTime lt ''{1}'' and "
                        + "Datastream/ObservedProperty/id eq ''{{2}}'' and "
                        + "st_within(location, geography''{{3}}'')",
                time.minus(range), time.plus(range), observedProperty.id, square), true).get(this);
    }

    @CrossOrigin
    @Override
    @PostMapping(value = MAPPING + "/id")
    public Observation get(@RequestBody IdWrapper idWrapper) {
        return get(idWrapper.id);
    }

    @Override
    public Observation get(String id) {
        return (Observation) new SingleOnlineLink<Observation>(
                MessageFormat.format("/Observations(''{0}'')", id), true).get(this);
    }

    @Override
    public Observation singleBuild(JSONObject json) {
        try {
            json = UtilityController.removeArrayWrapper(json);

            if (json == null) {
                return null;
            }

            SingleNavigationLink<Datastream> datastream =
                    new SingleNavigationLink.Builder<Datastream>().build(
                            "Datastream@iot.navigationLink", "Datastream",
                            new DatastreamController(), json);
            SingleNavigationLink<FeatureOfInterest> featureOfInterest =
                    new SingleNavigationLink.Builder<FeatureOfInterest>().build(
                            "FeatureOfInterest@iot.navigationLink", "FeatureOfInterest",
                            new FeatureOfInterestController(), json);

            Observation observation = new Observation(json.getString("@iot.id"),
                    json.getString("@iot.selfLink"), false,
                    UtilityController.buildTime(json, "phenomenonTime"), json.getDouble("result"),
                    UtilityController.buildTime(json, "resultTime"), datastream, featureOfInterest);
            return observation;
        } catch (JSONException e) {
            return null;
        }
    }
}
