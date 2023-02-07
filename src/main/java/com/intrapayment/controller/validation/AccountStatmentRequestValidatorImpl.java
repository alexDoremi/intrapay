package com.intrapayment.controller.validation;

import com.intrapayment.controller.dto.StatementRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountStatmentRequestValidatorImpl implements
    ConstraintValidator<AccountStatmentRequestValidator, StatementRequestDTO> {

  @Override
  public boolean isValid(StatementRequestDTO statementRequestDTO,
      ConstraintValidatorContext constraintValidatorContext) {
    if (!(statementRequestDTO instanceof StatementRequestDTO)) {
      throw new IllegalArgumentException(
          "@AccountStatmentRequestValidator only applies to StatementRequestDTO objects");
    }
    return !statementRequestDTO.getCreditId().equals(statementRequestDTO.getDebitId());
  }
}
