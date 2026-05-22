package com.bereketab.bank.core.ledger.web;

import com.bereketab.bank.core.ledger.api.spec.AccountApi;
import com.bereketab.bank.core.ledger.api.dto.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class AccountController implements AccountApi {

    @Override
    public ResponseEntity<AccountDto.AccountResponse> createAccount(AccountDto.CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new  AccountDto.AccountResponse(UUID.randomUUID(), BigDecimal.ZERO, request.currency(), "ACTIVE"));
    }

    @Override
    public ResponseEntity<AccountDto.AccountResponse> getAccount(UUID accountId) {
        return ResponseEntity.ok(
                new AccountDto.AccountResponse(accountId, new BigDecimal("150.00"), "PLN", "ACTIVE" )
        );
    }
}
