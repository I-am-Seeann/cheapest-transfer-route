package com.example.cheapest_transfer_route;

import com.example.cheapest_transfer_route.Controllers.TransferController;
import com.example.cheapest_transfer_route.Models.Transfer;
import com.example.cheapest_transfer_route.Models.TransferRequest;
import com.example.cheapest_transfer_route.Models.TransferResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransferControllerTests {

	@Autowired
	TransferController transferController;

	// MethodSource to provide the input and expected output for the parameterized test
	public static List<Object[]> transferTestData() {
		return Arrays.asList(new Object[][]{
				// Test 1: Empty transfer list
				{
						new TransferRequest(10, List.of()),
						new TransferResponse(List.of(), 0, 0)
				},

				// Test 2: Max weight too low to include any transfers
				{
						new TransferRequest(5, List.of(
								new Transfer(10, 20),
								new Transfer(15, 30)
						)),
						new TransferResponse(List.of(), 0, 0)
				},

				// Test 3: All transfers fit within max weight
				{
						new TransferRequest(50, List.of(
								new Transfer(10, 20),
								new Transfer(20, 30),
								new Transfer(15, 25)
						)),
						new TransferResponse(List.of(
								new Transfer(10, 20),
								new Transfer(20, 30),
								new Transfer(15, 25)
						), 75, 45)
				},

				// Test 4: Knapsack is full but some items cannot be included
				{
						new TransferRequest(25, List.of(
								new Transfer(10, 60),
								new Transfer(20, 100),
								new Transfer(15, 120),
								new Transfer(5, 50)
						)),
						new TransferResponse(List.of(
								new Transfer(10, 60),
								new Transfer(15, 120)
						), 180, 25)
				},

				// Test 5: Prioritize cost over weight
				{
						new TransferRequest(10, List.of(
								new Transfer(15, 100),
								new Transfer(10, 50),
								new Transfer(8, 60)
						)),
						new TransferResponse(List.of(
								new Transfer(8, 60)
						), 60, 8)
				},

				// Test 6: Max weight is 0
				{
						new TransferRequest(0, List.of(
								new Transfer(10, 20),
								new Transfer(5, 15)
						)),
						new TransferResponse(List.of(), 0, 0)
				},

				// Test 7: items with same weight and same cost
				{
						new TransferRequest(30, List.of(
							new Transfer(20, 200),
							new Transfer(10, 20),
							new Transfer(10, 20),
							new Transfer(10, 20)
						)),
						new TransferResponse(List.of(
								new Transfer(20, 200),
								new Transfer(10, 20)
						), 220, 30)
				}
		});
	}

	// The parameterized test with MethodSource
	@ParameterizedTest
	@MethodSource("transferTestData")
	void testCalculateCheapestRoute(TransferRequest request, TransferResponse expectedResponse) {
		// Act: Call the service method
		ResponseEntity<TransferResponse> response = transferController.calculateTransfers(request);

		// Assert: Check if the response matches the expected response
		assertThat(response.getBody().selectedTransfers()).isEqualTo(expectedResponse.selectedTransfers());
		assertThat(response.getBody().totalCost()).isEqualTo(expectedResponse.totalCost());
		assertThat(response.getBody().totalWeight()).isEqualTo(expectedResponse.totalWeight());
	}
}
