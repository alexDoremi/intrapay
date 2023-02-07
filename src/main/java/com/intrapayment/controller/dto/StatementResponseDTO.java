package com.intrapayment.controller.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatementResponseDTO {
  private Long id;
  private Long creditId;
  private Long debitId;
  private BigDecimal amount;
  String currency;
  private String date;
}
