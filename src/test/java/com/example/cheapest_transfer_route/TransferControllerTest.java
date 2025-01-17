package com.example.cheapest_transfer_route;


import com.example.cheapest_transfer_route.Controllers.TransferController;
import com.example.cheapest_transfer_route.Models.Transfer;
import com.example.cheapest_transfer_route.Models.TransferRequest;
import com.example.cheapest_transfer_route.Models.TransferResponse;
import com.example.cheapest_transfer_route.Services.TransferServiceImpKnapsack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TransferController.class)
public class TransferControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransferServiceImpKnapsack transferService;

    @InjectMocks
    private TransferController transferController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    }

    @Test
    public void CalculateTransfers() throws Exception {
        TransferRequest request = new TransferRequest(15, List.of(
                new Transfer(5, 10),
                new Transfer(10, 20),
                new Transfer(3, 5),
                new Transfer(8, 15)
        ));

        TransferResponse response = new TransferResponse(
                List.of(new Transfer(5, 10), new Transfer(10, 20)),
                30, 15
        );

        // Mock the service call to return a TransferResponse
        when(transferService.calculateCheapestRoute(request)).thenReturn(response);

        // Act & Assert: Perform the POST request and verify the response
        mockMvc.perform(post("/calculate-cheapest-route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"maxWeight\": 15, \"availableTransfers\": [ { \"weight\": 5, \"cost\": 10 }, { \"weight\": 10, \"cost\": 20 }, { \"weight\": 3, \"cost\": 5 }, { \"weight\": 8, \"cost\": 15 } ] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(30))
                .andExpect(jsonPath("$.totalWeight").value(15))
                .andExpect(jsonPath("$.selectedTransfers.length()").value(2));
    }

}
