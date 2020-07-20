package de.visaq.model.sensorthings;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.spring.DedupingResolver;

/**
 * <p>
 * Representation of the FeatureOfIntrest entity in the OGC SensorThings API.
 * </p>
 * <p>
 * An Observation results is a value being assigned to a phenomenon. The phenomenon is a property of
 * a feature, the latter being the FeatureOfInterest of the Observation
 * </p>
 * 
 * @see <a href=
 *      "https://developers.sensorup.com/docs/#featureOfInterest_post">https://developers.sensorup.com/docs/#featureOfInterest_postt</a>
 */
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class,
        scope = FeatureOfInterest.class, resolver = DedupingResolver.class)
public class FeatureOfInterest extends Sensorthing<FeatureOfInterest> {
    public final String description;
    public final String name;
    public final MultiNavigationLink<Observation> observationsLink;
    public final Map<String, Object> features;

    /**
     * Constructs a new {@link FeatureOfInterest}.
     * 
     * @param id               {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param selfUrl          {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param relative         {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param description      The description of the {@link FeatureOfInterest}
     * @param name             The name of the {@link FeatureOfInterest}
     * @param observationsLink Links to the {@link Observation}
     * @param features         Features
     */
    public FeatureOfInterest(@JsonProperty("id") String id, @JsonProperty("selfUrl") String selfUrl,
            @JsonProperty("relative") boolean relative,
            @JsonProperty("description") String description, @JsonProperty("name") String name,
            @JsonProperty("observationsLink") MultiNavigationLink<Observation> observationsLink,
            @JsonProperty("features") Map<String, Object> features) {
        super(id, selfUrl, relative);
        this.description = description;
        this.name = name;
        this.observationsLink = observationsLink;
        this.features = features;
    }

}
