package com.bereketab.bank.core.ledger.api.spec;

import com.bereketab.bank.core.ledger.api.dto.AccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Accounts", description = "Core Ledger Account Operation")
@RequestMapping("/api/v1/accounts")
public interface AccountApi {

    @Operation(summary = "Open a new ledger account")
    @ApiResponse(responseCode = "201", description = "Account successfully created")
    @PostMapping
    ResponseEntity<AccountDto.AccountResponse> createAccount(@RequestBody AccountDto.CreateAccountRequest request);


    @Operation(summary = "Retrieve current account balance and status")
    @ApiResponse(responseCode = "200", description = "Account retrieved")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @GetMapping("/{accountId}")
    ResponseEntity<AccountDto.AccountResponse> getAccount(@PathVariable("accountId") UUID accountId);
}
