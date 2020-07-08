package de.visaq.controller.link;

import org.json.JSONObject;

import de.visaq.controller.SensorthingController;
import de.visaq.model.sensorthings.Sensorthing;

/**
 * Encapsulates a Sensorthings query that can return a single Sensorthings entity.
 *
 * @param <SensorthingT> A class that extends Sensorthings
 */
public abstract class SingleNavigationLink<SensorthingT extends Sensorthing<SensorthingT>>
        extends NavigationLink<SensorthingT> {
    /**
     * Constructs a new NavigationLink to a Sensorthings query with the given url.
     * 
     * @param url      {@link NavigationLink#NavigationLink(String, boolean)}
     * @param relative {@link NavigationLink#NavigationLink(String, boolean)}
     */
    public SingleNavigationLink(String url, boolean relative) {
        super(url, relative);
    }

    /**
     * Returns the Sensorthings entity that results from the encapsulated query.
     * 
     * @param controller The controller used to build the Sensorthings entity
     * @return The Sensorthings entity
     */
    public abstract Sensorthing<SensorthingT> get(SensorthingController<SensorthingT> controller);

    /**
     * Encapsulates the construction of specific SingleNavigationLink object.
     * 
     * @param <SensorthingT> A class that extends Sensorthings
     */
    public static class Builder<SensorthingT extends Sensorthing<SensorthingT>> {
        /**
         * Builds a new SingleNavigationLink from a JSONObject and tries to populate it using the
         * provided data.
         * 
         * @param urlKey     Key of the query url
         * @param dataKey    Key of any data associated with the query url (For example when json is
         *                   the result of an $expand query)
         * @param controller Controller used to build a model from any data that is found
         * @param json       The JSONObject encapsulating the query
         * @return If data is present a SingleLocalLink instance, otherwise a SingleOnlineLink
         *         instance or null if no query url was specified
         */
        public SingleNavigationLink<SensorthingT> build(String urlKey, String dataKey,
                SensorthingController<SensorthingT> controller, JSONObject json) {
            if (!json.has(urlKey)) {
                return null;
            }

            String url = json.getString(urlKey);

            if (json.has(dataKey)) {
                return new SingleLocalLink<SensorthingT>(url, false,
                        controller.singleBuild(json.getJSONObject(dataKey)));
            }
            return new SingleOnlineLink<SensorthingT>(url, false);
        }
    }
}
