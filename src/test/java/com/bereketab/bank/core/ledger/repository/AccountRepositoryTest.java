package com.bereketab.bank.core.ledger.repository;


import com.bereketab.bank.core.ledger.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Container
    @ServiceConnection
    private final static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:15-alpine");

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    void findByIdForUpdate_existingAccount_returnsLockedAccount(){
        Account account = new Account();

        account.setBalance(new BigDecimal("100.0000"));
        account.setCurrency("USD");
        account.setStatus("ACTIVE");
        Account savedAccount = testEntityManager.persistAndFlush(account);

        Optional<Account> lockedAccount = accountRepository.findByIdForUpdate(savedAccount.getId());

        assertThat(lockedAccount).isPresent();
        assertThat(lockedAccount.get().getBalance()).isEqualByComparingTo("100.0000");
    }
}
