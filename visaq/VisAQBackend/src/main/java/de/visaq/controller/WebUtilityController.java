package de.visaq.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.visaq.VisAQ;

/**
 * Provides functions to check the spring application from the web interface.
 */
@RestController
public class WebUtilityController {
    /**
     * Returns the server-software-version.
     * 
     * @return The software-version
     */
    @GetMapping(value = "/version", produces = MediaType.TEXT_PLAIN_VALUE)
    public String versionPage() {
        return "Currently running on Version " + VisAQ.VERSION;
    }

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Duration test() {
        return Duration.ofHours(12);
    }
}
