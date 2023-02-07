package com.intrapayment.util.mapper;

import com.intrapayment.controller.dto.AccountBalanceResponseDTO;
import com.intrapayment.controller.dto.AccountDetailsResponseDTO;
import com.intrapayment.repository.entity.AccountEntity;

;

public class AccountMapper {

  public static AccountBalanceResponseDTO getAccountBalanceResponseDTO(
      AccountEntity accountEntity) {
    return AccountBalanceResponseDTO.builder()
        .id(accountEntity.getId())
        .balance(accountEntity.getBalance())
        .currency(accountEntity.getCurrency())
        .build();
  }

  public static AccountDetailsResponseDTO getAccountDetailsResponseDTO(
      AccountEntity accountEntity) {
    return AccountDetailsResponseDTO.builder()
        .id(accountEntity.getId())
        .balance(accountEntity.getBalance())
        .currency(accountEntity.getCurrency())
        .createDate(accountEntity.getCreateDate().toString())
        .build();
  }
}
