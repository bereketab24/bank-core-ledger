package com.bereketab.bank.core.ledger.mapper;

import com.bereketab.bank.core.ledger.api.dto.AccountDto;
import com.bereketab.bank.core.ledger.domain.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountMapperTest {

    private final AccountMapper mapper = AccountMapper.INSTANCE;

    @Test
    void toEntity_validRequest_mapsToActiveAccountWithZeroBalance(){
        AccountDto.CreateAccountRequest request = new AccountDto.CreateAccountRequest("USD");

        Account entity = mapper.toEntity(request);

        assertThat(entity.getCurrency()).isEqualTo("USD");
        assertThat(entity.getBalance()).isEqualTo(new BigDecimal("0.0000"));
        assertThat(entity.getStatus()).isEqualTo("ACTIVE");

        assertThat(entity.getId()).isNull();
    }
}
