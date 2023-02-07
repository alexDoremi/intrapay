package com.intrapayment.controller.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountBalanceResponseDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private Long id;
  private BigDecimal balance;
  private String currency;
}
