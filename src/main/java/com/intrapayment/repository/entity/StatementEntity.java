package com.intrapayment.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class StatementEntity {

  public StatementEntity(Long creditId, Long debitId, BigDecimal amount, String currency,
      LocalDateTime date) {
    this.creditId = creditId;
    this.debitId = debitId;
    this.amount = amount;
    this.currency = currency;
    this.date = date;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  private Long id;

  @NotNull
  @Getter
  private Long creditId;

  @NotNull
  @Getter
  private Long debitId;

  @NotNull
  @Getter
  @Setter
  @DecimalMin("0.1")
  private BigDecimal amount;

  @NotNull
  @Getter
  String currency;

  @NotNull
  @Getter
  private LocalDateTime date;
}
