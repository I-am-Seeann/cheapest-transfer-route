package com.example.cheapest_transfer_route.Controllers;

import com.example.cheapest_transfer_route.Services.TransferServiceImpKnapsack;
import com.example.cheapest_transfer_route.Models.TransferRequest;
import com.example.cheapest_transfer_route.Models.TransferResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/calculate-cheapest-route")
public class TransferController {

    @PostMapping()
    public ResponseEntity<TransferResponse> calculateTransfers(@RequestBody TransferRequest request) {
        TransferServiceImpKnapsack service = new TransferServiceImpKnapsack();
        TransferResponse tr = service.calculateCheapestRoute(request);
        if (tr != null) {
            return ResponseEntity.ok(tr);
        }
        return ResponseEntity.badRequest().build();
    }
}
