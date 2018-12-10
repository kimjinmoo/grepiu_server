package com.grepiu.www.process.sample.util.socket.module.pool;

import java.util.Optional;

public class ValidationSupport {

  private static final ValidationResult valid = new ValidationResult(){
    public boolean isValid(){ return true; }
    public Optional<String> getReason(){ return Optional.empty(); }
  };

  static ValidationResult valid(){
    return valid;
  }
}