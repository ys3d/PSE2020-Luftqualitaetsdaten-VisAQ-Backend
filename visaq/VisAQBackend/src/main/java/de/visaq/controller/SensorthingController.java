package de.visaq.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.SingleNavigationLink;
import de.visaq.model.sensorthings.Sensorthing;

/**
 * Encapsulates the control over all Sensorthings objects.
 * 
 * @param <SensorthingT> Sensorthings object this controller will work on.
 */
public abstract class SensorthingController<SensorthingT extends Sensorthing<SensorthingT>> {
    /**
     * Retrieves all Sensorthings objects of this type. Can be slow and should be avoided. Use more
     * specific requests instead.
     * 
     * @return An array containing the Sensorthings objects that were retrieved.
     */
    public abstract ArrayList<SensorthingT> getAll();

    /**
     * Retrieves all Sensorthings objects that are bound to the specified MultiNavigationLink.
     * 
     * @param navigationLink Link to the Sensorthings objects inside the database.
     * @return An array containing the Sensorthings objects that were retrieved.
     */
    public ArrayList<SensorthingT> get(MultiNavigationLink<SensorthingT> navigationLink) {
        return navigationLink.get(this);
    }

    /**
     * Retrieves the Sensorthings object that is bound to the specified SingleNavigationLink.
     * 
     * @param navigationLink Link to the Sensorthings object inside the database.
     * @return The Sensorthings object that was retrieved.
     */
    public Sensorthing<SensorthingT> get(SingleNavigationLink<SensorthingT> navigationLink) {
        return navigationLink.get(this);
    }

    /**
     * Retrieves the Sensorthings object with the specified identifier.
     * 
     * @param id Unique identifier of the Sensorthings object inside the database.
     * @return The Sensorthings object that was retrieved.
     */
    public abstract SensorthingT get(String id);

    /**
     * Builds the Sensorthings objects using the specified JSON.
     * 
     * @param json Sensorthings objects codified as JSONObject.
     * @return The Sensorthings objects that were constructed.
     */
    public ArrayList<SensorthingT> multiBuild(JSONObject json) {
        if (!json.has("value")) {
            ArrayList<SensorthingT> objects = new ArrayList<SensorthingT>();
            objects.add(singleBuild(json));
            return objects;
        }

        return multiBuild(json.getJSONArray("value"));
    }

    /**
     * Builds the Sensorthings objects using the specified JSON.
     * 
     * @param array Sensorthings objects codified as JSONArray.
     * @return The Sensorthings objects that were constructed.
     */
    public ArrayList<SensorthingT> multiBuild(JSONArray array) {
        int length = array.length();
        ArrayList<SensorthingT> objects = new ArrayList<SensorthingT>();

        for (int i = 0; i < length; i++) {
            objects.add(singleBuild(array.getJSONObject(i)));
        }

        return objects;
    }

    /**
     * Builds the Sensorthings object using the specified JSON.
     * 
     * @param json Sensorthings object codified as JSON.
     * @return The Sensorthings object that were constructed.
     */
    public abstract SensorthingT singleBuild(JSONObject json);
}
