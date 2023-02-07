package com.intrapayment.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.intrapayment.exception.InvalidAccountIdException;
import com.intrapayment.exception.InvalidBalanceException;
import com.intrapayment.exception.InvalidCurrencyException;
import com.intrapayment.repository.AccountRepository;
import com.intrapayment.repository.StatementRepository;
import com.intrapayment.repository.entity.AccountEntity;
import com.intrapayment.repository.entity.StatementEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StatementServiceImplTest {

  private final String EUR = "EUR";
  private final String USD = "USD";

  private final Long ACCOUNT_CODE_111 = 111L;
  private final Long ACCOUNT_CODE_222 = 222L;


  @Mock
  private StatementRepository statementRepository;

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private StatementServiceImpl statementService;

  @Test
  void shouldGetStatement() {
    StatementEntity statement = new StatementEntity(
        ACCOUNT_CODE_111,
        ACCOUNT_CODE_222,
        BigDecimal.TEN,
        "EUR",
        LocalDateTime.now());
    when(accountRepository.existsById(ACCOUNT_CODE_111)).thenReturn(true);
    when(statementRepository.findByCreditIdOrDebitId(ACCOUNT_CODE_111)).thenReturn(
        Collections.singletonList(statement));
    statementService.getStatementsMini(ACCOUNT_CODE_111);
    assertTrue(statementService.getStatementsMini(ACCOUNT_CODE_111).get(0).getCreditId().equals(ACCOUNT_CODE_111));
  }

  @Test
  void shouldThrowExceptionIfAccountNotExistTestGetBalance() {
    when(accountRepository.existsById(ACCOUNT_CODE_111)).thenReturn(false);
    assertThrows(InvalidAccountIdException.class, () -> statementService.getStatementsMini(ACCOUNT_CODE_111));
  }

  @Test
  void shouldThrowExceptionIfCreditAccountNotExistTestDoTransfer() {
    assertThrows(InvalidAccountIdException.class,
        () -> statementService.doTransfer(ACCOUNT_CODE_111, ACCOUNT_CODE_222, BigDecimal.TEN));
  }

  @Test
  void shouldThrowExceptionIfDebitAccountNotExistTestDoTransfer() {
    when(accountRepository.findByIdWithLock(ACCOUNT_CODE_111)).thenReturn(Optional.empty());
    assertThrows(InvalidAccountIdException.class,
        () -> statementService.doTransfer(ACCOUNT_CODE_111, ACCOUNT_CODE_222, BigDecimal.TEN));
  }

  @Test
  void shouldThrowInvalidCurrencyExceptionDoTransfer() {
    when(accountRepository.findByIdWithLock(ACCOUNT_CODE_111)).thenReturn(
        getAccount(ACCOUNT_CODE_111, BigDecimal.TEN, EUR));
    when(accountRepository.findByIdWithLock(ACCOUNT_CODE_222)).thenReturn(
        getAccount(ACCOUNT_CODE_111, BigDecimal.TEN, USD));
    assertThrows(InvalidCurrencyException.class,
        () -> statementService.doTransfer(ACCOUNT_CODE_111, ACCOUNT_CODE_222, BigDecimal.TEN));
  }

  @Test
  void shouldThrowInvalidBalanceExceptionDoTransfer() {
    when(accountRepository.findByIdWithLock(ACCOUNT_CODE_111)).thenReturn(
        getAccount(ACCOUNT_CODE_111, BigDecimal.ZERO, EUR));
    when(accountRepository.findByIdWithLock(ACCOUNT_CODE_222)).thenReturn(
        getAccount(ACCOUNT_CODE_111, BigDecimal.TEN, EUR));
    assertThrows(InvalidBalanceException.class,
        () -> statementService.doTransfer(ACCOUNT_CODE_111, ACCOUNT_CODE_222, BigDecimal.TEN));
  }

  @Test
  void shouldDoTransfer() throws InterruptedException {
    Optional<AccountEntity> creditAccount = getAccount(ACCOUNT_CODE_111, BigDecimal.TEN, EUR);
    Optional<AccountEntity> debitAccount = getAccount(ACCOUNT_CODE_222, BigDecimal.TEN, EUR);
    when(accountRepository.findByIdWithLock(ACCOUNT_CODE_111)).thenReturn(creditAccount);
    when(accountRepository.findByIdWithLock(ACCOUNT_CODE_222)).thenReturn(debitAccount);

    StatementEntity statementResult = statementService
        .doTransfer(ACCOUNT_CODE_111, ACCOUNT_CODE_222, BigDecimal.TEN);

    assertTrue(creditAccount.get().getBalance().equals(BigDecimal.ZERO));
    assertTrue(debitAccount.get().getBalance().equals(new BigDecimal(20)));
    assertTrue(statementResult.getCreditId().equals(ACCOUNT_CODE_111));
    assertTrue(statementResult.getDebitId().equals(ACCOUNT_CODE_222));
    assertTrue(statementResult.getAmount().equals(BigDecimal.TEN));
    assertTrue(statementResult.getCurrency().equals(EUR));
  }

  private Optional<AccountEntity> getAccount(Long id, BigDecimal balance, String currency) {
    return Optional.of(new AccountEntity(id, balance, currency));
  }
}