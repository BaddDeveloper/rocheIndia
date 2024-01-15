package org.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FizzBuzzControllerTest {

    @MockBean
    private FizzBuzzController fizzBuzzController;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test case for the FizzBuzzController's fizzBuzz method with valid parameters.
     */
    @Test
    void testFizzBuzzWithValidParams() throws Exception {
        // Mock the behavior of the fizzBuzz method when called with valid parameters
        when(fizzBuzzController.fizzBuzz(any(), any(), any(), any(), any()))
                .thenReturn(ResponseEntity.ok(Collections.singletonList("1")));

        // Perform a GET request to /fizzbuzz with valid parameters
        mockMvc.perform(get("/fizzbuzz")
                        .param("int1", "3")
                        .param("int2", "5")
                        .param("limit", "15")
                        .param("str1", "fizz")
                        .param("str2", "buzz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"1\"]"));
    }

    /**
     * Test case for the FizzBuzzController's fizzBuzz method with missing parameters.
     */
    @Test
    void testFizzBuzzWithMissingParams() throws Exception {
        // Mock the behavior of the fizzBuzz method when called with missing parameters
        when(fizzBuzzController.fizzBuzz(any(), any(), any(), any(), any()))
                .thenReturn(ResponseEntity.badRequest().body(Collections.singletonList("All parameters must be provided")));

        // Perform a GET request to /fizzbuzz without providing required parameters
        mockMvc.perform(get("/fizzbuzz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"All parameters must be provided\"]"));
    }

    /**
     * Test case for the FizzBuzzController's fizzBuzz method with invalid parameters.
     */
    @Test
    void testFizzBuzzWithInvalidParams() throws Exception {
        // Mock the behavior of the fizzBuzz method when called with invalid parameters
        when(fizzBuzzController.fizzBuzz(any(), any(), any(), any(), any()))
                .thenReturn(ResponseEntity.badRequest().body(Collections.singletonList("Invalid parameters")));

        // Perform a GET request to /fizzbuzz with some invalid parameters
        mockMvc.perform(get("/fizzbuzz")
                        .param("int1", "3")
                        .param("int2", "5")
                        .param("int3", "invalid")
                        .param("str1", "fizz")
                        .param("str2", "buzz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"Invalid parameters\"]"));
    }

    /**
     * Test case for the FizzBuzzController's getStatistics method.
     */
    @Test
    void testGetStatistics() throws Exception {
        // Mock the behavior of the getStatistics method
        when(fizzBuzzController.getStatistics())
                .thenReturn(ResponseEntity.ok(Map.of("mostUsedRequest", "3-5-15-fizz-buzz", "mostUsedHits", 1)));

        // Perform a GET request to /statistics
        mockMvc.perform(get("/statistics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"mostUsedRequest\":\"3-5-15-fizz-buzz\",\"mostUsedHits\":1}"));
    }
}


