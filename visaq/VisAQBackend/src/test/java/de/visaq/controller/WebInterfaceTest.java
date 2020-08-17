package de.visaq.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import de.visaq.VisAQ;

/**
 * Basic tests for the spring web-interface.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebInterfaceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertTrue(this.restTemplate
                .getForObject("http://localhost:" + port + "/version", String.class)
                .contains(VisAQ.VERSION));
    }

    @Test
    public void invalideUrlTest() throws Exception {
        // Expected Error-Code 404
        this.mockMvc.perform(get("/invalide")).andExpect(status().isNotFound());
    }

    @Test
    public void invalideRequestTypeTest() throws Exception {
        // Expected Error-Code 405
        this.mockMvc.perform(get("/api/thing/id")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void missingPostParameterTest() throws Exception {
        this.mockMvc.perform(post("/api/thing/id")).andExpect(status().isBadRequest());
    }

    @Test
    public void correctRequestParameterTest() throws Exception {
        this.mockMvc.perform(post("/api/thing/id").contentType(MediaType.APPLICATION_JSON).content(
                "{\"id\":\"saqn:t:umweltbundesamt.de:station_augsburg_bourges-platz:deby007\"}"))
                .andExpect(status().isOk());
    }

}
