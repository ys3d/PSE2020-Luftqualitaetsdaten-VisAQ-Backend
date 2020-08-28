package de.visaq.controller;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.FeatureOfInterest;
import de.visaq.model.sensorthings.Observation;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.Thing;

/**
 * Encapsulates the control over Observation objects.
 */
@RestController
public class ObservationController extends SensorthingController<Observation> {
    public static final String MAPPING = "/api/observation";

    class TimeframeRun implements Runnable {
        private Thing[] things;
        private Observation[] observations;
        private Instant lower;
        private Instant upper;
        private ObservedProperty observedProperty;
        private double average;
        private double variance;
        private int start;
        private int packetSize;

        public TimeframeRun(int start, int packetSize, Thing[] things, Observation[] observations,
                Instant lower, Instant upper, ObservedProperty observedProperty, double average,
                double variance) {
            this.things = things;
            this.observations = observations;
            this.lower = lower;
            this.upper = upper;
            this.observedProperty = observedProperty;
            this.average = average;
            this.variance = variance;
            this.start = start;
            this.packetSize = packetSize;
        }

        @Override
        public void run() {
            for (int i = this.start; i < this.start + this.packetSize; i++) {
                if (i >= things.length) {
                    break;
                }

                Thing thing = things[i];
                ArrayList<Observation> temp = new MultiOnlineLink<Observation>(MessageFormat.format(
                        "/Observations?$orderby=phenomenonTime desc&"
                                + "$filter=phenomenonTime ge {0} and phenomenonTime le {1} and "
                                + "Datastream/ObservedProperty/id eq ''{2}'' and "
                                + "Datastream/Thing/id eq ''{3}''&$top=1",
                        this.lower, this.upper, this.observedProperty.id, thing.id), true)
                                .get(ObservationController.this);
                if (!temp.isEmpty()) {
                    Observation observation = temp.get(0);
                    if (observation.result <= average + 10 * variance
                            && observation.result >= average - 10 * variance) {
                        this.observations[i] = observation;
                    }
                }
            }
        }
    }

    static class AreaWrapper {
        public Square square;
        public long millis;
        public Duration range;
        public ObservedProperty observedProperty;

        public AreaWrapper() {
        }

        public AreaWrapper(Square square, long millis, Duration range,
                ObservedProperty observedProperty) {
            this.square = square;
            this.millis = millis;
            this.range = range;
            this.observedProperty = observedProperty;
        }
    }

    static class TimeframedThingWrapper {
        public ArrayList<Thing> things;
        public long millis;
        public Duration range;
        public ObservedProperty observedProperty;
        public double average;
        public double variance;

        public TimeframedThingWrapper() {
        }

        public TimeframedThingWrapper(ArrayList<Thing> things, long millis, Duration range,
                ObservedProperty observedProperty, double average, double variance) {
            this.things = things;
            this.millis = millis;
            this.range = range;
            this.observedProperty = observedProperty;
            this.average = average;
            this.variance = variance;
        }
    }

    /**
     * Wraps data for the top request.
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
        return getAll(areaWrapper.square, Instant.ofEpochMilli(areaWrapper.millis),
                areaWrapper.range, areaWrapper.observedProperty);
    }

    /**
     * Returns the newest Observatiosn for the given Datastream.
     * 
     * @param topWrapper Encapsulates the number of elements and teh datastreams id
     * @return A number of newest elements
     */
    @CrossOrigin
    @PostMapping(value = MAPPING + "/all/newest")
    public ArrayList<Observation> getAll(@RequestBody TopWrapper topWrapper) {
        return getAll(topWrapper.topNumber, topWrapper.datastreamId);
    }

    /**
     * Retrieves the newest Observations entities of an Datastream.
     * 
     * @param topNumber    The number of Observations
     * @param datastreamId The id of the Datastream
     * @return An ArrayList of Observation entities
     */
    public ArrayList<Observation> getAll(int topNumber, String datastreamId) {
        return new MultiOnlineLink<Observation>(
                MessageFormat.format(
                        "/Observations?$orderby=phenomenonTime desc&$top={0,number,integer}&"
                                + "$filter=Datastream/id eq ''{1}''",
                        topNumber, datastreamId),
                true).get(this);
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
    public ArrayList<Observation> getAll(Square square, Instant time, Duration range,
            ObservedProperty observedProperty) {
        return new MultiOnlineLink<Observation>(MessageFormat.format(
                "/Observations?$orderby=phenomenonTime desc&$filter=phenomenonTime gt {0} and "
                        + "phenomenonTime lt {1} and "
                        + "Datastream/ObservedProperty/id eq ''{2}'' and "
                        + "st_within(Datastream/observedArea, geography''{3}'')",
                time.minus(range), time.plus(range), observedProperty.id, square), true).get(this);
    }

    /**
     * Retrieves the latest Observation entity of an ObservedProperty entity within a specified time
     * range from a list of things.
     * 
     * @param timeframedThingWrapper Encapsulates a list of Things, an Instant, a TimeRange and an
     *                               ObservedProperty
     * @return An ArrayList of Observation entities
     */
    @CrossOrigin
    @PostMapping(value = MAPPING + "/all/things/timeframed")
    public ArrayList<Observation>
            getAll(@RequestBody TimeframedThingWrapper timeframedThingWrapper) {
        return getAll(timeframedThingWrapper.things,
                Instant.ofEpochMilli(timeframedThingWrapper.millis), timeframedThingWrapper.range,
                timeframedThingWrapper.observedProperty, timeframedThingWrapper.average,
                timeframedThingWrapper.variance);
    }

    /**
     * Retrieves the latest Observation entity of an ObservedProperty entity within a specified time
     * range from a list of things.
     * 
     * @param things           A list of things
     * @param time             A point in time
     * @param range            The Observation must have been recorded in [time - range, time +
     *                         range]
     * @param observedProperty The ObservedProperty that was observed
     * @param average          Assumed average of the ObservedProperty
     * @param variance         Assumed variance of the ObservedProperty
     * @return An ArrayList of Observation entities
     */
    public ArrayList<Observation> getAll(ArrayList<Thing> things, Instant time, Duration range,
            ObservedProperty observedProperty, double average, double variance) {
        /*
         * int ticket = (int) (Math.random() * 1000);
         * 
         * System.out.println(ticket + " Things: " + things.size() + " Time: " + time + " Range: " +
         * range + " AirQ " + observedProperty.name + " Before: " + Instant.now().toEpochMilli());
         */

        Instant upper = time.plus(range);
        Instant lower = time.minus(range);

        Thing[] thingsArr = new Thing[things.size()];
        things.toArray(thingsArr);
        Observation[] observations = new Observation[things.size()];

        int packetSize = 4;

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < ((float) things.size() / (float) packetSize); i++) {
            es.execute(new TimeframeRun(i * packetSize, packetSize, thingsArr, observations, lower,
                    upper, observedProperty, average, variance));
        }

        es.shutdown();
        try {
            es.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /*
         * System.out.println( ticket + " Things: " + things.size() + " After: " +
         * Instant.now().toEpochMilli());
         */

        return new ArrayList<Observation>(Arrays.asList(observations));
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
