package com.intrapayment.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.intrapayment.exception.InvalidAccountIdException;
import com.intrapayment.repository.AccountRepository;
import com.intrapayment.repository.entity.AccountEntity;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

  private final String EUR = "EUR";
  private final Long ACCOUNT_CODE = 111L;

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountServiceImpl accountService;

  @Test
  void shouldGetAccountDetails() {
    when(accountRepository.findById(ACCOUNT_CODE))
        .thenReturn(Optional.of(new AccountEntity(BigDecimal.ZERO, EUR)));
    assertNotNull(accountService.getAccount(ACCOUNT_CODE));
  }

  @Test
  void shouldGetAccountBalance() {
    when(accountRepository.findById(ACCOUNT_CODE))
        .thenReturn(Optional.of(new AccountEntity(BigDecimal.ZERO, "EUR")));
    assertTrue(accountService.getAccount(ACCOUNT_CODE).getBalance().equals(BigDecimal.ZERO));
  }
}