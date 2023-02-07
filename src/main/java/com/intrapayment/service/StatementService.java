package com.intrapayment.service;

import com.intrapayment.repository.entity.StatementEntity;
import java.math.BigDecimal;
import java.util.List;

public interface StatementService {

  public List<StatementEntity> getStatementsMini(Long id);

  public StatementEntity doTransfer(Long creditId, Long debitId, BigDecimal amount)
      throws InterruptedException;
}
