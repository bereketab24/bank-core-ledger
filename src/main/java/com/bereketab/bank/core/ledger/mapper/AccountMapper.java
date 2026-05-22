package com.bereketab.bank.core.ledger.mapper;

import com.bereketab.bank.core.ledger.api.dto.AccountDto;
import com.bereketab.bank.core.ledger.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "balance", constant = "0.0000")
    @Mapping(target = "status", constant = "ACTIVE")
    Account toEntity(AccountDto.CreateAccountRequest request);

}
