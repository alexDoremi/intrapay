package com.intrapayment.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ErrorDTO {

  private HttpStatus httpStatus;
  private int errorCode;
  private LocalDateTime timestamp;
  private List<String> description;
}