package com.bereketab.bank.core.ledger.service;

import com.bereketab.bank.core.ledger.api.LedgerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LedgerServiceImpl implements LedgerService {

    @Override
    public void settleTransaction(UUID transactionId) {
    }

    @Override
    public void failTransaction(UUID transactionId, String failureReason) {
    }
}