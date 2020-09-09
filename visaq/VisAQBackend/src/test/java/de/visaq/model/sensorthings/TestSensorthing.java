package de.visaq.model.sensorthings;

/**
 * Mock implementation of Sensorthing.
 */
public class TestSensorthing extends Sensorthing<TestSensorthing> {

    /**
     * Constructs a new {@link TestSensorthing}.
     * 
     * @param id       {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param selfUrl  {@link Sensorthing#Sensorthing(String, String, boolean)}
     * @param relative {@link Sensorthing#Sensorthing(String, String, boolean)}
     */
    public TestSensorthing(String id, String selfUrl, boolean relative) {
        super(id, selfUrl, relative);
    }

}
