package com.intrapayment.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.intrapayment.repository.entity.AccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountRepositoryTest {

  private final Long ACCOUNT_CODE = 111L;

  @Autowired
  private AccountRepository accountRepository;

  @Test
  void shouldGetAccount() {
    assertTrue(
        accountRepository.findById(ACCOUNT_CODE)
            .map(AccountEntity::getId)
            .orElseThrow()
            .equals(ACCOUNT_CODE));
  }
}
