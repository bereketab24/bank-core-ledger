package com.bereketab.bank.core.ledger.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public final class TransferDto {
    private TransferDto (){}

    public record TransferRequest(
            UUID senderAccountId,
            UUID receiverAccountId,
            BigDecimal amount,
            String currency
    ){}

    public record TransferResponse(
            UUID transactionId,
            String status,
            String message
    ){}
}
