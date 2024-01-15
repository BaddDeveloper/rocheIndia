package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class FizzBuzzController {

    // Store statistics for FizzBuzz requests
    private static final Map<String, Integer> statistics = new HashMap<>();

    @GetMapping("/fizzbuzz")
    public ResponseEntity<List<String>> fizzBuzz(
            @RequestParam(required = false) String int1,
            @RequestParam(required = false) String int2,
            @RequestParam(required = false) String limit,
            @RequestParam(required = false) String str1,
            @RequestParam(required = false) String str2
    ) {
        // Type checks for parameters
        try {
            Integer int1Value = null;
            Integer int2Value = null;
            Integer limitValue = null;

            // Parse integer parameters if provided
            if (int1 != null) {
                int1Value = Integer.parseInt(int1);
            }
            if (int2 != null) {
                int2Value = Integer.parseInt(int2);
            }
            if (limit != null) {
                limitValue = Integer.parseInt(limit);
            }

            // Check if all required parameters are provided
            if (int1Value == null || int2Value == null || limitValue == null || str1 == null || str2 == null) {
                return ResponseEntity.badRequest().body(Collections.singletonList("All parameters must be provided"));
            }

            // FizzBuzz logic
            List<String> result = new ArrayList<>();
            for (int num = 1; num <= limitValue; num++) {
                if (num % int1Value == 0 && num % int2Value == 0) {
                    result.add((str1 + str2).trim());
                } else if (num % int1Value == 0) {
                    result.add(str1.trim());
                } else if (num % int2Value == 0) {
                    result.add(str2.trim());
                } else {
                    result.add(Integer.toString(num));
                }
            }

            // Update statistics for the current request
            String key = int1Value + "-" + int2Value + "-" + limitValue + "-" + str1 + "-" + str2.trim();
            statistics.put(key, statistics.getOrDefault(key, 0) + 1);

            return ResponseEntity.ok(result);
        } catch (NumberFormatException e) {
            // Handle invalid parameter types
            return ResponseEntity.badRequest().body(Collections.singletonList("Invalid parameter type. Please provide valid integers for int1, int2, and limit."));
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        // Get the most used request from the statistics
        String mostUsedRequest = statistics.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No requests yet");

        // Get the number of hits for the most used request
        int mostUsedHits = statistics.getOrDefault(mostUsedRequest, 0);

        // Prepare the statistics response
        Map<String, Object> response = new HashMap<>();
        response.put("mostUsedRequest", mostUsedRequest);
        response.put("mostUsedHits", mostUsedHits);

        return ResponseEntity.ok(response);
    }
}
