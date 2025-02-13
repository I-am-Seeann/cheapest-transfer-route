package com.example.cheapest_transfer_route.Services;

import com.example.cheapest_transfer_route.Models.Transfer;
import com.example.cheapest_transfer_route.Models.TransferRequest;
import com.example.cheapest_transfer_route.Models.TransferResponse;
import com.example.cheapest_transfer_route.Repository.ConcreteTransferRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class TransferServiceImpKnapsack implements TransferService {

    private ConcreteTransferRepository concreteTransferRepository;
    public TransferServiceImpKnapsack(ConcreteTransferRepository concreteTransferRepository) {
        this.concreteTransferRepository = concreteTransferRepository;
    }

    @Override
    public TransferResponse calculateCheapestRoute(TransferRequest request) {

        int maxWeight = request.maxWeight();
        List<Transfer> transfers = request.availableTransfers();
        List<Transfer> selectedTransfers = new ArrayList<>();

        int usedWeight = actualLogic(maxWeight, selectedTransfers, transfers);

        if(usedWeight > 0) {
            actualLogic(maxWeight - usedWeight, selectedTransfers, concreteTransferRepository.getUnusedTransfers());
            concreteTransferRepository.getUnusedTransfers().removeAll(selectedTransfers);
        }
        for(Transfer transfer : transfers) {
            if (!selectedTransfers.contains(transfer)) {
                concreteTransferRepository.getUnusedTransfers().add(transfer);
            }
        }

        Collections.reverse(selectedTransfers);
        int finalCost =  selectedTransfers.stream().mapToInt(i -> i.cost()).sum();
        int finalWight =  selectedTransfers.stream().mapToInt(i -> i.weight()).sum();
        return new TransferResponse(selectedTransfers, finalCost, finalWight);
    }

    private int actualLogic(int maxWeight, List<Transfer> selectedTransfers, List<Transfer> transfers){
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
//            else{
//                Transfer transfer = transfers.get(i - 1);
//                concreteTransferRepository.getUnusedTransfers().remove(transfer);
//
//                concreteTransferRepository.getUnusedTransfers().add(transfer);
//            }
        }

        return totalWeight;
    }
}
