package com.bereketab.bank.core.ledger.api.spec;

import com.bereketab.bank.core.ledger.api.dto.TransferDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Transfers", description = "Money movement operation")
@RequestMapping("/api/v1/transfers")
public interface TransferApi {

    @Operation(summary = "Execute a transfer between two accounts")
    @ApiResponse(responseCode = "200", description = "Transfer successful or pending settlement")
    @ApiResponse(responseCode = "400", description = "Invalid request or insufficient funds")
    @PostMapping
    ResponseEntity<TransferDto.TransferResponse> executeTransfer(
            @Valid
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
            @RequestBody TransferDto.TransferRequest request);
}
