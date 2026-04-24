package com.bereketab.bank.core.ledger.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public final class AccountDto {
    private AccountDto(){}

    public record CreateAccountRequest(String currency){}

    public record AccountResponse(
            UUID id,
            BigDecimal balance,
            String currency,
            String status
    ){}
}
