package com.intrapayment.controller;

import com.intrapayment.controller.dto.AccountBalanceResponseDTO;
import com.intrapayment.controller.dto.AccountDetailsResponseDTO;
import com.intrapayment.controller.dto.StatementMiniResponseDTO;
import com.intrapayment.util.mapper.*;
import com.intrapayment.service.AccountService;
import com.intrapayment.service.StatementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  AccountService accountService;

  @Autowired
  StatementService statementService;


  /**
   *
   * getAccountDetails
   *
   * @param id fetch services with given id
   *
   * @return AccountDetailsResponseDTO response with account details DTO
   */
  @GetMapping("/{id}")
  public @ResponseBody
  AccountDetailsResponseDTO getAccountDetails(@Valid @PathVariable @NotNull Long id) {
    log.info("LOG INFO: /accounts/{} - get request - account details", id);
    //Preconditions.checkNotNull(resource);
    return AccountMapper.getAccountDetailsResponseDTO(accountService.getAccount(id));
  }

  /**
   *
   * getAccountBalance
   *
   * @param id fetch services with given id
   *
   * @return AccountBalanceResponseDTO response with account balance - limited details DTO
   */
  @GetMapping("/{id}/balance")
  public @ResponseBody
  AccountBalanceResponseDTO getAccountBalance(@PathVariable @NotNull Long id) {
    log.info("LOG INFO: /accounts/{}/balance - get request - account balance", id);
    return AccountMapper.getAccountBalanceResponseDTO(accountService.getAccount(id));
  }

  /**
   *
   * getAccountBalance
   *
   * @param id fetch services with given id
   *
   * @return Collection of StatementMiniResponseDTO response
   */
  @GetMapping("/{id}/statements/mini")
  public @ResponseBody
  List<StatementMiniResponseDTO> getStatementsMini(@PathVariable @NotNull Long id) {
    log.info("LOG INFO: /accounts/{}/statements/mini - get request - account details", id);
    return statementService.getStatementsMini(id)
        .stream()
        .map(s -> StatementMapper.getStatementMiniResponseDTO(s, id))
        .collect(Collectors.toList());
  }
}
