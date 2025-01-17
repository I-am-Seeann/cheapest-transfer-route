package com.example.cheapest_transfer_route.Services;

import com.example.cheapest_transfer_route.Models.TransferRequest;
import com.example.cheapest_transfer_route.Models.TransferResponse;

public interface TransferService {
    TransferResponse calculateCheapestRoute(TransferRequest request);
}
