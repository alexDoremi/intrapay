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
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
public class AccountEntity {

  public AccountEntity(final Long id, final BigDecimal balance, final String currency,
      LocalDateTime createDate) {
    this.id = id;
    this.balance = balance;
    this.currency = currency;
    this.createDate = createDate;
  }

  public AccountEntity(final Long id, final BigDecimal balance, final String currency) {
    this.id = id;
    this.balance = balance;
    this.currency = currency;
  }

  public AccountEntity(final BigDecimal balance, final String currency) {
    this.balance = balance;
    this.currency = currency;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  private Long id;

  @NotNull
  @DecimalMin("0.0")
  @Getter
  @Setter
  private BigDecimal balance;

  @NotNull
  @Getter
  String currency;

  @NotNull
  @Getter
  private LocalDateTime createDate;
}
