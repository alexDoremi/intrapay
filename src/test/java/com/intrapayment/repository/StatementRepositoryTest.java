package com.intrapayment.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.intrapayment.repository.entity.AccountEntity;
import com.intrapayment.repository.entity.StatementEntity;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StatementRepositoryTest {

  private final Long ACCOUNT_CODE_888 = 888L;

  @Autowired
  private StatementRepository statementRepository;


  @Test
  void shouldSaveAndGetSameStatment() {
    StatementEntity statement = new StatementEntity(
        ACCOUNT_CODE_888,
        999L,
        BigDecimal.ONE,
        "EUR",
        LocalDateTime.now());

    assertTrue(statementRepository.findByCreditIdOrDebitId(ACCOUNT_CODE_888).isEmpty());

    statementRepository.save(statement);

    assertEquals(Collections.singletonList(statement),
        statementRepository.findByCreditIdOrDebitId(ACCOUNT_CODE_888));
  }
}