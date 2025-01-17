package com.example.cheapest_transfer_route.Models;

import java.util.List;

public record TransferRequest(int maxWeight,
                              List<Transfer> availableTransfers) {}
