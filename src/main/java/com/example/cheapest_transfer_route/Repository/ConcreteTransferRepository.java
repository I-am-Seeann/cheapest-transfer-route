package com.example.cheapest_transfer_route.Repository;

import com.example.cheapest_transfer_route.Models.Transfer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Component
public class ConcreteTransferRepository implements  TransferRepository{
    private List<Transfer> unusedTransfers = new ArrayList<Transfer>();

    public List<Transfer> getUnusedTransfers() {
        return unusedTransfers;
    }
}
