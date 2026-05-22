package com.bereketab.bank.core.ledger.api;

import java.util.UUID;

public interface LedgerService {

    void settleTransaction(UUID transactionId);
    void failTransaction(UUID transactionId, String failureReason);
}
