package de.visaq.controller;

import java.awt.Point;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;

import de.visaq.controller.link.MultiNavigationLink;
import de.visaq.controller.link.MultiOnlineLink;
import de.visaq.controller.link.SingleOnlineLink;
import de.visaq.model.sensorthings.FeatureOfInterest;
import de.visaq.model.sensorthings.Observation;

/**
 * Encapsulates the control over FeatureOfIntrest objects.
 */
public class FeatureOfInterestController extends SensorthingController<FeatureOfInterest> {
    public static final String MAPPING = "/api/featureOfInterest";

    @Override
    public ArrayList<FeatureOfInterest> getAll() {
        return new MultiOnlineLink<FeatureOfInterest>("/FeaturesOfInterest", true).get(this);
    }

    @Override
    @PostMapping(value = MAPPING, params = { "id" })
    public FeatureOfInterest get(String id) {
        return (FeatureOfInterest) new SingleOnlineLink<FeatureOfInterest>(
                MessageFormat.format("/FeaturesOfInterest(''{0}'')", id), true).get(this);
    }

    /**
     * Tries to get the location as a point from a FeatureOfInterest entity's features.
     * 
     * @param foi The FeatureOfInterest entity
     * @return The Point that was retrieved from the features or null if the features do not contain
     *         a point.
     */
    public Point getLocationPoint(FeatureOfInterest foi) {
        try {
            JSONObject json = new JSONObject(foi.features);
            return UtilityController.buildLocationPoint(json);
        } catch (JSONException | NullPointerException e) {
            return null;
        }
    }

    @Override
    public FeatureOfInterest singleBuild(JSONObject json) {
        try {
            json = UtilityController.removeArrayWrapper(json);

            if (json == null) {
                return null;
            }

            MultiNavigationLink<Observation> observations =
                    new MultiNavigationLink.Builder<Observation>().build(
                            "Observations@iot.navigationLink", "Observations",
                            new ObservationController(), json);

            FeatureOfInterest featureOfIntrest = new FeatureOfInterest(json.getString("@iot.id"),
                    json.getString("@iot.selfLink"), false, json.getString("description"),
                    json.getString("name"), observations, json.getJSONObject("feature").toMap());
            return featureOfIntrest;
        } catch (JSONException e) {
            return null;
        }
    }

}
