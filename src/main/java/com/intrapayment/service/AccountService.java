package com.intrapayment.service;

import com.intrapayment.repository.entity.AccountEntity;

public interface AccountService {

  public AccountEntity getAccount(Long id);
}
