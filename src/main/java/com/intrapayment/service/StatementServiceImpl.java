package com.intrapayment.service;

import com.intrapayment.exception.InvalidAccountIdException;
import com.intrapayment.exception.InvalidBalanceException;
import com.intrapayment.exception.InvalidCurrencyException;
import com.intrapayment.repository.AccountRepository;
import com.intrapayment.repository.StatementRepository;
import com.intrapayment.repository.entity.AccountEntity;
import com.intrapayment.repository.entity.StatementEntity;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StatementServiceImpl implements StatementService{

  @Autowired
  private StatementRepository statementRepository;
  @Autowired
  private AccountRepository accountRepository;

  //fetch statement from db with accound id
  public List<StatementEntity> getStatementsMini(Long id) {
    log.info("LOG INFO: service - statements mini");
    if (!accountRepository.existsById(id)) {
      throw new InvalidAccountIdException(Long.toString(id));
    }

    return statementRepository
        .findByCreditIdOrDebitId(id);
  }

  //create new statement
  @Transactional
  public StatementEntity doTransfer(Long creditId, Long debitId, BigDecimal amount)
      throws InterruptedException {
    log.info("LOG INFO: service - statement - doTransfer");
    AccountEntity creditAccount = accountRepository
        .findByIdWithLock(creditId)
        .orElseThrow(() -> new InvalidAccountIdException(creditId.toString()));

    AccountEntity debitAccount = accountRepository
        .findByIdWithLock(debitId)
        .orElseThrow(() -> new InvalidAccountIdException(debitId.toString()));

    validateTransfer(creditAccount, debitAccount, amount);

    creditAccount.setBalance(creditAccount.getBalance().subtract(amount));
    debitAccount.setBalance(debitAccount.getBalance().add(amount));

    StatementEntity statement = new StatementEntity(
        creditId,
        debitId,
        amount,
        creditAccount.getCurrency(),
        LocalDateTime.now());

    statementRepository.save(statement);

    return statement;
  }

  //validate credit and debit account in terms of ccurrency and balance
  private void validateTransfer(
      AccountEntity creditAccount,
      AccountEntity debitAccount,
      BigDecimal amount) {

    if (!creditAccount.getCurrency().equals(debitAccount.getCurrency())) {
      throw new InvalidCurrencyException(
          String.format("Different currency between accounts: %s, %s",
              creditAccount.getCurrency(), debitAccount.getCurrency()));
    }

    if (creditAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidBalanceException(creditAccount.getId().toString());
    }
  }
}
