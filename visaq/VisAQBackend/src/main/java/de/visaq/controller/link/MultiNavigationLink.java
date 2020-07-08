package de.visaq.controller.link;

import java.util.ArrayList;

import org.json.JSONObject;

import de.visaq.controller.SensorthingController;
import de.visaq.model.sensorthings.Sensorthing;

/**
 * Encapsulates a Sensorthings query that can return multiple Sensorthings entities.
 *
 * @param <SensorthingT> A class that extends Sensorthings
 */
public abstract class MultiNavigationLink<SensorthingT extends Sensorthing<SensorthingT>>
        extends NavigationLink<SensorthingT> {
    /**
     * Constructs a new NavigationLink to a Sensorthings query with the given url.
     * 
     * @param url      {@link NavigationLink#NavigationLink(String, boolean)}
     * @param relative {@link NavigationLink#NavigationLink(String, boolean)}
     */
    public MultiNavigationLink(String url, boolean relative) {
        super(url, relative);
    }

    /**
     * Returns the Sensorthings entities that result from the encapsulated query.
     * 
     * @param controller The controller used to build the Sensorthings entities
     * @return An ArrayList containing the results
     */
    public abstract ArrayList<SensorthingT> get(SensorthingController<SensorthingT> controller);

    /**
     * Encapsulates the construction of specific MultiNavigationLink objects.
     * 
     * @param <SensorthingT> A class that extends Sensorthings
     */
    public static class Builder<SensorthingT extends Sensorthing<SensorthingT>> {
        /**
         * Builds a new MultiNavigationLink from a JSONObject and tries to populate it using the
         * provided data.
         * 
         * @param urlKey     Key of the query url
         * @param dataKey    Key of any data associated with the query url (For example when json is
         *                   the result of an $expand query)
         * @param controller Controller used to build models from any data that is found
         * @param json       The JSONObject encapsulating the query
         * @return If data is present a MultiLocalLink instance, otherwise a MultiOnlineLink
         *         instance or null if no query url was specified
         */
        public MultiNavigationLink<SensorthingT> build(String urlKey, String dataKey,
                SensorthingController<SensorthingT> controller, JSONObject json) {
            if (!json.has(urlKey)) {
                return null;
            }

            String url = json.getString(urlKey);

            if (json.has(dataKey)) {
                return new MultiLocalLink<SensorthingT>(url, false,
                        controller.multiBuild(json.getJSONArray(dataKey)));
            }
            return new MultiOnlineLink<SensorthingT>(url, false);
        }
    }
}
