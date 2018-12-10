package com.grepiu.www.process.sample.util.socket.module.pool;

import java.util.Optional;


public interface ValidationResult {

  static ValidationResult valid() {
    return ValidationSupport.valid();
  }

  static ValidationResult invalid(String reason) {
    return new SejongInvalid(reason);
  }

  boolean isValid();

  Optional<String> getReason();
}



