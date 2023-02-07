package com.intrapayment.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatementMiniResponseDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private BigDecimal amount;
  String currency;
  String type;
  private String date;
}
