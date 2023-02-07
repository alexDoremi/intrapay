package com.intrapayment.controller;

import com.intrapayment.controller.dto.ErrorDTO;
import com.intrapayment.exception.InvalidAccountIdException;
import com.intrapayment.exception.InvalidBalanceException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler
  @ResponseBody
  ErrorDTO handle(InvalidAccountIdException e) {
    log.error("LOG ERROR: account {} not exists", e.getMessage());
    return ErrorDTO.builder()
        .errorCode(HttpStatus.BAD_REQUEST.value())
        .httpStatus(HttpStatus.BAD_REQUEST)
        .description(Collections.singletonList("Invalid Account Number: " + e.getMessage()))
        .timestamp(LocalDateTime.now()).build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler
  @ResponseBody
  ErrorDTO handle(InvalidBalanceException e) {
    log.error("LOG ERROR: invalid balance amount: {}" + e.getMessage());
    return ErrorDTO.builder()
        .errorCode(HttpStatus.BAD_REQUEST.value())
        .httpStatus(HttpStatus.BAD_REQUEST)
        .description(Collections.singletonList("Invalid Balance Amount: " + e.getMessage()))
        .timestamp(LocalDateTime.now()).build();
  }


  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorDTO handle(ConstraintViolationException e) {
    log.error("LOG ERROR: invalid request: {}" + e.getMessage());
    return ErrorDTO.builder()
        .errorCode(HttpStatus.BAD_REQUEST.value())
        .httpStatus(HttpStatus.BAD_REQUEST)
        .description(Collections.singletonList("Invalid request: " + e.getMessage()))
        .timestamp(LocalDateTime.now()).build();
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorDTO handle(MethodArgumentTypeMismatchException e) {
    log.error("LOG ERROR: invalid request: {}" + e.getMessage());
    return ErrorDTO.builder()
        .errorCode(HttpStatus.BAD_REQUEST.value())
        .httpStatus(HttpStatus.BAD_REQUEST)
        .description(Collections.singletonList("Invalid request parameter for: " + e.getName()))
        .timestamp(LocalDateTime.now()).build();
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorDTO handle(HttpMessageNotReadableException e) {
    log.error("LOG ERROR: invalid request: {}" + e.getMessage());
    return ErrorDTO.builder()
        .errorCode(HttpStatus.BAD_REQUEST.value())
        .httpStatus(HttpStatus.BAD_REQUEST)
        .description(Collections.singletonList("Invalid request parameters"))
        .timestamp(LocalDateTime.now()).build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ErrorDTO handle(MethodArgumentNotValidException e) {
    log.error("LOG ERROR: invalid request: {}" + e.getMessage());
    return ErrorDTO.builder()
        .errorCode(HttpStatus.BAD_REQUEST.value())
        .httpStatus(HttpStatus.BAD_REQUEST)
        .description(e.getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList()))
        .timestamp(LocalDateTime.now()).build();
  }
}
