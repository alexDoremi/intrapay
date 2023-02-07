package com.intrapayment.service;

import com.intrapayment.exception.InvalidAccountIdException;
import com.intrapayment.repository.AccountRepository;
import com.intrapayment.repository.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  //fetch Account by ID from DB
  public AccountEntity getAccount(Long id) {
    log.info("LOG INFO: service - account");
    return accountRepository
        .findById(id)
        .orElseThrow(() -> new InvalidAccountIdException(id.toString()));
  }
}
