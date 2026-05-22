package com.bereketab.bank.core.ledger.api.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionInitiatedEvent(
        UUID transactionId,
        String debtorIban,
        String creditorIban,
        BigDecimal amount,
        String currency,
        Instant initiatedAt
) { }
