package de.visaq.controller;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.locationtech.jts.geom.Coordinate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.visaq.controller.math.DefaultInterpolationTest;
import de.visaq.model.Square;
import de.visaq.model.sensorthings.Datastream;
import de.visaq.model.sensorthings.FeatureOfInterest;
import de.visaq.model.sensorthings.HistoricalLocation;
import de.visaq.model.sensorthings.Location;
import de.visaq.model.sensorthings.Observation;
import de.visaq.model.sensorthings.ObservedProperty;
import de.visaq.model.sensorthings.Sensor;
import de.visaq.model.sensorthings.Thing;

/**
 * Provides multiple reference Sensorthings entities for testing purposes.
 */
@RunWith(Suite.class)
@SuiteClasses({ DatastreamControllerTest.class, FeatureOfInterestControllerTest.class,
        HistoricalLocationControllerTest.class, LocationControllerTest.class,
        ObservationControllerTest.class, ObservedPropertyControllerTest.class,
        SensorControllerTest.class, ThingControllerTest.class, SensorthingControllerTest.class,
        UtilityControllerTest.class, DefaultInterpolationTest.class })
public class SensorthingsControllerTests {
    /**
     * The following ALIVE objects are used as a reference in various tests.
     */
    public static HistoricalLocation ALIVEHISTORICALLOCATION;
    public static Location ALIVELOCATION;
    public static Sensor ALIVESENSOR;
    public static ObservedProperty ALIVEOBSERVEDPROPERTY;
    public static Thing ALIVETHING;
    public static Datastream ALIVEDATASTREAM;
    public static FeatureOfInterest ALIVEFEATUREOFINTEREST;
    public static Observation ALIVEOBSERVATION;
    public static JSONObject EMPTYARRAY;
    public static Square INTERPOLATIONSQUARE;
    public static ArrayList<Coordinate> INTERPOLATIONCOORDINATES;

    @ClassRule
    public static ExternalResource testRule = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            ALIVEDATASTREAM = new DatastreamController()
                    .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                            .getResourceAsStream("/alive_datastream.json"))));

            ALIVEFEATUREOFINTEREST = new FeatureOfInterestController()
                    .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                            .getResourceAsStream("/alive_featureofinterest.json"))));

            ALIVEHISTORICALLOCATION = new HistoricalLocationController()
                    .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                            .getResourceAsStream("/alive_historicallocation.json"))));

            ALIVELOCATION = new LocationController()
                    .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                            .getResourceAsStream("/alive_location.json"))));

            ALIVEOBSERVATION = new ObservationController()
                    .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                            .getResourceAsStream("/alive_observation.json"))));

            ALIVEOBSERVEDPROPERTY = new ObservedPropertyController()
                    .singleBuild(new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                            .getResourceAsStream("/alive_observedproperty.json"))));

            ALIVESENSOR = new SensorController().singleBuild(new JSONObject(new JSONTokener(
                    SensorthingsControllerTests.class.getResourceAsStream("/alive_sensor.json"))));

            ALIVETHING = new ThingController().singleBuild(new JSONObject(new JSONTokener(
                    SensorthingsControllerTests.class.getResourceAsStream("/alive_thing.json"))));
            EMPTYARRAY = new JSONObject(new JSONTokener(SensorthingsControllerTests.class
                    .getResourceAsStream("/empty_json_array.json")));
            INTERPOLATIONSQUARE = new ObjectMapper().readValue(
                    new File("src/test/resources/interpolation_square.json"), Square.class);
            INTERPOLATIONCOORDINATES = new ObjectMapper().readValue(
                    new File("src/test/resources/interpolation_coordinates.json"),
                    new TypeReference<ArrayList<Coordinate>>() {
                    });
        }
    };

}
