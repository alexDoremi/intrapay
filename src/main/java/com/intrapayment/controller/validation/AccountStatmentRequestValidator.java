package com.intrapayment.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountStatmentRequestValidatorImpl.class)
public @interface AccountStatmentRequestValidator {

  String message() default "Invalid operation: credit account can't be same as debit account";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
