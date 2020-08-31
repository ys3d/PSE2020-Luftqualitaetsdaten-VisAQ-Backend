package de.visaq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.locationtech.jts.geom.Coordinate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.visaq.controller.DatastreamController;
import de.visaq.controller.FeatureOfInterestController;
import de.visaq.controller.HistoricalLocationController;
import de.visaq.controller.LocationController;
import de.visaq.controller.ObservationController;
import de.visaq.controller.ObservedPropertyController;
import de.visaq.controller.SensorController;
import de.visaq.controller.ThingController;
import de.visaq.controller.UtilityController;
import de.visaq.controller.link.MultiNavigationLinkTest;
import de.visaq.controller.link.SingleNavigationLinkTest;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.FeatureOfInterest;
import de.visaq.model.sensorthings.HistoricalLocation;
import de.visaq.model.sensorthings.Location;
import de.visaq.model.sensorthings.Observation;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.Sensor;
import de.visaq.model.sensorthings.Thing;
import de.visaq.model.sensorthings.UnitOfMeasurement;

/**
 * Provides multiple reference Sensorthings entities for testing purposes.
 */
public class ResourceTest {
    /**
     * The following ALIVE objects are used as a reference in various tests.
     */
    public static final HistoricalLocation ALIVEHISTORICALLOCATION;
    public static final JSONObject ALIVEHISTORICALLOCATIONJSON;
    public static final Location ALIVELOCATION;
    public static final JSONObject ALIVELOCATIONJSON;
    public static final Sensor ALIVESENSOR;
    public static final JSONObject ALIVESENSORJSON;
    public static final ObservedProperty ALIVEOBSERVEDPROPERTY;
    public static final Thing ALIVETHING;
    public static final Datastream ALIVEDATASTREAM;
    public static final JSONObject ALIVEDATASTREAMJSON;
    public static final UnitOfMeasurement ALIVEUNITOFMEASUREMENT;
    public static final JSONObject ALIVEUNITOFMEASUREMENTJSON;
    public static final FeatureOfInterest ALIVEFEATUREOFINTEREST;
    public static final JSONObject ALIVEFEATUREOFINTERESTJSON;
    public static final Observation ALIVEOBSERVATION;
    public static final JSONObject ALIVEOBSERVATIONJSON;
    public static final JSONObject EMPTYARRAY;
    public static final Square INTERPOLATIONSQUARE;
    public static final JSONObject ALIVETHINGEXPANDDATASTREAMSJSON;
    public static final JSONObject ALIVEDATASTREAMEXPANDTHINGJSON;
    public static final ObservedProperty ALIVEOBSERVEDPROPERTYPM10;
    public static final ArrayList<Coordinate> INTERPOLATIONCOORDINATES;
    public static final ArrayList<Datastream> MULTIALIVEDATASTREAM;
    public static final ArrayList<Datastream> MULTIALIVEDATASTREAMNOVALUE;

    static {
        ALIVEDATASTREAMJSON = new JSONObject(
                new JSONTokener(ResourceTest.class.getResourceAsStream("/alive_datastream.json")));
        ALIVEDATASTREAM = new DatastreamController().singleBuild(ALIVEDATASTREAMJSON);
        ALIVEUNITOFMEASUREMENTJSON = ALIVEDATASTREAMJSON.getJSONObject("unitOfMeasurement");
        ALIVEUNITOFMEASUREMENT =
                UtilityController.buildUnitOfMeasurement(ALIVEUNITOFMEASUREMENTJSON);

        ALIVEFEATUREOFINTERESTJSON = new JSONObject(new JSONTokener(
                ResourceTest.class.getResourceAsStream("/alive_featureofinterest.json")));
        ALIVEFEATUREOFINTEREST =
                new FeatureOfInterestController().singleBuild(ALIVEFEATUREOFINTERESTJSON);

        ALIVEHISTORICALLOCATIONJSON = new JSONObject(new JSONTokener(
                ResourceTest.class.getResourceAsStream("/alive_historicallocation.json")));
        ALIVEHISTORICALLOCATION =
                new HistoricalLocationController().singleBuild(ALIVEHISTORICALLOCATIONJSON);

        ALIVELOCATIONJSON = new JSONObject(
                new JSONTokener(ResourceTest.class.getResourceAsStream("/alive_location.json")));
        ALIVELOCATION = new LocationController().singleBuild(ALIVELOCATIONJSON);

        ALIVEOBSERVATIONJSON = new JSONObject(
                new JSONTokener(ResourceTest.class.getResourceAsStream("/alive_observation.json")));
        ALIVEOBSERVATION = new ObservationController().singleBuild(ALIVEOBSERVATIONJSON);

        ALIVEOBSERVEDPROPERTY =
                new ObservedPropertyController().singleBuild(new JSONObject(new JSONTokener(
                        ResourceTest.class.getResourceAsStream("/alive_observedproperty.json"))));
        ALIVEOBSERVEDPROPERTYPM10 = new ObservedPropertyController()
                .singleBuild(new JSONObject(new JSONTokener(ResourceTest.class
                        .getResourceAsStream("/alive_observedproperty_pm10.json"))));

        ALIVESENSORJSON = new JSONObject(
                new JSONTokener(ResourceTest.class.getResourceAsStream("/alive_sensor.json")));
        ALIVESENSOR = new SensorController().singleBuild(ALIVESENSORJSON);

        ALIVETHING = new ThingController().singleBuild(new JSONObject(
                new JSONTokener(ResourceTest.class.getResourceAsStream("/alive_thing.json"))));
        EMPTYARRAY = new JSONObject(
                new JSONTokener(ResourceTest.class.getResourceAsStream("/empty_json_array.json")));
        Square tempInterpolationSquare = null;
        try {
            tempInterpolationSquare = new ObjectMapper().readValue(
                    new File("src/test/resources/interpolation_square.json"), Square.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        INTERPOLATIONSQUARE = tempInterpolationSquare;
        ArrayList<Coordinate> tempInterpolationCoordinates = null;
        try {
            tempInterpolationCoordinates = new ObjectMapper().readValue(
                    new File("src/test/resources/interpolation_coordinates.json"),
                    new TypeReference<ArrayList<Coordinate>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        INTERPOLATIONCOORDINATES = tempInterpolationCoordinates;
        MULTIALIVEDATASTREAM = new DatastreamController().multiBuild(new JSONObject(new JSONTokener(
                ResourceTest.class.getResourceAsStream("/multi_alive_datastreams.json"))));
        MULTIALIVEDATASTREAMNOVALUE = new DatastreamController()
                .multiBuild(new JSONObject(new JSONTokener(ResourceTest.class
                        .getResourceAsStream("/multi_alive_datastreams_no_value.json"))));
        ALIVETHINGEXPANDDATASTREAMSJSON =
                new JSONObject(new JSONTokener(MultiNavigationLinkTest.class
                        .getResourceAsStream("/alive_thing_expand_datastreams.json")));
        ALIVEDATASTREAMEXPANDTHINGJSON =
                new JSONObject(new JSONTokener(SingleNavigationLinkTest.class
                        .getResourceAsStream("/alive_datastream_expand_thing.json")));
    }
}
