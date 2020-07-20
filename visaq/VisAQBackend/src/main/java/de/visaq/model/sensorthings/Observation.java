package de.visaq.model.sensorthings;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.spring.DedupingResolver;

/**
 * <p>
 * Representation of the Observation entity in the OGC SensorThings API.
 * </p>
 * <p>
 * An Observation is the act of measuring or otherwise determining the value of a property. An
 * Observation in SensorThings represents a single Sensor reading of an ObservedProperty.
 * </p>
 * 
 * @see <a href=
 *      "https://developers.sensorup.com/docs/#observations_post">https://developers.sensorup.com/docs/#observations_post</a>
 */
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class,
        scope = Observation.class, resolver = DedupingResolver.class)
public class Observation extends Sensorthing<Observation> implements SensorthingsTimeStamp {
    public final Instant phenomenonTime;
    public final Double result;
    public final Instant resultTime;
    public final SingleNavigationLink<Datastream> datastreamLink;
    public final SingleNavigationLink<FeatureOfInterest> featureOfInterestLink;

    /**
     * Constructs a new {@link Observation}.
     * 
     * @param id                    {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param selfUrl               {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param relative              {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param phenomenonTime        The time when the Observation occurred
     * @param result                The result of the {@link Observation}
     * @param resultTime            The Time of the measuring result
     * @param datastreamLink        Link to the {@link Datastream}
     * @param featureOfInterestLink Link to the {@link FeatureOfInterest}
     */
    public Observation(@JsonProperty("id") String id, @JsonProperty("selfUrl") String selfUrl,
            @JsonProperty("relative") boolean relative,
            @JsonProperty("phenomenonTime") Instant phenomenonTime,
            @JsonProperty("result") Double result, @JsonProperty("resultTime") Instant resultTime,
            @JsonProperty("datastreamLink") SingleNavigationLink<Datastream> datastreamLink,
            @JsonProperty("featureOfInterestLink") SingleNavigationLink<FeatureOfInterest> featureOfInterestLink) {
        super(id, selfUrl, relative);
        this.phenomenonTime = phenomenonTime;
        this.result = result;
        this.resultTime = resultTime;
        this.datastreamLink = datastreamLink;
        this.featureOfInterestLink = featureOfInterestLink;
    }

    @Override
    public boolean isOlder(SensorthingsTimeStamp other) {
        int compare = this.getTimeStamp().compareTo(other.getTimeStamp());
        return (compare < 0);
    }

    @Override
    public boolean isNewer(SensorthingsTimeStamp other) {
        int compare = this.getTimeStamp().compareTo(other.getTimeStamp());
        return (compare > 0);
    }

    @Override
    public boolean isEqualOld(SensorthingsTimeStamp other) {
        int compare = this.getTimeStamp().compareTo(other.getTimeStamp());
        return (compare == 0);
    }

    @Override
    public Instant getTimeStamp() {
        return phenomenonTime;
    }
}
