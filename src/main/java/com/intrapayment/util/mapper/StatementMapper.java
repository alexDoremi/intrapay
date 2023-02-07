package com.intrapayment.util.mapper;

import com.intrapayment.controller.dto.StatementMiniResponseDTO;
import com.intrapayment.controller.dto.StatementResponseDTO;
import com.intrapayment.repository.entity.StatementEntity;
import com.intrapayment.util.constant.*;

public class StatementMapper {

  public static StatementMiniResponseDTO getStatementMiniResponseDTO(StatementEntity statementEntity,
      Long id) {

    Long acctId = statementEntity.getCreditId();
    String type = Constants.TYPE_CREDIT;

    if (statementEntity.getCreditId().equals(id)) {
      acctId = statementEntity.getDebitId();
      type = Constants.TYPE_DEBIT;
    }

    return StatementMiniResponseDTO.builder()
        .id(acctId)
        .amount(statementEntity.getAmount())
        .currency(statementEntity.getCurrency())
        .type(type)
        .date(statementEntity.getDate().toString())
        .build();
  }

  public static StatementResponseDTO getStatementResponseDTO(StatementEntity statementEntity) {

    return StatementResponseDTO.builder()
        .id(statementEntity.getId())
        .creditId(statementEntity.getCreditId())
        .debitId(statementEntity.getDebitId())
        .amount(statementEntity.getAmount())
        .currency(statementEntity.getCurrency())
        .date(statementEntity.getDate().toString())
        .build();
  }
}
