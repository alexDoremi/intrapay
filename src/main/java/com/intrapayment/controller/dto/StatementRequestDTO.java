package com.intrapayment.controller.dto;

import com.intrapayment.controller.validation.AccountStatmentRequestValidator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AccountStatmentRequestValidator
@ToString
@Builder
public class StatementRequestDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  @NotNull
  @Getter
  private Long creditId;
  @NotNull
  @Getter
  private Long debitId;
  @NotNull
  @DecimalMin(value = "0.1", message = "Invalid minimum value")
  @Getter
  private BigDecimal amount;
}