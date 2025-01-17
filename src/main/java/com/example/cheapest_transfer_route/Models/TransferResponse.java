package com.example.cheapest_transfer_route.Models;

import java.util.List;

public record TransferResponse(List<Transfer> selectedTransfers,
                               int totalCost,
                               int totalWeight) {}
