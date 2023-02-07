package com.intrapayment.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrapayment.exception.InvalidAccountIdException;
import com.intrapayment.exception.InvalidBalanceException;
import com.intrapayment.repository.AccountRepository;
import com.intrapayment.repository.entity.AccountEntity;
import com.intrapayment.repository.entity.StatementEntity;
import com.intrapayment.service.AccountServiceImpl;
import com.intrapayment.service.StatementServiceImpl;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.intrapayment.util.mapper.*;


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

  private final String EUR = "EUR";
  private final String INVALID_ACCOUNT_MSG = "Invalid Account Number: ";
  private final String INVALID_BALANCE_MSG = "Invalid Balance Amount: ";
  private final String INVALID_TYPE_MSG = "Invalid request parameter for: ";
  private final Long ACCOUNT_CODE = 111L;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  AccountServiceImpl accountService;

  @MockBean
  StatementServiceImpl statementService;

  @Test
  void shouldReturnAccountDetails() throws Exception {

    AccountEntity account = getAccount();
    when(accountService.getAccount(ACCOUNT_CODE)).thenReturn(account);

    mockMvc.perform(get("/accounts/{id}", ACCOUNT_CODE))
        //.andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(
            objectMapper.writeValueAsString(AccountMapper.getAccountDetailsResponseDTO(account))));
  }

  @Test
  void shouldReturnAccountBalance() throws Exception {

    AccountEntity account = getAccount();
    when(accountService.getAccount(ACCOUNT_CODE)).thenReturn(account);

    mockMvc.perform(get("/accounts/{id}/balance", ACCOUNT_CODE))
        //.andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(
            objectMapper.writeValueAsString(AccountMapper.getAccountBalanceResponseDTO(account))));
  }

  @Test
  void shouldReturnAccountMiniStatement() throws Exception {

    List<StatementEntity> statementsMiniList = getStatementsMiniList();
    when(statementService.getStatementsMini(ACCOUNT_CODE)).thenReturn(statementsMiniList);

    mockMvc.perform(get("/accounts/{id}/statements/mini", ACCOUNT_CODE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(
            objectMapper.writeValueAsString(statementsMiniList.stream()
                .map(s -> StatementMapper.getStatementMiniResponseDTO(s, ACCOUNT_CODE))
                .collect(Collectors.toList()))));
  }

  @Test
  void shouldReturnAccountMiniStatementEmpty() throws Exception {

    List<StatementEntity> statementsMiniList = getStatementsMiniList();
    when(statementService.getStatementsMini(ACCOUNT_CODE)).thenReturn(statementsMiniList);

    mockMvc.perform(get("/accounts/{id}/statements/mini", ACCOUNT_CODE))
        //.andDo(print())
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(
            objectMapper.writeValueAsString(statementsMiniList.stream()
                .map(s -> StatementMapper.getStatementMiniResponseDTO(s, ACCOUNT_CODE))
                .collect(Collectors.toList()))));
  }

  @Test
  void shouldThrowInvalidAccountIdException() throws Exception {

    when(accountService.getAccount(ACCOUNT_CODE)).thenThrow(
        new InvalidAccountIdException(ACCOUNT_CODE.toString()));

    mockMvc.perform(get("/accounts/{id}", ACCOUNT_CODE))
        //.andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(containsString(INVALID_ACCOUNT_MSG + ACCOUNT_CODE.toString())));
  }

  @Test
  void shouldThrowInvalidBalanceException() throws Exception {

    when(accountService.getAccount(ACCOUNT_CODE)).thenThrow(
        new InvalidBalanceException(ACCOUNT_CODE.toString()));

    mockMvc.perform(get("/accounts/{id}", ACCOUNT_CODE))
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(containsString(INVALID_BALANCE_MSG + ACCOUNT_CODE.toString())));
  }

  @Test
  void shouldThrowInvalidRequestException() throws Exception {

    mockMvc.perform(get("/accounts/{id}", EUR))
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(containsString(INVALID_TYPE_MSG)));
  }

  private AccountEntity getAccount() {
    return new AccountEntity(ACCOUNT_CODE, BigDecimal.TEN, EUR, LocalDateTime.now());
  }

  private List<StatementEntity> getStatementsMiniList() {
    List<StatementEntity> statementEntityList = new LinkedList<>();
    statementEntityList.add(
        new StatementEntity(ACCOUNT_CODE, 222L, BigDecimal.TEN, EUR, LocalDateTime.now()));
    statementEntityList.add(
        new StatementEntity(ACCOUNT_CODE, 333L, BigDecimal.ONE, EUR, LocalDateTime.now()));
    return statementEntityList;
  }
}