package com.example.cheapest_transfer_route.Controllers;

import com.example.cheapest_transfer_route.Models.TransferRequest;
import com.example.cheapest_transfer_route.Models.TransferResponse;
import com.example.cheapest_transfer_route.Repository.ConcreteTransferRepository;
import com.example.cheapest_transfer_route.Services.TransferService;
import com.example.cheapest_transfer_route.Services.TransferServiceImpKnapsack;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate-cheapest-route")
public class TransferController {
    private TransferService transferService;

    public TransferController(TransferService transferService){
        this.transferService = transferService;
    }

    @PostMapping()
    public ResponseEntity<TransferResponse> calculateTransfers( @RequestBody TransferRequest request) {

        if (!isValidRequest(request)) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(transferService.calculateCheapestRoute(request));

    }

    private boolean isValidRequest(TransferRequest request) {
        return request != null
                && request.maxWeight() >= 0
                && request.availableTransfers() != null
                && request.availableTransfers().stream().noneMatch(t -> t.weight() < 0 || t.cost() < 0);
    }

}
