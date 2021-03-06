package de.visaq.model.sensorthings;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.spring.DedupingResolver;

/**
 * <p>
 * Representation of the {@link HistoricalLocation} entity in the OGC SensorThings API.
 * </p>
 * <p>
 * {@link HistoricalLocation} provides time for the stored Location
 * </p>
 * 
 * @see <a href=
 *      "https://developers.sensorup.com/docs/#historicalLocations_get">https://developers.sensorup.com/docs/#historicalLocations_get</a>
 */
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class,
        scope = HistoricalLocation.class, resolver = DedupingResolver.class)
public class HistoricalLocation extends Sensorthing<HistoricalLocation>
        implements SensorthingsTimeStamp {
    public final Instant time;
    public final SingleNavigationLink<Thing> thingLink;
    public final MultiNavigationLink<Location> locationsLink;

    /**
     * Constructs a new {@link HistoricalLocation}.
     * 
     * @param id            {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param selfUrl       {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param relative      {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param time          The time
     * @param thingLink     Link to the {@link Thing}
     * @param locationsLink Links to the {@link Location}s
     */
    public HistoricalLocation(@JsonProperty("id") String id,
            @JsonProperty("selfUrl") String selfUrl, @JsonProperty("relative") boolean relative,
            @JsonProperty("time") Instant time,
            @JsonProperty("thingLink") SingleNavigationLink<Thing> thingLink,
            @JsonProperty("locationsLink") MultiNavigationLink<Location> locationsLink) {
        super(id, selfUrl, relative);
        this.time = time;
        this.thingLink = thingLink;
        this.locationsLink = locationsLink;
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
        return time;
    }
}
