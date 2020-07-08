package de.visaq;

import java.io.IOException;
import java.util.Properties;

/**
 * Contains constants that are used in REST communication.
 */
public final class RestConstants {
    public static final String ENTRY_POINT;

    static {
        String entryPoint;
        Properties p = new Properties();
        try {
            p.load(RestConstants.class.getResourceAsStream("/rest_constants.properties"));
            entryPoint = p.getProperty("entry_point");
        } catch (IOException e) {
            entryPoint = null;
        }
        ENTRY_POINT = entryPoint;
    }

    private RestConstants() {
        throw new IllegalStateException();
    }
}
