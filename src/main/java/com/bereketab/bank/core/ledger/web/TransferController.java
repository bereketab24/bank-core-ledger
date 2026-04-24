package com.bereketab.bank.core.ledger.web;

import com.bereketab.bank.core.ledger.api.TransferApi;
import com.bereketab.bank.core.ledger.api.dto.TransferDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TransferController implements TransferApi {

    @Override
    public ResponseEntity<TransferDto.TransferResponse> executeTransfer(String idempotencyKey, TransferDto.TransferRequest request) {
        return ResponseEntity.ok(
                new TransferDto.TransferResponse(UUID.randomUUID(), "PENDING", "Transfer accepted for processing")
        );
    }
}
