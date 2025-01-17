package com.example.cheapest_transfer_route.Services;

import com.example.cheapest_transfer_route.Models.Transfer;
import com.example.cheapest_transfer_route.Models.TransferRequest;
import com.example.cheapest_transfer_route.Models.TransferResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransferServiceImpKnapsack implements TransferService {
    @Override
    public TransferResponse calculateCheapestRoute(TransferRequest request) {

        int maxWeight = request.maxWeight();
        List<Transfer> transfers = request.availableTransfers();
        int n = transfers.size();

        // Convert weights and costs into arrays for easier processing
        int[] weights = new int[n];
        int[] costs = new int[n];

        for (int i = 0; i < n; i++) {
            weights[i] = transfers.get(i).weight();
            costs[i] = transfers.get(i).cost();
        }

        // Create DP table
        int[][] dp = new int[n + 1][maxWeight + 1];

        // Build the table in a bottom-up manner
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= maxWeight; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(costs[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Find the selected transfers by backtracking the DP table
        ArrayList<Transfer> selectedTransfers = new ArrayList<>();
        int totalCost = dp[n][maxWeight];
        int totalWeight = 0;

        for (int i = n, w = maxWeight; i > 0 && totalCost > 0; i--) {
            if (totalCost != dp[i - 1][w]) {
                // Item was included in the optimal solution
                Transfer transfer = transfers.get(i - 1);
                selectedTransfers.add(transfer);
                totalCost -= transfer.cost();
                totalWeight += transfer.weight();
                w -= weights[i - 1];
            }
        }

        Collections.reverse(selectedTransfers);
        return new TransferResponse(selectedTransfers, dp[n][maxWeight], totalWeight);
    }
}
