package de.visaq.model.sensorthings;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Interface for all {@link Sensorthing} with properties.
 */
public interface SensorthingsProperties {
    /**
     * Returns the property given by the unique key.
     * 
     * @param key A unique key for the properties
     * @return The Property given by the key or null if nothing was found
     */
    @JsonIgnore
    public Object getPropertyByKey(String key);

    /**
     * Checks if the property with the given key exists for this {@link Sensorthing}.
     * 
     * @param key The key of the searched property
     * @return True if the property exists, false otherwise
     */
    @JsonIgnore
    public boolean hasProperties(String key);
}
