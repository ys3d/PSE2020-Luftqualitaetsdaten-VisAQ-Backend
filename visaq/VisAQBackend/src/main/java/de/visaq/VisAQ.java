package de.visaq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Provides the entry point to the Spring backend.
 */
@SpringBootApplication
public class VisAQ {

    /**
     * Version of the server-software.
     */
    public static final String VERSION = "2020.08.28#1";

    /**
     * Spring application entry point.
     * 
     * @param args An array of arguments passed to the Spring Application on startup
     */
    public static void main(String[] args) {
        SpringApplication.run(VisAQ.class, args);
    }

}
