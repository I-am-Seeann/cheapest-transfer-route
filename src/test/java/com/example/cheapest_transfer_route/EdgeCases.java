package com.example.cheapest_transfer_route;

import com.example.cheapest_transfer_route.Models.Transfer;
import com.example.cheapest_transfer_route.Models.TransferRequest;
import com.example.cheapest_transfer_route.Models.TransferResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EdgeCases {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void responseStructureCheck() {
        // Given a valid request
        TransferRequest request = new TransferRequest(15, List.of(
                new Transfer(5, 10),
                new Transfer(10, 20)
        ));

        // When calling the /calculate-cheapest-route endpoint
        ResponseEntity<TransferResponse> response = restTemplate.postForEntity("/calculate-cheapest-route", request, TransferResponse.class);

        // Then verify response body contains expected fields
        assertThat(response.getBody()).hasOnlyFields("totalCost", "totalWeight", "selectedTransfers");
    }

    @Test
    void invalidRequest1() {
        // Given an invalid request (e.g., null value for required fields)
        TransferRequest request = new TransferRequest(0, null);

        // When calling the /calculate-cheapest-route endpoint
        ResponseEntity<String> response = restTemplate.postForEntity("/calculate-cheapest-route", request, String.class);

        // Then verify that the server responds with a BadRequest (400) error
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void invalidRequest2() {
        // Given an invalid request
        TransferRequest request = null;

        // When calling the /calculate-cheapest-route endpoint
        ResponseEntity<String> response = restTemplate.postForEntity("/calculate-cheapest-route", request, String.class);

        // Then verify that the server responds with a BadRequest (400) error
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
