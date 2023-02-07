package com.intrapayment.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrapayment.controller.dto.StatementRequestDTO;
import com.intrapayment.controller.dto.StatementResponseDTO;
import com.intrapayment.repository.AccountRepository;
import com.intrapayment.repository.entity.AccountEntity;
import com.intrapayment.repository.entity.StatementEntity;
import com.intrapayment.service.AccountServiceImpl;
import com.intrapayment.service.StatementServiceImpl;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.intrapayment.util.mapper.*;


@SpringBootTest
@AutoConfigureMockMvc
class StatementControllerTest {

  private final String EUR = "EUR";
  private final Long ACCOUNT_CODE_111 = 111L;
  private final Long ACCOUNT_CODE_222 = 222L;
  private final BigDecimal AMOUNT = BigDecimal.TEN;
  private final String INVALID_AMOUNT_MSG = "Invalid minimum value";
  private final String INVALID_ARG_MSG = "Invalid request parameters";


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  StatementServiceImpl statementService;


  @Test
  void shouldReturnStatement() throws Exception {

    StatementRequestDTO statementRequestDTO = StatementRequestDTO
        .builder()
        .creditId(ACCOUNT_CODE_111)
        .debitId(ACCOUNT_CODE_222)
        .amount(AMOUNT)
        .build();

    StatementEntity statementEntity = new StatementEntity(ACCOUNT_CODE_111, ACCOUNT_CODE_222,
        AMOUNT, EUR,
        LocalDateTime.now());

    when(statementService.doTransfer(ACCOUNT_CODE_111, ACCOUNT_CODE_222, AMOUNT)).thenReturn(statementEntity);

    MvcResult result = mockMvc.perform(post("/statements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(statementRequestDTO)))
        .andExpect(status().isCreated())
        .andReturn();

    // then expect statement confirmed
    StatementResponseDTO confirmed = StatementMapper.getStatementResponseDTO(statementEntity);
    StatementResponseDTO statementResponseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), StatementResponseDTO.class);
    assertEquals(confirmed, statementResponseDTO);
  }

  @Test
  void shouldRejectRequestDueToAmountEqualsZero() throws Exception {

    StatementRequestDTO statementRequestDTO = StatementRequestDTO
        .builder()
        .creditId(ACCOUNT_CODE_111)
        .debitId(ACCOUNT_CODE_222)
        .amount(BigDecimal.ZERO)
        .build();

    mockMvc.perform(post("/statements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(statementRequestDTO)))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(containsString(INVALID_AMOUNT_MSG)));
  }

  @Test
  void shouldRejectRequestDueToIncorrectType() throws Exception {

    mockMvc.perform(post("/statements")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                "  \"creditId\": AAA,\n" +
                "  \"debitId\": 222,\n" +
                "  \"amount\": \"1\",\n"+
                "}"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(containsString(INVALID_ARG_MSG)));
  }
}