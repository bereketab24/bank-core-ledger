package com.bereketab.bank.core.ledger.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public final class TransferDto {
    private TransferDto (){}

    public record TransferRequest(
            String debtorIban,
            String creditorIban,
            BigDecimal amount,
            String currency
    ){}

    public record TransferResponse(
            UUID transactionId,
            String status,
            String message
    ){}
}
