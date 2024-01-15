package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FizzBuzzApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FizzBuzzApplicationTest {

    @Autowired
    private FizzBuzzApplication fizzBuzzApplication;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    /**
     * Test case to ensure that the application context loads successfully.
     */
    @Test
    public void testContextLoads() {
        assertNotNull(fizzBuzzApplication);
    }

    /**
     * Test case to ensure that the main method starts the Spring application without exceptions.
     */
    @Test
    public void testMainMethodStartsApplication() {
        FizzBuzzApplication.main(new String[]{});
        // If the application starts without any exceptions, the test passes
    }

    /**
     * Test case for the /fizzbuzz endpoint to validate the returned result.
     */
    @Test
    public void testFizzBuzzEndpointReturnsResult() {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(getBaseUrl() + "/fizzbuzz?int1=3&int2=5&limit=15&str1=fizz&str2=buzz", List.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Validate the response structure or specific values if needed
        List responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());
    }

    /**
     * Test case for the /fizzbuzz endpoint to handle invalid parameters.
     */
    @Test
    public void testFizzBuzzEndpointHandlesInvalidParameters() {
        // Test the /fizzbuzz endpoint with invalid parameters
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                getBaseUrl() + "/fizzbuzz?int1=invalid&int2=5&limit=15&str1=fizz&str2=buzz", String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody())
                .contains("Invalid parameter type. Please provide valid integers for int1, int2, and limit."));
    }

    /**
     * Test case for the /fizzbuzz endpoint to handle missing parameters.
     */
    @Test
    public void testFizzBuzzEndpointHandlesMissingParameters() {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(
                getBaseUrl() + "/fizzbuzz?int1=3&int2=5", List.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Validate the response structure or specific values if needed
        List responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertFalse(responseBody.isEmpty());

        // Check if the list contains the expected error message
        assertTrue(responseBody.contains("All parameters must be provided"));
    }

    /**
     * Test case for the /statistics endpoint to validate the returned result.
     */
    @Test
    public void testStatisticsEndpointReturnsResult() {
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(getBaseUrl() + "/statistics", Map.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Validate the response structure or specific values if needed
        Map responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("mostUsedRequest"));
        assertTrue(responseBody.containsKey("mostUsedHits"));
    }

    // Helper method to get the base URL
    private String getBaseUrl() {
        return "http://localhost:" + port;
    }
}
