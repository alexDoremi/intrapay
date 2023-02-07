package com.intrapayment.controller;

import com.intrapayment.controller.dto.StatementRequestDTO;
import com.intrapayment.controller.dto.StatementResponseDTO;
import com.intrapayment.service.StatementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.intrapayment.util.mapper.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/statements")
public class StatementController {

  @Autowired
  StatementService statementService;

  /**
   *
   * requestTransfer
   *
   * @param requestStatement json body with credit, debit and amount values
   *
   * @return StatementResponseDTO response with statment DTO
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping()
  public @ResponseBody
  StatementResponseDTO requestTransfer(@Valid @RequestBody StatementRequestDTO requestStatement)
      throws InterruptedException {
    log.info("LOG INFO: /statements/ - post request - new transfer: {}",
        requestStatement.toString());

    return StatementMapper.getStatementResponseDTO(
        statementService.doTransfer(
            requestStatement.getCreditId(),
            requestStatement.getDebitId(),
            requestStatement.getAmount()));
  }
}
